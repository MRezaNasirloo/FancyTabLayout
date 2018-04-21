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

package com.mrezanasirloo.fancytablayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author : M.Reza.Nasirloo@gmail.com
 *         Created on: 2015-11-11
 *
 * Container for tabs and responsible for drawing the indicator.
 */
class TabStrip extends LinearLayout {

    private static final int DEFAULT_INDICATOR_SIZE = 12;//dp
    private static final int DEFAULT_HEIGHT = 48;//dp
    private Drawable mDrawableIndicator;
    private int mSelectedPosition;
    private float mSelectionOffset;
    private int mLastPosition;
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
        @SuppressLint("CustomViewStyleable") TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FancyTabLayout);

        if (a.hasValue(R.styleable.FancyTabLayout_tab_IndicatorDrawable))
            mDrawableIndicator = a.getDrawable(R.styleable.FancyTabLayout_tab_IndicatorDrawable);
        else
            mDrawableIndicator = context.getResources().getDrawable(R.drawable.ic_arrow_up_24dp);

        int indicatorMaxHeight = a.getDimensionPixelSize(R.styleable.FancyTabLayout_tab_IndicatorSize, (int) (DEFAULT_INDICATOR_SIZE * density));

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
