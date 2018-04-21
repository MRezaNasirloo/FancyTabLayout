package com.sixthsolution.animatedtabbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sixthsolution.animatedtablayout.AnimatedTabLayout;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        setSupportActionBar(
                (Toolbar) findViewById(R.id.toolbar));

        AnimatedTabLayout animatedTabLayout =  findViewById(R.id.tabs);

        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("ONE").setIcon(R.drawable.ic_room_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("TWO").setIcon(R.drawable.ic_backup_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("THREE").setIcon(R.drawable.ic_brightness_2_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("FOUR").setIcon(R.drawable.ic_shopping_cart_24dp));

        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("five").setIcon(R.drawable.ic_room_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("six").setIcon(R.drawable.ic_backup_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("seven").setIcon(R.drawable.ic_brightness_2_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("eight").setIcon(R.drawable.ic_shopping_cart_24dp));

        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("nine").setIcon(R.drawable.ic_room_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("ten").setIcon(R.drawable.ic_backup_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("eleven").setIcon(R.drawable.ic_brightness_2_24dp));
        animatedTabLayout.addTab(animatedTabLayout.newTab().setText("twelve").setIcon(R.drawable.ic_shopping_cart_24dp));


        ViewPager viewPager = findViewById(R.id.vp);

        PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);

        animatedTabLayout.setViewPager(viewPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 12;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                    return BlankFragment.newInstance(position + 1, "Page # " + position + 1);
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
