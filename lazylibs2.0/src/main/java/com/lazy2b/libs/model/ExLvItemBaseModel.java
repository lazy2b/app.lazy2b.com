package com.lazy2b.libs.model;

import java.util.List;

/**
 * 类名: ExLvGroupBaseModel <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
@SuppressWarnings("serial")
public class ExLvItemBaseModel<T extends List<?>> extends BaseModel {
	public T mChildList;
}
