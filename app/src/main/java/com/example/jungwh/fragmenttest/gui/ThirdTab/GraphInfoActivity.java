package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class GraphInfoActivity extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View AssetTabView = inflater.inflate(R.layout.fragment_third_graph0, container,false);

        LineChart lineChart = (LineChart) AssetTabView.findViewById(R.id.chart0);
        ArrayList<Entry> entries = new ArrayList<>();
        // 값 추가 하는 부분, 3개월의 자산 내용을 추가
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Oct.");
        labels.add("Nov.");
        labels.add("Dec.");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(false);

        lineChart.setData(data);
        lineChart.animateY(1000);
        return AssetTabView;
    }

}
