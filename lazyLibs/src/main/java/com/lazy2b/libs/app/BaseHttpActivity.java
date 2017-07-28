/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseHttpActivity.java
 * 创  建:2015年10月22日上午10:38:37
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseReqCallBack;
import com.lazy2b.libs.utils.ActStack;
import com.lazy2b.libs.utils.ActStack.IActStackHandler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

/**
 * 类名: BaseHttpActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public abstract class BaseHttpActivity<T> extends Activity
		implements IContainerInit, IActStackHandler, IHttpHandler, ILazyBase {
	/**
	 * Context对象
	 */
	protected Context mCxt = null;
	/**
	 * 界面普通处理句柄
	 */
	protected Handler mUIHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCxt = this;
		onCreate();
	}

	/**
	 * 自定义onCreate方法
	 */
	protected void onCreate() {
		if (preOnCreate()) {

			addToActStack();

			init();

		} else {

		}
	}

	/**
	 * 加入到Activity堆栈
	 */
	@Override
	public void addToActStack() {
		ActStack.add(this);
	}

	/**
	 * 从Activity堆栈中移除
	 */
	@Override
	public void delByActStack() {
		ActStack.remove(this);
	}

	@Override
	public boolean preOnCreate() {
		return true;
	}

	/**
	 * 集中调用初始化流程方法
	 */
	@Override
	public void init() {

		initData();

		findView();

		initView();

	}

	// @Override
	// public void findView() {
	//
	// try {
	// ViewUtils.inject(this);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	@Override
	protected void onDestroy() {

		super.onDestroy();

		delByActStack();

	}

	/**
	 * 根据字符串资源id获取字符串
	 * 
	 * @param resId
	 * @return
	 */
	protected String str(int resId) {
		try {
			return getString(resId);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	public void post(String action, String url, Class<?> retDataModelCls, Object bodyUnfixParms, RespDataType... rdt) {
		Http.post(url, bodyUnfixParms, new BaseReqCallBack(action, retDataModelCls, this, rdt));
	}

	@Override
	public void get(String action, String url, Class<?> retDataModelCls, RespDataType... rdt) {
		Http.get(url, new BaseReqCallBack(action, retDataModelCls, this, rdt));
	}

	@Override
	public void onLoading(String action, long total, long current, boolean isUploading) {
	}

	@Override
	public void onReqStart(String action) {
	}

}
