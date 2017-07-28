package com.lazy2b.app;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.caimao.luzhu.view.LuZhuCacheTasks;
import com.caimao.luzhu.view.LuZhuSurfaceView;

public class Main3Activity extends Activity implements LuZhuCacheTasks.OnDrawCompleteListener {

    LuZhuSurfaceView lsv = null;
    LinearLayout ll_sh_luzhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tpl_luzhu_container);

        ll_sh_luzhu = (LinearLayout) findViewById(R.id.ll_sh_luzhu);

        Log.e("Main3Activity", "onCreate(Bundle savedInstanceState)");
        lsv = new LuZhuSurfaceView(this);
        ll_sh_luzhu.addView(lsv, new LinearLayout.LayoutParams(200, 300));
//        lsv.setData();
    }

    @Override
    public boolean onDrawComplete(int width, int height) {
        Log.e("Main3Activity", "onDrawComplete(int width, int height)");
        lsv.setLayoutParams(new LinearLayout.LayoutParams(width, height));
//        ll_sh_luzhu.addView(lsv, new LinearLayout.LayoutParams(width, height));
        return false;
    }
}
