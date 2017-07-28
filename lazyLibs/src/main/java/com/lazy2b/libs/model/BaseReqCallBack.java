/**
 * 项目名:LazyLibs
 * 包  名:com.lazy2b.libs.model
 * 文件名:BaseReqCallBack.java
 * 创  建:2015-10-28上午9:50:58
 * Copyright © 2015, lazy2b.com All Rights Reserved.
 */
package com.lazy2b.libs.model;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

import android.text.TextUtils;

/**
 * 类名: BaseReqCallBack <br/>
 * 描述: Http请求回调基类，在此处自动对返回数据进行了自动解析.
 * <p>
 * 自动解析使用的工具类为
 * {@link <a href="http://git.oschina.net/wenshao/fastjson">FastJSON</a>} <br/>
 * 该版本解析失败时会把{@linkplain com.lidroid.xutils.HttpUtils xUtils.HttpUtils}返回的对象
 * {@link RequestCallBack}赋值予 {@link RespBaseModel#xRi}<br/>
 * 供用户后续自行处理
 * </p>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseReqCallBack.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public class BaseReqCallBack extends RequestCallBack<String> implements ILazyBase {

	public final static int ERROR = 3;

	/**
	 * 当前请求的标记
	 */
	protected String action = null;

	/**
	 * 返回数据自动解析对象类名
	 */
	protected Class<?> retCls = null;

	/**
	 * 解析好的数据
	 */
	protected RespBaseModel resp = null;

	/**
	 * @see IHttpHandler
	 */
	protected IHttpHandler handler = null;
	/**
	 * 返回对象类型，Object 自动解析为retCls的实体类，List 解析为List<retCls>, Map
	 * 
	 * @see RespDataType
	 */
	protected RespDataType retType = RespDataType.Object;

	/**
	 * 构建一个Http请求回调
	 * 
	 * @param _act
	 *            当前请求的标记
	 * @param _cls
	 *            返回数据自动解析对象类名
	 * @param _handler
	 *            请求生命周期处理
	 * @see IHttpHandler
	 */
	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler) {
		this.action = _act;
		this.retCls = _cls;
		this.handler = _handler;
	}

	/**
	 * 构建一个Http请求回调
	 * 
	 * @param _act
	 *            当前请求的标记
	 * @param _cls
	 *            返回数据自动解析对象类名
	 * @param _handler
	 *            请求生命周期处理
	 * @param rdt
	 *            可不填，返回对象类型，Object 自动解析为retCls的实体类，List 解析为List<retCls>, Map
	 * @see IHttpHandler
	 * @see RespDataType
	 */
	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler, RespDataType... rdt) {
		this(_act, _cls, _handler);
		if (rdt != null && rdt.length > 0) {
			this.retType = rdt[0];
		}
	}

	/**
	 * 构建一个Http请求回调
	 * 
	 * @param _act
	 *            当前请求的标记
	 * @param _cls
	 *            返回数据自动解析对象类名
	 * @param _handler
	 *            请求生命周期处理
	 * @param _type
	 *            返回对象类型，Object 自动解析为retCls的实体类，List 解析为List<retCls>, Map
	 * @see IHttpHandler
	 * @see RespDataType
	 */
	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler, RespDataType _type) {
		this(_act, _cls, _handler);
		this.retType = _type;
	}

	/**
	 * 加载中。。。
	 */
	@Override
	public void onLoading(long total, long current, boolean isUploading) {
		if (handler != null) {
			handler.onLoading(action, total, current, isUploading);
		}
	}

	/**
	 * 加载前。。。
	 */
	@Override
	public void onStart() {
		if (handler != null) {
			handler.onReqStart(action);
		}
	}

	/**
	 * 请求成功，自动解析数据
	 */
	@Override
	public void onSuccess(ResponseInfo<String> respInfo) {

		try {
			LogUtils.d("[ onSuccess.respInfo ] action->" + action + " | respStr->" + respInfo.result + " | statusCode->"
					+ respInfo.statusCode);

			if (retCls == null)
				return;

			if (TextUtils.isEmpty(respInfo.result)) {
				LogUtils.d("返回数据为空！");
				resp = new RespBaseModel();
				resp.empty = true;
				resp.action = action;
				// resp.xRi = respInfo;
			} else if (retCls != null) {
				try {
					switch (retType) {
					case Object:
						if (ExtraProcessor.class.isAssignableFrom(retCls)) {
							try {
								resp = (RespBaseModel) JSON.parseObject(respInfo.result, retCls,
										(ExtraProcessor) retCls.newInstance());
							} catch (Exception e) {
								resp = (RespBaseModel) JSON.parseObject(respInfo.result, retCls);
							}
						} else {
							resp = (RespBaseModel) JSON.parseObject(respInfo.result, retCls);
						}
						break;
					case List:
						resp = new RespBaseModel();
						resp.list = JSON.parseArray(respInfo.result, retCls);
						break;
					case Map:
						resp = new RespBaseModel();
						resp.map = (Map<?, ?>) JSON.parseObject(respInfo.result, Map.class,
								new MapExtraProcessor(retCls));
						break;
					default:
						break;
					}

					resp.str = respInfo.result;
					resp.action = action;
					// resp.xRi = respInfo;

				} catch (Exception e) {

					LogUtils.i("解析返回数据异常！");

					e.printStackTrace();

					resp = new RespBaseModel();
					resp.empty = true;
					resp.action = action;
					resp.xRi = respInfo;

				}
			}

			if (handler != null) {
				handler.onSuccess(resp);
			}

			return;
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 请求失败
	 */
	@Override
	public void onFailure(HttpException error, String msg) {
		try {
			LogUtils.d("[ reqFailure ] action->" + action + " | msg->" + msg + " | error->" + error.getMessage());
			if (handler != null) {
				handler.onFailure(action, error, msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
