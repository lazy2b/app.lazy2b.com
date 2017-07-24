/**
 * 项目名:CommSrcIsAlibs
 * 包  名:com.royaleu.comm.model
 * 文件名:RespCommModel.java
 * 创  建:2016年6月3日上午10:31:00
 * Copyright © 2016, GDQL All Rights Reserved.
 */
package com.lazy2b.app.luzhu;

import com.lazy2b.libs.model.RespBaseModel;

import java.util.List;

/**
 * 类名: RespCommModel <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:Administrator
 * @version $Id: RespCommModel.java 5028 2016-06-03 03:45:09Z linjp $
 */
public abstract class RespCommModel extends RespBaseModel {
	public String error;

	public abstract boolean isEmpty();

	public abstract List<?> getList();
}
