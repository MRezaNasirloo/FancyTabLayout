package com.sixthsolution.tabbarflipper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author : Pedramrn@gmail.com
 *         Created on: 2015-11-11
 */
class TabStrip extends LinearLayout {

    private static final int DEFAULT_INDICATOR_SIZE = 12;//dp
    private static final int DEFAULT_HEIGHT = 48;//dp
    private Paint mPaintIndicator;
    private Drawable mDrawableIndicator;
    private int mSelectedPosition;
    private float mSelectionOffset;
    private int mLastPosition;
    private int mIndicatorMaxWidth;
    private int mIndicatorMaxHeight;
    private int mDrawableMinimumWidth;
    private int mDrawablwMinimumHeight;
    private int mDiffHeight;

    public TabStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        float density = getResources().getDisplayMetrics().density;

        init(context, attrs);

        mIndicatorMaxHeight = ((int) (DEFAULT_INDICATOR_SIZE * density));
        mPaintIndicator = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintIndicator.setColor(Color.WHITE);

        mDrawableIndicator = context.getResources().getDrawable(R.drawable.ic_account_circle_24dp);

        mDrawableMinimumWidth = mDrawableIndicator.getMinimumWidth();
        mDrawablwMinimumHeight = mDrawableIndicator.getMinimumHeight();

        if (mDrawablwMinimumHeight > mIndicatorMaxHeight) {
            int diffHeight = mDrawablwMinimumHeight - mIndicatorMaxHeight;
            float diffScale = diffHeight * 100f / mDrawablwMinimumHeight;
            int diffWidth = Math.round(mDrawableMinimumWidth * diffScale / 100f);
            mDrawableMinimumWidth -= diffWidth;
            mDrawablwMinimumHeight -= diffHeight;
        }


        setMinimumHeight(((int) (density * DEFAULT_HEIGHT)));


        //TODO: Add indicator text, and drawable.

    }

    private void init(Context context, AttributeSet attrs) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int height = getHeight();
//        final int width = getWidth();
        View selectedTab = getChildAt(mSelectedPosition);
        View nextTab = getChildAt(mSelectedPosition + 1);

        int selectedTabLeft = Utils.getStart(selectedTab);
        int selectedTabRight = Utils.getEnd(selectedTab);
        int selectedCenter = (selectedTabLeft + selectedTabRight) / 2;
        int nextTabRight = Utils.getEnd(nextTab);
        int nextTabCenter = (selectedTabRight + nextTabRight) / 2;

        int distance = nextTabCenter - selectedCenter;

        mDrawableIndicator.setBounds(
                selectedCenter + ((int) (mSelectionOffset * distance)) - (mDrawableMinimumWidth / 2),
                height - mDrawablwMinimumHeight,
                selectedCenter + ((int) (mSelectionOffset * distance)) + (mDrawableMinimumWidth / 2),
                height);

        mDrawableIndicator.draw(canvas);

        /*int x = paddingLeft + (contentWidth) / 2;
        int y = paddingTop + (contentHeight) / 2;
        canvas.drawText("\u25B2",
                selectedCenter + (mSelectionOffset * des) - mTextWidth / 2,
                height + mTextHeight,
                mPaintIndicator);*/
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
