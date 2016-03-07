package com.lazy2b.libs.model;

import com.lazy2b.libs.interfaces.IHttpHandler;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;

/**
 * Created by lazy2b on 16/2/27.
 */
public abstract class BaseCallBack<T> extends BaseHttpRequestCallback<T> {

    protected IHttpHandler httpHandler = null;




}
