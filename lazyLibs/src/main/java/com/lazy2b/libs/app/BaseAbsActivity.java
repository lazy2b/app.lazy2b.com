/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseAbsActivity.java
 * 创  建:2015年10月20日下午4:04:30
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.utils.ActStack;
import com.lazy2b.libs.utils.ActStack.IActStackHandler;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;

/**
 * 类名: BaseAbsActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@Deprecated
public abstract class BaseAbsActivity extends Activity implements IContainerInit, IActStackHandler, ILazyBase {

	protected Context mCxt = null;

	/**
	 * 界面普通处理句柄
	 */
	protected Handler mUIHandler = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onCreate();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// LogUtils.i(getTag() + "onRestart()");
	}

	protected void onCreate() {
		if (preOnCreate()) {

			addToActStack();

		} else {

		}
	}

	@Override
	public void addToActStack() {
		ActStack.add(this);
	}

	@Override
	public void delByActStack() {
		ActStack.remove(this);
	}

	@Override
	public boolean preOnCreate() {
		return true;
	}

	@Override
	public void init() {

		initData();

		findView();

		initView();

	}

	@Override
	public void initView() {
	}

	@Override
	public void findView() {

		try {
			ViewUtils.inject(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化页面要使用到的数据
	 */
	public void initData() {

		mCxt = this;

	}

	@Override
	protected void onDestroy() {

		super.onDestroy();

		delByActStack();

	}

	protected String str(int resId) {
		try {
			return getString(resId);
		} catch (Exception e) {
			return "";
		}
	}

}
