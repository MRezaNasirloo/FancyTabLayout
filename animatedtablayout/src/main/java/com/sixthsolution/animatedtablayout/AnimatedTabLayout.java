package com.sixthsolution.animatedtablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.sixthsolution.tabbarflipper.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2015-11-09
 *         <p>
 *         AnimatedTabLayout, Some of the codes has taken from google support library,
 *         and SmartTabLayout from ogaclejapan.
 */


/**
 * TabLayout provides a horizontal layout to display tabs.
 *
 * <p>Population of the tabs to display is
 * done through {@link Tab} instances. You create tabs via {@link #newTab()}. From there you can
 * change the tab's label or icon via {@link Tab#setText(CharSequence)} and {@link Tab#setIcon(int)}
 * respectively. To display the tab, you need to add it to the layout via one of the
 * {@link #addTab(Tab)} methods. For example:
 * <pre>
 * AnimatedTabLayout animatedTabLayout = ...;
 * animatedTabLayout.addTab(animatedTabLayout.newTab().setText("Tab 1").setIcon(icon));
 * animatedTabLayout.addTab(animatedTabLayout.newTab().setText("Tab 2").setIcon(icon));
 * animatedTabLayout.addTab(animatedTabLayout.newTab().setText("Tab 3").setIcon(icon));
 * </pre>
 *
 * And setting up viewpager with {@link #setViewPager(ViewPager)} for liking the viewpager with
 * AnimatedTabLayout
 */
public class AnimatedTabLayout extends HorizontalScrollView {

    private static final int DEFAULT_PADDING = 7;//dp
    private static final int DEFAULT_MIN_WIDTH = 48;//dp
    private int INVALID_TEXT_SIZE = -1;

    private final ArrayList<Tab> mTabs = new ArrayList<>();
    private int mTabMinWidth;
    private int mTabTextColor;
    private int mTabTextSize = INVALID_TEXT_SIZE;
    private int mTabTextAppearance;
    private int mTabPaddingStart;
    private int mTabPaddingTop;
    private int mTabPaddingEnd;
    private int mTabPaddingBottom;
    private TabStrip mTabStrip;
    private Tab mSelectedTab;
    private ViewPager mViewPager;

    public AnimatedTabLayout(Context context) {
        super(context);
    }

    public AnimatedTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimatedTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //Disables scrollbar
        setHorizontalScrollBarEnabled(false);
        setFillViewport(false);

        // Add the TabStrip
        mTabStrip = new TabStrip(context, attrs);
        addView(mTabStrip, LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);


        float density = getResources().getDisplayMetrics().density;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AnimatedTabLayout,
                defStyleAttr, 0);


        mTabPaddingStart = mTabPaddingTop = mTabPaddingEnd = mTabPaddingBottom =
                a.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tab_Padding, ((int) (DEFAULT_PADDING * density)));
        mTabPaddingStart = a.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tab_PaddingStart, mTabPaddingStart);
        mTabPaddingTop = a.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tab_PaddingTop, mTabPaddingTop);
        mTabPaddingEnd = a.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tab_PaddingEnd, mTabPaddingEnd);
        mTabPaddingBottom = a.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tab_PaddingBottom, mTabPaddingBottom);

        mTabMinWidth = a.getDimensionPixelSize(R.styleable.AnimatedTabLayout_tab_MinWidth, ((int) (DEFAULT_MIN_WIDTH * density)));

        mTabTextSize = (int) a.getDimension(R.styleable.AnimatedTabLayout_tab_TextSize, INVALID_TEXT_SIZE);

        mTabTextColor = a.getColor(R.styleable.AnimatedTabLayout_tab_TextColor, Color.WHITE);

        mTabTextAppearance = a.getResourceId(R.styleable.AnimatedTabLayout_tab_TextAppearance, R.style.TextAppearance_Tab);

        a.recycle();

    }

    /**
     * Sets viewpager for this instance
     *
     * @param viewPager viewPager
     */
    public void setViewPager(ViewPager viewPager) {
        mViewPager = viewPager;
        viewPager.addOnPageChangeListener(new TabBarFlipperOnPageChangeListener(this));
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
    }

    /**
     * Adds tab to {@link #mTabs} and Resets the position of all tabs.
     *
     * @param tab      tab to add
     * @param position position to add the tab
     */
    private void configureTab(Tab tab, int position) {
        tab.setPosition(position);
        mTabs.add(position, tab);

        final int count = mTabs.size();
        for (int i = position + 1; i < count; i++) {
            mTabs.get(i).setPosition(i);
        }
    }

    /**
     * Adds a {@link TabView} to the {@link #mTabStrip}
     *
     * @param tab tab to add
     */
    private void addTabView(Tab tab) {
        final TabView tabView = createTabView(tab);
        mTabStrip.addView(tabView, createLayoutParamsForTabs());
    }

    /**
     * Adds a {@link TabView} to the {@link #mTabStrip}
     *
     * @param tab         tab to add
     * @param setSelected tab status
     */
    private void addTabView(Tab tab, boolean setSelected) {
        final TabView tabView = createTabView(tab);
        mTabStrip.addView(tabView, createLayoutParamsForTabs());
        if (setSelected) {
            tabView.select();
        }
    }

    /**
     * Adds a {@link TabView} to the {@link #mTabStrip} with its position
     *
     * @param tab         tab to add
     * @param setSelected tab status
     * @param position    tab position
     */
    private void addTabView(Tab tab, int position, boolean setSelected) {
        final TabView tabView = createTabView(tab);
        mTabStrip.addView(tabView, position, createLayoutParamsForTabs());
        if (setSelected) {
            tabView.select();
        }
    }

    public TabView createTabView(Tab tab) {
        return new TabView(getContext(), tab);
    }

    private FrameLayout.LayoutParams createLayoutParamsForTabs() {
        return new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
    }

    private TabView getTabViewAt(int position) {
        return (TabView) mTabStrip.getChildAt(position);
    }

    private int dpToPx(int dps) {
        return Math.round(getResources().getDisplayMetrics().density * dps);
    }

    /**
     * Deselect all tabs and select the tab at given position.
     *
     * @param position position to select tab
     */
    private void setSelectedTabView(int position) {
        final int tabCount = mTabs.size();
        if (position < tabCount) {
            for (int i = 0; i < tabCount; i++) {
                final TabView child = getTabViewAt(i);
                if (i == position) {
                    child.select();
                    mSelectedTab = mTabs.get(position);
                } else child.Deselect();
            }
        }
    }

    /**
     * Deselects all tabs with an animation, except the selected one
     */
    private void DeselectTabViews() {
        final int tabCount = mTabs.size();
        for (int i = 0; i < tabCount; i++) {
            final TabView child = getTabViewAt(i);
            if (i != mSelectedTab.getPosition()) {
                child.animateDeselect();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getChildCount() > 0) {
            View firstTab = mTabStrip.getChildAt(0);
            View lastTab = mTabStrip.getChildAt(getChildCount() - 1);
            int start = (w - Utils.getMeasuredWidth(firstTab)) / 2 - Utils.getMarginStart(firstTab);
            int end = (w - Utils.getMeasuredWidth(lastTab)) / 2 - Utils.getMarginEnd(lastTab);
            mTabStrip.setMinimumWidth(mTabStrip.getMeasuredWidth());
            ViewCompat.setPaddingRelative(this, start, getPaddingTop(), end, getPaddingBottom());
            setClipToPadding(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // Ensure first scroll
        if (changed && mSelectedTab != null) {
            scrollToTab(mSelectedTab.getPosition(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedTab = mTabStrip.getChildAt(tabIndex);
        if (selectedTab == null) {
            return;
        }

        final boolean isLayoutRtl = Utils.isLayoutRtl(this);

        View firstTab = mTabStrip.getChildAt(0);
        int x;
        if (isLayoutRtl) {
            int first = Utils.getWidth(firstTab) + Utils.getMarginEnd(firstTab);
            int selected = Utils.getWidth(selectedTab) + Utils.getMarginEnd(selectedTab);
            x = Utils.getEnd(selectedTab) - Utils.getMarginEnd(selectedTab) - positionOffset;
            x -= (first - selected) / 2;
        } else {
            int first = Utils.getWidth(firstTab) + Utils.getMarginStart(firstTab);
            int selected = Utils.getWidth(selectedTab) + Utils.getMarginStart(selectedTab);
            x = Utils.getStart(selectedTab) - Utils.getMarginStart(selectedTab) + positionOffset;
            x -= (first - selected) / 2;
        }

        scrollTo(x, 0);

    }

    /**
     * ValueObject class for holding tab data,
     */
    public static class Tab {

        public static final int INVALID_POSITION = -1;
        private final AnimatedTabLayout mParent;
        private CharSequence mTabText;
        private Drawable mTabIcon;
        private int mPosition = INVALID_POSITION;

        public Tab(AnimatedTabLayout parent) {
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
            mTabText = text;
            return this;
        }

        public Drawable getIcon() {
            return mTabIcon;
        }

        public Tab setIcon(Drawable mTabIcon) {
            this.mTabIcon = mTabIcon;
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
            return setIcon(AppCompatResources.getDrawable(mParent.getContext(), resId));
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

    /**
     * The container view for text and icon
     */
    class TabView extends FrameLayout implements OnClickListener {
        private final Tab mTab;
        private final int duration = 300;
        private TextView mTextView;
        private ImageView mIconView;


        public TabView(Context context, Tab tab) {
            super(context);
            mTab = tab;

            ViewCompat.setPaddingRelative(this, mTabPaddingStart, mTabPaddingTop, mTabPaddingEnd, mTabPaddingBottom);
            setMinimumWidth(mTabMinWidth);


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
                if (Build.VERSION.SDK_INT < 23)
                    textView.setTextAppearance(getContext(), mTabTextAppearance);
                else
                    textView.setTextAppearance(mTabTextAppearance);

            }

            mTextView.setText(tab.getText());
            mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize);
            mTextView.setTextColor(mTabTextColor);
            mIconView.setImageDrawable(tab.getIcon());
            Deselect();
            setOnClickListener(this);
        }

        //TODO: Use strategy pattern for letting other kind of animations.
        public void transformSelect(float positionOffset) {

            float shrinkFactor = positionOffset / 2;
            mTextView.setScaleX(Math.abs(shrinkFactor - 1f));
            mTextView.setScaleY(Math.abs(shrinkFactor - 1f));
            mTextView.setAlpha(Math.abs(positionOffset - 1f));
            mTextView.setRotationY((180f * positionOffset / 1.0f));

            mIconView.setScaleX(positionOffset);
            mIconView.setScaleY(positionOffset);
            mIconView.setAlpha(positionOffset);
            mIconView.setRotationY(Math.abs((180f * positionOffset / 1.0f) - 180f));

        }

        public void transformDeselect(float positionOffset) {

            float shrinkFactor = positionOffset / 2;
            mIconView.setScaleX(Math.abs(shrinkFactor - 1f));
            mIconView.setScaleY(Math.abs(shrinkFactor - 1f));
            mIconView.setAlpha(Math.abs(positionOffset - 1f));
            mIconView.setRotationY((180f * positionOffset / 1.0f));

            mTextView.setScaleX(positionOffset);
            mTextView.setScaleY(positionOffset);
            mTextView.setAlpha(positionOffset);
            mTextView.setRotationY(Math.abs((180f * positionOffset / 1.0f) - 180f));

        }

        public void animateSelect() {

            mTextView.animate().scaleX(1).scaleY(1).alpha(1).rotationY(0).setDuration(duration).start();
            mIconView.animate().scaleX(0).scaleY(0).alpha(0).rotationY(180).setDuration(duration).start();

        }

        public void animateDeselect() {

            mTextView.animate().scaleX(0).scaleY(0).alpha(0).rotationY(180).setDuration(duration).start();
            mIconView.animate().scaleX(1).scaleY(1).alpha(1).rotationY(0).setDuration(duration).start();
        }

        public void select() {
            transformSelect(0);
            setSelected(true);
        }

        public void Deselect() {
            transformDeselect(0);
            setSelected(false);
        }

        @Override
        public void onClick(View v) {
            mViewPager.setCurrentItem(mTab.getPosition());
        }
    }

    public static class TabBarFlipperOnPageChangeListener implements ViewPager.OnPageChangeListener {

        private final WeakReference<AnimatedTabLayout> mTabBarFlipperRef;

        public TabBarFlipperOnPageChangeListener(AnimatedTabLayout animatedTabLayout) {
            mTabBarFlipperRef = new WeakReference<>(animatedTabLayout);
        }


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            AnimatedTabLayout animatedTabLayout = mTabBarFlipperRef.get();

            //Only update offset if the (position + offset) is not bigger than tab count.
            if (position >= animatedTabLayout.mTabs.size() - 1 && positionOffset > 0.0f) return;

            animatedTabLayout.mTabStrip.onViewPagerPageChanged(position, positionOffset);
            TabView selectedTab = animatedTabLayout.getTabViewAt(position);
            TabView selectingTab = animatedTabLayout.getTabViewAt(position + 1);
            if (selectingTab == null) return;
            selectedTab.transformSelect(positionOffset);
            selectingTab.transformDeselect(positionOffset);

            if (0f < positionOffset && positionOffset < 1f) {
                int current = Utils.getWidth(selectedTab) / 2 + Utils.getMarginEnd(selectedTab);
                int next = Utils.getWidth(selectingTab) / 2 + Utils.getMarginStart(selectingTab);
                int extraOffset = Math.round(positionOffset * (current + next));
                animatedTabLayout.scrollToTab(position, extraOffset);
            }


        }

        @Override
        public void onPageSelected(int position) {
            AnimatedTabLayout animatedTabLayout = mTabBarFlipperRef.get();
            int tabCount = animatedTabLayout.mTabs.size() - 1;
            //if there are more page than tabs select the last tab
            if (position > tabCount) {
                animatedTabLayout.mTabStrip.onViewPagerPageChanged(tabCount, 0f);
                animatedTabLayout.setSelectedTabView(tabCount);
                return;
            }
            animatedTabLayout.setSelectedTabView(position);
            //Setting the viewpager current item programmatically causes tabs animate incompletely,
            //so reset all tabs status
            animatedTabLayout.DeselectTabViews();
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
