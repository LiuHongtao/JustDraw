package com.lht.justdraw.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.lht.justdraw.R;
import com.lht.justdraw.util.ScreenUtil;

/**
 * Created by lht on 16/8/30.
 */
public class NativeView extends View {

    int mWidth, mHeight;
    Context mContext;
    Paint mPaint;

    int mCount;
    OnDrawFinishedListener onDrawFinishedListener;

    public NativeView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public NativeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWidth = manager.getDefaultDisplay().getWidth();
        mHeight = manager.getDefaultDisplay().getHeight()
                - ScreenUtil.getStatusBarHeight(mContext)
                - ScreenUtil.getNavBarHeight(mContext) - 17;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public void setCount(int count) {
        mCount = count;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        long start = System.currentTimeMillis();
        int i = mCount;
        while (i-- > 0) {
            float x = (float)Math.random() * mWidth;
            float y = (float)Math.random() * mHeight;

            canvas.drawLine(x, y, x + 300, y, mPaint);
            canvas.drawRect(x, y, x + 20, y + 20, mPaint);
            canvas.drawCircle(x, y, 2, mPaint);
        }
        long end = System.currentTimeMillis();


        onDrawFinishedListener.onDrawFinished(
                String.format(
                        getResources().getString(R.string.format_time), end - start + ""));
    }

    public void setOnDrawFinishedListener(OnDrawFinishedListener onDrawFinishedListener) {
        this.onDrawFinishedListener = onDrawFinishedListener;
    }
}
