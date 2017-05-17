package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.eclipsesource.v8.V8;
import com.lht.justdraw.v8.JustJsCore;
import com.lht.justdraw.v8.JustViewGroup;
import com.lht.justdraw.v8.wrapper.JustWindow;
import com.lht.justdraw.R;
import com.lht.justdraw.util.FileUtil;

/**
 * Run JavaScript code and draw shapes on native Canvas
 */
public class V8Activity extends AppCompatActivity {

    long time = 0;
    String js;

    TextView mTvMsg;
    JustViewGroup mViewGroup;
    JustWindow mJustWindow;

    int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_v8);
        setContentView(R.layout.activity_v8);

        mCount = getIntent().getIntExtra("count", 1000);

        mViewGroup = (JustViewGroup) findViewById(R.id.view_group);
        mTvMsg = (TextView) findViewById(R.id.tv_msg);

        js = FileUtil.getFromAssets(this, "v8.js");
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

            V8 v8Runtime = JustJsCore.getRuntime();
            mJustWindow = new JustWindow(v8Runtime, mViewGroup);
            v8Runtime.add("window", mJustWindow.getV8Object());

            JustJsCore.run(js);

            mJustWindow.drawWindow();

            JustJsCore.clean(mJustWindow);

            long end = System.currentTimeMillis();
            time = end - start;
            handler.sendEmptyMessage(0);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            long start = System.currentTimeMillis();
            mJustWindow.invalidate();
            long end = System.currentTimeMillis();
            time += end - start;
            Log.d("EXETIME", time + "");
            mTvMsg.setText(
                    String.format(
                            getResources().getString(R.string.format_time),
                            time + ""));
        }
    };
}
