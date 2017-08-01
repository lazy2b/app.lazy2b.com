package com.lazy2b.app;

import android.util.Log;
import android.widget.LinearLayout;

import com.caimao.luzhu.model.LuZhuModel;
import com.caimao.luzhu.view.LuZhuCacheTasks;
import com.caimao.luzhu.view.LuZhuSurfaceView;
import com.lazy2b.app.luzhu.RespLuZhuModel;
import com.lazy2b.libs.app.BaseHttpActivity;
import com.lazy2b.libs.model.RespBaseModel;
import com.lidroid.xutils.exception.HttpException;

import java.util.List;

import static com.lazy2b.app.Main2Activity.tTs;

public class Main3Activity extends BaseHttpActivity implements LuZhuCacheTasks.OnDrawCompleteListener {

    LuZhuSurfaceView lsv = null;
    LinearLayout ll_sh_luzhu;

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
    public boolean onDrawComplete(int width, int height) {
        Log.e("Main3Activity", "onDrawComplete(" + width + ", " + height + ")");
//        lsv.setLayoutParams(new LinearLayout.LayoutParams(width+500, getResources().getDisplayMetrics().heightPixels));
//        ll_sh_luzhu.addView(lsv, new LinearLayout.LayoutParams(width, height));
        return false;
    }

    @Override
    public void initData() {

    }

    @Override
    public void findView() {

    }

    @Override
    public void initView() {
        ll_sh_luzhu = (LinearLayout) findViewById(R.id.ll_sh_luzhu);
        Log.e("Main3Activity", "onCreate(Bundle savedInstanceState)");
        lsv = new LuZhuSurfaceView(this);
        lsv.setColorMap(Main2Activity.COLOR);
        lsv.setOnCompleteListener(this);

    }

    protected List<LuZhuModel> fillEmptyModel(List<LuZhuModel> items) {
        for (LuZhuModel item : items) {
            if (item.resData.size() > mostColumnCnt) {
                mostColumnCnt = item.resData.size();
            }
        }
        return items;
    }


    protected List<LuZhuModel> mDataList;

    protected int mostColumnCnt = 0;

    public void onDestory() {
        try {
            mostColumnCnt = 0;
//            for (int i = 0; i < mLuZhuContainerView.getChildCount(); i++) {
//                ((ViewGroup) mLuZhuContainerView.getChildAt(i)).removeAllViews();
//            }
//            mLuZhuContainerView.removeAllViews();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillData(List<LuZhuModel> items) {
        onDestory();
        mDataList = fillEmptyModel(items);
        ll_sh_luzhu.addView(lsv, lsv.setData(mDataList.get(0), mostColumnCnt));
    }

    @Override
    public void onSuccess(RespBaseModel resp) {
        fillData(((RespLuZhuModel) resp).items);
    }

    @Override
    public void onFailure(String action, HttpException error, String msg) {

    }
}
