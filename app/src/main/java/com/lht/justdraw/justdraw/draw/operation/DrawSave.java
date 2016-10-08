package com.lht.justdraw.justdraw.draw.operation;

import android.graphics.Canvas;

import com.lht.justdraw.justdraw.draw.AbstractDraw;

/**
 * Created by lht on 16/9/8.
 */
public class DrawSave extends AbstractDraw {

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
    }
}
