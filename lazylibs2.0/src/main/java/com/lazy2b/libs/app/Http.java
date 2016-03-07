package com.lazy2b.libs.app;

import android.content.Context;
import android.net.ConnectivityManager;

import com.lazy2b.libs.constants.AppData;
import com.lazy2b.libs.interfaces.ILazyBase;
import com.lazy2b.libs.model.BaseModel;
import com.lidroid.xutils.util.LogUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.finalteam.okhttpfinal.BaseHttpRequestCallback;
import cn.finalteam.okhttpfinal.HttpRequest;
import cn.finalteam.okhttpfinal.RequestParams;
import okhttp3.OkHttpClient;

/**
 * 类名: Http <br/>
 * 描述: TODO. <br/>
 * 功能: TODO. <br/>
 *
 * @author E-mail:jack.lin@qq.com
 * @version $Id$
 */
public class Http implements ILazyBase {

    private static OkHttpClient http;

    private static List<OkHttpClient> tmpHttp;

    public static class HttpConfig extends BaseModel {
        public int timeOut = HTTP_TIMEOUT;
        public int soTimeOut = HTTP_SO_TIMEOUT;
        public int cacheTime = HTTP_CACHE_TIME;
        public String charset = "UTF-8";
        public boolean needCookie = false;
        public String userAgent = "";
    }

    public static String userAgent = "Lazylibs by OkHttp.";

    public static void setUserAgent(String _userAgent) {
        userAgent = _userAgent;
    }

    /**
     * 连接超时：connectionTimeout指的是连接一个url的连接等待时间。
     */
    public static final int HTTP_TIMEOUT = 10 * 1000;
    /**
     * 读取数据超时：soTimeout指的是连接上一个url，获取response的返回等待时间
     */
    public static final int HTTP_SO_TIMEOUT = 10 * 1000;
    public static final int HTTP_CACHE_TIME = 0 * 1000;

    public synchronized static void init(Context cxt) {

        synchronized (String.class) {
            try {
                if (http == null) {
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public synchronized static void init(Context cxt, HttpConfig cfg) {

        synchronized (String.class) {
            try {
                if (http == null) {
                    if (cfg == null) {
                        init(cxt);
                        return;
                    }
                    http = new OkHttpClient.Builder()
                            .connectTimeout(cfg.timeOut, TimeUnit.MILLISECONDS)
                            .readTimeout(cfg.timeOut, TimeUnit.MILLISECONDS)
                            .build();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


//	public synchronized static void init(Context cxt, HttpConfig cfg) {
//		synchronized (String.class) {
//			try {
//				if (http == null) {
//					if (cfg == null) {
//						init(cxt);
//						return;
//					}
//					// 设置请求超时时间为10秒
//					http = new HttpUtils(cfg.timeOut);
//					http.configSoTimeout(cfg.soTimeOut);
//					http.configResponseTextCharset(cfg.charset);
//					http.configUserAgent(cfg.userAgent);
//					// 设置当前请求的缓存时间
//					http.configCurrentHttpCacheExpiry(cfg.cacheTime);
//					// 设置默认请求的缓存时间
//					http.configDefaultHttpCacheExpiry(cfg.cacheTime);
//					// 保存服务器端(Session)的Cookie
//					if (cfg.needCookie) {
//						PreferencesCookieStore cookieStore = new PreferencesCookieStore(cxt);
//						cookieStore.clear(); // 清除原来的cookie
//						http.configCookieStore(cookieStore);
//					}
//					LogUtils.i("初始化[Http]成功！");
//				}
//			} catch (Exception e) {
//				LogUtils.i("初始化[Http]失败！");
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public synchronized static void init(Context cxt) {
//		synchronized (String.class) {
//			try {
//				if (http == null) {
//					// 设置请求超时时间为10秒
//					http = new HttpUtils(HTTP_TIMEOUT);
//					http.configSoTimeout(HTTP_SO_TIMEOUT);
//					http.configResponseTextCharset("UTF-8");
//					http.configUserAgent(usrAgent(cxt));
//					// 设置当前请求的缓存时间
//					http.configCurrentHttpCacheExpiry(HTTP_CACHE_TIME);
//					// 设置默认请求的缓存时间
//					http.configDefaultHttpCacheExpiry(HTTP_CACHE_TIME);
//					// 保存服务器端(Session)的Cookie
//					PreferencesCookieStore cookieStore = new PreferencesCookieStore(cxt);
//					cookieStore.clear(); // 清除原来的cookie
//					http.configCookieStore(cookieStore);
//					LogUtils.i("初始化[Http]成功！");
//				}
//			} catch (Exception e) {
//				LogUtils.i("初始化[Http]失败！");
//				e.printStackTrace();
//			}
//		}
//	}

    public synchronized static void getByTimeout(String url, int timeOut, BaseHttpRequestCallback callback) {
//        tmpHttp =
//        http.newBuilder().readTimeout(timeOut, TimeUnit.MILLISECONDS).connectTimeout(timeOut, TimeUnit.MILLISECONDS).build()
//                .newCall(new Request.Builder()
//                        .header("User-Agent", userAgent)
//                        .url(url) // This URL is served with a 1 second delay.
//                        .get()
//                        .build())
//                .enqueue(callback);
        HttpRequest.get(url, callback);

    }

    public synchronized static void get(String url, BaseHttpRequestCallback callback) {
        LogUtils.d("url==>" + url);
        HttpRequest.get(url, callback);
//        http.newCall(new Request.Builder().tag(callback.action).url(url).header("User-Agent", userAgent).get().build()).enqueue(callback);
    }

    public synchronized static void get(String url, RequestParams params, BaseHttpRequestCallback callback) {
        LogUtils.d("url==>" + url);
        HttpRequest.get(url, params, callback);
    }

    public synchronized static void cancel(String url) {
        LogUtils.d("url==>" + url);
        HttpRequest.cancel(url);
    }

    public synchronized static void post(String url, Object bodyUnfixParms, BaseHttpRequestCallback callBack) {
        LogUtils.d("url==>" + url);
        HttpRequest.post(url, param(bodyUnfixParms), callBack);
//        http.send(HttpRequest.HttpMethod.POST, url, param(bodyUnfixParms), callBack);
    }

    public synchronized static void post(String url, RequestParams params, BaseHttpRequestCallback callBack) {
        LogUtils.d("url==>" + url);
        HttpRequest.post(url, params, callBack);
//        http.send(HttpRequest.HttpMethod.POST, url, param(bodyUnfixParms), callBack);
    }

    public static String usrAgent(Context cxt) {
        return "Mozilla/5.0 (" + cxt.getPackageName() + "/" + AppData.VER_NAME + ") (Android; Android OS "
                + android.os.Build.VERSION.RELEASE + ";" + Locale.getDefault().getLanguage() + ")";
    }

    public static boolean canUsableUrl(String url) {
        boolean result = false;
        BufferedReader in = null;
        try {
            java.net.URL oUrl = new java.net.URL(url);
            in = new BufferedReader(new InputStreamReader(oUrl.openStream()));
            result = in.ready();
        } catch (Exception e) { // Report any errors that arise
            result = false;
            LogUtils.e(e.getMessage());
            LogUtils.e("Usage:   java   HttpClient   <URL>   [<filename>]");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static boolean isAvailable(Context cxt) {
        try {
            if (cxt != null) {
                return ((ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo()
                        .isConnectedOrConnecting();
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static RequestParams param(Object unfixParms) {
        RequestParams params = new RequestParams();
        try {
            if (unfixParms != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> up = (Map<String, Object>) unfixParms;
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                LogUtils.d("本次请求参数如下:");
                for (String key : up.keySet()) {
                    list.add(new BasicNameValuePair(key, up.get(key).toString()));
                    LogUtils.d("key==>" + key + " | value==>" + up.get(key).toString());
                }
//                params.addBodyParameter(list);
            }
        } catch (Exception e) {
        }
        return params;
    }

}