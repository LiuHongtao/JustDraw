package com.lht.justcanvas.draw.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.lht.justcanvas.draw.AbstractDraw;

/**
 * Created by lht on 16/9/7.
 */
public class DrawText extends AbstractDraw {

    private String text;
    private float x, y;

    public DrawText(String text, float x, float y, Paint paint) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.mPaint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, x, y, mPaint);
    }
}
