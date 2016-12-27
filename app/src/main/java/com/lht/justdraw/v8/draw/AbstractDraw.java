package com.lht.justdraw.v8.draw;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lht on 16/9/5.
 */
public abstract class AbstractDraw {

    protected Paint mPaint;

    abstract public void draw(Canvas canvas);
}
