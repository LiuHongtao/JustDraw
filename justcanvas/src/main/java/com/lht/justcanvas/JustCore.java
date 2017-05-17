package com.lht.justcanvas;

import android.util.Log;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.lht.justcanvas.common.JustV8Object;

/**
 * Created by lht on 16/9/20.
 */
public class JustCore {

    final static String LOG_TAG = "JustCanvasNative";

    static V8 sRuntime;

    public static V8 getRuntime() {
        sRuntime = V8.createV8Runtime();
        // log() in JS
        sRuntime.registerJavaMethod(new JavaVoidCallback() {
            public void invoke(final V8Object receiver, final V8Array parameters) {
                if (parameters.length() > 0) {
                    Log.d(LOG_TAG, parameters.getString(0));
                }
            }
        }, "log");
        sRuntime.add("screenWidth", JustConfig.screenWidth);
        sRuntime.add("screenHeight", JustConfig.screenHeight - JustConfig.navbarheight - JustConfig.statusbarheight);
        return sRuntime;
    }

    public static void run(String js) {
        sRuntime.executeScript(js);
    }

    public static void clean(JustV8Object v8Object) {
        v8Object.clean();
        sRuntime.release();
    }
}

