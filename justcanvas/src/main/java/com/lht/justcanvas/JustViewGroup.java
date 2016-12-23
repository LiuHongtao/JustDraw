package com.lht.justcanvas;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by lht on 16/9/9.
 */
public class JustViewGroup extends ViewGroup {

    Context mContext;

    public JustViewGroup(Context context) {
        super(context);
        mContext = context;
    }

    public JustViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            JustView childView = (JustView)getChildAt(i);

            int x = (int)childView.getCanvasX();
            int y = (int)childView.getCanvasY();
            int width  = (int)childView.getCanvasWidth();
            int height = (int)childView.getCanvasHeight();

            childView.layout(x, y, x + width, y + height);
        }
    }
}
