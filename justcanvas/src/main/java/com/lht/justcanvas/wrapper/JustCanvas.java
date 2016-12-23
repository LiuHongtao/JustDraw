package com.lht.justcanvas.wrapper;

import android.content.Context;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.lht.justcanvas.JustConfig;
import com.lht.justcanvas.JustV8Object;
import com.lht.justcanvas.JustView;

/**
 * Created by lht on 16/9/9.
 */
public class JustCanvas extends JustV8Object {

    JustView mJustView;
    JustContext mJustContext;

    public JustCanvas(V8 v8Runtime, Context context) {
        super(v8Runtime);
        mJustView = new JustView(context);

        mJustContext = new JustContext(v8Runtime);
        mObject.add("context", mJustContext.getObject());
    }

    @Override
    protected void initV8Object() {
        mObject = new V8Object(mRuntime);
        mObject.registerJavaMethod(this, "setX", "setX", new Class[]{Number.class});
        mObject.registerJavaMethod(this, "setY", "setY", new Class[]{Number.class});
        mObject.registerJavaMethod(this, "setWidth", "setWidth", new Class[]{Number.class});
        mObject.registerJavaMethod(this, "setHeight", "setHeight", new Class[]{Number.class});
        mObject.registerJavaMethod(this, "setSize", "setSize", new Class[]{Number.class, Number.class, Number.class, Number.class});

        mObject.add("x", 0);
        mObject.add("y", 0);
        mObject.add("width", JustConfig.screenWidth);
        mObject.add("height", JustConfig.screenHeight - JustConfig.navbarheight - JustConfig.statusbarheight);
    }

    @Override
    public void clean() {
        mJustContext.clean();
        mObject.release();
    }

    public JustView getJustView() {
        return mJustView;
    }

    public void drawCanvas() {
        mJustView.draw(
                mJustContext.getShapeList());
    }

    public void invalidate() {
        mJustView.invalidate();
    }

    public void setX(Number x) {
        mJustView.setCanvasX(x.floatValue());
    }

    public void setY(Number y) {
        mJustView.setCanvasY(y.floatValue());
    }

    public void setWidth(Number width) {
        mJustView.setCanvasWidth((int)width);
    }

    public void setHeight(Number height) {
        mJustView.setCanvasHeight((int)height);
    }

    public void setSize(Number x, Number y, Number width, Number height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
    }
}
