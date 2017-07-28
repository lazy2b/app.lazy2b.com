package com.caimao.luzhu.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.caimao.luzhu.R;

import java.util.Map;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.lazy2b.app
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuItemViewMgr {

    public int divWidth;
    public int resTxtSize;
    public int titleHeight;
    public int titleTxtSize;
    public int resTxtPadding;
    public int resBgColor = Color.WHITE, titleTxtColor = Color.BLACK, titleBgColor = Color.WHITE,
            p5Color = Color.LTGRAY, divColor = Color.LTGRAY;

    public Map<String, Integer> tColorMap = null;

    public float scale;

    public int dip2px(float dpValue) {
        return (int) (dpValue * scale + 0.5F);
    }

    public static final RectF rf(float l, float t, float r, float b) {
        return new RectF(l, t, r, b);
    }

    public static final Rect r(int l, int t, int r, int b) {
        return new Rect(l, t, r, b);
    }

    protected Context context = null;
    protected TypedArray attr;

    int c(int idx, int def) {
        return attr.hasValue(idx) ? attr.getColor(idx, def) : def;
    }

    int d(int idx, float def) {
        return (int) (attr.hasValue(idx) ? attr.getDimension(idx, def) : def);
    }

    private LuZhuItemViewMgr(Context cxt, AttributeSet attrs) {
        context = cxt;

        scale = context.getResources().getDisplayMetrics().density;

        attr = context.obtainStyledAttributes(attrs, R.styleable.LuZhuSurfaceView);

        divWidth = d(R.styleable.LuZhuSurfaceView_divWidth, dip2px(0.5f));
        resTxtSize = d(R.styleable.LuZhuSurfaceView_resTxtSize, dip2px(14f));
        titleHeight = d(R.styleable.LuZhuSurfaceView_titleHeight, dip2px(35f));
        titleTxtSize = d(R.styleable.LuZhuSurfaceView_titleTxtSize, dip2px(14f));
        resTxtPadding = d(R.styleable.LuZhuSurfaceView_resTxtPadding, dip2px(2f));

        p5Color = c(R.styleable.LuZhuSurfaceView_p5Color, Color.LTGRAY);
        divColor = c(R.styleable.LuZhuSurfaceView_divColor, Color.LTGRAY);
        resBgColor = c(R.styleable.LuZhuSurfaceView_resBgColor, Color.WHITE);
        titleBgColor = c(R.styleable.LuZhuSurfaceView_titleBgColor, Color.WHITE);
        titleTxtColor = c(R.styleable.LuZhuSurfaceView_titleTxtColor, Color.BLACK);

//        LogUtils.e(toString());

        attr.recycle();
        attr = null;
        context = null;
    }


    public int tColor(String txt) {
        return tColorMap.get(txt.substring(0, 1));
    }

    public LuZhuItemViewMgr cMaps(Map<String, Integer> maps) {
        tColorMap = maps;
        return this;
    }

    public static final LuZhuItemViewMgr create(Context context, AttributeSet attrs, Map<String, Integer> maps) {
        return new LuZhuItemViewMgr(context, attrs).cMaps(maps);
    }

    public static final LuZhuItemViewMgr create(Context context, AttributeSet attrs) {
        return new LuZhuItemViewMgr(context, attrs);
    }

}
