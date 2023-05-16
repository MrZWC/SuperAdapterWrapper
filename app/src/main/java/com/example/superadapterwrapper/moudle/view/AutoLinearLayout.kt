package com.example.superadapterwrapper.moudle.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

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
        // 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val sizeWidth = MeasureSpec.getSize(widthMeasureSpec)
        val sizeHeight = MeasureSpec.getSize(heightMeasureSpec)
        var layoutWidth = 0
        var layoutHeight = 0
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        var cWidth = 0
        var cHeight = 0
        val count = childCount
        if (widthMode == MeasureSpec.EXACTLY) {
            //如果布局容器的宽度模式是确定的（具体的size或者match_parent），直接使用父窗体建议的宽度
            layoutWidth = sizeWidth
            //动态计算高度
            if (heightMode == MeasureSpec.EXACTLY) {
                layoutHeight = sizeHeight
            } else {
                var tempWidth = 0//临时宽度
                var maxHeight = 0
                var tagLayoutHeight = 0
                for (i in 0 until count) {
                    val child = getChildAt(i)
                    tempWidth += child.measuredWidth//单行宽度
                    cHeight = child.measuredHeight
                    if (tempWidth > layoutWidth) {
                        tagLayoutHeight = layoutHeight
                        //换行
                        //重置tempWidth
                        tempWidth = child.measuredWidth
                        layoutHeight = tagLayoutHeight + cHeight
                        maxHeight = cHeight
                    } else {
                        maxHeight = if (cHeight > maxHeight) cHeight else maxHeight
                        layoutHeight = tagLayoutHeight + maxHeight
                    }
                }
            }

        } else {
            //如果是未指定或者wrap_content，我们都按照包裹内容做，宽度方向上只需要拿到所有子控件中宽度做大的作为布局宽度
            for (i in 0 until count) {
                val child = getChildAt(i)
                cWidth = child.getMeasuredWidth()
                //获取子控件最大宽度
                layoutWidth = if (cWidth > layoutWidth) cWidth else layoutWidth
            }
        }

        /*  if (heightMode == MeasureSpec.EXACTLY) {
              layoutHeight = sizeHeight
          } else {
              for (i in 0 until count) {
                  val child = getChildAt(i)
                  cHeight = child.measuredHeight
                  layoutHeight = if (cHeight > layoutHeight) cHeight else layoutHeight

              }
          }*/
        setMeasuredDimension(layoutWidth, layoutHeight)
    }

    /**
     * 为所有的子控件摆放位置.
     */
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        val count = childCount
        var childMeasureWidth = 0
        var childMeasureHeight = 0
        // 容器已经占据的宽度
        var layoutWidth = 0
        // 容器已经占据的高度度
        var layoutHeight = 0
        var maxChildHeight = 0 //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (i in 0 until count) {
            val child = getChildAt(i)
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.measuredWidth
            childMeasureHeight = child.measuredHeight
            if (layoutWidth + childMeasureWidth <= width) {
                // 如果一行没有排满，继续往右排列
                left = layoutWidth
                top = layoutHeight
                right = left + childMeasureWidth
                bottom = top + childMeasureHeight
            } else {
                // 排满后换行
                layoutWidth = 0
                layoutHeight += maxChildHeight
                maxChildHeight = 0
                left = layoutWidth
                top = layoutHeight
                right = left + childMeasureWidth
                bottom = top + childMeasureHeight
            }
            layoutWidth += childMeasureWidth //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight
            }
            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom)
        }
    }
}