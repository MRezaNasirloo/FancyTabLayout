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

import android.support.v4.view.MarginLayoutParamsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;

final class Utils {

  static int getMeasuredWidth(View v) {
    return (v == null) ? 0 : v.getMeasuredWidth();
  }

  static int getWidth(View v) {
    return (v == null) ? 0 : v.getWidth();
  }

  static int getStart(View v) {
    return getStart(v, false);
  }

  static int getStart(View v, boolean withoutPadding) {
    if (v == null) {
      return 0;
    }
    if (isLayoutRtl(v)) {
      return (withoutPadding) ? v.getRight() - getPaddingStart(v) : v.getRight();
    } else {
      return (withoutPadding) ? v.getLeft() + getPaddingStart(v) : v.getLeft();
    }
  }

  static int getEnd(View v) {
    return getEnd(v, false);
  }

  static int getEnd(View v, boolean withoutPadding) {
    if (v == null) {
      return 0;
    }
    if (isLayoutRtl(v)) {
      return (withoutPadding) ? v.getLeft() + getPaddingEnd(v) : v.getLeft();
    } else {
      return (withoutPadding) ? v.getRight() - getPaddingEnd(v) : v.getRight();
    }
  }

  static int getPaddingStart(View v) {
    if (v == null) {
      return 0;
    }
    return ViewCompat.getPaddingStart(v);
  }

  static int getPaddingEnd(View v) {
    if (v == null) {
      return 0;
    }
    return ViewCompat.getPaddingEnd(v);
  }

  static int getPaddingHorizontally(View v) {
    if (v == null) {
      return 0;
    }
    return v.getPaddingLeft() + v.getPaddingRight();
  }

  static int getMarginStart(View v) {
    if (v == null) {
      return 0;
    }
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(lp);
  }

  static int getMarginEnd(View v) {
    if (v == null) {
      return 0;
    }
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginEnd(lp);
  }

  static int getMarginHorizontally(View v) {
    if (v == null) {
      return 0;
    }
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
    return MarginLayoutParamsCompat.getMarginStart(lp) + MarginLayoutParamsCompat.getMarginEnd(lp);
  }

  static boolean isLayoutRtl(View v) {
    return ViewCompat.getLayoutDirection(v) == ViewCompat.LAYOUT_DIRECTION_RTL;
  }

  private Utils() { }

}
