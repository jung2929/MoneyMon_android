package com.example.jungwh.fragmenttest.gui.firstTab;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class FirstTabActivity extends Fragment {
    TextView tv_total_price;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View FirstTabView = layoutInflater.inflate(R.layout.fragment_first_tab, viewGroup, false);

        FirstTabView.findViewById(R.id.income).setOnClickListener(mOnClickLister);
        return FirstTabView;
    }

    Button.OnClickListener mOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.income:
                    startActivityForResult(new Intent(getActivity(), IncomeDetailActivity.class),0);
                    break;
            }
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK:
                Toast.makeText(getContext(), "ComeBack Success", Toast.LENGTH_SHORT);
                break;
            default:
                Toast.makeText(getContext(), "ComeBack Failure", Toast.LENGTH_SHORT);
                break;
        }
    }
}
