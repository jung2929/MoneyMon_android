package com.example.jungwh.fragmenttest.gui.secondTab;

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
public class SecondTabActivity extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        return layoutInflater.inflate(R.layout.fragment_second_tab, viewGroup, false);
    }
}
