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
import com.example.jungwh.fragmenttest.business.logic.SpendRegisterService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.ShowProgressHelper;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jungwh on 2016-10-30.
 */

public class SpendDetailActivity extends AppCompatActivity {
    private SpendRegisterTask authTask = null;
    private SpendCateRetrieveTask authRetrieveTask = null;
    private View viewProgress, viewForm;
    // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
    private DecimalFormat mDecimalFormat = new DecimalFormat("###,###.####");
    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    private String mResult ="";
    private String userId;
    private DatePickerDialog mDatePickerDialog;
    EditText etSpendDate, etSpendPrice, etSpendContents, etSpendMemo;
    Spinner spCategoryContents, spMethodContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spend_detail);

        // 사용자 아이디
        userId = getIntent().getExtras().getString("USER_ID");

        setTitle("지출내역 입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewForm = findViewById(R.id.spend_detail_form);
        viewProgress = findViewById(R.id.spend_detail_layout);

        // 일자
        etSpendDate = (EditText) findViewById(R.id.spend_date);

        // 일자 포멧팅
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etSpendDate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        // 일자 이벤트
        etSpendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });

        // 가격
        etSpendPrice = (EditText) findViewById(R.id.spend_price);
        etSpendPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(mResult) && s.length() > 3){
                    mResult = mDecimalFormat.format(Long.parseLong(s.toString().replaceAll(",", "")));
                    etSpendPrice.setText(mResult);
                    etSpendPrice.setSelection(mResult.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // 내역
        etSpendContents = (EditText) findViewById(R.id.spend_contents);

        // 내용
        etSpendMemo = (EditText) findViewById(R.id.spend_memo);

        //카테고리
        spCategoryContents = (Spinner) findViewById(R.id.spend_category_contents);
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

        //지출수단
        spMethodContents = (Spinner) findViewById(R.id.spend_method_contents);
        spMethodContents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        (findViewById(R.id.spend_okay)).setOnClickListener(mOnClickListener);
        (findViewById(R.id.spend_cancel)).setOnClickListener(mOnClickListener);
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.spend_okay :
                    saveData();
                    break;
                case R.id.spend_cancel :
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

        authRetrieveTask = new SpendCateRetrieveTask(getApplicationContext() , userId);
        authRetrieveTask.execute((Void) null);
    }

    public void saveData(){
        if (authTask != null) {
            return;
        }

        String spendDate = etSpendDate.getText().toString().replace("-","");
        if (spendDate == null || spendDate.equals("")){
            Toast.makeText(getApplicationContext(), "일자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String spendPrice = etSpendPrice.getText().toString().replace(",","");
        if (spendPrice == null || spendPrice.equals("")){
            Toast.makeText(getApplicationContext(), "가격을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String spendContents = etSpendContents.getText().toString();
        String spendMemo = etSpendMemo.getText().toString();
        String spendCategory = spCategoryContents.getSelectedItem().toString();
        if (spendCategory == null || spendCategory.equals("")){
            Toast.makeText(getApplicationContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String spendMethodCategory = spMethodContents.getSelectedItem().toString();
        if (spendMethodCategory == null || spendMethodCategory.equals("")){
            Toast.makeText(getApplicationContext(), "지출수단을 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        authTask = new SpendRegisterTask(getApplicationContext() , spendDate, spendPrice, spendContents, spendMemo, spendCategory, spendMethodCategory);
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

    private class SpendCateRetrieveTask
            extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String userId;
        CateRetrieveService cateRetrieveService = new CateRetrieveService();
        CateRetrieveData cateRetrieveData = new CateRetrieveData();
        private String retrieveErrMsg;

        SpendCateRetrieveTask(Context context, String userId) {
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
                cateRetrieveData = cateRetrieveService.retrieve(userId, "002");
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
                alertDialogWrapper.showAlertDialog(SpendDetailActivity.this, getString(R.string.help), retrieveErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authRetrieveTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }

    private class SpendRegisterTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String spendDate;
        private final String spendPrice;
        private final String spendContents;
        private final String spendMemo;
        private final String spendCategory;
        private final String spendMethodContents;
        SpendRegisterService spendRegisterService = new SpendRegisterService();
        private String registerErrMsg;

        SpendRegisterTask(Context context, String spendDate, String spendPrice, String spendContents, String spendMemo, String spendCategory, String spendMethodContents) {
            registerErrMsg = "지출내역 등록에 실패하셨습니다.";
            this.context = context;
            this.spendDate = spendDate;
            this.spendPrice = spendPrice;
            this.spendContents = spendContents;
            this.spendMemo = spendMemo;
            this.spendCategory = spendCategory;
            this.spendMethodContents = spendMethodContents;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return spendRegisterService.register(spendDate, spendPrice, spendContents, spendMemo, spendCategory, spendMethodContents, userId);
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
                etSpendDate.setText("");
                etSpendPrice.setText("");
                etSpendContents.setText("");
                etSpendMemo.setText("");

                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(SpendDetailActivity.this, getString(R.string.help), "지출내역 등록에 성공하셨습니다.", AlertDialogWrapper.DialogButton.OK);
            } else {
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(SpendDetailActivity.this, getString(R.string.help), registerErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }
}