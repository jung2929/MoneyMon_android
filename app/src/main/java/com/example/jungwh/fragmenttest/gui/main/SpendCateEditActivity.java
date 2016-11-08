package com.example.jungwh.fragmenttest.gui.main;

import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.CateRetrieveData;
import com.example.jungwh.fragmenttest.business.logic.CateRetrieveService;
import com.example.jungwh.fragmenttest.util.AlertDialogWrapper;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.ShowProgressHelper;

import org.json.JSONException;

/**
 * Created by jungwh on 2016-10-30.
 */

public class SpendCateEditActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener
{
    private SpendCateRetrieveTask authRetrieveTask;
    private SpendCateRegisterTask authRegisterTask;
    private SpendCateDeleteTask authDeleteTask;
    private View viewProgress, viewForm;
    private String userId;

    // 추가될 아이템 내용을 입력받는 EditText
    private EditText mEtInputText;

    // 아이템 추가 버튼
    private Button mBInputToList;

    // 리스트뷰
    private ListView mLvList;

    // 데이터 리스트
    private ArrayList<String> mAlData;

    // 리스트뷰에 사용되는 ArrayAdapter
    private ArrayAdapter<String> mAaString;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spend_cate_edit);

        userId = getIntent().getStringExtra("USER_ID");

        viewForm = findViewById(R.id.spend_cate_edit_form);
        viewProgress = findViewById(R.id.spend_cate_edit_layout);

        //////////////////////////////////////////////////////////////

        // 위젯 레퍼런스 시작
        mEtInputText = (EditText) findViewById(R.id.spend_et_text);
        mBInputToList = (Button) findViewById(R.id.spend_add_to_list);
        mLvList = (ListView) findViewById(R.id.spend_list);

        // 위젯 레퍼런스 끝
        ////////////////////////////////////////////////////////////


        // 아이템 추가 버튼에 클릭리스너를 등록한다.
        mBInputToList.setOnClickListener(this);

        // 리스트뷰에 아이템클릭리스너를 등록한다.
        mLvList.setOnItemClickListener(this);

        defaultData();
    }


    private void defaultData() {
        if (authRetrieveTask != null) {
            return;
        }

        authRetrieveTask = new SpendCateRetrieveTask(getApplicationContext() , userId);
        authRetrieveTask.execute((Void) null);
    }

    private void saveData(String data) {
        if (authRegisterTask != null) {
            return;
        }

        if (mAlData != null && mAlData.contains(data)){
            authRegisterTask= null;
            Toast.makeText(this, "이미 존재하는 카테고리입니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        authRegisterTask = new SpendCateRegisterTask(getApplicationContext() , userId, data);
        authRegisterTask.execute((Void) null);
    }

    private void deleteData(String data){
        if (authDeleteTask != null) {
            return;
        }

        authDeleteTask = new SpendCateDeleteTask(getApplicationContext() , userId, data);
        authDeleteTask.execute((Void) null);
    }

    public void onItemClick(AdapterView<?> parent, View v, final int position, long id) {
        final String data = mAlData.get(position);

        // 삭제 다이얼로그에 보여줄 메시지를 만든다.
        String message = data + " 카테고리를 삭제하시겠습니까?";

        DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                deleteData(data);
            }
        };


        // 삭제를 물어보는 다이얼로그를 생성한다.
        new AlertDialog.Builder(this)
                .setTitle("도움말")
                .setMessage(message)
                .setPositiveButton("삭제", deleteListener)
                .show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            // 리스트에 추가 버튼이 클릭되었을때의 처리
            case R.id.spend_add_to_list :
                if (mEtInputText.getText().length() == 0) {
                    // 데이터를 입력하라는 메시지 토스트를 출력한다.
                    Toast.makeText(this, "데이터를 입력하세요.", Toast.LENGTH_SHORT).show();
                } else {
                    // 입력할 데이터를 받아온다.
                    String data = mEtInputText.getText().toString();

                    saveData(data);
                }
                break;
        }
    }

    private class SpendCateRetrieveTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String userId;
        private CateRetrieveService cateRetrieveService = new CateRetrieveService();
        private CateRetrieveData cateRetrieveData = new CateRetrieveData();
        private String retrieveErrMsg;

        SpendCateRetrieveTask(Context context, String userId) {
            retrieveErrMsg = "카테고리를 등록해주세요.";
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
                mAlData = cateRetrieveData.getCateList();
                mAaString = new ArrayAdapter<>(context, R.layout.simple_list_row, R.id.simple_list_row_text, mAlData);

                // 어뎁터를 리스트뷰에 세팅한다.
                mLvList.setAdapter(mAaString);
            } else {
                //AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                //alertDialogWrapper.showAlertDialog(SpendCateEditActivity.this, getString(R.string.help), retrieveErrMsg, AlertDialogWrapper.DialogButton.OK);
                Toast.makeText(context, retrieveErrMsg, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            authRetrieveTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }

    private class SpendCateRegisterTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String userId, data;
        CateRetrieveService cateRetrieveService = new CateRetrieveService();
        private String registerErrMsg;

        SpendCateRegisterTask(Context context, String userId, String data) {
            registerErrMsg = "카테고리 등록에 실패하였습니다.";
            this.context = context;
            this.userId = userId;
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return cateRetrieveService.register(userId, "002", data);
            } catch (JSONException | IOException e) {
                registerErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authRegisterTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);

            if (success) {
                mEtInputText.setText("");
                Toast.makeText(context, "데이터가 추가되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                //AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                //alertDialogWrapper.showAlertDialog(SpendCateEditActivity.this, getString(R.string.help), registerErrMsg, AlertDialogWrapper.DialogButton.OK);
                Toast.makeText(context, registerErrMsg, Toast.LENGTH_SHORT).show();
            }
            defaultData();
        }

        @Override
        protected void onCancelled() {
            authRegisterTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }

    private class SpendCateDeleteTask extends AsyncTask<Void, Void, Boolean> {
        private final Context context;
        private final String userId, data;
        CateRetrieveService cateRetrieveService = new CateRetrieveService();
        private String deleteErrMsg;

        SpendCateDeleteTask(Context context, String userId, String data) {
            deleteErrMsg = "카테고리 삭제에 실패하였습니다.";
            this.context = context;
            this.userId = userId;
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ShowProgressHelper.showProgress(context, true, viewProgress, viewForm);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return cateRetrieveService.delete(userId, "002", data);
            } catch (JSONException | IOException e) {
                deleteErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authDeleteTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);

            if (success) {
                Toast.makeText(context, "데이터가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                //AlertDialogWrapper alertDialogWrapper = new AlertDialogWrapper();
                //alertDialogWrapper.showAlertDialog(SpendCateEditActivity.this, getString(R.string.help), registerErrMsg, AlertDialogWrapper.DialogButton.OK);
                Toast.makeText(context, deleteErrMsg, Toast.LENGTH_SHORT).show();
            }
            defaultData();
        }

        @Override
        protected void onCancelled() {
            authDeleteTask = null;
            ShowProgressHelper.showProgress(context, false, viewProgress, viewForm);
        }
    }
}