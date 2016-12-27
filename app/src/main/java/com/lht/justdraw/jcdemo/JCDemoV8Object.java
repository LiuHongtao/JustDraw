package com.lht.justdraw.jcdemo;

import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.lht.justcanvas.common.CloneablePaint;
import com.lht.justcanvas.draw.AbstractDraw;
import com.lht.justcanvas.draw.shape.DrawPath;

import java.util.ArrayList;

/**
 * Created by lht on 16/12/23.
 */

public class JCDemoV8Object {

    private final static String LOG_TAG = "JCDemoLog";

    private boolean bNewStart = true;

    private CloneablePaint mPaintStroke = new CloneablePaint();

    private float mStartX = 0, mStartY = 0;
    private Path mPath = new Path();

    private ArrayList<AbstractDraw> mShapeList = new ArrayList<>();
    public ArrayList<AbstractDraw> getShapeList() {
        return mShapeList;
    }

    public void clearShapeList() {
        mShapeList.clear();
    }

    public JCDemoV8Object() {
        mPaintStroke.setStyle(Paint.Style.STROKE);
    }

    public void call(V8Object call) {

        V8Array parameter = call.getArray("parameter");
        switch (call.getString("name")) {
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

    private void log(V8Array parameter) {
        Log.d(LOG_TAG, parameter.getString(0));
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

    private void moveTo(V8Array parameter) {
        moveTo(getFloat(parameter.getDouble(0)), getFloat(parameter.getDouble(1)));
    }

    private void moveTo(float x, float y) {
        bNewStart = false;
        mStartX = x;
        mStartY = y;
        mPath.moveTo(mStartX, mStartY);
    }

    private void lineTo(V8Array parameter) {
        lineTo(getFloat(parameter.getDouble(0)), getFloat(parameter.getDouble(1)));
    }

    private void lineTo(float x, float y) {
        if (bNewStart) {
            moveTo(x, y);
        }
        else {
            mPath.lineTo(x, y);
        }
    }

    private void arc(V8Array parameter) {
        float x = getFloat(parameter.getDouble(0)), y = getFloat(parameter.getDouble(1)),
                r = getFloat(parameter.getDouble(2)), sAngle = getFloat(parameter.getDouble(3)),
                eAngle = getFloat(parameter.getDouble(4));
        boolean counterclockwise = parameter.getBoolean(5);

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

    private void rect(V8Array parameter) {
        float x = getFloat(parameter.getDouble(0)), y = getFloat(parameter.getDouble(1)),
                width = getFloat(parameter.getDouble(2)), height = getFloat(parameter.getDouble(3));
        mPath.addRect(x, y, x + width, y + height, Path.Direction.CW);
    }

    private float getFloat(double param) {
        return (float)param;
    }
}
