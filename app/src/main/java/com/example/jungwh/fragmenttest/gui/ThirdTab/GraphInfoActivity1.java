package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jungwh.fragmenttest.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * Created by 김한빛 on 2016-10-19.
 */

public class GraphInfoActivity1 extends Fragment  {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third_graph1, container, false);

        CombinedChart mChart = (CombinedChart) view.findViewById(R.id.chart1);
        CombinedData data = new CombinedData(getXAxisValues());
        data.setData(barData());
        data.setData(lineData());
        mChart.animateXY(1000,1000);
        mChart.setData(data);

        return view;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Oct.");
        labels.add("Nov.");
        labels.add("Dec.");
        return labels;
    }

    public LineData lineData(){
        ArrayList<Entry> line = new ArrayList<>();
        line.add(new Entry(2f, 0));
        line.add(new Entry(4f, 1));
        line.add(new Entry(3f, 2));
        LineDataSet lineDataSet = new LineDataSet(line, "LineGraph");
        lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        lineDataSet.setDrawCubic(true);
        lineDataSet.setDrawFilled(false);

        LineData lineData = new LineData(getXAxisValues(),lineDataSet);
        return lineData;
    }
    // this method is used to create data for Bar graph<br />
    public BarData barData(){
        ArrayList<BarEntry> group1 = new ArrayList<>();
        group1.add(new BarEntry(4f, 0));
        group1.add(new BarEntry(8f, 1));
        group1.add(new BarEntry(6f, 2));
        BarDataSet barDataSet = new BarDataSet(group1, "BarGraph");
        //barDataSet.setColor(Color.rgb(0, 155, 0));
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(getXAxisValues(),barDataSet);
        return barData;
    }


}
