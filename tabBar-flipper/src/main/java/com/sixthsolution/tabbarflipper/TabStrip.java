package com.sixthsolution.tabbarflipper;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2015-11-11
 */
class TabStrip extends LinearLayout {

    private static final int DEFAULT_INDICATOR_SIZE = 12;//dp
    private static final int DEFAULT_HEIGHT = 48;//dp
    private Drawable mDrawableIndicator;
    private int mSelectedPosition;
    private float mSelectionOffset;
    private int mLastPosition;
    private int mIndicatorMaxWidth;
    private int mDrawableMinimumWidth;
    private int mDrawableMinimumHeight;

    public TabStrip(Context context, AttributeSet attrs) {
        super(context);
        setWillNotDraw(false);

        float density = getResources().getDisplayMetrics().density;
        setMinimumHeight(((int) (density * DEFAULT_HEIGHT)));

        init(context, attrs, density);

    }

    private void init(Context context, AttributeSet attrs, float density) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TabBarFlipper);

        if (a.hasValue(R.styleable.TabBarFlipper_tab_IndicatorDrawable))
            mDrawableIndicator = a.getDrawable(R.styleable.TabBarFlipper_tab_IndicatorDrawable);
        else
            mDrawableIndicator = context.getResources().getDrawable(R.drawable.ic_account_circle_24dp);

        int indicatorMaxHeight = a.getDimensionPixelSize(R.styleable.TabBarFlipper_tab_IndicatorSize, (int) (DEFAULT_INDICATOR_SIZE * density));

        a.recycle();


        mDrawableMinimumWidth = mDrawableIndicator.getMinimumWidth();
        mDrawableMinimumHeight = mDrawableIndicator.getMinimumHeight();

        if (mDrawableMinimumHeight > indicatorMaxHeight) {
            int diffHeight = mDrawableMinimumHeight - indicatorMaxHeight;
            float diffScale = diffHeight * 100f / mDrawableMinimumHeight;
            int diffWidth = Math.round(mDrawableMinimumWidth * diffScale / 100f);
            mDrawableMinimumWidth -= diffWidth;
            mDrawableMinimumHeight -= diffHeight;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();

        View selectedTab = getChildAt(mSelectedPosition);
        View nextTab = getChildAt(mSelectedPosition + 1);

        int selectedTabLeft = Utils.getStart(selectedTab);
        int selectedTabRight = Utils.getEnd(selectedTab);
        int selectedCenter = (selectedTabLeft + selectedTabRight) / 2;
        int nextTabRight = Utils.getEnd(nextTab);
        int nextTabCenter = (selectedTabRight + nextTabRight) / 2;

        int distance = nextTabCenter - selectedCenter;

        mDrawableIndicator.setBounds(selectedCenter + ((int) (mSelectionOffset * distance)) - (mDrawableMinimumWidth / 2),
                height - mDrawableMinimumHeight,
                selectedCenter + ((int) (mSelectionOffset * distance)) + (mDrawableMinimumWidth / 2),
                height);

        mDrawableIndicator.draw(canvas);

    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        if (positionOffset == 0f && mLastPosition != mSelectedPosition) {
            mLastPosition = mSelectedPosition;
        }
        invalidate();
    }
}
