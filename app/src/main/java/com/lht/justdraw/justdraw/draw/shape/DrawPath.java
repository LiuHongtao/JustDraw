package com.lht.justdraw.justdraw.draw.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.lht.justdraw.justdraw.draw.AbstractDraw;

/**
 * Created by lht on 16/9/6.
 */
public class DrawPath extends AbstractDraw {

    Path path;

    public DrawPath(Path path, Paint paint) {
        this.path = path;
        this.mPaint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(path, mPaint);
    }
}
