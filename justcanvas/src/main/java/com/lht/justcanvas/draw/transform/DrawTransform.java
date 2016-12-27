package com.lht.justcanvas.draw.transform;

import android.graphics.Canvas;
import android.graphics.Matrix;

import com.lht.justcanvas.draw.AbstractDraw;

/**
 * Created by lht on 16/9/8.
 */
public class DrawTransform extends AbstractDraw {

    private float a, b, c, d, dx, dy;

    public DrawTransform(Number a, Number b, Number c, Number d, Number dx, Number dy) {
        this.a = a.floatValue();
        this.b = b.floatValue();
        this.c = c.floatValue();
        this.d = d.floatValue();
        this.dx = dx.floatValue();
        this.dy = dy.floatValue();
    }

    @Override
    public void draw(Canvas canvas) {
        Matrix newMatrix = new Matrix();
        newMatrix.setValues(new float[] {
                a, c, dx,
                b, d, dy,
                0f, 0f, 1f
        });

        canvas.concat(newMatrix);
    }
}
