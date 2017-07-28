/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IViewHolder.java
 * 创  建:2015-11-7上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import com.lazy2b.libs.view.BaseViewHolder;

import android.content.Context;
import android.os.Handler;
import android.view.View;

/**
 * 类名: IViewHolder <br/>
 * 描述: 界面Holder通用方法约束. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IViewHolder.java 8 2015-11-07 02:27:21Z lazy2b $
 */
public interface IViewHolder extends ILazyBase {

	/**
	 * 填充数据，一般于获取完数据时调取
	 * 
	 * @param args
	 */
	void fill(Object... args);

	/**
	 * 初始化Holder
	 * 
	 * @param cxt
	 * @param root
	 *            根控件，一般为ViewGroup，也可以为其他诸如TextView的普通
	 * @return
	 */
	IViewHolder init(Context cxt, View root);

	/**
	 * 
	 * @param cxt
	 * @param handler
	 *            来自拥有者的可操作Handler
	 * @param root
	 *            根控件，一般为ViewGroup，也可以为其他诸如TextView的普通
	 * @return
	 */
	IViewHolder init(Context cxt, Handler handler, View root);

	/**
	 * 用于用户初始化时需要进行的操作定义
	 */
	void onInit();

}
