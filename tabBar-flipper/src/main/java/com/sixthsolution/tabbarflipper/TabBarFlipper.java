package com.sixthsolution.tabbarflipper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.internal.widget.TintManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2015-11-09
 */
public class TabBarFlipper extends HorizontalScrollView {

    private final ArrayList<Tab> mTabs = new ArrayList<>();
    private LinearLayout mTabStrip;

    public TabBarFlipper(Context context) {
        super(context);
    }

    public TabBarFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        setHorizontalScrollBarEnabled(false);

        // Add the TabStrip
        mTabStrip = new LinearLayout(context);
        addView(mTabStrip, LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

    }

    public void setUpWithViewPager(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new TabBarFlipperOnPageChangeListener(this));
//        viewPager.getAdapter();
        //TODO: get the titles from adapter
    }

    public Tab newTab() {
        return new Tab(this);
    }

    /**
     * Add a tab to this layout. The tab will be added at the end of the list.
     * If this is the first tab to be added it will become the selected tab.
     *
     * @param tab Tab to add
     */
    public void addTab(@NonNull Tab tab) {
        addTab(tab, mTabs.isEmpty());
    }

    /**
     * Add a tab to this layout. The tab will be inserted at <code>position</code>.
     * If this is the first tab to be added it will become the selected tab.
     *
     * @param tab      The tab to add
     * @param position The new position of the tab
     */
    public void addTab(@NonNull Tab tab, int position) {
        addTab(tab, position, mTabs.isEmpty());
    }

    /**
     * Add a tab to this layout. The tab will be added at the end of the list.
     *
     * @param tab         Tab to add
     * @param setSelected True if the added tab should become the selected tab.
     */
    public void addTab(@NonNull Tab tab, boolean setSelected) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabBarFlipper.");
        }

        addTabView(tab, setSelected);
        configureTab(tab, mTabs.size());
        /*if (setSelected) {
            tab.select();
        }*/
    }

    /**
     * Add a tab to this layout. The tab will be inserted at <code>position</code>.
     *
     * @param tab         The tab to add
     * @param position    The new position of the tab
     * @param setSelected True if the added tab should become the selected tab.
     */
    public void addTab(@NonNull Tab tab, int position, boolean setSelected) {
        if (tab.mParent != this) {
            throw new IllegalArgumentException("Tab belongs to a different TabBarFlipper.");
        }

        addTabView(tab, position, setSelected);
        configureTab(tab, position);
        /*if (setSelected) {
            tab.select();
        }*/
    }

    private void configureTab(Tab tab, int position) {
        tab.setPosition(position);
        mTabs.add(position, tab);

        final int count = mTabs.size();
        for (int i = position + 1; i < count; i++) {
            mTabs.get(i).setPosition(i);
        }
    }

    private void addTabView(Tab tab) {
        final TabView tabView = createTabView(tab);
        mTabStrip.addView(tabView, createLayoutParamsForTabs());
    }

    private void addTabView(Tab tab, boolean setSelected) {
        final TabView tabView = createTabView(tab);
        mTabStrip.addView(tabView, createLayoutParamsForTabs());
        if (setSelected) {
            tabView.select();
            setSelected(true);
        }
    }

    private void addTabView(Tab tab, int position, boolean setSelected) {
        final TabView tabView = createTabView(tab);
        mTabStrip.addView(tabView, position, createLayoutParamsForTabs());
        if (setSelected) {
            tabView.select();
            setSelected(true);
        }
    }

    public TabView createTabView(Tab tab) {
        return new TabView(getContext(), tab);
    }

    private LinearLayout.LayoutParams createLayoutParamsForTabs() {
        return new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    private TabView getTabViewAt(int position) {
        return (TabView) mTabStrip.getChildAt(position);
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    /**
     * class for holding tab data and adding a new TabView to the container LinearLayout
     */
    public static class Tab {

        public static final int INVALID_POSITION = -1;
        private final TabBarFlipper mParent;
        private CharSequence mTabText;
        private Drawable mTabIcon;
        private int mPosition = INVALID_POSITION;

        public Tab(TabBarFlipper parent) {
            mParent = parent;
        }

        /**
         * Return the text of this tab.
         *
         * @return The tab's text
         */
        public CharSequence getText() {
            return mTabText;
        }

        /**
         * Set the text displayed on this tab. Text may be truncated if there is not room to display
         * the entire string.
         *
         * @param text The text to display
         * @return The current instance for call chaining
         */
        @NonNull
        public Tab setText(@Nullable CharSequence text) {
            mTabText = text;//TODO: update TabBar
            return this;
        }

        public Drawable getIcon() {
            return mTabIcon;
        }

        public Tab setIcon(Drawable mTabIcon) {
            this.mTabIcon = mTabIcon;//TODO: update TabBar
            return this;
        }

        /**
         * Set the icon displayed on this tab.
         *
         * @param resId A resource ID referring to the icon that should be displayed
         * @return The current instance for call chaining
         */
        @NonNull
        public Tab setIcon(@DrawableRes int resId) {
            return setIcon(TintManager.getDrawable(mParent.getContext(), resId));
        }

        /**
         * Return the current position of this tab in the action bar.
         *
         * @return Current position, or {@link #INVALID_POSITION} if this tab is not currently in
         * the action bar.
         */
        public int getPosition() {
            return mPosition;
        }

        void setPosition(int position) {
            mPosition = position;
        }
    }

    class TabView extends FrameLayout {
        private final String TAG = TabView.class.getSimpleName();
        private final Tab mTab;
        private TextView mTextView;
        private ImageView mIconView;


        public TabView(Context context, Tab tab) {
            super(context);
            mTab = tab;

            setPadding(10, 10, 10, 10);

            /*if (mTabBackgroundResId != 0) {
                setBackgroundDrawable(TintManager.getDrawable(context, mTabBackgroundResId));
            }*/


            if (mIconView == null) {
                ImageView iconView = (ImageView) LayoutInflater.from(getContext())
                        .inflate(R.layout.flipper_tab_icon, this, false);
                addView(iconView, 0);
                mIconView = iconView;
            }
            if (mTextView == null) {
                TextView textView = (TextView) LayoutInflater.from(getContext())
                        .inflate(R.layout.flipper_tab_text, this, false);
                addView(textView);
                mTextView = textView;
            }

            mTextView.setText(tab.getText());
            mIconView.setImageDrawable(tab.getIcon());
            unSelect();
            /*mTextView.setTextAppearance(getContext(), mTabTextAppearance);
            if (mTabTextColors != null) {
                mTextView.setTextColor(mTabTextColors);
            }
            updateTextAndIcon(tab, mTextView, mIconView);*/
        }

        public void animateIn(float positionOffset) {

            mTextView.setScaleX(Math.abs(positionOffset - 1f));
            mTextView.setScaleY(Math.abs(positionOffset - 1f));
            mTextView.setAlpha(Math.abs(positionOffset - 1f));
            mTextView.setRotationY((180f * positionOffset / 1.0f));

            mIconView.setScaleX(positionOffset);
            mIconView.setScaleY(positionOffset);
            mIconView.setAlpha(positionOffset);
            mIconView.setRotationY(Math.abs((180f * positionOffset / 1.0f) - 180f));

        }

        public void animateOut(float positionOffset) {

            mIconView.setScaleX(Math.abs(positionOffset - 1f));
            mIconView.setScaleY(Math.abs(positionOffset - 1f));
            mIconView.setAlpha(Math.abs(positionOffset - 1f));
            mIconView.setRotationY((180f * positionOffset / 1.0f));

            mTextView.setScaleX(positionOffset);
            mTextView.setScaleY(positionOffset);
            mTextView.setAlpha(positionOffset);
            mTextView.setRotationY(Math.abs((180f * positionOffset / 1.0f) - 180f));

        }

        public void select() {
            animateIn(0);
            setSelected(true);
        }

        public void unSelect() {
            animateOut(0);
            setSelected(false);
        }
    }

    public static class TabBarFlipperOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final WeakReference<TabBarFlipper> mTabBarFlipperRef;
        private int mPreviousScrollState;
        private int mScrollState;
        private int mSelectedTab;

        public TabBarFlipperOnPageChangeListener(TabBarFlipper tabBarFlipper) {
            mTabBarFlipperRef = new WeakReference<>(tabBarFlipper);
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            TabBarFlipper tabBarFlipper = mTabBarFlipperRef.get();
            TabView selectedTab = tabBarFlipper.getTabViewAt(position);
            TabView selectingTab = tabBarFlipper.getTabViewAt(position + 1);
            if (selectingTab == null) return;
            selectedTab.animateIn(positionOffset);
            selectingTab.animateOut(positionOffset);

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
