package com.caimao.luzhu.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.caimao.luzhu.R;
import com.caimao.luzhu.model.LuZhuModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名: HighFrequencyLotteryBox
 * 包 名: com.royaleu.hfl.view
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuContainerView {

    protected static Map<String, Integer> mTxtColor = new HashMap<String, Integer>();

    protected Context mCxt;
    protected ViewGroup mRoot;
    protected LinearLayout mLuZhuContainerView;
    protected LayoutInflater mInflater;
    protected HorizontalScrollView mHorizontalScrollView;

    protected List<LuZhuModel> mDataList;

    protected int mostColumnCnt = 0;

    private LuZhuContainerView(Context cxt, ViewGroup root) {
        mCxt = cxt;
        mRoot = root;
        init();
    }

    public View find(int id) {
        return mRoot.findViewById(id);
    }

    public ViewGroup root() {
        return mRoot;
    }

    public LuZhuContainerView txtColor(Map<String, Integer> txtColor) {
        mTxtColor.putAll(txtColor);
        return this;
    }

    public static final LuZhuContainerView create(Context context, ViewGroup root) {
        return new LuZhuContainerView(context, root);
    }

    @SuppressLint("NewApi")
    void init() {
        mInflater = LayoutInflater.from(mCxt);
        mLuZhuContainerView = (LinearLayout) find(R.id.ll_sh_luzhu);
        mHorizontalScrollView = (HorizontalScrollView) find(R.id.hs_luzhu);
//        root().setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        ViewTreeObserver vto2 = mRoot.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHorizontalScrollView.setMinimumHeight(mRoot.getHeight());
            }
        });
    }

    protected List<LuZhuModel> fillEmptyModel(List<LuZhuModel> items) {
        for (LuZhuModel item : items) {
            if (item.resData.size() > mostColumnCnt) {
                mostColumnCnt = item.resData.size();
            }
        }
        return items;
    }

    public void onDestory() {
        try {
            mostColumnCnt = 0;
            for (int i = 0; i < mLuZhuContainerView.getChildCount(); i++) {
                ((ViewGroup) mLuZhuContainerView.getChildAt(i)).removeAllViews();
            }
            mLuZhuContainerView.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillData(List<LuZhuModel> items) {
//        Toast.makeText(mCxt, "start!", Toast.LENGTH_SHORT).show();
        onDestory();
        mDataList = fillEmptyModel(items);
//        LuZhuItemView lzv;
        LuZhuHorizontalView lzv;
//        long t = System.currentTimeMillis();
        for (LuZhuModel item : mDataList) {
            lzv = (LuZhuHorizontalView) mInflater.inflate(R.layout.tpl_luzhu_hor, null);
            lzv.setColorMap(mTxtColor);
            lzv.setData(item, mostColumnCnt);
            mLuZhuContainerView.addView(lzv);
        }
        mHorizontalScrollView.postDelayed(new Runnable() {
            public void run() {
                mHorizontalScrollView.smoothScrollTo(mLuZhuContainerView.getMeasuredWidth(), 0);
            }
        }, 400);
//        Toast.makeText(mCxt, "complete!" + (System.currentTimeMillis() - t), Toast.LENGTH_SHORT).show();
    }

}
