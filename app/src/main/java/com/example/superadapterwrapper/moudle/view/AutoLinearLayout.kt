package com.example.superadapterwrapper.moudle.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * author:zuoweichen
 * DAte:2023/5/9 17:43
 * Description:描述
 */
class AutoLinearLayout : ViewGroup {
    private var totalWSize = 0

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    constructor(
        context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //父布局的测量模式
        val wMode = MeasureSpec.getMode(widthMeasureSpec)
        val hMode = MeasureSpec.getMode(heightMeasureSpec)
        totalWSize = MeasureSpec.getSize(widthMeasureSpec) - paddingRight - paddingRight
        var totalHeight = paddingTop
        var lineTotalW = paddingLeft

        //记录有几行
        var lineCount = 1
        //去除内边距父容器的最大宽高
        val sizeW = MeasureSpec.getSize(widthMeasureSpec) - paddingRight - paddingLeft
        val sizeH = MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom

        val count = childCount

        for (i in 0 until count) {
            val child = getChildAt(i)
            val lp = child.layoutParams as LayoutParams
            val childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, 0, lp.width)
            val childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, 0, lp.height)
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec)
            //MeasureSpec.makeMeasureSpec(sizeW, if (wMode == MeasureSpec.EXACTLY))
        }
        setMeasuredDimension(sizeW, sizeH)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount == 0) {
            return
        }
        var childStart = 0
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            childView.layout(childStart, 0, childView.width, childView.height)
            childStart += childView.width
        }
    }

    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return super.generateLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}