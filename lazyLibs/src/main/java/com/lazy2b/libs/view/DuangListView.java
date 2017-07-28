package com.lazy2b.libs.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ListView;

import com.lazy2b.libs.R;
import com.lazy2b.libs.utils.DensityUtils;

/**
 * 项目名: HighFrequencyLotteryBox
 * 包 名: com.lazy2b.libs.view
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class DuangListView extends ListView {

    protected static final int DEF_Y_SCROLL = 10;

    protected Context mCxt = null;
    protected int mOverYScrollDistance = -1;
    protected int mTopOverScrollDistance = -1;
    protected int mBottomOverScrollDistance = -1;
    protected boolean mTopOverScrollEnable = false;
    protected boolean mBottomOverScrollEnable = false;

    public DuangListView(Context context) {
        this(context, null);
    }

    public DuangListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DuangListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mCxt = context;
        if (attrs == null) {
            return;
        }
        final TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.DuangListView);
        int dempHeight = attr.hasValue(R.styleable.DuangListView_dempHeight) ? attr.getInt(R.styleable.DuangListView_dempHeight, DEF_Y_SCROLL) : DEF_Y_SCROLL;
        mOverYScrollDistance = DensityUtils.dip2px(mCxt, dempHeight);
        mTopOverScrollDistance = DensityUtils.dip2px(mCxt, attr.getInt(R.styleable.DuangListView_dempTopHeight, dempHeight));
        mBottomOverScrollDistance = DensityUtils.dip2px(mCxt, attr.getInt(R.styleable.DuangListView_dempBottomHeight, dempHeight));
        mTopOverScrollEnable = attr.getBoolean(R.styleable.DuangListView_dempTop, false);
        mBottomOverScrollEnable = attr.getBoolean(R.styleable.DuangListView_dempBottom, false);
        attr.recycle();
    }


    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        final int overScrollMode = getOverScrollMode();
        final boolean canScrollHorizontal =
                computeHorizontalScrollRange() > computeHorizontalScrollExtent();
        final boolean canScrollVertical =
                computeVerticalScrollRange() > computeVerticalScrollExtent();
        final boolean overScrollHorizontal = overScrollMode == OVER_SCROLL_ALWAYS ||
                (overScrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollHorizontal);
        final boolean overScrollVertical = overScrollMode == OVER_SCROLL_ALWAYS ||
                (overScrollMode == OVER_SCROLL_IF_CONTENT_SCROLLS && canScrollVertical);

        int newScrollX = scrollX + deltaX;
        if (!overScrollHorizontal) {
            maxOverScrollX = 0;
        }

        int newScrollY = scrollY + deltaY;
        if (!overScrollVertical) {
            mTopOverScrollDistance = 0;
            mBottomOverScrollDistance = 0;
        }

        // Clamp values if at the limits and record
        final int left = -maxOverScrollX;
        final int right = maxOverScrollX + scrollRangeX;
        final int top = -mTopOverScrollDistance;
        final int bottom = mBottomOverScrollDistance + scrollRangeY;

        boolean clampedX = false;
        if (newScrollX > right) {
            newScrollX = right;
            clampedX = true;
        } else if (newScrollX < left) {
            newScrollX = left;
            clampedX = true;
        }

        boolean clampedY = false;
        if (newScrollY > scrollRangeY) {    //判断是否超过底部
            if (!mBottomOverScrollEnable) {
                newScrollY = scrollRangeY;
                clampedY = true;
            }
        } else if (newScrollY < 0) {    //判断是否超过顶部
            if (!mTopOverScrollEnable) {
                newScrollY = 0;
                clampedY = true;
            }
        }

        if (newScrollY > bottom) {
            newScrollY = bottom;
            clampedY = true;
        } else if (newScrollY < top) {
            newScrollY = top;
            clampedY = true;
        }

        onOverScrolled(newScrollX, newScrollY, clampedX, clampedY);
        return clampedX || clampedY;
    }


}
