package com.example.jungwh.fragmenttest.gui.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.LoginData;
import com.example.jungwh.fragmenttest.business.logic.LoginService;
import com.example.jungwh.fragmenttest.util.ExceptionHelper;
import com.example.jungwh.fragmenttest.util.KeyboardHelper;

import org.json.JSONException;

import java.io.IOException;


/**
 * Created by jungwh on 2016-10-03.
 */


public class LoginActivity extends AppCompatActivity {
    private EditText etUserId, etUserPassword;
    private View progressView, loginFormView;
    private UserLoginTask authTask = null;
    private boolean isBackPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserId = (EditText) findViewById(R.id.user_id);
        etUserId.setNextFocusDownId(R.id.user_password);

        etUserPassword = (EditText) findViewById(R.id.user_password);
        etUserPassword.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etUserPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE) {
                    Login();
                    return false;
                }
                return true;
            }
        });

        findViewById(R.id.login).setOnClickListener(mOnClickListener);
        findViewById(R.id.register).setOnClickListener(mOnClickListener);

        loginFormView = findViewById(R.id.activity_login_ll_login_form);
        progressView = findViewById(R.id.activity_login_rl_login_layout);

        /*LoginService loginService = new LoginService();
        LoginData cachedLoginData = loginService.getCachedLoginData(this.getBaseContext());
        if (cachedLoginData == null)
            return;

        String loginId = cachedLoginData.getLoginId();
        String password = cachedLoginData.getLoginPassword();
        etUserId.setText(loginId);
        if (!loginService.isAutoLoginEnabled(this.getBaseContext()))
            return;
        etUserPassword.setText(password);
        if (TextUtils.isEmpty(loginId) || TextUtils.isEmpty(password))
            return;
        Login();*/
        finish();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    Button.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.login :
                    Login();
                    break;
                case R.id.register :
                    Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        handleUserApplicationExit();
    }

    private void handleUserApplicationExit() {
        if (isBackPressedOnce) {
            finish();
            return;
        }

        isBackPressedOnce = true;
        Toast.makeText(this, "\"뒤로\"버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                isBackPressedOnce = false;
            }
        }, 1500);
    }

    public void Login() {
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
            authTask = new UserLoginTask(getApplicationContext(), userId, userPassword);
            authTask.execute((Void) null);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            loginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationEnd(Animator animation) {
                    loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    private class UserLoginTask
            extends AsyncTask<Void, Void, Boolean> {

        private final Context context;
        private final String userId;
        private final String password;
        private LoginService loginService = new LoginService();
        private LoginData loginData = new LoginData();
        private String loginErrMsg;

        UserLoginTask(Context context, String userId, String password) {
            loginErrMsg = "사용자 ID 혹은 비밀번호가 잘못되었습니다.";
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
                loginData = loginService.login(userId, password);
                return loginData.isValidLogin();
            } catch (JSONException | IOException e) {
                loginErrMsg = ExceptionHelper.getApplicationExceptionMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            authTask = null;
            showProgress(false);

            if (success) {
                loginService.saveAutoLoginOption(this.context, true);
                loginService.saveLoginData(this.context, loginData);

                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("LOGIN_DATA", loginData);
                startActivity(intent);
            } else {
                etUserPassword.setError(loginErrMsg);
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