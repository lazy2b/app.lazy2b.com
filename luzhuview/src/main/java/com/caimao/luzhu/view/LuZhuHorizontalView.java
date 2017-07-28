package com.caimao.luzhu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.caimao.luzhu.model.LuZhuModel;

import java.util.Map;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.caimao.luzhu.view
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuHorizontalView extends LinearLayout implements LuZhuCacheTask.OnDrawCompleteListener {

    protected Context mCxt;
    protected LuZhuItemViewMgr mLuZhuMgr;

    public LuZhuHorizontalView(Context context) {
        this(context, null);
    }

    public LuZhuHorizontalView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    int cWidth, cHeight, scWidth, lastWidth;

    void init(Context context, AttributeSet attrs) {
        mCxt = context;
        mLuZhuMgr = LuZhuItemViewMgr.create(context, attrs);
        scWidth = mCxt.getResources().getDisplayMetrics().widthPixels;
        setOrientation(LinearLayout.HORIZONTAL);
        setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        isInEditMode();
    }

    public void setColorMap(Map<String, Integer> tColorMap) {
        mLuZhuMgr.cMaps(tColorMap);
    }

    public void setData(LuZhuModel item, int mostColumnCnt) {
        if (mCacheTask != null) {
            mCacheTask.clean();
            if (mCacheTask.getStatus() != AsyncTask.Status.FINISHED) {
                mCacheTask.cancel(true);
            }
        }
        mCacheTask = null;
        mCacheTask = new LuZhuCacheTask(mCxt, mLuZhuMgr).execute(item, mostColumnCnt, this);
    }

    //    Bitmap mCacheBitMap;
    LuZhuCacheTask mCacheTask;

    ImageView image(Bitmap tmp) {
        ImageView iv = new ImageView(mCxt);
        iv.setLayoutParams(new LayoutParams(tmp.getWidth(), tmp.getHeight()));
        iv.setImageBitmap(tmp);
        return iv;
    }

//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Paint mPaint = new Paint();
//        int i = 0;
//        if (lastWidth > 0) {
//            canvas.drawBitmap(cacheMaps[i++], 0, 0, mPaint);
//        }
//        for (; i < cacheMaps.length; ) {
//            canvas.drawBitmap(cacheMaps[i++], lastWidth + (i - 1) * scWidth, 0, mPaint);
//        }
//    }


    //    Bitmap[] cacheMaps = null;
    @Override
    public boolean onDrawComplete(Bitmap cache) {
        removeAllViews();
        cWidth = cache.getWidth();
        cHeight = cache.getHeight();
        lastWidth = cWidth % scWidth;
        setLayoutParams(
                getParent() instanceof LinearLayout ?
                        new LinearLayout.LayoutParams(cWidth, cHeight) :
                        (
                                getParent() instanceof RelativeLayout ?
                                        new RelativeLayout.LayoutParams(cWidth, cHeight) :
                                        new FrameLayout.LayoutParams(cWidth, cHeight)
                        )
        );
//        int i = 0;
//        cacheMaps = new Bitmap[cWidth / scWidth + lastWidth > 0 ? 1 : 0];
//        if (lastWidth > 0) {
//            cacheMaps[i++] = Bitmap.createBitmap(cache, 0, 0, lastWidth, cHeight);
////            addViecacheMaps[i++] = w(image(Bitmap.createBitmap(cache, 0, 0, lastWidth, cHeight)));
//        }
//        for (; i < cacheMaps.length; ) {
//            cacheMaps[i++] = Bitmap.createBitmap(cache, lastWidth + (i - 1) * scWidth, 0, scWidth, cHeight);
//        }


        if (lastWidth > 0) {

            addView(image(Bitmap.createBitmap(cache, 0, 0, lastWidth, cHeight)));
        }
        int cnt = cWidth / scWidth;
        for (int i = 0; i < cnt; i++) {
            addView(image(Bitmap.createBitmap(cache, lastWidth + i * scWidth, 0, scWidth, cHeight)));
        }
        return true;
    }
}
