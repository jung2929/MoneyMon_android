package com.example.jungwh.fragmenttest.gui.ThirdTab;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.FragmentManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;

import static android.R.style.Animation;
import static android.content.ContentValues.TAG;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class ThirdTabActivity extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third_tab, container,false );

        Fragment fr =  new GraphInfoActivity();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();

        Button btn0 = (Button) view.findViewById(R.id.btn0);
        Button btn1 = (Button) view.findViewById(R.id.btn1);
        Button btn2 = (Button) view.findViewById(R.id.btn2);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(v);
                Log.i("STATE0","Log in SERVER BTN0");
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(v);
                Log.i("STATE1","Log in SERVER BTN1");
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(v);
                Log.i("STATE2","Log in SERVER BTN2");
            }
        });
        return view;
    }


    public void selectFrag(View view){
        Fragment fr;
        if(view == view.findViewById(R.id.btn2)){
            fr = new GraphInfoActivity2();
        }else if(view == view.findViewById(R.id.btn0)){
            fr = new GraphInfoActivity();
        }else {
            fr = new GraphInfoActivity1();
        }
        FragmentManager fm = getFragmentManager();
           /*
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getFragmentManager().getBackStackEntryCount() == 0) finish(this);
            }
        });*/

        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        // fragmentTransaction.replace(R.id.contianer_replace, fr);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
    public android.view.animation.Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if(!enter){
            //leaving fragment
            Log.d(TAG,"leaving fragment");
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

}
