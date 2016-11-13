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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jungwh.fragmenttest.R;
import com.example.jungwh.fragmenttest.business.data.LoginData;
import com.example.jungwh.fragmenttest.business.logic.LoginService;
import com.example.jungwh.fragmenttest.gui.InputTab.InputTabActivity;
import com.example.jungwh.fragmenttest.gui.secondTab.SecondTabActivity;
import com.example.jungwh.fragmenttest.gui.ThirdTab.ThirdTabActivity;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private LoginData loginData;
    private boolean isBackPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginData = getIntent().getParcelableExtra("LOGIN_DATA");

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getString(R.string.input_section_title));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_compare_arrows_white_24dp, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getString(R.string.second_section_title));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_monetization_on_white_24dp, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getString(R.string.third_section_title));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_equalizer_white_24dp, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
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
            case R.id.income_settings: {
                Intent intent = new Intent(getApplicationContext(), IncomeCateEditActivity.class);
                intent.putExtra("USER_ID", loginData.getLoginId());
                startActivity(intent);
                break;
            }
            case R.id.spend_settings: {
                Intent intent = new Intent(getApplicationContext(), SpendCateEditActivity.class);
                intent.putExtra("USER_ID", loginData.getLoginId());
                startActivity(intent);
                break;
            }
            case R.id.budget: {
                Intent intent = new Intent(getApplicationContext(), MonthlyBudgetActivity.class);
                intent.putExtra("USER_ID", loginData.getLoginId());
                startActivity(intent);
                break;
            }
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
            Bundle bundle = new Bundle();
            bundle.putString("USER_ID", loginData.getLoginId());
            //bundle.putString("USER_ID", "test");
            switch (position){
                case 0:
                    InputTabActivity inputTabActivity = new InputTabActivity();
                    inputTabActivity.setArguments(bundle);
                    return inputTabActivity;
                case 1:
                    SecondTabActivity secondTabActivity = new SecondTabActivity();
                    secondTabActivity.setArguments(bundle);
                    return secondTabActivity;
                case 2:
                    ThirdTabActivity thirdTabActivity = new ThirdTabActivity();
                    thirdTabActivity.setArguments(bundle);
                    return thirdTabActivity;
                default:
                    return null;
            }
        }

        // 탭의 갯수를 지정하는 곳
        @Override
        public int getCount() {
            return 3;
        }
    }
}