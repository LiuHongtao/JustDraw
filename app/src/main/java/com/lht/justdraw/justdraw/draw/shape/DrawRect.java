package com.lht.justdraw.justdraw.draw.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.lht.justdraw.justdraw.draw.AbstractDraw;

/**
 * Created by lht on 16/9/7.
 */
public class DrawRect extends AbstractDraw {

    float left, top, right, bottom;

    public DrawRect(Number x, Number y, Number width, Number height, Paint paint) {
        this.left = x.floatValue();
        this.top = y.floatValue();
        this.right = x.floatValue() + width.floatValue();
        this.bottom = y.floatValue() + height.floatValue();
        this.mPaint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(left, top, right, bottom, mPaint);
    }
}
