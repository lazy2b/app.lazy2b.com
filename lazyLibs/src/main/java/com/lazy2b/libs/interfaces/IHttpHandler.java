/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs
 * 文件名:IHttpHandler.java
 * 创  建:2015年10月22日上午10:58:02
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.interfaces;

import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.model.BaseReqCallBack;
import com.lazy2b.libs.model.RespBaseModel;
import com.lidroid.xutils.exception.HttpException;

/**
 * 类名: IHttpHandler <br/>
 * 描述: 关于Http请求的操作约束于此. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: IHttpHandler.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public interface IHttpHandler extends ILazyBase {

	/**
	 * 发送POST请求
	 * 
	 * @param action
	 *            本次请求的标记，用于区分多个请求的情况
	 * @param url
	 *            请求的URL
	 * @param retDataModelCls
	 *            返回数据后的自动解析类，继承自{@link RespBaseModel}
	 * @param bodyUnfixParms
	 *            要post的参数集，用HashMap组装
	 * @param rdt
	 *            <br/>
	 *            返回数据类型标识【可不传，默认为{@link RespDataType#Object}】:<br/>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;枚举 {@link RespDataType#Object} |
	 *            {@link RespDataType#List} | {@link RespDataType#Map}
	 */
	void post(String action, String url, Class<?> retDataModelCls, Object bodyUnfixParms, RespDataType... rdt);

	/**
	 * 发送GET请求
	 * 
	 * @param action
	 *            本次请求的标记，用于区分多个请求的情况
	 * @param url
	 *            请求的URL
	 * @param retDataModelCls
	 *            返回数据后的自动解析类，继承自{@link RespBaseModel}
	 * @param rdt
	 *            <br/>
	 *            返回数据类型标识【可不传，默认为{@link RespDataType#Object}】:<br/>
	 *            &nbsp;&nbsp;&nbsp;&nbsp;枚举 {@link RespDataType#Object} |
	 *            {@link RespDataType#List} | {@link RespDataType#Map}
	 */
	void get(String action, String url, Class<?> retDataModelCls, RespDataType... rdt);

	/**
	 * 加载中。。。
	 * 
	 * @param action
	 *            本次请求的标记，用于区分多个请求的情况
	 * @param total
	 *            本次请求的总数据大小
	 * @param current
	 *            当前进度
	 * @param isUploading
	 *            是否上传中
	 */
	void onLoading(String action, long total, long current, boolean isUploading);

	/**
	 * 开始请求的时候
	 * 
	 * @param action
	 *            本次请求的标记，用于区分多个请求的情况
	 */
	void onReqStart(String action);

	/**
	 * 请求成功的回调，解析过程参考
	 * {@link BaseReqCallBack#onSuccess(com.lidroid.xutils.http.ResponseInfo)}
	 * 
	 * @param resp
	 *            {@link RespBaseModel}
	 */
	void onSuccess(RespBaseModel resp);

	/**
	 * 请求失败的回调
	 * 
	 * @param action
	 *            本次请求的标记，用于区分多个请求的情况
	 * @param error
	 *            失败的异常
	 * @param msg
	 *            失败的MSG
	 */
	void onFailure(String action, HttpException error, String msg);

}