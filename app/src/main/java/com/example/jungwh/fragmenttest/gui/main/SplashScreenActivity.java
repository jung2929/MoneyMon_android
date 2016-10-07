package com.example.jungwh.fragmenttest.gui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.jungwh.fragmenttest.R;

/**
 * Created by jungwh on 2016-10-08.
 */

public class SplashScreenActivity extends Activity {

    private final int SCREEN_DELAY_MILLISECONDS = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startLoginActivity();
            }
        }, SCREEN_DELAY_MILLISECONDS);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        finish();
        startActivity(intent);
    }

    /*
    // 나중에 백그라운드 로딩이 필요할 때 이걸 사용하면 된다.
    // 만약 작업이 1초 이상 걸리면 Timer와 AsyncTask가 서로 끝났는지 체크를 해야 한다
    private class BackgroundTask
            extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean successful) {
        }
    }*/
}
