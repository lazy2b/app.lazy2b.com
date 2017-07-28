/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseAbsFragment.java
 * 创  建:2015年10月20日下午4:04:30
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IFrgMutualHandler;
import com.lazy2b.libs.interfaces.ILazyBase;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类名: BaseAbsFragment <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@Deprecated
public abstract class BaseAbsFragment extends Fragment implements ILazyBase, IContainerInit {

	protected Context mCxt = null;

	/**
	 * 界面普通处理句柄
	 */
	protected Handler mUIHandler = null;

	protected IFrgMutualHandler mMutualHandler = null;

	protected View mRoot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mRoot = inflater.inflate(getLayoutResId(), container, false);

		findView();

		return mRoot;

	}

	protected abstract int getLayoutResId();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCxt = getActivity();
		if (getActivity() instanceof IFrgMutualHandler) {
			mMutualHandler = (IFrgMutualHandler) getActivity();
		}
		mUIHandler = new Handler() {
		};
		initData();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		onCreate();
	}

	protected void onCreate() {
		if (preOnCreate()) {

			// setTheme(0); ViewUtils.inject(this, mRoot);

		} else {

		}
	}

	@Override
	public boolean preOnCreate() {
		return true;
	}

	@Override
	public void init() {
		initView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
