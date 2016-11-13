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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.LineChartData;
import com.example.jungwh.fragmenttest.business.logic.LineChartService;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 김한빛 on 2016-10-19.
 */

public class LineChartActivity extends Fragment {
    private GraphInfoRetrieveTask authTask = null;
    private ProgressDialog progressDialog;
    private LineChart lineChart;
    private TextView tvGraphInfo;
    private Bundle userInfoBundle;
    private String userId;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View AssetTabView = inflater.inflate(R.layout.fragment_line_chart, container,false);

        userInfoBundle = getArguments();
        userId = userInfoBundle.getString("USER_ID");

        lineChart = (LineChart) AssetTabView.findViewById(R.id.main_line_chart);

        Animation animFadeIn;
        tvGraphInfo = (TextView) AssetTabView.findViewById(R.id.graph_info);
        animFadeIn = AnimationUtils.loadAnimation( this.getContext(), R.anim.fade_in);
        tvGraphInfo.startAnimation(animFadeIn);

        lineChartRetrieve();

        return AssetTabView;
    }

    private void chartDataAdapter(LineChartData lineChartData){
        /*ArrayList<String> labels = new ArrayList<>();
        labels.add("Oct.");
        labels.add("Nov.");
        labels.add("Dec.");

        ArrayList<Entry> entries1 = new ArrayList<>();
        // 값 추가 하는 부분, 3개월의 자산 내용을 추가
        entries1.add(new Entry(44210, 0));
        entries1.add(new Entry(81300, 1));
        entries1.add(new Entry(60900, 2));

        ArrayList<Entry> entries2 = new ArrayList<>();
        // 값 추가 하는 부분, 3개월의 자산 내용을 추가
        entries2.add(new Entry(50000, 0));
        entries2.add(new Entry(60000, 1));
        entries2.add(new Entry(70000, 2));*/

        LineDataSet set1 = new LineDataSet(lineChartData.getsumPriceEntry(), "지출 금액");
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        set1.setDrawCubic(false);
        set1.setLineWidth(4f);
        set1.setDrawFilled(false);

        LineDataSet set2 = new LineDataSet(lineChartData.getbudgetEntry(), "예산 금액");
        set2.setColors(ColorTemplate.JOYFUL_COLORS);
        set2.setLineWidth(2f);
        set2.setDrawCubic(true);
        set2.setDrawFilled(false);

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        dataSets.add(set2);

        LineData data = new LineData(lineChartData.getDateYYYYMM(), dataSets);
        data.setValueTextColor(Color.BLUE);
        data.setValueTextSize(9f);

        lineChart.animateY(1000);

        lineChart.setData(data);
    }

    private void lineChartRetrieve(){
        if (authTask != null) {
            return;
        }

        authTask = new GraphInfoRetrieveTask(getActivity(), userId);
        authTask.execute((Void) null);
    }

    private class GraphInfoRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String userId;
        private LineChartService lineChartService = new LineChartService();
        private LineChartData lineChartData = new LineChartData();
        private String retrieveErrMsg;

        GraphInfoRetrieveTask(Context context,String userId) {
            this.context = context;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(context, getString(R.string.barChartTitle), "데이터 조회중입니다...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                lineChartData = lineChartService.retrieve(userId);
                return lineChartData.isSuccess();
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
                retrieveErrMsg = lineChartData.getGraphInfo();
                tvGraphInfo.setText(retrieveErrMsg);
                //Toast.makeText(context, retrieveErrMsg, Toast.LENGTH_SHORT).show();
            } else {
                tvGraphInfo.setText(lineChartData.getGraphInfo());
            }

            chartDataAdapter(lineChartData);
        }

        @Override
        protected void onCancelled() {
            authTask = null;
        }
    }
}