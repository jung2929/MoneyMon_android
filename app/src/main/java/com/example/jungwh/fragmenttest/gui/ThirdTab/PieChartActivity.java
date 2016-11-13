package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.PieChartData;
import com.example.jungwh.fragmenttest.business.logic.PieChartService;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * Created by 김한빛 on 2016-10-19.
 */

public class PieChartActivity extends Fragment  {
    private GraphInfoRetrieveTask authTask = null;
    private ProgressDialog progressDialog;
    private PieChart pieChart;
    private Spinner spMonths;
    private Bundle userInfoBundle;
    private String userId;
    private ListView mLvList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);

        userInfoBundle = getArguments();
        userId = userInfoBundle.getString("USER_ID");

        pieChart = (PieChart) view.findViewById(R.id.main_pie_chart);
        mLvList = (ListView) view.findViewById(R.id.pie_chart_list);

        final TextView tv = (TextView) view.findViewById(R.id.graph_title_category);

        spMonths = (Spinner) view.findViewById(R.id.spinner_category);
        spMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(parent.getItemAtPosition(position) + " 지출 패턴 분석"  );
                barChartRetrieve();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private void chartDataAdapter(PieChartData pieChartData, Context context){
        String[] xValues = pieChartData.getxValues();
        Integer[] yValues = pieChartData.getyValues();

        pieChart.setDescription("");
        pieChart.setRotationEnabled(true);
        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e==null)
                    return;

            }

            @Override
            public void onNothingSelected() {

            }
        });

        ArrayList<Entry> yVals1 = new ArrayList<>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<>();

        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        dataSet.setColors(context.getResources().getIntArray(R.array.colorNumberList));
        PieData data = new PieData(xVals, dataSet);

        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        pieChart.animateXY(1400, 1400);
        pieChart.setDrawSliceText(true);

        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);

        mLvList.setAdapter(new PieChartBaseAdapter(context, pieChartData));
    }

    private class PieChartBaseAdapter extends BaseAdapter {
        private Context context;
        private PieChartData pieChartData;

        public PieChartBaseAdapter(Context context, PieChartData pieChartData) {
            this.context = context;
            this.pieChartData = pieChartData;
        }

        @Override
        public int getCount() {
            return pieChartData.getxValues().length;
        }

        @Override
        public Object getItem(int position) {
            return pieChartData.getxValues()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_row, null);
            }

            // 카테고리명
            TextView tvInputDate = (TextView) convertView.findViewById(R.id.groupInputDateValue);
            tvInputDate.setText(pieChartData.getxValues()[position]);

            // 가격
            Integer sumInputPc = pieChartData.getyValues()[position];
            TextView tvTotalInputPc = (TextView) convertView.findViewById(R.id.groupTotalInputPcValue);
            DecimalFormat decimalFormat = new DecimalFormat("#,###");
            String formattedTotalInputPc = decimalFormat.format(sumInputPc);
            tvTotalInputPc.setText(String.valueOf(formattedTotalInputPc));

            return convertView;
        }
    }

    private void barChartRetrieve(){
        if (authTask != null) {
            return;
        }

        Integer selectedMonth = (spMonths.getSelectedItemPosition()) + 1;

        authTask = new GraphInfoRetrieveTask(getActivity(), userId, selectedMonth);
        authTask.execute((Void) null);
    }

    private class GraphInfoRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String userId;
        private final Integer month;
        private PieChartService pieChartService = new PieChartService();
        private PieChartData pieChartData = new PieChartData();
        private String retrieveErrMsg = "데이터를 가져오지 못했습니다.";

        GraphInfoRetrieveTask(Context context,String userId, Integer month) {
            this.context = context;
            this.userId = userId;
            this.month = month;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, getString(R.string.barChartTitle), "데이터 조회중입니다...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                pieChartData = pieChartService.retrieve(userId, month);
                return pieChartData.isSuccess();
            } catch (JSONException | IOException e) {
                retrieveErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            progressDialog.dismiss();

            if (!success) {
                Toast.makeText(context, retrieveErrMsg, Toast.LENGTH_SHORT).show();
            }

            chartDataAdapter(pieChartData, context);
        }

        @Override
        protected void onCancelled() {
            authTask = null;
        }
    }
}
