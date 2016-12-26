package com.lht.justdraw.jcdemo;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.lht.justcanvas.common.CloneablePaint;
import com.lht.justcanvas.draw.AbstractDraw;
import com.lht.justcanvas.draw.shape.DrawPath;

import java.util.ArrayList;

/**
 * Created by lht on 16/12/23.
 */

public class JCDemo {

    private final static String LOG_TAG = "JCDemoLog";

    private boolean bNewStart = true;

    private CloneablePaint mPaintStroke = new CloneablePaint();

    private float mStartX = 0, mStartY = 0;
    private Path mPath = new Path();

    private ArrayList<AbstractDraw> mShapeList = new ArrayList<>();
    public ArrayList<AbstractDraw> getShapeList() {
        return mShapeList;
    }

    public JCDemo() {
        mPaintStroke.setStyle(Paint.Style.STROKE);
    }

    public void call(String call) {
        int index = call.indexOf(']');
        int length = call.length();
        String name = call.substring(1, index);
        String[] parameter = null;
        if (index != length - 1) {
            parameter = splitBy(call.substring(index + 1, length), 10, ',');
        }

        switch (name) {
            case "log":
                log(parameter);
                break;
            case "beginPath":
                beginPath();
                break;
            case "closePath":
                closePath();
                break;
            case "stroke":
                stroke();
                break;
            case "moveTo":
                moveTo(parameter);
                break;
            case "lineTo":
                lineTo(parameter);
                break;
            case "arc":
                arc(parameter);
                break;
            case "rect":
                rect(parameter);
                break;
        }
    }

    private void log(String[] parameter) {
        Log.d(LOG_TAG, parameter[0]);
    }

    private void beginPath() {
        mPath = new Path();
        bNewStart = true;
    }

    private void closePath() {
        lineTo(mStartX, mStartY);
    }

    private void stroke() {
        mShapeList.add(new DrawPath(mPath, mPaintStroke.clonePaint()));
    }

    private void moveTo(String[] parameter) {
        moveTo(getFloat(parameter[0]), getInt(parameter[1]));
    }

    private void moveTo(float x, float y) {
        bNewStart = false;
        mStartX = x;
        mStartY = y;
        mPath.moveTo(mStartX, mStartY);
    }

    private void lineTo(String[] parameter) {
        lineTo(getFloat(parameter[0]), getFloat(parameter[1]));
    }

    private void lineTo(float x, float y) {
        if (bNewStart) {
            moveTo(x, y);
        }
        else {
            mPath.lineTo(x, y);
        }
    }

    private void arc(String[] parameter) {
        float x = getFloat(parameter[0]), y = getFloat(parameter[1]),
                r = getFloat(parameter[2]), sAngle = getFloat(parameter[3]),
                eAngle = getFloat(parameter[4]);
        boolean counterclockwise = getBoolean(parameter[5]);

        RectF rectF = new RectF(x - r, y - r, x + r, y + r);

        //弧度与角度转换
        sAngle = (float)(sAngle / Math.PI * 180);
        eAngle = (float)(eAngle / Math.PI * 180);

        if (counterclockwise) {
            sAngle = -sAngle;
            eAngle = -eAngle;
        }

        mPath.addArc(rectF, sAngle, eAngle - sAngle);
    }

    private void rect(String[] parameter) {
        float x = getFloat(parameter[0]), y = getFloat(parameter[1]),
                width = getFloat(parameter[2]), height = getFloat(parameter[3]);
        mPath.addRect(x, y, x + width, y + height, Path.Direction.CW);
    }

    private int getInt(Object param) {
        return Integer.valueOf(param.toString());
    }

    private float getFloat(Object param) {
        return Float.valueOf(param.toString());
    }

    private boolean getBoolean(Object param) {
        return Boolean.valueOf(param.toString());
    }

    public static String[] splitBy(String content, int size, char symbol) {
        String[] result = new String[size];
        int index = 0, length = content.length(), start, end;
        int[] posForSymbol = new int[size];

        for (int i = 0; i < length; i++) {
            if (content.charAt(i) == symbol) {
                posForSymbol[index++] = i;
            }
        }

        start = 0;
        for (int i = 0; i < index; i++) {
            end = posForSymbol[i];
            result[i] = content.substring(start, end);
            start = end + 1;
        }

        return result;
    }
}
