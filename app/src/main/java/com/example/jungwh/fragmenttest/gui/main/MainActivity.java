package com.example.jungwh.fragmenttest.gui.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.LoginData;
import com.example.jungwh.fragmenttest.business.logic.LoginService;
import com.example.jungwh.fragmenttest.gui.firstTab.FirstTabActivity;
import com.example.jungwh.fragmenttest.gui.secondTab.SecondTabActivity;
import com.example.jungwh.fragmenttest.gui.ThirdTab.ThirdTabActivity;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private LoginData loginData;
    private boolean isBackPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginData = getIntent().getParcelableExtra("LOGIN_DATA");
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return;
        }

        handleUserApplicationExit();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelable("LOGIN_DATA", loginData);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout: {
                LoginService loginService = new LoginService();
                loginService.logout(getApplicationContext());
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // 탭의 컨택스트를 연동하는 곳
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FirstTabActivity();
                case 1:
                    SecondTabActivity secondTabActivity = new SecondTabActivity();
                    Bundle bundle = new Bundle();
                    bundle.putString("USER_ID", loginData.getLoginId());
                    secondTabActivity.setArguments(bundle);
                    return secondTabActivity;
                case 2:
                    return new ThirdTabActivity();
            }


            return null;
        }

        // 탭의 갯수를 지정하는 곳
        @Override
        public int getCount() {
            return 3;
        }

        // 탭의 타이틀을 지정하는 곳
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.first_section_title);
                case 1:
                    return getString(R.string.second_section_title);
                case 2:
                    return getString(R.string.third_section_title);
            }
            return null;
        }
    }
}