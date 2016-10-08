package com.lht.justdraw.justdraw;

import android.util.Log;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.Releasable;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.lht.justdraw.util.ScreenUtil;

/**
 * Created by lht on 16/9/20.
 */
public class JustJsCore {

    final static String LOG_TAG = "ColorBoxJSCore";

    static V8 mRuntime;

    public static V8 getRuntime() {
        mRuntime = V8.createV8Runtime();
        mRuntime.registerJavaMethod(new JavaVoidCallback() {
            public void invoke(final V8Object receiver, final V8Array parameters) {
                if (parameters.length() > 0) {
                    Object msg = parameters.get(0);
                    Log.d(LOG_TAG, msg.toString());
                    if (msg instanceof Releasable) {
                        ((Releasable) msg).release();
                    }
                }
            }
        }, "log");
        mRuntime.add("screenWidth", ScreenUtil.screenWidth);
        mRuntime.add("screenHeight", ScreenUtil.screenHeight - ScreenUtil.navbarheight - ScreenUtil.statusbarheight);
        return mRuntime;
    }

    public static void run(String js) {
        mRuntime.executeScript(js);
    }

    public static void clean(JustV8Object justV8Object) {
        justV8Object.clean();
        mRuntime.release();
    }
}
