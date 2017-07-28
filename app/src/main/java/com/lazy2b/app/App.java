package com.lazy2b.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import com.lazy2b.libs.app.BaseAppMgr;
import com.lazy2b.libs.app.CrashHandler;
import com.lazy2b.libs.app.Http;
import com.lazy2b.libs.app.Image;
import com.lazy2b.libs.constants.AppData;
import com.lazy2b.libs.utils.ActStack;
import com.lazy2b.libs.utils.SharedPreferencesUtils;

import static android.graphics.Bitmap.Config;

/**
 * 项目名: app.lazy2b.com
 * 包 名: com.lazy2b.app
 * Copyright © 2017, CAIMAO.COM All Rights Reserved.
 * $Id$
 */

public class App extends BaseAppMgr {

    public void aaaa() {
        Bitmap bit = Bitmap.createBitmap(100, 100, Config.ARGB_8888);
        Canvas canva = new Canvas();

    }

    /**
     * 把输入值转换为Color值
     *
     * @param cArgs 可为资源ID或颜色字符串
     * @return Color值【int】
     */
    public static int c(Object cArgs) {
        return cArgs instanceof String ? Color.parseColor((String) cArgs)
                : cxt.getResources().getColor((Integer) cArgs);
    }

    protected void init() {
//        if(!d) {
//            CrashHandler catchExcep = new CrashHandler(this);
//            Thread.setDefaultUncaughtExceptionHandler(catchExcep);
//        }

        AppData.init(cxt);
        Http.init(cxt, this.getHttpCfg());
        Image.init(cxt, this.getImageConfig());
        ActStack.init();
    }

    private static SharedPreferencesUtils spu = null;

    public static final SharedPreferencesUtils spu() {
        if (spu == null && inst != null) {
            spu = new SharedPreferencesUtils(inst, inst.getPackageName() + ".cMgr");
        }
        return spu;
    }

}
