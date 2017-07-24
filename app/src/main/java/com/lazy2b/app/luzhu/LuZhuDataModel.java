package com.lazy2b.app.luzhu;

import com.lazy2b.app.LuZhuAdapter;
import com.lazy2b.libs.model.BaseModel;

/**
 * 项目名: HighFrequencyLotteryBox
 * 包 名: com.royaleu.comm.model.luzhu
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class LuZhuDataModel extends BaseModel {

    public LuZhuDataModel(int _type, String _cxt, int _most) {
        type = _type;
        context = _cxt;
        most = _most;
    }

    public int most = 1;
    public int type = LuZhuAdapter.LUZHU_RESULT_VIEW_TYPE;
    public String context = "";
}
