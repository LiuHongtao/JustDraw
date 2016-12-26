package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.lht.justcanvas.JustConfig;
import com.lht.justcanvas.JustView;
import com.lht.justdraw.R;
import com.lht.justdraw.jcdemo.JCDemo;
import com.lht.justdraw.jcdemo.JCDemoJson;
import com.lht.justdraw.jcdemo.JCDemoString;
import com.lht.justdraw.jcdemo.JustCall;
import com.lht.justdraw.util.FileUtil;

public class JCDemoActivity extends AppCompatActivity {

    private final static String LOG_TAG = "JCDemoLog";

    JustView justView;

    String js;
    JCDemo jsdemo = new JCDemo();
    JCDemoString jsdemoString = new JCDemoString();
    JCDemoJson jsdemoJson = new JCDemoJson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jcdemo);

        justView = (JustView)findViewById(R.id.justview);
        js = FileUtil.getFromAssets(this, "jcdemoString.js");
    }

    public void draw(View v) {
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            V8 runtime = V8.createV8Runtime();
            runtime.registerJavaMethod(new JavaVoidCallback() {
                public void invoke(final V8Object receiver, final V8Array parameters) {

                    if (parameters.length() > 0) {
                        jcdemoString(parameters);
                    }

                }
            }, "call");
            runtime.add("screenWidth", JustConfig.screenWidth);
            runtime.add("screenHeight", JustConfig.screenHeight - JustConfig.navbarheight - JustConfig.statusbarheight);
            runtime.executeScript(js);

            handler.sendEmptyMessage(0);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            justView.invalidate();
        }
    };

    private void jcdemo(V8Array parameters) {
        long start = System.currentTimeMillis();
        V8Array calls = parameters.getArray(0);
        int length = calls.length();
        for (int i = 0; i < length; i++) {
            jsdemo.call(calls.getObject(i));
        }
        long end = System.currentTimeMillis();
        Log.d(LOG_TAG, "V8Object:" + Long.toString(end - start));
        justView.draw(jsdemo.getShapeList());
        jsdemo.clearShapeList();
    }

    private void jcdemoString(V8Array parameters) {
        long start = System.currentTimeMillis();
        String[] calls = JCDemoString.splitBy(parameters.getString(0), parameters.getInteger(1), '&');
        parameters.release();
        for (String call: calls) {
            jsdemoString.call(call);
        }
        long end = System.currentTimeMillis();
        Log.d(LOG_TAG, "String:" + Long.toString(end - start));
        justView.draw(jsdemoString.getShapeList());
        jsdemoString.clearShapeList();
    }

    private void jcdemoJson(V8Array parameters) {
        long start = System.currentTimeMillis();
        JustCall[] calls = JSON.parseObject(parameters.getString(0), JustCall[].class);
        parameters.release();
        for (JustCall call: calls) {
            jsdemoJson.call(call);
        }
        long end = System.currentTimeMillis();
        Log.d(LOG_TAG, "JSON:" + Long.toString(end - start));
        justView.draw(jsdemoJson.getShapeList());
        jsdemoJson.clearShapeList();
    }
}
