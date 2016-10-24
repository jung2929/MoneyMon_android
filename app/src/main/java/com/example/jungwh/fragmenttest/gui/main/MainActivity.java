package com.example.jungwh.fragmenttest.gui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.LoginData;
import com.example.jungwh.fragmenttest.business.logic.LoginService;
import com.example.jungwh.fragmenttest.gui.firstTab.FirstTabActivity;
import com.example.jungwh.fragmenttest.gui.secondTab.SecondTabActivity;
import com.example.jungwh.fragmenttest.gui.ThirdTab.ThirdTabActivity;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;

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

        mViewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[0]))
                        .title(getString(R.string.first_section_title))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor(colors[1]))
                        .title(getString(R.string.second_section_title))
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
                        .title(getString(R.string.third_section_title))
                        .build()
        );
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(mViewPager,0);

        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View viewPager = findViewById(R.id.vp_horizontal_ntb);
                ((ViewGroup.MarginLayoutParams) viewPager.getLayoutParams()).topMargin =
                        (int) -navigationTabBar.getBadgeMargin();
                viewPager.requestLayout();
            }
        });
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
                    //bundle.putString("USER_ID", loginData.getLoginId());
                    bundle.putString("USER_ID", "test");
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
    }
}