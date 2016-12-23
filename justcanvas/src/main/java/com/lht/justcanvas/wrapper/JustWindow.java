package com.lht.justcanvas.wrapper;

import android.graphics.Paint;
import android.graphics.Rect;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Object;
import com.lht.justcanvas.JustConfig;
import com.lht.justcanvas.JustV8Object;
import com.lht.justcanvas.JustViewGroup;

import java.util.ArrayList;

/**
 * Created by lht on 16/9/9.
 */
public class JustWindow extends JustV8Object {

    private V8Object mV8Object;

    public V8Object getV8Object() {
        return mV8Object;
    }

    JustViewGroup mJustViewGroup;
    ArrayList<JustCanvas> mJustCanvasList = new ArrayList<>();

    public JustWindow(V8 v8Runtime, JustViewGroup viewGroup) {
        super(v8Runtime);
        this.mJustViewGroup = viewGroup;
    }

    @Override
    protected void initV8Object() {
        mV8Object = new V8Object(mRuntime);
        mV8Object.add("devicePixelRatio", JustConfig.density);
        mV8Object.registerJavaMethod(this, "createCanvas", "createCanvas", null);
        mV8Object.registerJavaMethod(this, "measureText", "measureText", new Class[]{String.class, V8Object.class});
    }

    @Override
    public void clean() {
        for (JustCanvas justCanvas: mJustCanvasList) {
            justCanvas.clean();
        }

        mV8Object.release();
    }

    public void drawWindow() {
        for (JustCanvas justCanvas: mJustCanvasList) {
            justCanvas.drawCanvas();
        }
    }

    public void invalidate() {
        for (JustCanvas justCanvas: mJustCanvasList) {
            mJustViewGroup.addView(justCanvas.getJustView());
            justCanvas.invalidate();
        }
    }

    public V8Object createCanvas() {
        JustCanvas justCanvas =
                new JustCanvas(mV8Object.getRuntime(), mJustViewGroup.getContext());
        mJustCanvasList.add(justCanvas);

        return justCanvas.getObject();
    }

    public V8Object measureText(String str, V8Object font) {
        //TODO
        Paint paint = new Paint();
        paint.setTextSize(font.getInteger("size"));

        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);

        V8Object result = new V8Object(mV8Object.getRuntime());
        result.add("width", rect.width());
        result.add("height", rect.height());
        return result;
    }
}
