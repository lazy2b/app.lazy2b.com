package com.caimao.luzhu.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.caimao.luzhu.model.LuZhuModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.lazy2b.app
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuItemView extends ImageView implements LuZhuCacheTask.OnDrawCompleteListener {
    protected Context mCxt;
    protected LuZhuItemViewMgr mLuZhuMgr;

    public LuZhuItemView(Context context) {
        this(context, null);
    }

    public LuZhuItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LuZhuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs) {
        mCxt = context;
        mLuZhuMgr = LuZhuItemViewMgr.create(context, attrs);
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

    Bitmap mCacheBitMap;
    LuZhuCacheTask mCacheTask;

    /**
     * 质量压缩方法
     *
     * @param image
     * @return
     */
    public Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 900;

        while (baos.toByteArray().length / 1024 > 1000) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset(); // 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 100;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        try {
            isBm.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public boolean onDrawComplete(Bitmap cache) {

        int cWidth = cache.getWidth();
        int cHeight = cache.getHeight();
        int scWidth = mCxt.getResources().getDisplayMetrics().widthPixels;
        int lastWidth = cWidth % scWidth;
        int cnt = cWidth / scWidth;// + (lastWidth > 0 ? 1 : 0);

        Log.e("LuZhuView.onDrawComple", cache.getWidth() + ":" + cache.getHeight() + "-size:" + (cache.getRowBytes() * cache.getHeight()));

        Bitmap[] maps = new Bitmap[cnt + (lastWidth > 0 ? 1 : 0)];
        int i = 0;
        if (lastWidth > 0) {
            maps[i++] = Bitmap.createBitmap(cache, lastWidth, 0, lastWidth, cHeight-1);
        }
        for (; i < cnt; i++) {
            maps[i] = Bitmap.createBitmap(cache, lastWidth + i * scWidth, 0, scWidth, cHeight-1);
        }



        mCacheBitMap = maps[1];


//        Log.e("LuZhuView.onDrawComple", cache.getWidth() + ":" + cache.getHeight() + "-size:" + (cache.getRowBytes() * cache.getHeight()));
//        mCacheBitMap = Bitmap.createBitmap(compressImage(cache));////Bitmap.createBitmap(cache,0,0,1080,cache.getHeight());//cache.copy(Bitmap.Config.RGB_565, true);
//        setLayoutParams(
//                getParent() instanceof LinearLayout ?
//                        new LinearLayout.LayoutParams(mCacheBitMap.getWidth(), mCacheBitMap.getHeight()) :
//                        (
//                                getParent() instanceof RelativeLayout ?
//                                        new RelativeLayout.LayoutParams(mCacheBitMap.getWidth(), mCacheBitMap.getHeight()) :
//                                        new FrameLayout.LayoutParams(mCacheBitMap.getWidth(), mCacheBitMap.getHeight())
//                        )
//        );
////        Log.e("LuZhuView", mCacheBitMap.getWidth() + ":" + mCacheBitMap.getHeight() + "-size:" + (mCacheBitMap.getRowBytes() * mCacheBitMap.getHeight()));
        setImageBitmap(mCacheBitMap);
//        setIm
        invalidate();
        return true;
    }


}
