package com.caimao.luzhu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;
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

public class LuZhuSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    protected SurfaceHolder mHolder;
    protected Context mCxt;
    protected LuZhuItemViewMgr mLuZhuMgr;

    public LuZhuSurfaceView(Context context) {
        this(context, null);
        Log.e("LuZhuSurfaceView", "LuZhuSurfaceView(Context context)");
    }

    public LuZhuSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        Log.e("LuZhuSurfaceView", "LuZhuSurfaceView(Context context, AttributeSet attrs)");
    }

    DrawThread mThread;

    void init(Context context, AttributeSet attrs) {
        mCxt = context;
        mLuZhuMgr = LuZhuItemViewMgr.create(context, attrs);
        mHolder = getHolder();
        mHolder.addCallback(this); // 添加surface回调函数
        mThread = new DrawThread(mHolder);
        Log.e("LuZhuSurfaceView", "init(Context context, AttributeSet attrs)");
        isInEditMode();
    }

    public void setColorMap(Map<String, Integer> tColorMap) {
        mLuZhuMgr.cMaps(tColorMap);
    }

    public void setData(SurfaceHolder holder) {


//        Canvas canvas = getHolder().lockCanvas(null);//获取画布
//        Paint mPaint = new Paint();
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawRect(new RectF(100, 100, getWidth() - 100, getHeight() - 100), mPaint);
//        getHolder().unlockCanvasAndPost(canvas);//解锁画布，提交画好的图像

//        if (mCacheTask != null) {
//            mCacheTask.clean();
//            if (mCacheTask.getStatus() != AsyncTask.Status.FINISHED) {
//                mCacheTask.cancel(true);
//            }
//        }
//        mCacheTask = null;
////        mCacheTask =

//        new AsyncTask<SurfaceHolder, Object, Object>() {
//            @Override
//            protected Object doInBackground(SurfaceHolder... params) {
//                Paint mPaint = new Paint();
//                mPaint.setColor(Color.YELLOW);
//                Canvas mCacheCanvas = params[0].lockCanvas(LuZhuItemViewMgr.r(100, 100, 720, 700));
//                mCacheCanvas.drawRect(new RectF(100, 100, 720 - 100, 700 - 100), mPaint);
//                params[0].unlockCanvasAndPost(mCacheCanvas);
//                return null;
//            }
//        }.execute(getHolder());

//        mThread.start();
        new LuZhuCacheTasks(mCxt, mLuZhuMgr)
                .execute(holder);
////                .execute(item, mostColumnCnt, this);
    }

    class DrawThread extends Thread {

        SurfaceHolder mHolder = null;

        public DrawThread(SurfaceHolder holder) {
            mHolder = holder;
        }

        @Override
        public void run() {
            super.run();

            Paint mPaint = new Paint();
            mPaint.setColor(Color.YELLOW);
            Canvas mCacheCanvas = mHolder.lockCanvas(LuZhuItemViewMgr.r(5, 5, 720, 700));
            mCacheCanvas.drawRect(new RectF(5, 5, 720 - 100, 700 - 100), mPaint);
            mHolder.unlockCanvasAndPost(mCacheCanvas);

        }
    }

    public void setData(LuZhuModel item, int mostColumnCnt) {

        Canvas canvas = getHolder().lockCanvas(null);//获取画布
        Paint mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new RectF(100, 100, getWidth() - 100, getHeight() - 100), mPaint);
        getHolder().unlockCanvasAndPost(canvas);//解锁画布，提交画好的图像

//        if (mCacheTask != null) {
//            mCacheTask.clean();
//            if (mCacheTask.getStatus() != AsyncTask.Status.FINISHED) {
//                mCacheTask.cancel(true);
//            }
//        }
//        mCacheTask = null;
//        mCacheTask = new LuZhuCacheTasks(mCxt, mLuZhuMgr, getHolder().lockCanvas())
//                .execute(item, mostColumnCnt, this);
    }

    //    Bitmap mCacheBitMap;
    LuZhuCacheTasks mCacheTask;

    @Override
    public void surfaceCreated(SurfaceHolder holder) {


        Log.e("LuZhuSurfaceView", "surfaceCreated(SurfaceHolder holder)");
        Canvas canvas = holder.lockCanvas(null);//获取画布
        Paint mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(new RectF(0, 0, getWidth(), getHeight()), mPaint);
        holder.unlockCanvasAndPost(canvas);//解锁画布，提交画好的图像


        setData(holder);
        Log.e("surfaceCreated", "sdfsdfsd");
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
