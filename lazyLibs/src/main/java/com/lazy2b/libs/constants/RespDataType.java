/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.constants
 * 文件名:RespDataType.java
 * 创  建:2015年11月25日下午4:44:01
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.constants;

/**
 * 类名: RespDataType <br/>
 * 描述: 返回对象类型，Object 自动解析为retCls的实体类，List 解析为List<retCls>, Map. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id: RespDataType.java 80 2016-06-15 06:20:34Z lazy2b $
 */
public enum RespDataType {
	/**
	 * 返回的为常规JSON对象
	 */
	Object,
	/**
	 * 返回的为List对象
	 */
	List,
	/**
	 * 返回的为Map对象
	 */
	Map
}
