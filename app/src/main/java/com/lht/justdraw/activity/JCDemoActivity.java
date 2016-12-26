package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

    long time = 0;

    TextView mTvMsg;
    JustView justView;

    String js;
    JCDemo jsdemo = new JCDemo();
    JCDemoString jsdemoString = new JCDemoString();
    JCDemoJson jsdemoJson = new JCDemoJson();

    int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_jc);
        setContentView(R.layout.activity_jcdemo);

        mCount = getIntent().getIntExtra("count", 1000);

        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        justView = (JustView)findViewById(R.id.justview);

        js = FileUtil.getFromAssets(this, "jcdemoString.js");
        js = js.replace("$count$", mCount + "");

        draw(null);
    }

    public void draw(View v) {
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long start = System.currentTimeMillis();

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

            long end = System.currentTimeMillis();
            time = end - start;
            handler.sendEmptyMessage(0);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            justView.invalidate();

            mTvMsg.setText(
                    String.format(
                            getResources().getString(R.string.format_time),
                            time + ""));
        }
    };

    private void jcdemo(V8Array parameters) {
        V8Array calls = parameters.getArray(0);
        int length = calls.length();
        for (int i = 0; i < length; i++) {
            jsdemo.call(calls.getObject(i));
        }
        justView.draw(jsdemo.getShapeList());
        jsdemo.clearShapeList();
    }

    private void jcdemoString(V8Array parameters) {
        String[] calls = JCDemoString.splitBy(parameters.getString(0), parameters.getInteger(1), '&');
        parameters.release();
        for (String call: calls) {
            jsdemoString.call(call);
        }
        justView.draw(jsdemoString.getShapeList());
        jsdemoString.clearShapeList();
    }

    private void jcdemoJson(V8Array parameters) {
        JustCall[] calls = JSON.parseObject(parameters.getString(0), JustCall[].class);
        parameters.release();
        for (JustCall call: calls) {
            jsdemoJson.call(call);
        }
        justView.draw(jsdemoJson.getShapeList());
        jsdemoJson.clearShapeList();
    }
}
