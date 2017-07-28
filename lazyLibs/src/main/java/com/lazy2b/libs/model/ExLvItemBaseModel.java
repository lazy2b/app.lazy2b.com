/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.model
 * 文件名:ExLvItemBaseModel.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.model;

import java.util.List;

/**
 * 类名: ExLvItemBaseModel <br/>
 * 描述: 可展开列表中列表项，里面包含子列表项对象. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class ExLvItemBaseModel<T extends List<?>> extends BaseModel {
	/**
	 * 子列表项对象,需为List<?>
	 */
	public T mChildList;
}
