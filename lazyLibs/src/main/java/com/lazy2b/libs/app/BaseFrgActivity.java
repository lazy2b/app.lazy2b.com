/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseFrgActivity.java
 * 创  建:2015年10月22日上午10:38:37
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IFrgMutualHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.interfaces.IV4FragmentHandler;
import com.lazy2b.libs.utils.ActStack;
import com.lazy2b.libs.utils.ActStack.IActStackHandler;
import com.lazy2b.libs.view.BaseViewHolder;
import com.lidroid.xutils.ViewUtils;

/**
 * 类名: BaseFrgActivity <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseFrgActivity.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public abstract class BaseFrgActivity<T> extends FragmentActivity
		implements IContainerInit, IActStackHandler, IV4FragmentHandler, IFrgMutualHandler, ILazyBase {

	protected int mFrgResId = -1;

	protected FragmentManager mFrgManager = null;

	protected abstract int getFrgResId();

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

	/**
	 * 
	 */
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
	 * 
	 * @return
	 */
	@Override
	public void initData() {

		mCxt = this;

		mUIHandler = new Handler();

		mFrgManager = frgManager();

		mFrgResId = getFrgResId();

	}

	protected String str(int resId) {
		try {
			return getString(resId);
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	protected void onDestroy() {

		try {
			delByActStack();
		} catch (Exception e) {

		}

		super.onDestroy();

	}

	@Override
	public FragmentManager frgManager() {
		return getSupportFragmentManager();
	}

	@Override
	public FragmentTransaction frgTrans() {
		return frgManager().beginTransaction();
	}

	@Override
	public int addFrg(Fragment frg, String tag) {
		return frgTrans().add(mFrgResId, frg, tag).commit();
	}

	@Override
	public int addFrgBackStack(Fragment frg) {
		return frgTrans().add(mFrgResId, frg).addToBackStack(null).commit();
	}

	@Override
	public int addFrg(Fragment frg) {
		return frgTrans().add(mFrgResId, frg).addToBackStack(null).commit();
	}

	@Override
	public int addFrgBackStack(Fragment frg, String tag) {
		return frgTrans().add(mFrgResId, frg, tag).addToBackStack(tag).commit();
	}

	@Override
	public Fragment findFrg(int id) {
		return frgManager().findFragmentById(id);
	}

	@Override
	public Fragment findFrg(String tag) {
		return frgManager().findFragmentByTag(tag);
	}
}
