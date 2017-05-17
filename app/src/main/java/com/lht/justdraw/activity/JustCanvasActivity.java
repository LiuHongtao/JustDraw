package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eclipsesource.v8.V8;
import com.lht.justcanvas.JustCanvasNative;
import com.lht.justcanvas.JustCore;
import com.lht.justcanvas.view.JustView;
import com.lht.justdraw.R;
import com.lht.justdraw.util.FileUtil;

public class JustCanvasActivity extends AppCompatActivity {

    long time = 0;

    TextView mTvMsg;
    JustView mJustView;

    String js;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_jc);
        setContentView(R.layout.activity_just_canvas);

        mTvMsg = (TextView) findViewById(R.id.tv_msg);
        mJustView = (JustView)findViewById(R.id.justview);

        js = FileUtil.getFromAssets(this, "jc.js");
        js = js.replace("$count$", getIntent().getIntExtra("count", 1000) + "");

        draw(null);
    }

    public void draw(View v) {
        new Thread(runnable).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mJustView.clear();

            long start = System.currentTimeMillis();

            V8 v8Runtime = JustCore.getRuntime();
            JustCanvasNative just = new JustCanvasNative(v8Runtime, mJustView, handler);
            v8Runtime.add("JustCanvasNative", just.getObject());

            JustCore.run(js);

            long end = System.currentTimeMillis();
            time = end - start;

            JustCore.clean(just);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            mJustView.invalidate();

            mTvMsg.setText(
                    String.format(
                            getResources().getString(R.string.format_time),
                            time + ""));
        }
    };
}
