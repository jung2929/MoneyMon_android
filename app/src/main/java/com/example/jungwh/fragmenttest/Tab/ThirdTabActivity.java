package com.example.jungwh.fragmenttest.Tab;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jungwh.fragmenttest.R;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class ThirdTabActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_third_tab, viewGroup, false);
    }
}
