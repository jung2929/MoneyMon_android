package com.example.jungwh.fragmenttest;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.jungwh.fragmenttest.Tab.FirstTabActivity;
import com.example.jungwh.fragmenttest.Tab.SecondTabActivity;
import com.example.jungwh.fragmenttest.Tab.ThirdTabActivity;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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
                    return new SecondTabActivity();
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