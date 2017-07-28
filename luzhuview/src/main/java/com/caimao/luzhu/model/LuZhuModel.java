package com.caimao.luzhu.model;

import java.io.Serializable;
import java.util.List;

public class LuZhuModel implements Serializable {
    public String name; // 打个路珠的名字
    public List<LuZhuExtDataModel> extData;
    //	public String closeTotal;
    public List<String> resData = null;
    public int mostResCnt = 0;
//	public int fCount;
//	public int sCount;
//	public int fTotalCount;
//	public List<LuZhuDataModel> data;

    public String getTitleStr() {
        String extStr = "";
        for (LuZhuExtDataModel ext : extData) {
            extStr += ext.getExtStr();
        }
        return " " + name + "    " + "今日累计：" + extStr.replace("Y", "总来").replace("N", "没来").replace("y", "总来").replace("n", "没来");
    }
}
