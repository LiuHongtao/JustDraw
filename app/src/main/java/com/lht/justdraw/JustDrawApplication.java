package com.lht.justdraw;

import android.app.Application;

import com.lht.justcanvas.JustConfig;
import com.lht.justdraw.util.ScreenUtil;

/**
 * Created by lht on 16/9/18.
 */
public class JustDrawApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ScreenUtil.GetInfo(this);
        JustConfig.init(this);
    }
}
