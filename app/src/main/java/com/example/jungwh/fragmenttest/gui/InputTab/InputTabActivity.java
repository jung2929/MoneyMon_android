package com.example.jungwh.fragmenttest.gui.InputTab;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.InputData;
import com.example.jungwh.fragmenttest.business.logic.InputService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.ShowProgressHelper;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;

import static android.app.Activity.RESULT_OK;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class InputTabActivity extends Fragment {
    private InputTask authTask = null;
    private View viewProgress, viewInputForm;
    private InputService inputService = new InputService();
    private InputData inputData = new InputData();
    private TextView tvTotalPrice;
    private Bundle userInfoBundle;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View fragmentInputView = layoutInflater.inflate(R.layout.fragment_input_tab, viewGroup, false);

        userInfoBundle = getArguments();

        viewInputForm = fragmentInputView.findViewById(R.id.fragment_input_form);
        viewProgress = fragmentInputView.findViewById(R.id.fragment_input_layout);

        fragmentInputView.findViewById(R.id.income).setOnClickListener(mOnClickLister);
        fragmentInputView.findViewById(R.id.spend).setOnClickListener(mOnClickLister);

        tvTotalPrice = (TextView) fragmentInputView.findViewById(R.id.total_price);
        retrieve(userInfoBundle.getString("USER_ID"));

        return fragmentInputView;
    }

    Button.OnClickListener mOnClickLister = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.income:
                    Intent intent = new Intent(getActivity(), IncomeDetailActivity.class);
                    intent.putExtra("USER_ID", userInfoBundle.getString("USER_ID"));
                    startActivityForResult(intent,0);
                    break;
                case R.id.spend:
                    startActivityForResult(new Intent(getActivity(), SpendDetailActivity.class),0);
                    break;
            }
        }
    };

    private void retrieve(String userId){
        if (authTask != null) {
            return;
        }

        authTask = new InputTask(getActivity() , userId);
        authTask.execute((Void) null);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        retrieve(userInfoBundle.getString("USER_ID"));
        /*
        switch (resultCode) {
            case RESULT_OK:
                Toast.makeText(getActivity(), "ComeBack Success", Toast.LENGTH_SHORT);
                break;
            default:
                Toast.makeText(getContext(), "ComeBack Failure", Toast.LENGTH_SHORT);
                break;
        }
        */
    }

    private class InputTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String userId;
        private String inputErrMsg;

        InputTask(Context context, String userId) {
            inputErrMsg = "자금을 불러오는데 실패했습니다.";
            this.context = context;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewInputForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                inputData = inputService.retrieve(userId);
                return true;
            } catch (JSONException | IOException e) {
                inputErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewInputForm);

            if (success) {
                DecimalFormat decimalFormat = new DecimalFormat("#,###");
                String totalPrice = inputData.getTotalPrice();
                if (totalPrice.contains("-")) {
                    String formattedTotalPriceValue = decimalFormat.format(Integer.parseInt(totalPrice.replace("-","")));
                    tvTotalPrice.setText("-" + String.valueOf(formattedTotalPriceValue));
                }else{
                    String formattedTotalPriceValue = decimalFormat.format(Integer.parseInt(inputData.getTotalPrice()));
                    tvTotalPrice.setText(String.valueOf(formattedTotalPriceValue));
                }

            } else {
                tvTotalPrice.setText(String.valueOf(0));

                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(getActivity(), getString(R.string.help), inputErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewInputForm);
        }
    }
}