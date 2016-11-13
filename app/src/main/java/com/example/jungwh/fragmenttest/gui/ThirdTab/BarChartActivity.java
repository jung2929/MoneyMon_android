package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.BarChartData;
import com.example.jungwh.fragmenttest.business.data.CateRetrieveData;
import com.example.jungwh.fragmenttest.business.logic.BarChartService;
import com.example.jungwh.fragmenttest.business.logic.CateRetrieveService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import static android.hardware.camera2.params.BlackLevelPattern.COUNT;


/**
 * Created by 김한빛 on 2016-10-19.
 */

public class BarChartActivity extends Fragment{
    private SpendCateRetrieveTask authRetrieveTask = null;
    private GraphInfoRetrieveTask authTask = null;
    private ProgressDialog progressDialog;
    private TextView tvGraphInfo;
    private BarChart barChart;
    private Bundle userInfoBundle;
    private String userId;
    private Spinner spMonths, spCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View AssetTabView = inflater.inflate(R.layout.fragment_bar_chart, container,false);

        userInfoBundle = getArguments();
        userId = userInfoBundle.getString("USER_ID");

        barChart = (BarChart) AssetTabView.findViewById(R.id.main_bar_chart);

        spMonths= (Spinner) AssetTabView.findViewById(R.id.spinner_months);
        spCategory= (Spinner) AssetTabView.findViewById(R.id.spinner_category);

        cateRetrieve();

        tvGraphInfo = (TextView) AssetTabView.findViewById(R.id.graph_info);
        Animation animFadeIn = AnimationUtils.loadAnimation( this.getContext() ,R.anim.fade_in);
        tvGraphInfo.startAnimation(animFadeIn);

        final TextView tvTitleMonth = (TextView) AssetTabView.findViewById(R.id.graph_title_month);
        final TextView tvTitleCategory = (TextView) AssetTabView.findViewById(R.id.graph_title_category);

        spMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitleMonth.setText(parent.getItemAtPosition(position) + " "  );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tvTitleCategory.setText(parent.getItemAtPosition(position) + " 지출 패턴 분석"  );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AssetTabView.findViewById(R.id.button_retrieve).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barChartRetrieve();
            }
        });

        return AssetTabView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    public void cateRetrieve(){
        if (authRetrieveTask != null) {
            return;
        }

        authRetrieveTask = new SpendCateRetrieveTask(getActivity() , userId);
        authRetrieveTask.execute((Void) null);
    }

    private void chartDataAdapter(BarChartData barChartData){
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(barChartData.getLastYearPrice(), 0));
        entries.add(new BarEntry(barChartData.getCurrentYearPrice(), 1));

        ArrayList<String> labels = new ArrayList<>();
        labels.add(barChartData.getLastYearMonth());
        labels.add(barChartData.getCurrentYearMonth());

        BarDataSet dataSet = new BarDataSet(entries, "금액");

        BarData data = new BarData(labels, dataSet);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setBarSpacePercent(((float) (COUNT - data.getXValCount()) / (float) COUNT) * 100f);
        barChart.setDescription("");
        barChart.setData(data);
        barChart.animateY(1000);
    }

    private void barChartRetrieve(){
        if (authTask != null) {
            return;
        }

        Integer selectedMonth = (spMonths.getSelectedItemPosition()) + 1;
        String selectedCategory = spCategory.getSelectedItem().toString();

        authTask = new GraphInfoRetrieveTask(getActivity(), userId, selectedMonth, selectedCategory);
        authTask.execute((Void) null);
    }

    public class SpendCateRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String userId;
        CateRetrieveService cateRetrieveService = new CateRetrieveService();
        CateRetrieveData cateRetrieveData = new CateRetrieveData();
        private String retrieveErrMsg;

        SpendCateRetrieveTask(Context context, String userId) {
            retrieveErrMsg = "카테고리부터 등록해주세요.";
            this.context = context;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                cateRetrieveData = cateRetrieveService.retrieve(userId, "002");
                return cateRetrieveData.getCateList().size() > 0;
            } catch (JSONException | IOException e) {
                retrieveErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authRetrieveTask = null;
            //ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);

            if (success) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, cateRetrieveData.getCateList());
                spCategory.setAdapter(arrayAdapter);
            } else {
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(context, getString(R.string.help), retrieveErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authRetrieveTask = null;
            //ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }

    private class GraphInfoRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String userId, category;
        private final Integer month;
        private BarChartService barChartService = new BarChartService();
        private BarChartData barChartData = new BarChartData();
        private String retrieveErrMsg;

        GraphInfoRetrieveTask(Context context,String userId, Integer month, String category) {
            this.context = context;
            this.userId = userId;
            this.month = month;
            this.category = category;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, getString(R.string.barChartTitle), "데이터 조회중입니다...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                barChartData = barChartService.retrieve(userId, month, category);
                return barChartData.isSuccess();
            } catch (JSONException | IOException e) {
                retrieveErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            progressDialog.dismiss();

            if (success) {
                retrieveErrMsg = barChartData.getGraphInfo();
                tvGraphInfo.setText(retrieveErrMsg);
                //Toast.makeText(context, retrieveErrMsg, Toast.LENGTH_SHORT).show();
            } else {
                tvGraphInfo.setText(barChartData.getGraphInfo());
            }

            chartDataAdapter(barChartData);
        }

        @Override
        protected void onCancelled() {
            authTask = null;
        }
    }
}
