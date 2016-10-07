package com.example.jungwh.fragmenttest.gui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.logic.RegisterService;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.KeyboardHelper;

import org.json.JSONException;

import java.io.IOException;

/**
 * Created by jungwh on 2016-10-04.
 */

public class RegisterActivity extends AppCompatActivity {
    private EditText etUserId, etUserPassword;
    private View progressView, registerFormView;
    private RegisterTask authTask = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("회원가입");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etUserId = (EditText) findViewById(R.id.user_id);
        etUserPassword = (EditText) findViewById(R.id.user_password);

        registerFormView = findViewById(R.id.activity_register_ll_register_form);
        progressView = findViewById(R.id.activity_register_pb_register_progress);

        (findViewById(R.id.register)).setOnClickListener(mOnClickListener);
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveData();
        }
    };

    public void saveData(){
        if (authTask != null) {
            return;
        }

        KeyboardHelper.hideSoftKeyboard(this);

        // Reset errors.
        etUserId.setError(null);
        etUserPassword.setError(null);

        String userId = etUserId.getText().toString();
        String userPassword = etUserPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(userId)) {
            etUserId.setError("사용자 ID를 입력해 주십시오");
            focusView = etUserId;
            cancel = true;
        }else if (TextUtils.isEmpty(userPassword)) {
            etUserPassword.setError("비밀번호를 입력해 주십시오");
            focusView = etUserPassword;
            cancel = true;
        }else if (!isPasswordValid(userPassword)) {
            etUserPassword.setError("비밀번호는 4자리 보다 더 길어야 합니다");
            focusView = etUserPassword;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            authTask = new RegisterTask(getApplicationContext(), userId, userPassword);
            authTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            registerFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            registerFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private class RegisterTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String userId;
        private final String password;
        private RegisterService registerService = new RegisterService();
        private String registerErrMsg;

        RegisterTask(Context context, String userId, String password) {
            registerErrMsg = "사용자 ID 혹은 비밀번호가 잘못되었습니다.";
            this.context = context;
            this.userId = userId;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress(true);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                return registerService.register(userId, password, "woohyun", "010-1234-5678", "test@test.com");
            } catch (JSONException | IOException e) {
                registerErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            showProgress(false);

            if (success) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                etUserPassword.setError(registerErrMsg);
                etUserPassword.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            authTask = null;
            showProgress(false);
        }
    }
}
