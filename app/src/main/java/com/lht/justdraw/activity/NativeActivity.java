package com.lht.justdraw.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.lht.justdraw.R;
import com.lht.justdraw.view.NativeView;
import com.lht.justdraw.view.OnDrawFinishedListener;

public class NativeActivity extends AppCompatActivity {

    NativeView mView;
    TextView mTvMsg;

    int mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_native);
        setContentView(R.layout.activity_native);

        mCount = getIntent().getIntExtra("count", 1000);

        mView = (NativeView)findViewById(R.id.sv_canvas);
        mView.setOnDrawFinishedListener(new OnDrawFinishedListener() {
            @Override
            public void onDrawFinished(String msg) {
                mTvMsg.setText(msg);
            }
        });

        mTvMsg = (TextView)findViewById(R.id.tv_msg);

        thread.start();
    }

    private Thread thread = new Thread() {
        @Override
        public void run() {
            mView.setCount(mCount);
            handler.sendEmptyMessage(0);
        }
    };

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(android.os.Message msg) {
            mView.invalidate();
        }
    };
}
