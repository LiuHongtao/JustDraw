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

public class JCDemoJson {

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

    public JCDemoJson() {
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintStroke.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public void call(JustCall call) {

        switch (call.getName()) {
            case "log":
                log(call.getParameter());
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
                moveTo(call.getParameter());
                break;
            case "lineTo":
                lineTo(call.getParameter());
                break;
            case "arc":
                arc(call.getParameter());
                break;
            case "rect":
                rect(call.getParameter());
                break;
        }
    }

    private void log(Object[] parameter) {
        Log.d(LOG_TAG, parameter[0].toString());
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

    private void moveTo(Object[] parameter) {
        moveTo(getFloat(parameter[0]), getFloat(parameter[1]));
    }

    private void moveTo(float x, float y) {
        bNewStart = false;
        mStartX = x;
        mStartY = y;
        mPath.moveTo(mStartX, mStartY);
    }

    private void lineTo(Object[] parameter) {
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

    private void arc(Object[] parameter) {
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

    private void rect(Object[] parameter) {
        float x = getFloat(parameter[0]), y = getFloat(parameter[1]),
                width = getFloat(parameter[2]), height = getFloat(parameter[3]);
        mPath.addRect(x, y, x + width, y + height, Path.Direction.CW);
    }

    private int getInt(Object param) {
        return (int)param;
    }

    private float getFloat(Object param) {
        return ((int)param) * 1.0f;
    }

    private boolean getBoolean(Object param) {
        return (boolean)param;
    }
}
