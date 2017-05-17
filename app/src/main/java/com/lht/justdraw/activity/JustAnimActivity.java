package com.lht.justdraw.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.eclipsesource.v8.V8;
import com.lht.justcanvas.JustCanvasNative;
import com.lht.justcanvas.JustCore;
import com.lht.justcanvas.view.JustView;
import com.lht.justdraw.R;
import com.lht.justdraw.util.FileUtil;

public class JustAnimActivity extends AppCompatActivity {

    JustView mJustView;

    String js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_jc_anim);
        setContentView(R.layout.activity_just_anim);

        mJustView = (JustView)findViewById(R.id.justview);

        js = FileUtil.getFromAssets(this, "jcAnim.js");

        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mJustView.clear();

            V8 v8Runtime = JustCore.getRuntime();
            JustCanvasNative just = new JustCanvasNative(v8Runtime, mJustView, handler);
            v8Runtime.add("JustCanvasNative", just.getObject());

            JustCore.run(js);
            JustCore.clean(just);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            mJustView.invalidate();
        }
    };
}
