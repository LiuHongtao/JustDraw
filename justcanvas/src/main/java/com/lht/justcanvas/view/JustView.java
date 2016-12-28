package com.lht.justcanvas.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lht.justcanvas.JustConfig;
import com.lht.justcanvas.draw.AbstractDraw;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lht on 16/9/1.
 */
public class JustView extends View implements Serializable {

    float mCanvasWidth = JustConfig.screenWidth;
    float mCanvasHeight = JustConfig.screenHeight - JustConfig.navbarheight - JustConfig.statusbarheight;
    float mCanvasX = 0, mCanvasY = 0;

    Bitmap mBitmap = null;
    boolean bDrawFinished = false;

    public JustView(Context context) {
        super(context);
    }

    public JustView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JustView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (changed) {
            mCanvasWidth = right - left;
            mCanvasHeight = bottom - top;
            clear();
        }
    }

    public void clear() {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = Bitmap.createBitmap((int)mCanvasWidth, (int)mCanvasHeight, Bitmap.Config.ARGB_8888);
    }

    public void draw(ArrayList<AbstractDraw> abstractDrawList) {
        Canvas canvas = new Canvas();
        canvas.setBitmap(mBitmap);

        for (AbstractDraw abstractDraw : abstractDrawList) {
            abstractDraw.draw(canvas);
        }

        bDrawFinished = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bDrawFinished) {
            canvas.drawBitmap(mBitmap, 0, 0, new Paint());
            bDrawFinished = false;
        }
    }

    public float getCanvasHeight() {
        return mCanvasHeight;
    }

    public void setCanvasHeight(float mCanvasHeight) {
        this.mCanvasHeight = mCanvasHeight;
    }

    public float getCanvasWidth() {
        return mCanvasWidth;
    }

    public void setCanvasWidth(float mCanvasWidth) {
        this.mCanvasWidth = mCanvasWidth;
    }

    public float getCanvasX() {
        return mCanvasX;
    }

    public void setCanvasX(float mCanvasX) {
        this.mCanvasX = mCanvasX;
    }

    public float getCanvasY() {
        return mCanvasY;
    }

    public void setCanvasY(float mCanvasY) {
        this.mCanvasY = mCanvasY;
    }
}
