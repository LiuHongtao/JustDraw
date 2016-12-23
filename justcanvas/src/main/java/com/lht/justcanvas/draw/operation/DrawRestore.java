package com.lht.justcanvas.draw.operation;

import android.graphics.Canvas;

import com.lht.justcanvas.draw.AbstractDraw;

/**
 * Created by lht on 16/9/8.
 */
public class DrawRestore extends AbstractDraw {

    @Override
    public void draw(Canvas canvas) {
        canvas.restore();
    }
}
