/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:BaseHttpFragment.java
 * 创  建:2015年10月22日上午10:38:37
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.app;

import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IContainerInit;
import com.lazy2b.libs.interfaces.IFrgMutualHandler;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseReqCallBack;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类名: BaseHttpFragment <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public abstract class BaseHttpFragment<T> extends Fragment implements IHttpHandler, IContainerInit, ILazyBase {

	protected Context mCxt = null;

	/**
	 * 界面普通处理句柄
	 */
	protected Handler mUIHandler = null;

	/**
	 * 跟FragmentActivity的交互对象句柄
	 */
	protected IFrgMutualHandler mMutualHandler = null;

	/**
	 * 根View对象
	 */
	protected View mRoot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		try {
			mRoot = inflater.inflate(getLayoutResId(), container, false);
			// getView();
		} catch (Exception e) {
			e.printStackTrace();
		}

		findView();

		return mRoot;

	}

	/**
	 * 获取Fragment内部布局文件
	 * 
	 * @return
	 */
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

	// protected void onCreate() {
	// if (preOnCreate()) {
	//
	// // setTheme(0); ViewUtils.inject(this, mRoot);
	//
	// } else {
	//
	// }
	// }

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

	protected void onCreate() {
		if (preOnCreate()) {

			init();

		} else {

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
