/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.interfaces
 * 文件名:IV4FragmentHandler.java
 * 创  建:2015年10月22日上午10:12:06
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 类名: IV4FragmentHandler <br/>
 * 描述: 关于V4补丁包中FragmentActivity对Fragment进行操作的约束定义于此。 <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IV4FragmentHandler.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface IV4FragmentHandler extends ILazyBase {

	/**
	 * 获取FragmentManager
	 * 
	 * @return
	 */
	public FragmentManager frgManager();

	/**
	 * 获取FragmentTransaction
	 * 
	 * @return
	 */
	public FragmentTransaction frgTrans();

	/**
	 * 添加Fragment
	 * 
	 * @param frg
	 *            要添加的Fragment
	 * @return
	 */
	public int addFrg(Fragment frg);

	/**
	 * 添加Fragment
	 * 
	 * @param frg
	 *            要添加的Fragment
	 * @param tag
	 *            要添加的Fragment的标记
	 * @return
	 */
	public int addFrg(Fragment frg, String tag);

	/**
	 * 添加Fragment到Fragment回退栈
	 * 
	 * @param frg
	 *            要添加的Fragment
	 * @return
	 */
	public int addFrgBackStack(Fragment frg);

	/**
	 * 添加Fragment到Fragment回退栈
	 * 
	 * @param frg
	 *            要添加的Fragment
	 * @param tag
	 *            要添加的Fragment的标记
	 * @return
	 */
	public int addFrgBackStack(Fragment frg, String tag);

	/**
	 * 根据ID查找Fragment
	 * 
	 * @param id
	 * @return
	 */
	public Fragment findFrg(int id);

	/**
	 * 根据标记查找Fragment
	 * 
	 * @param tag
	 * @return
	 */
	public Fragment findFrg(String tag);

}
