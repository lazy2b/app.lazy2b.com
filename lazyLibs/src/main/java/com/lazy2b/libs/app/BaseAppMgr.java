/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseAppMgr.java
 * 创  建:2015年10月20日下午4:04:30
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import java.io.File;

import com.lazy2b.libs.app.Http.HttpConfig;
import com.lazy2b.libs.constants.AppData;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.utils.ActStack;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.R;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

/**
 * 类名: AppMgr <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseAppMgr.java 5 2015-11-06 05:20:17Z lazy2b $
 */
public class BaseAppMgr extends Application implements ILazyBase {
    /**
     * DEBUG 标记
     */
    public static boolean d = false;
    /**
     * 应用资源引用
     */
    public static Resources res = null;

    /**
     *
     */ {
        inst = this;
        LogUtils.i("[ App ] 静态代码块 ");
        // try {// 为解决安卓系统BUG[数据处理后不调用onPostExecutes()函数]
        // Class.forName("android.os.AsyncTask");
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    public static void iBug(boolean db) {
        d = db;
        LogUtils.allowD = d;
        LogUtils.allowE = d;
        LogUtils.allowI = d;
        LogUtils.allowW = d;
        LogUtils.allowV = d;
        LogUtils.allowWtf = d;
    }

    /**
     * ApplicationContext 引用
     */
    public static Context cxt;

    /**
     * 本身的引用
     */
    public static BaseAppMgr inst = null;

    public static BaseAppMgr inst() {
        return (BaseAppMgr) inst;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        cxt = this.getApplicationContext();

        init();
    }

    protected int getDefImg() {
        return R.color.darker_gray;
    }

    protected String getCacheImgPath() {
        return "";
    }

    /**
     * 全局Http的配置{@link HttpConfig}
     *
     * @return
     */
    protected HttpConfig getHttpCfg() {
        return null;
    }

    /**
     * 获取网络图片加载框架配置
     *
     * @return {@link ImageLoaderConfiguration}
     */
    @SuppressWarnings("deprecation")
    public ImageLoaderConfiguration getImageConfig() {

        int defImg = getDefImg();
        String cachePath = getCacheImgPath();
        DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnFail(defImg)
                .showImageOnLoading(defImg).showImageForEmptyUri(defImg).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中.build())
                .build();
        if (TextUtils.isEmpty(cachePath)) {
            return new ImageLoaderConfiguration.Builder(cxt).defaultDisplayImageOptions(options).build();
        } else {
            File cacheDir = StorageUtils.getOwnCacheDirectory(this, cachePath);
            return new ImageLoaderConfiguration.Builder(cxt).diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                    .defaultDisplayImageOptions(options).build();
        }
    }

    /**
     * 初始化全局数据，子类添加内容时需首先调用super.init()<br/>
     * 否则AppData、Http、Image、ActStack将不可用， 或引发不可预料的BUG
     */
    protected void init() {

        if (!d) {
            /* 全局异常崩溃处理 */
            CrashHandler catchExcep = new CrashHandler(this);
            Thread.setDefaultUncaughtExceptionHandler(catchExcep);
        }

        AppData.init(cxt);

        Http.init(cxt, getHttpCfg());

        Image.init(cxt, getImageConfig());

        ActStack.init();

    }

    /**
     * 退出应用并杀死进程
     */
    public void exit() {
        ActStack.finishAll();
        LogUtils.i("[ App ] before killProcess");
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 根据资源名字获取资源ID，如根据"tv001" 获取R.id.tv001
     *
     * @param resName 资源名字
     * @param type    资源类型<br/>
     *                [drawable|anim|layout|color|attr|color|dimen|style|styleable|
     *                string]<br/>
     * @return 资源ID
     */
    public static final int rId(String resName, String type) {

        if (res == null) {
            if (cxt != null) {
                res = cxt.getResources();
            } else {
                return 0;
            }
        }

        return res.getIdentifier(resName, type, cxt.getPackageName());
    }

    /**
     * 根据资源名获取图片文件ID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iDraw(String resName) {
        return rId(resName, "drawable");
    }

    /**
     * 根据资源名获取动画ID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iAnim(String resName) {
        return rId(resName, "anim");
    }

    /**
     * 根据资源名获取attrID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iAttr(String resName) {
        return rId(resName, "attr");
    }

    /**
     * 根据资源名获取颜色ID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iColor(String resName) {
        return rId(resName, "color");
    }

    /**
     * 根据资源名获取dimenID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iDimen(String resName) {
        return rId(resName, "dimen");
    }

    /**
     * 根据资源名获取样式ID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iStyle(String resName) {
        return rId(resName, "style");
    }

    /**
     * 根据资源名获取ID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iStyleable(String resName) {
        return rId(resName, "styleable");
    }

    /**
     * 根据资源名获取字符资源ID
     *
     * @param resName 资源名
     * @return
     */
    public static final int iStr(String resName) {
        return rId(resName, "string");
    }

    /**
     * 根据资源名获取ID<br/>
     * 如定义{@code <}TextView android:id="@+id/tv001" ...
     * ,调用iId("tv001"),则返回R.id.tv001的整型值
     *
     * @param resName 资源名
     * @return
     */
    public static final int iId(String resName) {
        return rId(resName, "id");
    }

    /**
     * 根据资源名获取布局ID
     *
     * @param resName 资源名
     * @return 布局ID
     */
    public static final int iLayout(String resName) {
        return rId(resName, "layout");
    }

    /**
     * 根据资源ID获取资源名字符串
     *
     * @param resId 资源ID
     * @return 资源名字符串[如传入R.id.abc，将返回“abc”]
     */
    public static final String nId(int resId) {
        if (res == null) {
            if (cxt != null) {
                res = cxt.getResources();
            } else {
                return "";
            }
        }
        try {
            return res.getResourceEntryName(resId);
        } catch (Resources.NotFoundException e) {
            try {
                return res.getResourceName(resId);
            } catch (Resources.NotFoundException e1) {
                e1.printStackTrace();
            }
        }
        return "";
    }

}
