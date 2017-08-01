package com.lazy2b.app;

import android.text.TextUtils;
import android.view.ViewGroup;

import com.caimao.luzhu.view.LuZhuContainerView;
import com.lazy2b.app.luzhu.RespLuZhuModel;
import com.lazy2b.libs.app.BaseHttpActivity;
import com.lazy2b.libs.model.RespBaseModel;
import com.lidroid.xutils.exception.HttpException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Main2Activity extends BaseHttpActivity {

    public static long tTs(String date) {
        return dateToTimetick(date, "yyyy-MM-dd");
    }

    public static long dateToTimetick(String dateStr, String dateFormatStr) {
        try {
            long result = parseDate(dateStr, dateFormatStr).getTime() / 1000;
            return result < 0 ? 1 : result;
        } catch (Exception e) {
            return 0L;//new Date().getTime()/1000;
        }
    }

    /**
     * 用指定的格式字符串
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            Date date = new SimpleDateFormat(format).parse(dateStr);
            return date;
        } catch (ParseException e) {
            // ignore
        }
        return null;
    }

    @Override
    protected void onCreate() {
        setContentView(R.layout.tpl_luzhu_container);
        super.onCreate();
        String jsonCache = App.spu().getString("lzdata", "");
//        if (TextUtils.isEmpty(jsonCache)) {
        get("dsd", "http://m.1396mo.com/pk10/BigorSmallRoadmap?version=3000&timestamp="
//                        +"0"
                        + tTs("2017-07-10")
                , RespLuZhuModel.class);
//        } else {
//            new AsyncTask<Object, String, RespBaseModel>() {
//                @Override
//                protected RespBaseModel doInBackground(Object... params) {
//                    String jStr = (String) params[0];
//                    Class retCls = (Class) params[1];
//                    RespBaseModel resp = null;
//                    if (ExtraProcessor.class.isAssignableFrom(retCls)) {
//                        try {
//                            resp = (RespBaseModel) JSON.parseObject(jStr, retCls,
//                                    (ExtraProcessor) retCls.newInstance());
//                        } catch (Exception e) {
//                            resp = (RespBaseModel) JSON.parseObject(jStr, retCls);
//                        }
//                    } else {
//                        resp = (RespBaseModel) JSON.parseObject(jStr, retCls);
//                    }
//                    return resp;
//                }
//
//                @Override
//                protected void onPostExecute(RespBaseModel resp) {
//                    onSuccess(resp);
//                }
//            }.execute(jsonCache, RespLuZhuModel.class);
//        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void findView() {

    }

    protected static final HashMap<String, Integer> COLOR = new HashMap<String, Integer>() {
        {
            put("大", App.c(App.iColor("red")));
            put("小", App.c(App.iColor("blue")));
            put("单", App.c(App.iColor("red")));
            put("双", App.c(App.iColor("blue")));
            put("龍", App.c(App.iColor("red")));
            put("虎", App.c(App.iColor("blue")));
            put("和", App.c(App.iColor("green")));
            put("中", App.c(App.iColor("red")));
            put("发", App.c(App.iColor("blue")));
            put("白", App.c(App.iColor("orange")));
            put("后", App.c(App.iColor("blue")));
            put("前", App.c(App.iColor("red")));
            put("东", App.c(App.iColor("green")));
            put("南", App.c(App.iColor("red")));
            put("西", App.c(App.iColor("orange")));
            put("北", App.c(App.iColor("blue")));
            put("√", App.c(App.iColor("red")));
            put("×", App.c(App.iColor("blue")));
            put("奇", App.c(App.iColor("red")));
            put("偶", App.c(App.iColor("blue")));
            put("上", App.c(App.iColor("red")));
            put("下", App.c(App.iColor("blue")));
            put("通", App.c(App.iColor("orange")));
            put("吃", App.c(App.iColor("orange")));
            put(" ", App.c(App.iColor("white")));
        }
    };

    LuZhuContainerView mLzView;

    @Override
    public void initView() {
        mLzView = LuZhuContainerView.create(mCxt, (ViewGroup) findViewById(R.id.sv_luzhu)).txtColor(COLOR);
    }

    @Override
    public void onSuccess(RespBaseModel respBaseModel) {
        if (!TextUtils.isEmpty(respBaseModel.str)) {
            App.spu().putString("lzdata", respBaseModel.str);
        }
        mLzView.fillData(((RespLuZhuModel) respBaseModel).items);
    }

    @Override
    public void onFailure(String s, HttpException e, String s1) {

    }
}
