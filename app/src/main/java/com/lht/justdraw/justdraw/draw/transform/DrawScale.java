package com.lht.justdraw.justdraw.draw.transform;

import android.graphics.Canvas;

import com.lht.justdraw.justdraw.draw.AbstractDraw;

/**
 * Created by lht on 16/9/9.
 */
public class DrawScale extends AbstractDraw {

    float scaleWidth, scaleHeight;

    public DrawScale(Number scaleWidth, Number scaleHeight) {
        this.scaleWidth = scaleWidth.floatValue();
        this.scaleHeight = scaleHeight.floatValue();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.scale(scaleWidth, scaleHeight);
    }
}
