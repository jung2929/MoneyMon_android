package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.data.Entry;

import org.json.JSONException;

import java.io.IOException;

import java.util.ArrayList;


/**
 * Created by 김한빛 on 2016-10-19.
 */

public class GraphInfoActivity1 extends Fragment  {

    PieChart mChart;
    private int[] yValues = {45 ,28, 18,9};//식비에 해당하는 카테고리 비율?..
    private String[] xValues = {"식비", "교통비", "통신비","문화생활비"};
    public static  final int[] MY_COLORS = {
            Color.rgb(255,102,102), Color.rgb(255,204,51), Color.rgb(255,204,153),
            Color.rgb(255,153,102), Color.rgb(255,102,153)
    };

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_graph1, container, false);

        Spinner s= (Spinner) view.findViewById(R.id.spinner1);
        final TextView tv = (TextView) view.findViewById(R.id.graph1_tv1);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv.setText(parent.getItemAtPosition(position) + " 지출 패턴 분석"  );
           /*
                //현재 로그인되어 있는 id
                String userId = "";

                //선택한 지출패턴에 해당되는 데이터 가져오기????
                Graph2Service graph2Service = new Graph2Service();
                try {
                    graph2Service.graph2(String.valueOf(parent.getItemAtPosition(position)), userId);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //탭 누르면 강제종료..이 부분이 문제.....
*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mChart = (PieChart) view.findViewById(R.id.chart1);
        mChart.setDescription("");
        mChart.setRotationEnabled(true);
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                if (e==null)
                    return;

            }

            @Override
            public void onNothingSelected() {

            }
        });

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yValues.length; i++)
            yVals1.add(new Entry(yValues[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xValues.length; i++)
            xVals.add(xValues[i]);

        // create pieDataSet
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // adding colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        // Added My Own colors
        for (int c : MY_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        //  create pie data object and set xValues and yValues and set it to the pieChart
        PieData data = new PieData(xVals, dataSet);
         //  data.setValueFormatter(new DefaultValueFormatter());
        //   data.setValueFormatter(new PercentFormatter());
      //  data.setDrawValuses(false);

         // data.setValueFormatter(new MyValueFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);

        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // refresh/update pie chart
        mChart.invalidate();

        // animate piechart
        mChart.animateXY(1400, 1400);
        mChart.setDrawSliceText(true);

        // Legends to show on bottom of the graph
        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7);
        l.setYEntrySpace(5);
        return view;
    }

}
