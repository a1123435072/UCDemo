package com.example.yangg.uc;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.yangg.uc.Behavior.NewsPagerBehavior;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private NewsPagerBehavior newsPagerBehavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Fragment> fragments = new ArrayList<>();

        fragments.add(new MyFragment());
        fragments.add(new MyFragment());
        fragments.add(new MyFragment());
        viewPager = (ViewPager) findViewById(R.id.vp);

        MyFragmentPagerAdapter myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        myFragmentPagerAdapter.setDatas(fragments);
        viewPager.setAdapter(myFragmentPagerAdapter);


        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) findViewById(R.id.fl_head).getLayoutParams();

        newsPagerBehavior = (NewsPagerBehavior) layoutParams.getBehavior();

        initTab();


    }

    private void initTab() {
         TabLayout tableLayout = (TabLayout) findViewById(R.id.tab_layout);


        for (int i = 0; i < 3; i++) {
            tableLayout.addTab(tableLayout.newTab().setText("tab" + i));
        }
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tableLayout));
    }

    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> datas;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setDatas(List<Fragment> datas) {
            this.datas = datas;
        }

        @Override
        public Fragment getItem(int position) {
            return datas.get(position);
        }

        @Override
        public int getCount() {
            return datas.size();
        }
    }

    @Override
    public void onBackPressed() {
        if (newsPagerBehavior.isClosed()) {
            newsPagerBehavior.open();
        } else {
            super.onBackPressed();
        }
    }
}
