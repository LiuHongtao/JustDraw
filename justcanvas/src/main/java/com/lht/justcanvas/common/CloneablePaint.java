package com.lht.justcanvas.common;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by lht on 16/9/7.
 */
public class CloneablePaint extends Paint {

    public CloneablePaint clonePaint() {
        CloneablePaint paint = new CloneablePaint();
        paint.setColor(getColor());
        paint.setAlpha(getAlpha());
        paint.setStyle(getStyle());
        paint.setStrokeWidth(getStrokeWidth());
        paint.setTextSize(getTextSize());
        paint.setPathEffect(getPathEffect());
        paint.setFlags(getFlags());

        return paint;
    }

    /**
     * 设置颜色,Paint.setColor()后会重置Alpha为255
     *
     * @param color "#ffffff"
     */
    public void setRgbColor(String color) {
        int alpha = getAlpha();
        setColor(rgb(color));
        setAlpha(alpha);
    }

    /**
     * Converts the given hex-color-string to rgb.
     *
     * @param hex
     * @return
     */
    private int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }
}
