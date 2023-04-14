package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.superadapterwrapper.R;

/**
 * author:zuoweichen
 * DAte:2023/3/9 9:40
 * Description:描述
 */
public class SensorView extends View {
    // 定义水平仪盘图片
    public Bitmap back;
    // 定义水平仪中的气泡图标
    public Bitmap bubble;
    // 定义水平仪中气泡的X、Y坐标
    public int bubbleX, bubbleY;

    public SensorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 加载水平仪图片和气泡图片
        back = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bubble = BitmapFactory.decodeResource(getResources(), R.drawable.icon_login_loading);
    }

    public void setBubble(int bubbleX,int bubbleY) {
        this.bubbleX = bubbleX;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制水平仪图片
        canvas.drawBitmap(back, 0, 0, null);
        // 根据气泡坐标绘制气泡
        canvas.drawBitmap(bubble, bubbleX, bubbleY, null);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }
}
