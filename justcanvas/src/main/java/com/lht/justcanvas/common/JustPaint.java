package com.lht.justcanvas.common;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by lht on 16/9/7.
 */
public class JustPaint extends Paint {

    public JustPaint() {
    }

    public JustPaint(JustPaint paint) {
        super(paint);
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
