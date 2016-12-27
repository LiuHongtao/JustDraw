package com.lht.justcanvas.draw.transform;

import android.graphics.Canvas;

import com.lht.justcanvas.draw.AbstractDraw;

/**
 * Created by lht on 16/9/9.
 */
public class DrawRotate extends AbstractDraw {

    private float angle;

    public DrawRotate(Number angle) {
        this.angle = angle.floatValue();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.rotate((float)(angle / Math.PI * 180));
    }
}
