/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.utils
 * 文件名:BaseViewHolder.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.view;

import com.lazy2b.libs.interfaces.IEmptyViewHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseModel;
import com.lidroid.xutils.ViewUtils;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * 类名: BaseViewHolder <br/>
 * 描述: ViewHolder，实现界面操作和数据逻辑分离，也可解决界面注入无法继承的问题。. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseViewHolder.java 5 2015-11-06 05:20:17Z lazy2b $
 */
public class BaseViewHolder extends BaseModel implements IEmptyViewHandler, ILazyBase {

	public Context mCxt;

	/***
	 * 页面根布局，一般为系统默认的 android.R.id.context;
	 */
	public View mRoot;
	/**
	 * 与Holder所有者交互的Handler【Activity或者Fragment等】
	 */
	public Handler mHandler;

	/**
	 * 数据为空或者网络不可用时的临时空界面
	 */
	public View mEmpty;

	/**
	 * 装载空界面和内容界面的容器或许为mRoot
	 */
	public ViewGroup mContainer;

	/**
	 * 重新加载数据后的界面处理，包括把隐藏的界面显示，把空界面移除
	 */
	public void reloadByNoNet() {
		try {
			if (mContainer != null && mEmpty != null) {
				if (mContainer.getVisibility() == View.GONE || mContainer.getVisibility() == View.INVISIBLE) {
					((ViewGroup) mContainer.getParent()).removeView(mEmpty);
					mContainer.setVisibility(View.VISIBLE);
				} else {
					mContainer.removeView(mEmpty);
				}
				mEmpty = null;
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 查找View
	 * 
	 * @param id
	 * @return
	 */
	public View findView(int id) {
		if (mRoot != null) {
			return mRoot.findViewById(id);
		}
		return null;
	}

	public ViewGroup getEmptyView(boolean isNoNet) {
		return null;
	}

	public int getEmptyViewInsertIdx() {
		return 1;
	}

	/**
	 * 获取特殊显示的空界面LayoutParams【Root为RelativeLayout时有效】
	 * 
	 * @return
	 */
	public RelativeLayout.LayoutParams getHasRuleRlLp() {
		return null;
	}

	public void showEmptyView(boolean isNoNet) {
		try {
			if (mRoot instanceof ViewGroup) {
				ViewGroup vgRoot = (ViewGroup) mRoot;
				if (mContainer == null) {
					mContainer = vgRoot.getId() == R.id.content ? (ViewGroup) vgRoot.getChildAt(0) : vgRoot;
				}
				if (mEmpty == null) {
					mEmpty = getEmptyView(isNoNet);
				}
				if (mEmpty == null) {
					mContainer = null;
					return;
				}
				if (mContainer instanceof RelativeLayout) {
					RelativeLayout.LayoutParams rlp = getHasRuleRlLp();
					if (rlp == null) {
						rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
								RelativeLayout.LayoutParams.MATCH_PARENT);
					}
					mContainer.addView(mEmpty, mContainer.getChildCount(), rlp);
				} else {
					int idx = getEmptyViewInsertIdx();
					if (idx == -1) {
						try {
							mContainer.setVisibility(View.GONE);
							((ViewGroup) mContainer.getParent()).addView(mEmpty);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						mContainer.addView(mEmpty, idx,
								new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 填充数据，一般于获取完数据时调取
	 * 
	 * @param args
	 */
	public void fill(Object... args) {
	}

	public BaseViewHolder() {
	}

	public BaseViewHolder(Context cxt, View root) {

		init(cxt, root);

	}

	public BaseViewHolder(Context cxt, Handler handler, View root) {

		init(cxt, handler, root);

	}

	/**
	 * 初始化Holder
	 * 
	 * @param cxt
	 * @param root
	 *            根控件，一般为ViewGroup，也可以为其他诸如TextView的普通
	 * @return
	 */
	protected BaseViewHolder init(Context cxt, View root) {
		ViewUtils.inject(this, root);
		mCxt = cxt;
		mRoot = root;
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 
	 * @param cxt
	 * @param handler
	 *            来自拥有者的可操作Handler
	 * @param root
	 *            根控件，一般为ViewGroup，也可以为其他诸如TextView的普通
	 * @return
	 */
	protected BaseViewHolder init(Context cxt, Handler handler, View root) {
		ViewUtils.inject(this, root);
		mCxt = cxt;
		mHandler = handler;
		mRoot = root;
		try {
			onInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * 用于用户初始化时需要进行的操作定义
	 */
	protected void onInit() {
		// Toast.makeText(mCxt, mRoot.getClass().toString(),
		// Toast.LENGTH_SHORT).show();
	}

	/**
	 * 把输入值转换为Color值
	 * @param cArgs
	 *            可为资源ID或颜色字符串
	 * @return Color值【int】
	 */
	public int c(Object cArgs) {
		return cArgs instanceof String ? Color.parseColor((String) cArgs)
				: mCxt.getResources().getColor((Integer) cArgs);
	}

	public String s(Object obj) {
		return obj instanceof String ? (String) obj : mCxt.getString((Integer) obj);
	}

	/**
	 * 根据传入的类型返回一个ViewHolder类的实例， 并进行初始化及界面注入
	 * 
	 * @param cxt
	 * @param root
	 * @param cls
	 * @return
	 */
	public static BaseViewHolder create(Context cxt, View root, Class<? extends BaseViewHolder> cls) {
		try {
			return cls.newInstance().init(cxt, root);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据传入的类型返回一个ViewHolder类的实例， 并进行初始化及界面注入
	 * 
	 * @param cxt
	 * @param handler
	 * @param root
	 * @param cls
	 * @return
	 */
	public static BaseViewHolder create(Context cxt, Handler handler, View root, Class<? extends BaseViewHolder> cls) {
		try {
			return cls.newInstance().init(cxt, handler, root);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
