package com.lazy2b.app.luzhu;

import com.lazy2b.libs.model.BaseModel;

/**
 * 项目名: HighFrequencyLotteryBox
 * 包 名: com.royaleu.comm.model.luzhu
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuExtDataModel extends BaseModel {
    public String Name = "";
    public int Count = 0;
    public String getExtStr(){
        return Name+"("+Count+")";
    }
}
