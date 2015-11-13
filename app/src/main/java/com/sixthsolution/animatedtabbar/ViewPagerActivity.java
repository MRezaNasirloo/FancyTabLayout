package com.sixthsolution.animatedtabbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sixthsolution.tabbarflipper.TabBarFlipper;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        TabBarFlipper tabBarFlipper = (TabBarFlipper) findViewById(R.id.tabs);

        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("ONE").setIcon(R.drawable.ic_room_24dp));
        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("TWO").setIcon(R.drawable.ic_backup_24dp));
        /*tabBarFlipper.addTab(tabBarFlipper.newTab().setText("THREE").setIcon(R.drawable.ic_brightness_2_24dp));
        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("FOUR").setIcon(R.drawable.ic_shopping_cart_24dp));

        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("five").setIcon(R.drawable.ic_room_24dp));
        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("six").setIcon(R.drawable.ic_backup_24dp));
        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("seven").setIcon(R.drawable.ic_brightness_2_24dp));
        tabBarFlipper.addTab(tabBarFlipper.newTab().setText("eight").setIcon(R.drawable.ic_shopping_cart_24dp));*/


        ViewPager viewPager = (ViewPager) findViewById(R.id.vp);

        PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);

        tabBarFlipper.setUpWithViewPager(viewPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 4;

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
                case 0:
                    return BlankFragment.newInstance(1, "Page # 1");
                case 1:
                    return BlankFragment.newInstance(2, "Page # 2");
                case 2:
                    return BlankFragment.newInstance(3, "Page # 3");
                default:
                    return BlankFragment.newInstance(4, "Page # 3");
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }
    }
}
