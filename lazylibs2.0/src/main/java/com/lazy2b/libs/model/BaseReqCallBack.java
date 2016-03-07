package com.lazy2b.libs.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.lazy2b.libs.constants.RespDataType;
import com.lazy2b.libs.interfaces.IHttpHandler;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lidroid.xutils.util.LogUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 类名: BaseReqCallBack <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 * 
 * @author E-mail:jack.lin@qq.com
 * @version $Id: BaseReqCallBack.java 3 2015-10-28 03:27:15Z lazy2b $
 */
public class BaseReqCallBack implements Callback,ILazyBase {

	public final static int ERROR = 3;

	public String action = null;

	protected Class<?> retCls = null;

	protected RespBaseModel resp = null;

	protected IHttpHandler handler = null;

	protected RespDataType retType = RespDataType.Object;

	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler) {
		this.action = _act;
		this.retCls = _cls;
		this.handler = _handler;
	}

	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler, RespDataType... rdt) {
		this(_act, _cls, _handler);
		if (rdt != null && rdt.length > 0) {
			this.retType = rdt[0];
		}
	}

	public BaseReqCallBack(String _act, Class<?> _cls, IHttpHandler _handler, RespDataType _type) {
		this(_act, _cls, _handler);
		this.retType = _type;
	}

	@Deprecated
	public void onLoading(long total, long current, boolean isUploading) {
		if (handler != null) {
			handler.onLoading(action, total, current, isUploading);
		}
	}

	@Deprecated
	public void onStart() {
		if (handler != null) {
			handler.onReqStart(action);
		}
	}

//	@Deprecated
//	public void onFailure(HttpException error, String msg) {
//		try {
////			LogUtils.d("[ reqFailure ] action->" + action + " | msgsg->" + msg + " | error->" + error.getMessage());
//			if (handler != null) {
//				handler.onFailure(action, error, msg);
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public void onFailure(Call call, IOException e) {
		try {
//			LogUtils.d("[ reqFailure ] action->" + action + " | msgsg->" + msg + " | error->" + error.getMessage());
			if (handler != null) {
				handler.onFailure(action, e, call, null);
			}

		} catch (Exception ex) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResponse(Call call, Response response) throws IOException {
		try {

			LogUtils.d("[ onResponse.respInfo ] action->" + action + " | respStr->" + response.body().string() + " | statusCode->"
					+ response.code());

			if (retCls == null)
				return;

			String result = response.body().string();

			if (TextUtils.isEmpty(result)) {
				LogUtils.d("返回数据为空！");
				resp = new RespBaseModel();
				resp.respEmpty = true;
				resp.reqAction = action;
				if (handler != null) {
					handler.onSuccess(resp);
				}
			} else if (retCls != null) {
				try {
					switch (retType) {
						case Object:
							if (ExtraProcessor.class.isAssignableFrom(retCls)) {
								try {
									resp = (RespBaseModel) JSON.parseObject(result, retCls,
											(ExtraProcessor) retCls.newInstance());
								} catch (Exception e) {
									resp = (RespBaseModel) JSON.parseObject(result, retCls);
								}
							} else {
								resp = (RespBaseModel) JSON.parseObject(result, retCls);
							}
							break;
						case List:
							resp = new RespBaseModel();
							resp.list = JSON.parseArray(result, retCls);
							break;
						case Map:
							resp = new RespBaseModel();
							resp.map = (Map<?, ?>) JSON.parseObject(result, Map.class,
									new MapExtraProcessor(retCls));
							break;
						default:
							break;
					}

					resp.respStr = result;
					resp.reqAction = action;

					if (handler != null) {
						handler.onSuccess(resp);
					}

				} catch (Exception e) {

					LogUtils.i("解析返回数据异常！");

					e.printStackTrace();

					if (handler != null) {
						handler.onFailure(action, e, call, response);
					}

				}
			}

			return;
			// }

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
