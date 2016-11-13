package com.example.jungwh.fragmenttest.gui.InputTab;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.CateRetrieveData;
import com.example.jungwh.fragmenttest.business.logic.CateRetrieveService;
import com.example.jungwh.fragmenttest.business.logic.IncomeRegisterService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.ShowProgressHelper;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jungwh on 2016-09-26.
 */

public class IncomeDetailActivity extends AppCompatActivity {
    private IncomeRegisterTask authTask = null;
    private IncomeCateRetrieveTask authRetrieveTask = null;
    private View viewProgress, viewForm;
    // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
    private DecimalFormat mDecimalFormat = new DecimalFormat("###,###.####");
    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    private String mResult ="";
    private String userId;
    private DatePickerDialog mDatePickerDialog;
    EditText etIncomeDate, etIncomePrice, etIncomeContents, etIncomeMemo;
    Spinner spCategoryContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_detail);

        // 사용자 아이디
        userId = getIntent().getExtras().getString("USER_ID");

        setTitle("수입내역 입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewForm = findViewById(R.id.income_detail_form);
        viewProgress = findViewById(R.id.income_detail_layout);

        // 일자
        etIncomeDate = (EditText) findViewById(R.id.income_date);

        // 일자 포멧팅
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etIncomeDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        // 일자 이벤트
        etIncomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });

        // 가격
        etIncomePrice = (EditText) findViewById(R.id.income_price);
        etIncomePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(mResult) && s.length() > 3){
                    mResult = mDecimalFormat.format(Long.parseLong(s.toString().replaceAll(",", "")));
                    etIncomePrice.setText(mResult);
                    etIncomePrice.setSelection(mResult.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 내역
        etIncomeContents = (EditText) findViewById(R.id.income_contents);

        // 내용
        etIncomeMemo = (EditText) findViewById(R.id.income_memo);

        // 카테고리
        spCategoryContents = (Spinner) findViewById(R.id.income_category_contents);
        spCategoryContents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        cateRetrieve();

        (findViewById(R.id.income_okay)).setOnClickListener(mOnClickListener);
        (findViewById(R.id.income_cancel)).setOnClickListener(mOnClickListener);
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.income_okay :
                    saveData();
                    break;
                case R.id.income_cancel :
                    setResult(RESULT_OK, null);
                    finish();
                    break;
            }
        }
    };

    public void cateRetrieve(){
        if (authRetrieveTask != null) {
            return;
        }

        authRetrieveTask = new IncomeCateRetrieveTask(getApplicationContext() , userId);
        authRetrieveTask.execute((Void) null);
    }

    public void saveData(){
        if (authTask != null) {
            return;
        }

        String incomeDate = etIncomeDate.getText().toString().replace("-","");
        if (incomeDate == null || incomeDate.equals("")){
            Toast.makeText(getApplicationContext(), "일자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String incomePrice = etIncomePrice.getText().toString().replace(",","");
        if (incomePrice == null || incomePrice.equals("")){
            Toast.makeText(getApplicationContext(), "가격을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String incomeContents = etIncomeContents.getText().toString();
        String incomeMemo = etIncomeMemo.getText().toString();
        String incomeCategory = spCategoryContents.getSelectedItem().toString();
        if (incomeCategory == null || incomeCategory.equals("")){
            Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        authTask = new IncomeRegisterTask(getApplicationContext() , incomeDate, incomePrice, incomeContents, incomeMemo, incomeCategory);
        authTask.execute((Void) null);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            setResult(RESULT_OK, null);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home){
            setResult(RESULT_OK, null);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class IncomeCateRetrieveTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String userId;
        CateRetrieveService cateRetrieveService = new CateRetrieveService();
        CateRetrieveData cateRetrieveData = new CateRetrieveData();
        private String retrieveErrMsg;

        IncomeCateRetrieveTask(Context context, String userId) {
            retrieveErrMsg = "카테고리부터 등록해주세요.";
            this.context = context;
            this.userId = userId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                cateRetrieveData = cateRetrieveService.retrieve(userId, "001");
                return cateRetrieveData.getCateList().size() > 0;
            } catch (JSONException | IOException e) {
                retrieveErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authRetrieveTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);

            if (success) {
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, cateRetrieveData.getCateList());
                spCategoryContents.setAdapter(arrayAdapter);
            } else {
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(IncomeDetailActivity.this, getString(R.string.help), retrieveErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authRetrieveTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }

    private class IncomeRegisterTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String incomeDate;
        private final String incomePrice;
        private final String incomeContents;
        private final String incomeMemo;
        private final String incomeCategory;
        IncomeRegisterService incomeRegisterService = new IncomeRegisterService();
        private String registerErrMsg;

        IncomeRegisterTask(Context context, String incomeDate, String incomePrice, String incomeContents, String incomeMemo, String incomeCategory) {
            registerErrMsg = "수입내역 등록에 실패하셨습니다.";
            this.context = context;
            this.incomeDate = incomeDate;
            this.incomePrice = incomePrice;
            this.incomeContents = incomeContents;
            this.incomeMemo = incomeMemo;
            this.incomeCategory = incomeCategory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return incomeRegisterService.register(incomeDate, incomePrice, incomeContents, incomeMemo, incomeCategory, userId);
            } catch (JSONException | IOException e) {
                registerErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);

            if (success) {
                etIncomeDate.setText("");
                etIncomePrice.setText("");
                etIncomeContents.setText("");
                etIncomeMemo.setText("");

                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(IncomeDetailActivity.this, getString(R.string.help), "수입내역 등록에 성공하셨습니다.", AlertDialogWrapper.DialogButton.OK);
            } else {
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(IncomeDetailActivity.this, getString(R.string.help), registerErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }
}
