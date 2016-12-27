package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.eclipsesource.v8.V8;
import com.lht.justcanvas.JustContext;
import com.lht.justcanvas.JustCore;
import com.lht.justcanvas.JustView;
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
            long start = System.currentTimeMillis();

            V8 v8Runtime = JustCore.getRuntime();
            JustContext just = new JustContext(v8Runtime);
            v8Runtime.add("just", just.getObject());

            JustCore.run(js);
            mJustView.draw(just.getShapeList());

            long end = System.currentTimeMillis();
            time = end - start;

            JustCore.clean(just);
            handler.sendEmptyMessage(0);
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
