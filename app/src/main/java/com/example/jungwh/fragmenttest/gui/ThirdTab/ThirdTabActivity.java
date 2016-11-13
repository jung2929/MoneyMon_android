package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.FragmentManager;
import android.widget.Button;

import com.example.jungwh.fragmenttest.R;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class ThirdTabActivity extends Fragment {
    private Bundle userInfoBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        userInfoBundle = getArguments();

        View view = inflater.inflate(R.layout.fragment_third_tab, container,false );

        //메인 두번째 프래그먼트가 켜질때 오른쪽의 프래그먼트를 준비하는과정에서 Task가 돌아가므로 막아둠.
        /*Fragment fr =  new BarChartActivity();
        fr.setArguments(userInfoBundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();*/

        Button btn0 = (Button) view.findViewById(R.id.barChartButton);
        Button btn1 = (Button) view.findViewById(R.id.PieChartButton);
        Button btn2 = (Button) view.findViewById(R.id.btn2);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(v);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(v);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(v);
            }
        });
        return view;
    }


    public void selectFrag(View view){
        Fragment fr = null;
        FragmentManager fm = getFragmentManager();
        switch (view.getId()){
            case R.id.barChartButton: {
                fr = new BarChartActivity();
                break;
            }
            case R.id.PieChartButton: {
                fr = new PieChartActivity();
                break;
            }
            case R.id.btn2: {
                fr = new LineChartActivity();
                break;
            }
        }
        fr.setArguments(userInfoBundle);
        /*
        if(view == view.findViewById(R.id.btn2)){
            fr = new LineChartActivity();
        }else if(view == view.findViewById(R.id.barChartButton)){
            fr = new BarChartActivity();
        }else {
            fr = new PieChartActivity();
        }
        */

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //이전 프래그먼트를 스택에 담아놓아주는 함수
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public android.view.animation.Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

}
