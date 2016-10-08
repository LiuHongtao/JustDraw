package com.lht.justdraw.justdraw.draw.transform;

import android.graphics.Canvas;

import com.lht.justdraw.justdraw.draw.AbstractDraw;

/**
 * Created by lht on 16/9/8.
 */
public class DrawTranslate extends AbstractDraw {

    float x, y;

    public DrawTranslate(Number x, Number y) {
        this.x = x.floatValue();
        this.y = y.floatValue();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.translate(x, y);
    }
}
