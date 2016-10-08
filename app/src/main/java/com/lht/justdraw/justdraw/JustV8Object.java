package com.lht.justdraw.justdraw;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;

/**
 * Created by lht on 16/9/20.
 */
public abstract class JustV8Object {

    protected V8 mRuntime;
    protected V8Object mObject;

    public V8Object getObject() {
        return mObject;
    }

    public abstract void clean();

    public JustV8Object(V8 v8Runtime) {
        mRuntime = v8Runtime;
        initV8Object();
    }

    protected abstract void initV8Object();
}
