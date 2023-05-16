package com.example.superadapterwrapper.moudle.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.socks.library.KLog

class MyView : View {
    private val TAG = this.javaClass.simpleName

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val wsize = MeasureSpec.getSize(widthMeasureSpec)
        val hsize = MeasureSpec.getSize(heightMeasureSpec)
        KLog.i(TAG, "onMeasure:wsize=$wsize hsize=$hsize")
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        KLog.i(TAG, "onLayout")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        KLog.i(TAG, "onDraw")
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        KLog.i(MotionEvent.actionToString(event.action))
        return super.onTouchEvent(event)
    }
}