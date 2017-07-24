package com.lazy2b.app.luzhu;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.lazy2b.app.LuZhuAdapter;

import java.util.ArrayList;
import java.util.List;


public class RespLuZhuModel extends RespCommModel implements ExtraProcessor {
    public List<LuZhuModel> items;
    /**
     * 记录当前路珠数据最大列数
     */
    public static int mostColumnCnt = 1;
    List<LuZhuDataModel> nFormat = new ArrayList<>();

    @Override
    public boolean isEmpty() {
        return items == null || items.size() == 0;
    }

    void fillEmptyModel() {
        for (LuZhuModel item : items) {
            nFormat.add(new LuZhuDataModel(LuZhuAdapter.LUZHU_TITLE_VIEW_TYPE, item.getTitleStr(), -1));
            for (String res : item.resData) {
                nFormat.add(new LuZhuDataModel(LuZhuAdapter.LUZHU_RESULT_VIEW_TYPE, res, item.mostResCnt));
            }
            if (item.resData.size() < mostColumnCnt) {
                int lostCnt = mostColumnCnt - item.resData.size();
                for (int i = 0; i < lostCnt; i++) {
                    nFormat.add(0, new LuZhuDataModel(LuZhuAdapter.LUZHU_RESULT_VIEW_TYPE, " ", item.mostResCnt));
                    item.resData.add(0, " ");
                }
            }
        }
    }

    @Override
    public List<?> getList() {
//        if (nFormat == null || nFormat.size() == 0) {
//            fillEmptyModel();
//        }
//        return nFormat;
        return items;
    }

    @Override
    public void processExtra(Object resp, String key, final Object val) {
        if (key.equals("data")) {
            LuZhuModel lzm = (LuZhuModel) resp;
            JSONArray arr = (JSONArray) val;
            int len = arr.size();
            if (len > mostColumnCnt) {
                mostColumnCnt = len;
            }
            if (lzm.resData == null) {
                lzm.resData = new ArrayList<>();
            }
            String res;
            for (int i = 0; i < len; i++) {
                res = "";
                JSONArray dArr = arr.getJSONObject(i).getJSONArray("data");
                int dArrLen = dArr.size();
                if (lzm.mostResCnt < dArrLen) {
                    lzm.mostResCnt = dArrLen;
                }
                for (int j = 0; j < dArrLen; j++) {
                    JSONObject obj = dArr.getJSONObject(j);
                    res += "\n" + obj.getString("result");
                }
                lzm.resData.add(res.substring(1, res.length()));
            }
        }

//		try{
//			List<LuZhuModel> tmpList ;
//			if ("items".equals(key)) {
//				JSONArray optJSONArray = (JSONArray) value;
//				tmpList = new ArrayList<LuZhuModel>();
//
//
//
//				((RespLuZhuModel) response).luzhuArrayList = tmpList;
//
//				// return luzhuArrayList;
//
//			}
//
//		}catch(Exception e){
//			e.printStackTrace();
//		}

    }
}
