package com.lazy2b.app;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseModel;
import com.lazy2b.libs.utils.DensityUtils;
import com.lidroid.xutils.util.LogUtils;

import java.util.Map;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.lazy2b.app
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LvMgr去 extends BaseModel implements ILazyBase {

    public int divWidth = dip2px(0.5f);
    public int titleHeight = dip2px(35f);
    public int titleTxtSize = dip2px(14f);
    public int resTxtSize = dip2px(14f);
    public int resBgColor = Color.WHITE, titleTxtColor = Color.BLACK, titleBgColor = Color.WHITE,
            p5Color = Color.LTGRAY, divColor = Color.LTGRAY;

    public Map<String, Integer> tColorMap = null;

    public static final int dip2px(float dip) {
        return DensityUtils.dip2px(App.inst, dip);
    }

    public static final RectF rf(float l, float t, float r, float b) {
        return new RectF(l, t, r, b);
    }

    public static final Rect r(int l, int t, int r, int b) {
        return new Rect(l, t, r, b);
    }

    int iStyleable(String name) {
        LogUtils.e(context.getPackageName() + "." + viewName + name + "-" + context.getResources().getIdentifier(viewName + name, "styleable", context.getPackageName()));
        return context.getResources().getIdentifier(viewName + name, "styleable", context.getPackageName());
    }

//    public int iStyleable(String name) {
//        try {
//            if (context == null)
//                return 0;
//            // use reflection to access the resource class
//            Field field = Class.forName(context.getPackageName() + ".R$styleable").getDeclaredField(viewName + name);
//            int ret = (Integer) field.get(null);
//            LogUtils.e(viewName + name + "-" + ret);
//            return ret;
//        } catch (Throwable t) {
//        }
//        return 0;
//    }

    int iAttr(String name) {
        LogUtils.e("attr-" + context.getResources().getIdentifier(name, "attr", context.getPackageName()));
        return context.getResources().getIdentifier(name, "attr", context.getPackageName());
    }

    protected String viewName = "LuZhuView";
    protected Context context = null;
    protected TypedArray attr;

    int i(String name, int def) {
        return attr.hasValue(iStyleable(name)) ? attr.getInt(iStyleable(name), def) : def;
    }

    float f(String name, float def) {
        return attr.hasValue(iStyleable(name)) ? attr.getFloat(iStyleable(name), def) : def;
    }

    int c(String name, int def) {
        return attr.hasValue(iStyleable(name)) ? attr.getColor(iStyleable(name), def) : def;
    }

    float d(String name, float def) {
        return attr.hasValue(iStyleable(name)) ? attr.getDimension(iStyleable(name), def) : def;
    }

    String[] attNames = {"divWidth", "resTxtSize", "titleHeight", "titleTxtSize", "p5Color", "divColor",
            "resBgColor", "titleBgColor", "titleTxtColor"};

    private LvMgr去(String tag, Context cxt, AttributeSet attrs) {
        viewName = tag;
        context = cxt;
        int[] attIds = new int[attNames.length];
        for (int i = 0; i < attIds.length; i++) {
            attIds[i] = iAttr(attNames[i]);
        }
        LogUtils.e("LvMgrLvMgrLvMgrLvMgr-" + context.getResources().getIdentifier("LuZhuView_p5Color", "styleable", context.getPackageName()));


        attr = context.obtainStyledAttributes(attrs, attIds);
//        int attrId = context.getResources().getIdentifier(viewName, "attr", context.getPackageName());
//        attr = context.obtainStyledAttributes(attrs, new int[]{attrId});
        divWidth = dip2px(d("_divWidth", 0.2f));
        resTxtSize = dip2px(d("_resTxtSize", 14f));
        titleHeight = dip2px(d("_titleHeight", 35f));
        titleTxtSize = dip2px(d("_titleTxtSize", 14f));

        p5Color = c("_p5Color", Color.LTGRAY);
        divColor = c("_divColor", Color.LTGRAY);
        resBgColor = c("_resBgColor", Color.WHITE);
        titleBgColor = c("_titleBgColor", Color.WHITE);
        titleTxtColor = c("_titleTxtColor", Color.BLACK);

        LogUtils.e(toString());

        attr.recycle();
        attr = null;
        context = null;
    }


    public int tColor(String txt) {
        return tColorMap.get(txt.substring(0, 1));
    }

    public LvMgr去 cMaps(Map<String, Integer> maps) {
        tColorMap = maps;
        return this;
    }

    public static final LvMgr去 create(String tag, Context context, AttributeSet attrs, Map<String, Integer> maps) {
        return new LvMgr去(tag, context, attrs).cMaps(maps);
    }

    public static final LvMgr去 create(String tag, Context context, AttributeSet attrs) {
        return new LvMgr去(tag, context, attrs);
    }

}
