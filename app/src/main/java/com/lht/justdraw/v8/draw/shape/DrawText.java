package com.lht.justdraw.v8.draw.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.lht.justdraw.v8.draw.AbstractDraw;

/**
 * Created by lht on 16/9/7.
 */
public class DrawText extends AbstractDraw {

    String text;
    float x, y;

    public DrawText(String text, Number x, Number y, Paint paint) {
        this.text = text;
        this.x = x.floatValue();
        this.y = y.floatValue();
        this.mPaint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, x, y, mPaint);
    }
}
