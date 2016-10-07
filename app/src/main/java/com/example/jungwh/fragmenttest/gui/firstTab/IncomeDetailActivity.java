package com.example.jungwh.fragmenttest.gui.firstTab;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jungwh.fragmenttest.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jungwh on 2016-09-26.
 */

public class IncomeDetailActivity extends AppCompatActivity {
    // 세자리로 끊어서 쉼표 보여주고, 소숫점 셋째짜리까지 보여준다.
    private DecimalFormat mDecimalFormat = new DecimalFormat("###,###.####");
    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    private String mResult ="";
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.income_detail);

        setTitle("수입내역 입력");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 일자
        final EditText etIncomeDate = (EditText) findViewById(R.id.income_date);

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
        final EditText editTextPrice = (EditText) findViewById(R.id.income_price);
        editTextPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(mResult)){
                    mResult = mDecimalFormat.format(Long.parseLong(s.toString().replaceAll(",", "")));
                    editTextPrice.setText(mResult);
                    editTextPrice.setSelection(mResult.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Spinner spCategoryContents = (Spinner) findViewById(R.id.income_category_contents);
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

    public void saveData(){

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
}
