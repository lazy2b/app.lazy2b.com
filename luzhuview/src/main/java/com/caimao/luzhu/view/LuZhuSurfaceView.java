package com.caimao.luzhu.view;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.caimao.luzhu.model.LuZhuModel;

import java.util.Map;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.caimao.luzhu.view
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    protected Context mCxt;
    protected SurfaceHolder mHolder;
    protected LuZhuItemViewMgr mLuZhuMgr;
    protected LuZhuCacheTasks mLuZhuTask;

    public LuZhuSurfaceView(Context context) {
        this(context, null);
        Log.e("LuZhuSurfaceView", "LuZhuSurfaceView(Context context)");
    }

    public LuZhuSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        Log.e("LuZhuSurfaceView", "LuZhuSurfaceView(Context context, AttributeSet attrs)");
    }


    void init(Context context, AttributeSet attrs) {
        mCxt = context;
        mLuZhuMgr = LuZhuItemViewMgr.create(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this); // 添加surface回调函数
        getHolder().setFormat(PixelFormat.TRANSLUCENT);
        Log.e("LuZhuSurfaceView", "init(Context context, AttributeSet attrs)");
        isInEditMode();
    }

    LuZhuCacheTasks initTask() {
        if (mLuZhuTask != null) {
//            mLuZhuTask.clean();
            if (mLuZhuTask.getStatus() != AsyncTask.Status.FINISHED) {
                mLuZhuTask.cancel(true);
            }
        }
        mLuZhuTask = null;
        mLuZhuTask = new LuZhuCacheTasks(mCxt, mLuZhuMgr);
        return mLuZhuTask;
    }

    public void setColorMap(Map<String, Integer> tColorMap) {
        mLuZhuMgr.cMaps(tColorMap);
    }

    LuZhuCacheTasks.OnDrawCompleteListener mListener;

    public void setOnCompleteListener(LuZhuCacheTasks.OnDrawCompleteListener listener) {
        mListener = listener;
    }

    public synchronized LinearLayout.LayoutParams setData(LuZhuModel item, int mostColumnCnt) {
        return initTask().init(item, mostColumnCnt, mListener);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mLuZhuTask.getStatus() != AsyncTask.Status.FINISHED && mLuZhuTask.getStatus() != AsyncTask.Status.RUNNING) {
            mLuZhuTask.execute(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("LuZhuSurfaceView", "surfaceChanged(SurfaceHolder holder, int format, int width, int height)");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e("LuZhuSurfaceView", "surfaceDestroyed(SurfaceHolder holder)");
    }
}
