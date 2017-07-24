package com.lazy2b.app;

import android.graphics.Color;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.lazy2b.app.luzhu.LuZhuModel;
import com.lazy2b.app.luzhu.RespLuZhuModel;
import com.lazy2b.libs.app.BaseHttpActivity;
import com.lazy2b.libs.model.RespBaseModel;
import com.lazy2b.libs.utils.DensityUtils;
import com.lidroid.xutils.exception.HttpException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends BaseHttpActivity {

//    public static final int rows = 100;

//    RecyclerView rv_66;

    LinearLayout ll_conatior;
    List<?> mList;

    protected long tTs(String date) {
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
        setContentView(R.layout.activity_main2);
        super.onCreate();
        String jsonCache = App.spu().getString("lzdata", "");
//        if (TextUtils.isEmpty(jsonCache)) {
        get("dsd", "http://m.1396mo.com/pk10/BigorSmallRoadmap?version=3000&timestamp="
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

    LinearLayout lin_layout;

    @Override
    public void initView() {
        ll_conatior = (LinearLayout) findViewById(R.id.ll_conatior);
        mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.hor_scroll);
        lzv = (LuZhuView) findViewById(R.id.lzv_main);
        lin_layout = (LinearLayout) findViewById(R.id.lin_layout);
        mScrollView = (ScrollView) findViewById(R.id.srollview);
        ViewTreeObserver vto2 = mScrollView.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHorizontalScrollView.setMinimumHeight(mScrollView.getHeight());
            }
        });
        lzv.setColorMap(COLOR);
//        rv_66 = (RecyclerView) findViewById(R.id.rv_66);
    }

    int i = 0;
    ScrollView mScrollView;
    HorizontalScrollView mHorizontalScrollView;
    LuZhuView lzv;

    protected List<LuZhuModel> fillEmptyModel(List<LuZhuModel> items) {
        int mostColumnCnt = 0;
        for (LuZhuModel item : items) {
            if (item.resData.size() > mostColumnCnt) {
                mostColumnCnt = item.resData.size();
            }
        }
        for (LuZhuModel item : items) {
            if (item.resData.size() < mostColumnCnt) {
                int lostCnt = mostColumnCnt - item.resData.size();
                for (int i = 0; i < lostCnt; i++) {
                    item.resData.add(0, " ");
                }
            }
        }
        return items;
    }

    @Override
    public void onSuccess(RespBaseModel respBaseModel) {
        if (!TextUtils.isEmpty(respBaseModel.str)) {
            App.spu().putString("lzdata", respBaseModel.str);
        }
        RespLuZhuModel reap = (RespLuZhuModel) respBaseModel;
//        mList = ;

//        lin_layout.setLayoutParams(new FrameLayout.LayoutParams(600,1000));

//        ll_conatior.addView(lv);

//        hor_scroll.setMinimumWidth(1000);
//
//        hor_scroll.addView(lv);
        lzv.setBackgroundColor(Color.LTGRAY);
//        lzv.setLayoutParams(new LinearLayout.LayoutParams(600, 1000));
        lzv.setData(fillEmptyModel((List<LuZhuModel>) reap.getList()));
//        lzv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (i++ % 2 == 0) {
//                    lzv.setData(null);
//                } else {
//                    lzv.setData((List<LuZhuModel>) mList);
//                }
////
//            }
//        });

//        GridLayoutManager glm = new GridLayoutManager(this, reap.mostColumnCnt);, lvLp
//        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return position % (reap.mostColumnCnt + 1) == 0 ? reap.mostColumnCnt : 1;
//            }
//        });
//        rv_66.setLayoutManager(glm);
//        rv_66.setAdapter(new LuZhuAdapter(this, mList));
    }

    @Override
    public void onFailure(String s, HttpException e, String s1) {

    }
}
