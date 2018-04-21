/*
 * MIT License
 *
 * Copyright (c) 2018 M. Reza Nasirloo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mrezanasirloo.fancytablayout.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.mrezanasirloo.fancytablayout.FancyTabLayout;

public class ViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        FancyTabLayout fancyTabLayout =  findViewById(R.id.tabs);

        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("ONE").setIcon(R.drawable.ic_room_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("TWO").setIcon(R.drawable.ic_backup_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("THREE").setIcon(R.drawable.ic_brightness_2_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("FOUR").setIcon(R.drawable.ic_shopping_cart_24dp));

        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("five").setIcon(R.drawable.ic_room_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("six").setIcon(R.drawable.ic_backup_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("seven").setIcon(R.drawable.ic_brightness_2_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("eight").setIcon(R.drawable.ic_shopping_cart_24dp));

        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("nine").setIcon(R.drawable.ic_room_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("ten").setIcon(R.drawable.ic_backup_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("eleven").setIcon(R.drawable.ic_brightness_2_24dp));
        fancyTabLayout.addTab(fancyTabLayout.newTab().setText("twelve").setIcon(R.drawable.ic_shopping_cart_24dp));


        ViewPager viewPager = findViewById(R.id.vp);

        PagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);

        fancyTabLayout.setViewPager(viewPager);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
