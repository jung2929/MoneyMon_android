package com.example.jungwh.fragmenttest.gui.secondTab;

import android.annotation.SuppressLint;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveData;
import com.example.jungwh.fragmenttest.business.data.ListRetrieveDetailData;
import com.example.jungwh.fragmenttest.business.logic.ListRetrieveService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.Tuple;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * Created by jungwh on 2016-09-26.
 */

@SuppressLint("ValidFragment")
public class SecondTabActivity extends Fragment {
    private ListRetrieveTask authTask = null;
    private ProgressDialog progressDialog;
    private Tuple<ListRetrieveData, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>>> tupleResult;
    private ExpandableListView mListView;
    private TextView tvSummaryPriceValue;
    private Bundle userInfoBundle;
    private EditText etInputDataFrom, etInputDataTo;
    private DatePickerDialog datePickerDialogFrom, datePickerDialogTo;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View secondTabView = layoutInflater.inflate(R.layout.fragment_second_tab, viewGroup, false);

        userInfoBundle = getArguments();

        etInputDataFrom = (EditText) secondTabView.findViewById(R.id.searchBarDateFrom);
        etInputDataTo = (EditText) secondTabView.findViewById(R.id.searchBarDateTo);

        etInputDataFrom.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date()));
        etInputDataTo.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(new Date()));

        etInputDataFrom.setOnClickListener(mOnClickListener);
        etInputDataTo.setOnClickListener(mOnClickListener);

        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        datePickerDialogFrom = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etInputDataFrom.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialogTo = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etInputDataTo.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mListView = (ExpandableListView) secondTabView.findViewById(R.id.second_expandable_list_view);
        secondTabView.findViewById(R.id.retrieve).setOnClickListener(mOnClickListener);

        tvSummaryPriceValue = (TextView) secondTabView.findViewById(R.id.summaryPriceValue);

        return secondTabView;
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.retrieve :
                    asyncTaskStart();
                    break;
                case R.id.searchBarDateFrom :
                    datePickerDialogFrom.show();
                    break;
                case R.id.searchBarDateTo :
                    datePickerDialogTo.show();
                    break;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    private void asyncTaskStart() {
        if (authTask != null) {
            return;
        }

        //authTask = new ListRetrieveTask(getApplicationContext(), inputDateFrom, inputDateTo, userId);
        authTask = new ListRetrieveTask(getActivity(), etInputDataFrom.getText().toString().replace("-",""), etInputDataTo.getText().toString().replace("-",""), userInfoBundle.getString("USER_ID"));
        authTask.execute((Void) null);
    }

    private void retrieve(Integer sumPriceValue, LinkedHashMap<String, ArrayList<ListRetrieveDetailData>> mapResult){

        if (mapResult == null) return;

        ExpandableListAdapter expandableListAdapter = new BaseExpandableAdapter(getActivity(),
                mapResult);
        mListView.setAdapter(expandableListAdapter);


        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedSummaryPriceValue = decimalFormat.format(sumPriceValue);
        tvSummaryPriceValue.setText(String.valueOf(formattedSummaryPriceValue));
    }

    private class ListRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String inputDateFrom;
        private final String inputDateTo;
        private final String userId;
        private ListRetrieveService listRetrieveService = new ListRetrieveService();
        private String listRetrieveErrMsg;

        ListRetrieveTask(Context context, String inputDateFrom, String inputDateTo, String userId) {
            this.context = context;
            this.inputDateFrom = inputDateFrom;
            this.inputDateTo = inputDateTo;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(getActivity(), getString(R.string.second_section_title), "데이터 조회중입니다...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                tupleResult = listRetrieveService.listRetrieve(inputDateFrom, inputDateTo, userId);
                return tupleResult.getX().isThereData();
            } catch (JSONException | IOException e) {
                listRetrieveErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            progressDialog.dismiss();

            if (success) {
                retrieve(tupleResult.getX().getSumPriceValue(), tupleResult.getY());
            } else {
                listRetrieveErrMsg = tupleResult.getX().getErrMsg();
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(getActivity(), getString(R.string.help), listRetrieveErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
        }
    }
}
