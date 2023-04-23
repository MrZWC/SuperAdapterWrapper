package com.example.superadapterwrapper.moudle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import com.socks.library.KLog;

/**
 * author:zuoweichen
 * DAte:2023/3/2 10:26
 * Description:描述
 */
public class MyViewGroup2 extends FrameLayout {
    public MyViewGroup2(Context context) {
        super(context);
    }

    public MyViewGroup2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyViewGroup2(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        KLog.i(MotionEvent.actionToString(ev.getAction()));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        KLog.i(MotionEvent.actionToString(event.getAction()));
        return super.onTouchEvent(event);
    }
}
