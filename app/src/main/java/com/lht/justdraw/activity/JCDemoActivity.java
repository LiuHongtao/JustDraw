package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.eclipsesource.v8.JavaVoidCallback;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.lht.justcanvas.JustConfig;
import com.lht.justcanvas.JustView;
import com.lht.justdraw.R;
import com.lht.justdraw.jcdemo.JCDemo;
import com.lht.justdraw.util.FileUtil;

public class JCDemoActivity extends AppCompatActivity {

    private final static String LOG_TAG = "JCDemoLog";

    JustView justView;

    String js;
    JCDemo jsdemo = new JCDemo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jcdemo);

        justView = (JustView)findViewById(R.id.justview);

        int count = getIntent().getIntExtra("count", 1000);

        js = FileUtil.getFromAssets(this, "jcdemo.js");
        js = js.replace("$count$", Integer.toString(count));

        thread.start();
    }

    private Thread thread = new Thread() {
        @Override
        public void run() {

            V8 runtime = V8.createV8Runtime();
            runtime.registerJavaMethod(new JavaVoidCallback() {
                public void invoke(final V8Object receiver, final V8Array parameters) {

                    if (parameters.length() > 0) {
                        long start = System.currentTimeMillis();
                        String[] calls = JCDemo.splitBy(parameters.getString(0), parameters.getInteger(1), '&');
                        for (String call: calls) {
                            jsdemo.call(call);
                        }
                        long end = System.currentTimeMillis();
                        Log.d(LOG_TAG, Long.toString(end - start));

                        parameters.release();
                        justView.draw(jsdemo.getShapeList());
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
}
