/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:AppMgr.java
 * 创  建:2015年10月20日下午4:04:30
 * Copyright © 2015, GDQL All Rights Reserved.
 */
package com.lazy2b.libs.app;

import android.R;
import android.app.Application;
import android.content.Context;

import com.lazy2b.libs.BuildConfig;
import com.lazy2b.libs.app.Http.HttpConfig;
import com.lazy2b.libs.constants.AppData;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.utils.ActStack;
import com.lidroid.xutils.util.LogUtils;

import cn.finalteam.okhttpfinal.OkHttpFinal;
import cn.finalteam.okhttpfinal.OkHttpFinalConfiguration;

/**
 * 类名: AppMgr <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: AppMgr.java 5 2015-11-06 05:20:17Z lazy2b $
 */
public class BaseAppMgr extends Application implements ILazyBase {
	public static boolean d = false;

	{
		d = BuildConfig.DEBUG;
		LogUtils.allowD = d;
		LogUtils.allowE = d;
		LogUtils.allowI = d;
		LogUtils.allowW = d;
		LogUtils.allowV = d;
		LogUtils.allowWtf = d;
		inst = this;
		LogUtils.i("[ App ] 静态代码块 ");
		// try {// 为解决安卓系统BUG[数据处理后不调用onPostExecutes()函数]
		// Class.forName("android.os.AsyncTask");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}

	public static Context cxt;

	public static BaseAppMgr inst = null;

	@Override
	public void onCreate() {
		super.onCreate();

		cxt = this.getApplicationContext();

		if (!d) {
			/* 全局异常崩溃处理 */
			CrashHandler catchExcep = new CrashHandler(this);
			Thread.setDefaultUncaughtExceptionHandler(catchExcep);
		}

		init();
	}

	protected int getDefImg() {
		return R.color.darker_gray;
	}

	protected String getCacheImgPath() {
		return "";
	}

	protected HttpConfig getHttpCfg() {
		return null;
	}

	@SuppressWarnings("deprecation")
//	public ImageLoaderConfiguration getImageConfig() {
//
//		int defImg = getDefImg();
//		String cachePath = getCacheImgPath();
//		DisplayImageOptions options = new DisplayImageOptions.Builder().showImageOnFail(defImg)
//				.showImageOnLoading(defImg).showImageForEmptyUri(defImg).cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//				.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中.build())
//				.build();
//		if (TextUtils.isEmpty(cachePath)) {
//			return new ImageLoaderConfiguration.Builder(cxt).defaultDisplayImageOptions(options).build();
//		} else {
//			File cacheDir = StorageUtils.getOwnCacheDirectory(this, cachePath);
//			return new ImageLoaderConfiguration.Builder(cxt).diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
//					.defaultDisplayImageOptions(options).build();
//		}
//	}

	protected void init() {

		AppData.init(cxt);

		Http.init(cxt, getHttpCfg());

		OkHttpFinalConfiguration.Builder builder = new OkHttpFinalConfiguration.Builder();
		OkHttpFinal.getInstance().init(builder.build());

//		Image.init(cxt, getImageConfig());

		ActStack.init();

	}

	public void exit() {
		ActStack.finishAll();
		LogUtils.i("[ App ] before killProcess");
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
