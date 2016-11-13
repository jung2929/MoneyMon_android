package com.example.jungwh.fragmenttest.gui.main;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.logic.change;
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
 * Created by inyou on 2016-11-12.
 */

public class MonthlyBudgetActivity extends AppCompatActivity {
    private BudgetRegisterTask authTask = null;
    private View viewProgress, viewForm;
    // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
    private DecimalFormat mDecimalFormat = new DecimalFormat("###,###.####");
    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    private String mResult ="";
    private String userId;
    private DatePickerDialog mDatePickerDialog;
    EditText etBudgetDate, etBudgetPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monthly_budget);

        // 사용자 아이디
        userId = getIntent().getExtras().getString("USER_ID");

        setTitle("예산 입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewForm = findViewById(R.id.monthly_budget_form);
        viewProgress = findViewById(R.id.monthly_budget_layout);

        // 일자
        etBudgetDate = (EditText) findViewById(R.id.budget_date);

        // 일자 포멧팅
        Calendar newCalendar = Calendar.getInstance();
        final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etBudgetDate.setText(dateFormatter.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),  newCalendar.get(Calendar.DAY_OF_MONTH));

        // 일자 이벤트
        etBudgetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickerDialog.show();
            }
        });

        // 가격
        etBudgetPrice = (EditText) findViewById(R.id.budget_price);
        etBudgetPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(mResult) && s.length() > 3) {
                    mResult = mDecimalFormat.format(Long.parseLong(s.toString().replaceAll(",", "")));
                    etBudgetPrice.setText(mResult);
                    etBudgetPrice.setSelection(mResult.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        (findViewById(R.id.budget_okay)).setOnClickListener(mOnClickListener);
        (findViewById(R.id.budget_cancel)).setOnClickListener(mOnClickListener);
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.budget_okay :
                    saveData();
                    break;
                case R.id.budget_cancel :
                    setResult(RESULT_OK, null);
                    finish();
                    break;
            }
        }
    };

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

    public void saveData(){
        if (authTask != null) {
            return;
        }

        String budgetDate = etBudgetDate.getText().toString().replace("-","");
        if (budgetDate == null || budgetDate.equals("")){
            Toast.makeText(getApplicationContext(), "일자를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        String budgetPrice = etBudgetPrice.getText().toString().replace(",","");
        if (budgetPrice == null || budgetPrice.equals("")){
            Toast.makeText(getApplicationContext(), "가격을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        authTask = new BudgetRegisterTask(getApplicationContext() , budgetDate.substring(0,6), budgetPrice);
        authTask.execute((Void) null);
    }

    private class BudgetRegisterTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String budgetDate;
        private final String budgetPrice;
        //private BudgetRegisterService budgetRegisterService = new BudgetRegisterService();
        private change budgetRegisterService = new change();
        private String registerErrMsg = "예산 등록에 실패하셨습니다.";

        BudgetRegisterTask(Context context, String budgetDate, String budgetPrice) {
            this.context = context;
            this.budgetDate = budgetDate;
            this.budgetPrice = budgetPrice;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return budgetRegisterService.register(budgetDate, budgetPrice, userId);
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
                etBudgetDate.setText("");
                etBudgetPrice.setText("");
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(MonthlyBudgetActivity.this, getString(R.string.help), "예산 등록에 성공하셨습니다.", AlertDialogWrapper.DialogButton.OK);
            } else {
                AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                alertDialogWrapper.showAlertDialog(MonthlyBudgetActivity.this, getString(R.string.help), registerErrMsg, AlertDialogWrapper.DialogButton.OK);
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }
}
