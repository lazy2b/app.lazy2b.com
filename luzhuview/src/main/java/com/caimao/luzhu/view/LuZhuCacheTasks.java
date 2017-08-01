package com.caimao.luzhu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.SurfaceHolder;
import android.widget.LinearLayout;

import com.caimao.luzhu.model.LuZhuModel;

import java.util.List;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.lazy2b.app
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuCacheTasks extends AsyncTask<SurfaceHolder, String, Object> {

    public static final String TEXT_TEMPLATE = "龍";

    public interface OnDrawCompleteListener {
        boolean onDrawComplete(int width, int height);
    }

    void doDraw() {
        if (mItem != null && mItem.resData.size() > 0) {
            /* * 初始化其他变量 * */
            drawTitle(mItem.getTitleStr(),
                    //Color.CYAN
                    mMgr.titleBgColor, mMgr.divColor, LuZhuItemViewMgr.rf(0, 0, mTotalWidth, mMgr.titleHeight));
            /* * 初始化其他变量 * */
            drawResults(mItem.resData, mMgr.titleHeight, mItem.mostResCnt);
        }
    }

    void drawResult(String res, float y, int height, int i) {
        float x;
        int txtLen = 0;//当前路珠长度
        /* * 初始化其他变量 tWidth -tWidth -* */
        drawBackground(
                LuZhuItemViewMgr.rf(
                        x = (i * (mResultWidth)),
                        y,
                        ((i + 1) * mResultWidth),
                        y + height + mMgr.resTxtPadding * 2
                ),
                i % 2 == 0 ? Color.WHITE : mMgr.resBgColor,
                // Color.YELLOW ,
                mMgr.divColor);
        mCacheCanvas.save();
        /* * 初始化其他变量  + mMgr.resTxtPadding* 1.35f* */
        mCacheCanvas.translate(x, y + mMgr.resTxtPadding);
        /* * 初始化其他变量 * */
        drawResult(res).draw(mCacheCanvas);
        /* * 初始化其他变量 * */
        txtLen = res.replaceAll("(\r\n|\r|\n|\n\r)", "").length();
        if (txtLen > 5) {
            draw5p(txtLen, mMgr.p5Color);
        }
        /* * 初始化其他变量 * */
        mCacheCanvas.restore();
    }

    int drawResults(List<String> resList, float y, int mostResCnt) {
        int resHeight = measuredHeight(mostResCnt, TEXT_TEMPLATE) + mMgr.resTxtPadding * 2;//当前路珠最大高度
        int i = 0, e = mMostColumnCnt - resList.size();
        for (; i < e; i++) {
            drawResult(" ", y, resHeight, i);
        }
        for (; i < mMostColumnCnt; i++) {
            drawResult(resList.get(i - e), y, resHeight, i);
        }
        return resHeight;
    }

    void draw5p(int txtLen, int divColor) {
        toDiv(divColor);
        int cnt = txtLen / 5 - (txtLen % 5 == 0 ? 1 : 0);
        for (int i = 0; i < cnt; i++) {
            float y = mResultSingleHeight * 5 * (i + 1);
            mCacheCanvas.drawLine(0, y, mResultWidth, y, mBgPaint);
        }
    }

    void drawTitle(String txt, float x, float y) {
        mCacheCanvas.drawText(txt, x, y, mTitlePaint);
    }

    void drawTitle(String txt, int bgColor, int divColor, RectF rf) {
        drawBackground(rf, bgColor, divColor);
        float y = rf.bottom - mTitlePadding * 1.35f;
        int titleWidth = (int) mTitlePaint.measureText(txt);
        int tmpPadding = (widthPixels - titleWidth) / 2;
        if (rf.right > widthPixels * 2) {
            int cnt = (int) (rf.right / widthPixels);

            if ((rf.right % widthPixels) > (titleWidth + 100)) {
                for (int i = 1; i <= cnt; i++) {
                    drawTitle(txt, (rf.right - (i * widthPixels) + rf.left + tmpPadding), y);
                }
                drawTitle(txt, 0, y);
            } else {
                for (int i = 1; i < cnt; i++) {
                    drawTitle(txt, (rf.right - (i * widthPixels) + rf.left + tmpPadding), y);
                }
                drawTitle(txt, tmpPadding, y);
            }
        } else {
            drawTitle(txt, (rf.right - widthPixels + rf.left + tmpPadding), y);
            if (rf.right % widthPixels > titleWidth) {
                drawTitle(txt, tmpPadding, y);
            }
        }
    }


    void toDiv(int divColor) {
        mBgPaint.setColor(divColor);
        mBgPaint.setStrokeWidth(mMgr.divWidth);
        mBgPaint.setStyle(Paint.Style.STROKE);
    }

    void toBg(int color) {
        mBgPaint.setColor(color);
        mBgPaint.setStyle(Paint.Style.FILL);
    }

    void drawBackground(RectF rf, int color, int divColor) {
        toBg(color);
        mCacheCanvas.drawRect(rf, mBgPaint);
        toDiv(divColor);
        mCacheCanvas.drawRect(rf, mBgPaint);
    }

    StaticLayout drawResult(String txt) {
        mResultPaint.setColor(mMgr.tColor(txt));
        return new StaticLayout(txt, mResultPaint, mResultWidth, Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
    }

    int measuredWidth(String txt) {
        return (int) mResultPaint.measureText(txt.substring(0, 1));
    }

    int measuredHeight(int mostCnt, String singleTxt) {
        String tmp = "";
        for (int i = 0; i < mostCnt; i++) {
            tmp += "\n" + singleTxt;
        }
        tmp = tmp.substring(1);
//        Log.e("LuZhuCacheTask", mostCnt + "+mostCnt+" + tmp);
        return drawResult(tmp).getHeight();
    }

    LinearLayout.LayoutParams measureSize() {
        if (mItem != null && mItem.resData.size() > 0) {
            mResultWidth = measuredWidth(TEXT_TEMPLATE) + mMgr.resTxtPadding * 2;
            mResultSingleHeight = measuredHeight(1, TEXT_TEMPLATE);
            mTotalHeight = mMgr.titleHeight + mResultSingleHeight * mItem.mostResCnt + mMgr.resTxtPadding * 2;
            int tmp = (int) Math.ceil(widthPixels / mResultWidth);
            mMostColumnCnt = tmp > mMostColumnCnt ? (tmp + 1) : mMostColumnCnt;
            mTotalWidth = mMostColumnCnt * mResultWidth;
            /* * 初始化其他变量 * */
            Paint.FontMetrics fm = mTitlePaint.getFontMetrics();
            mTitlePadding = (mMgr.titleHeight - (fm.descent - fm.ascent)) / 2;
        }
        return new LinearLayout.LayoutParams(mTotalWidth, mTotalHeight);
    }

    protected LuZhuItemViewMgr mMgr = null;
    protected float mTitlePadding = 0;
    protected int
            widthPixels = 720,
            heightPixels = 1280,
            mTotalWidth = 720,
            mTotalHeight = 1280,
            mResultWidth = 0,
            mResultSingleHeight = 0;

    protected Context mCxt;
    protected Canvas mCacheCanvas;
    protected TextPaint mResultPaint;
    protected Paint mTitlePaint, mBgPaint;
    protected OnDrawCompleteListener mCompleteListener;

    protected LuZhuModel mItem;
    protected int mMostColumnCnt = 37;

    public LuZhuCacheTasks(Context context, LuZhuItemViewMgr mgr) {
        mMgr = mgr;
        mCxt = context;
        widthPixels = mTotalWidth = mCxt.getResources().getDisplayMetrics().widthPixels;
        heightPixels = mTotalHeight = mCxt.getResources().getDisplayMetrics().heightPixels;
        if (mResultPaint == null) {
            mResultPaint = new TextPaint();
            mTitlePaint = new Paint();
            mBgPaint = new Paint();
             /* * 初始化路珠结果画笔 * */
            mResultPaint.setColor(Color.DKGRAY);
            mResultPaint.setTextSize(mMgr.resTxtSize);
            mResultPaint.setAntiAlias(true);
        /* * 初始化标题画笔 * */
            mTitlePaint.setColor(mMgr.titleTxtColor);
            mTitlePaint.setTextSize(mMgr.titleTxtSize);
            mTitlePaint.setAntiAlias(true);
        }
    }

    public LinearLayout.LayoutParams init(LuZhuModel item, int mostColumnCnt, OnDrawCompleteListener listener) {
        mItem = item;
        mMostColumnCnt = mostColumnCnt;
        mCompleteListener = listener;
        return measureSize();
    }


    @Override
    protected int[] doInBackground(SurfaceHolder... holder) {
        if (holder != null && holder.length > 0 && mItem != null && mItem.resData.size() > 0) {
            mCacheCanvas = holder[0].lockCanvas(new Rect(0, 0, mTotalWidth, mTotalHeight));
            doDraw();
            holder[0].unlockCanvasAndPost(mCacheCanvas);
            if (mCompleteListener != null) {
                if (mCompleteListener.onDrawComplete(mTotalWidth, mTotalHeight)) {
                }
            }
        }
        return null;
    }

}
