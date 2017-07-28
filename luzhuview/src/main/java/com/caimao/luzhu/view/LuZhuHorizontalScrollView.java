package com.caimao.luzhu.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.HorizontalScrollView;

/**
 * 项目名: HighFrequencyLotteryBox
 * 包 名: com.royaleu.hfl.view
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuHorizontalScrollView extends HorizontalScrollView {
    public LuZhuHorizontalScrollView(Context context) {
        super(context);
    }

    public LuZhuHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LuZhuHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected ScrollViewListener mScroollViewListener;

    public void setScrollViewListener(ScrollViewListener listener) {
        mScroollViewListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldl);
        if (mScroollViewListener != null) {
            mScroollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }

    public interface ScrollViewListener {
        void onScrollChanged(LuZhuHorizontalScrollView scrollView, int x, int y, int oldx, int oldy);
    }

}
