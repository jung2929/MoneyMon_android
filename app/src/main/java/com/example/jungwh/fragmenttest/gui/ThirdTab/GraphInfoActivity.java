package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.logic.Graph1Service;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ArrayList;

import static android.R.attr.data;
import static android.hardware.camera2.params.BlackLevelPattern.COUNT;


/**
 * Created by 김한빛 on 2016-10-19.
 */

public class GraphInfoActivity extends Fragment{

    private Bundle userInfoBundle;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View AssetTabView = inflater.inflate(R.layout.fragment_third_graph0, container,false);
        userInfoBundle = getArguments();

        Spinner s0= (Spinner) AssetTabView.findViewById(R.id.spinner0);
              Spinner s1= (Spinner) AssetTabView.findViewById(R.id.spinner1);

        Animation animFadein;
        TextView anim_tv0 = (TextView) AssetTabView.findViewById(R.id.graph0_tv0);
        animFadein = AnimationUtils.loadAnimation( this.getContext() ,R.anim.fade_in);
        anim_tv0.startAnimation(animFadein);

        final TextView tv0 = (TextView) AssetTabView.findViewById(R.id.graph1_tv0);
        final TextView tv1 = (TextView) AssetTabView.findViewById(R.id.graph1_tv1);

        s0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv0.setText(parent.getItemAtPosition(position) + " "  );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tv1.setText(parent.getItemAtPosition(position) + " 지출 패턴 분석"  );

                /* 데이터 가져오는 ... 코드...?ㅠ
    JSONObject jobj = new JSONObject(response.getRawResponse());

    JSONArray data = jobj.getString("data");
for (int i = 0; i< data.length(); i++){
        JSONObject indi = data.getJSONObject(i).getString("email");
        }
        */
                /*  그래프에 데이터 가져오는.. 코드..?

                Graph1Service graph1Service = new Graph1Service();
                try{
                    graph1Service.graph1((String) parent.getItemAtPosition(position), userInfoBundle.getString("USER_ID"));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        BarChart barChart = (BarChart) AssetTabView.findViewById(R.id.chart0);

        // HorizontalBarChart barChart= (HorizontalBarChart) findViewById(R.id.chart);

       // String date = date.getString("date");
       // Integer count = location.getInt("Count");

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(128320, 0));
        entries.add(new BarEntry(153250, 1));

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2015. Nov.");
        labels.add("2106. Nov.");


        BarDataSet dataset = new BarDataSet(entries, "금액");


        BarData data = new BarData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setBarSpacePercent(((float) (COUNT - data.getXValCount()) / (float) COUNT) * 100f);
        barChart.setData(data);
        barChart.animateY(1000);

        return AssetTabView;

    }

}
