package com.lht.justdraw.justdraw.wrapper;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.lht.justdraw.justdraw.JustV8Object;
import com.lht.justdraw.justdraw.common.CloneablePaint;
import com.lht.justdraw.justdraw.draw.AbstractDraw;
import com.lht.justdraw.justdraw.draw.shape.DrawPath;
import com.lht.justdraw.justdraw.draw.shape.DrawRect;
import com.lht.justdraw.justdraw.draw.operation.DrawRestore;
import com.lht.justdraw.justdraw.draw.operation.DrawSave;
import com.lht.justdraw.justdraw.draw.shape.DrawText;
import com.lht.justdraw.justdraw.draw.transform.DrawRotate;
import com.lht.justdraw.justdraw.draw.transform.DrawScale;
import com.lht.justdraw.justdraw.draw.transform.DrawTransform;
import com.lht.justdraw.justdraw.draw.transform.DrawTranslate;

import java.util.ArrayList;

/**
 * Created by lht on 16/9/20.
 */
public class JustContext extends JustV8Object {

    private boolean bNewStart = true;
    private float mStartX = 0, mStartY = 0;

    private Path mPath = new Path();

    private CloneablePaint
            mPaintFill = new CloneablePaint(),
            mPaintFillSave = new CloneablePaint(),
            mPaintStroke = new CloneablePaint(),
            mPaintStrokeSave = new CloneablePaint();

    private ArrayList<AbstractDraw> mShapeList = new ArrayList<>();

    public ArrayList<AbstractDraw> getShapeList() {
        return mShapeList;
    }

    public JustContext(V8 v8Runtime) {
        super(v8Runtime);

        mPaintFill.setStyle(Paint.Style.FILL);
        mPaintStroke.setStyle(Paint.Style.STROKE);
        mPaintFill.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaintStroke.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void initV8Object() {
        mObject = new V8Object(mRuntime);
        /**
         * 属性
         */
        mObject.registerJavaMethod(this, "setFillColor", "setFillColor", new Class[]{String.class});
        mObject.registerJavaMethod(this, "setStrokeColor", "setStrokeColor", new Class[]{String.class});

        /**
         * 线条样式
         */
        mObject.registerJavaMethod(this, "setLineWidth", "setLineWidth", new Class[]{Number.class});
        mObject.registerJavaMethod(this, "setLineDash", "setLineDash", new Class[]{V8Array.class});

        /**
         * 矩形
         */
        mObject.registerJavaMethod(this, "fillRect", "fillRect", new Class[]{Number.class, Number.class, Number.class, Number.class});
        mObject.registerJavaMethod(this, "strokeRect", "strokeRect", new Class[]{Number.class, Number.class, Number.class, Number.class});

        /**
         * 路径
         */
        mObject.registerJavaMethod(this, "beginPath", "beginPath", null);
        mObject.registerJavaMethod(this, "closePath", "closePath", null);
        mObject.registerJavaMethod(this, "fill", "fill", null);
        mObject.registerJavaMethod(this, "stroke", "stroke", null);
        mObject.registerJavaMethod(this, "moveTo", "moveTo", new Class[]{Number.class, Number.class});
        mObject.registerJavaMethod(this, "lineTo", "lineTo", new Class[]{Number.class, Number.class});
        mObject.registerJavaMethod(this, "quadraticCurveTo", "quadraticCurveTo", new Class[]{Number.class, Number.class, Number.class, Number.class});
        mObject.registerJavaMethod(this, "bezierCurveTo", "bezierCurveTo", new Class[]{Number.class, Number.class, Number.class, Number.class, Number.class, Number.class});
        mObject.registerJavaMethod(this, "arc", "arc", new Class[]{Number.class, Number.class, Number.class, Number.class, Number.class, Boolean.class});
        mObject.registerJavaMethod(this, "rect", "rect", new Class[]{Number.class, Number.class, Number.class, Number.class});
        mObject.registerJavaMethod(this, "clearRect", "clearRect", new Class[]{Number.class, Number.class, Number.class, Number.class});

        /**
         * 转换
         */
        mObject.registerJavaMethod(this, "scale", "scale", new Class[]{Number.class, Number.class});
        mObject.registerJavaMethod(this, "rotate", "rotate", new Class[]{Number.class});
        mObject.registerJavaMethod(this, "translate", "translate", new Class[]{Number.class, Number.class});
        mObject.registerJavaMethod(this, "transform", "transform", new Class[]{Number.class, Number.class, Number.class, Number.class, Number.class, Number.class});

        /**
         * 文本
         */
        mObject.registerJavaMethod(this, "setFont", "setFont", new Class[]{V8Object.class,});
        mObject.registerJavaMethod(this, "fillText", "fillText", new Class[]{String.class, Number.class, Number.class});
        mObject.registerJavaMethod(this, "measureText", "measureText", new Class[]{String.class});

        /**
         * 合成
         */
        mObject.registerJavaMethod(this, "setGlobalAlpha", "setGlobalAlpha", new Class[]{Number.class});

        /**
         * 其他
         */
        mObject.registerJavaMethod(this, "save", "save", null);
        mObject.registerJavaMethod(this, "restore", "restore", null);
    }

    @Override
    public void clean() {
        mObject.release();
    }

    public void setFillColor(String color) {
        mPaintFill.setRgbColor(color);
    }

    public void setStrokeColor(String color) {
        mPaintStroke.setRgbColor(color);
    }

    public void setLineWidth(Number lineWidth) {
        mPaintStroke.setStrokeWidth(lineWidth.intValue());
    }

    public void setLineDash(V8Array intervals) {
        float[] floatIntervals = new float[intervals.length()];
        for (int i = 0; i < intervals.length(); i++) {
            floatIntervals[i] = (float)((double)intervals.getDouble(i));
        }

        PathEffect effects = new DashPathEffect(floatIntervals, 1);
        mPaintStroke.setPathEffect(effects);

        intervals.release();
    }

    public void fillRect(Number x, Number y, Number width, Number height) {
        mShapeList.add(new DrawRect(x, y, width, height,
                mPaintFill.clonePaint()));
    }

    public void strokeRect(Number x, Number y, Number width, Number height) {
        mShapeList.add(new DrawRect(x, y, width, height,
                mPaintStroke.clonePaint()));
    }

    public void beginPath() {
        mPath = new Path();
        bNewStart = true;
    }

    public void closePath() {
        lineTo(mStartX, mStartY);
    }

    public void fill() {
        mShapeList.add(new DrawPath(mPath, mPaintFill.clonePaint()));
    }

    public void stroke() {
        mShapeList.add(new DrawPath(mPath, mPaintStroke.clonePaint()));
    }

    public void moveTo(Number x, Number y) {
        bNewStart = false;
        mStartX = x.floatValue();
        mStartY = y.floatValue();
        mPath.moveTo(mStartX, mStartY);
    }

    public void lineTo(Number x, Number y) {
        if (bNewStart) {
            moveTo(x, y);
        }
        else {
            mPath.lineTo(x.floatValue(), y.floatValue());
        }
    }

    public void quadraticCurveTo(Number cpx, Number cpy, Number x, Number y) {
        mPath.quadTo(cpx.floatValue(), cpy.floatValue(),
                x.floatValue(), y.floatValue());
    }

    public void bezierCurveTo(Number cp1x, Number cp1y, Number cp2x, Number cp2y, Number x, Number y) {
        mPath.cubicTo(cp1x.floatValue(), cp1y.floatValue(),
                cp2x.floatValue(), cp2y.floatValue(),
                x.floatValue(), y.floatValue());
    }

    public void arc(Number x, Number y, Number r, Number sAngle, Number eAngle, Boolean counterclockwise) {
        RectF rectF = new RectF(x.floatValue() - r.floatValue(), y.floatValue() - r.floatValue(),
                x.floatValue() + r.floatValue(), y.floatValue() + r.floatValue());

        //弧度与角度转换
        sAngle = (float)(sAngle.floatValue() / Math.PI * 180);
        eAngle = (float)(eAngle.floatValue() / Math.PI * 180);

        if (counterclockwise) {
            sAngle = -sAngle.floatValue();
            eAngle = -eAngle.floatValue();
        }

        mPath.addArc(rectF, sAngle.floatValue(), eAngle.floatValue() - sAngle.floatValue());
    }

    public void rect(Number x, Number y, Number width, Number height) {
        mPath.addRect(x.floatValue(), y.floatValue(),
                x.floatValue() + width.floatValue(), y.floatValue() + height.floatValue(),
                Path.Direction.CW);
    }

    public void clearRect(Number x, Number y, Number width, Number height) {

    }

    public void scale(Number scaleWidth, Number scaleHeight) {
        mShapeList.add(new DrawScale(scaleWidth, scaleHeight));
    }

    public void rotate(Number angle) {
        mShapeList.add(new DrawRotate(angle));
    }

    public void translate(Number x, Number y) {
        mShapeList.add(new DrawTranslate(x, y));
    }

    public void transform(Number a, Number b, Number c, Number d, Number dx, Number dy) {
        mShapeList.add(new DrawTransform(a, b, c, d, dx, dy));
    }

    public void setFont(V8Object font) {
        mPaintFill.setTextSize(font.getInteger("size"));

        font.release();
    }

    public void fillText(String text, Number x, Number y) {
        mShapeList.add(new DrawText(text, x, y, mPaintFill.clonePaint()));
    }

    public V8Object measureText(String str) {
        Rect rect = new Rect();
        mPaintFill.getTextBounds(str, 0, str.length(), rect);

        V8Object result = new V8Object(mRuntime);
        result.add("width", rect.width());
        result.add("height", rect.height());
        return result;
    }

    public void setGlobalAlpha(Number alpha) {
        mPaintFill.setAlpha((int)(alpha.floatValue() * 255));
        mPaintStroke.setAlpha((int)(alpha.floatValue() * 255));
    }

    public void save() {
        mShapeList.add(new DrawSave());
        mPaintFillSave = mPaintFill.clonePaint();
        mPaintStrokeSave = mPaintStroke.clonePaint();
    }

    public void restore() {
        mShapeList.add(new DrawRestore());
        mPaintFill = mPaintFillSave.clonePaint();
        mPaintStroke = mPaintStrokeSave.clonePaint();
    }
}
