package com.lht.justcanvas;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.eclipsesource.v8.V8;
import com.lht.justcanvas.common.CloneablePaint;
import com.lht.justcanvas.draw.AbstractDraw;
import com.lht.justcanvas.draw.shape.DrawPath;
import com.lht.justcanvas.draw.shape.DrawText;

import java.util.ArrayList;

/**
 * Created by lht on 16/12/27.
 */

public class JustCanvas extends JustV8Object{

    private final static String LOG_TAG = "JustCanvas";

    private boolean bNewStart = true;
    private CloneablePaint mPaintFill = new CloneablePaint(),
            mPaintStroke = new CloneablePaint();

    private float mStartX = 0, mStartY = 0;
    private Path mPath = new Path();

    private ArrayList<AbstractDraw> mShapeList = new ArrayList<>();
    public ArrayList<AbstractDraw> getShapeList() {
        return mShapeList;
    }

    public JustCanvas(V8 v8Runtime) {
        super(v8Runtime);

        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintFill.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void initV8Object() {
        mObject.registerJavaMethod(this, "call", "call", new Class[]{String.class, Integer.class});
    }

    public void call(String call, Integer times) {
        String[] calls = splitBy(call, times, '&');
        for (String item: calls) {
            call(item);
        }
    }

    private void call(String call) {
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
            case "fillText":
                fillText(parameter);
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
        mShapeList.add(new DrawPath(mPath, new CloneablePaint(mPaintStroke)));
    }

    private void moveTo(String[] parameter) {
        moveTo(getFloat(parameter[0]), getFloat(parameter[1]));
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

    private void fillText(String[] parameter) {
        mShapeList.add(new DrawText(parameter[0],
                getFloat(parameter[1]), getFloat(parameter[2]),
                new CloneablePaint(mPaintFill)));
    }

    private int getInt(Object param) {
        return Integer.parseInt(param.toString());
    }

    private float getFloat(Object param) {
        return Float.parseFloat(param.toString());
    }

    private boolean getBoolean(Object param) {
        return Boolean.parseBoolean(param.toString());
    }

    private static String[] splitBy(String content, int size, char symbol) {
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
