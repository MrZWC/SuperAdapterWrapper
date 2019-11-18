package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.superadapterwrapper.R;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/9/19 019
 * Time: 10:48
 */
public class CircleView extends View {
    private Paint paint = new Paint();
    private int paintColor;

    public CircleView(Context context) {
        super(context);
        initView(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CircleView);//对文字只描边
        paintColor = ta.getColor(R.styleable.CircleView_bg_color, Color.WHITE);
    }

    public void setBgColor(@ColorInt int bgColor) {
        paintColor = bgColor;
        invalidate();
    }

    public void setBgColorResources(@ColorRes int bgColor) {
        paintColor = ContextCompat.getColor(getContext(), bgColor);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        给画笔设置颜色
        paint.setColor(paintColor);
//        设置画笔属性
        paint.setStyle(Paint.Style.FILL);//画笔属性是实心圆
        //paint.setStyle(Paint.Style.STROKE);//画笔属性是空心圆
        paint.setStrokeWidth(8);//设置画笔粗细

        /*四个参数：
                参数一：圆心的x坐标
                参数二：圆心的y坐标
                参数三：圆的半径
                参数四：定义好的画笔
                */
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2, paint);
    }

}
