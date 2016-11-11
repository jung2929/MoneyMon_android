package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.graphics.Color;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by 김한빛 on 2016-10-19.
 */

public class GraphInfoActivity2 extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View AssetTabView = inflater.inflate(R.layout.fragment_third_graph2, container,false);

        Animation animFadein;
        final TextView tv = (TextView) AssetTabView.findViewById(R.id.graph2_tv0);
        animFadein = AnimationUtils.loadAnimation( this.getContext() ,R.anim.fade_in);

        tv.startAnimation(animFadein);

        LineChart lineChart = (LineChart) AssetTabView.findViewById(R.id.chart2);

        ArrayList<String> labels = new ArrayList<String>();
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
        entries2.add(new Entry(70000, 2));

        LineDataSet set1, set2;

        //LineDataSet dataset1 = new LineDataSet(entries1, "예산 금액");

        set1 = new LineDataSet(entries1, "지출 금액");
        set1.setColors(ColorTemplate.COLORFUL_COLORS);
        set1.setDrawCubic(false);
        set1.setLineWidth(4f);
        set1.setDrawFilled(false);

        set2 = new LineDataSet(entries2, "예산 금액");
        set2.setColors(ColorTemplate.JOYFUL_COLORS);
        set2.setLineWidth(2f);
        set2.setDrawCubic(true);
        set2.setDrawFilled(false);

        //  LineData data1 = new LineData(labels, set1);
        //  LineData data2 = new LineData(labels, set2);

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set1); // add the datasets
        dataSets.add(set2);

        LineData data = new LineData(labels, dataSets);
        data.setValueTextColor(Color.BLUE);
        data.setValueTextSize(9f);

        lineChart.animateY(1000);

        lineChart.setData(data);

        return AssetTabView;
    }
}
      /*

        */