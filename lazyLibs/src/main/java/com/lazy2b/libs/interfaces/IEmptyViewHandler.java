/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IEmptyViewHandler.java
 * 创  建:2015年12月30日下午3:00:19
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * 类名: IEmptyViewHandler <br/>
 * 描述: 关于界面上需要显示诸如无网络，无数据时提示界面的操作集中于此. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IEmptyViewHandler.java 80 2016-06-15 06:20:34Z lazy2b $
 */
public interface IEmptyViewHandler extends ILazyBase {

	/**
	 * 在没网重新加载数据后重新处理界面
	 */
	void reloadByNoNet();

	/**
	 * 获取空界面ViewGroup
	 * 
	 * @param isNoNet
	 *            是否因为没有网络而需要该界面
	 * @return
	 */
	ViewGroup getEmptyView(boolean isNoNet);

	/**
	 * 获取特殊显示的空界面LayoutParams【Root为RelativeLayout时有效】
	 * 
	 * @return
	 */
	RelativeLayout.LayoutParams getHasRuleRlLp();

	/**
	 * 获取界面插入层级，即在父类中的索引位置,【Root为非RelativeLayout时有效】
	 * 
	 * @return
	 */
	int getEmptyViewInsertIdx();

	/**
	 * 显示空界面
	 * 
	 * @param isNoNet
	 *            是否没有网络
	 */
	void showEmptyView(boolean isNoNet);

}
