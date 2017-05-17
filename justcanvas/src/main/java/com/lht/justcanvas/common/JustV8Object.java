package com.lht.justcanvas.common;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

/**
 * Created by lht on 16/12/27.
 */

public abstract class JustV8Object {
    protected V8 mRuntime;
    protected V8Object mObject;

    public JustV8Object(V8 v8Runtime) {
        mRuntime = v8Runtime;
        mObject = new V8Object(mRuntime);
    }

    public V8Object getObject() {
        return mObject;
    }

    protected abstract void initV8Object();

    public void clean() {
        mObject.release();
    }
}
