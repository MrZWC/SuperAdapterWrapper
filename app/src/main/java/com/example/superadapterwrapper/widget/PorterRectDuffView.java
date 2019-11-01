package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.superadapterwrapper.R;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/10/23 023
 * Time: 16:30
 */
public class PorterRectDuffView extends View {

    Paint mPaint;
    Context mContext;
    int BlueColor;
    int PinkColor;
    int mWith;
    int mHeight;

    public PorterRectDuffView(Context context) {
        super(context);
        init(context);
    }

    public PorterRectDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PorterRectDuffView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWith = getMeasuredWidth();
    }

    private void init(Context context) {
        mContext = context;
        BlueColor = ContextCompat.getColor(mContext, R.color.colorPrimary);
        PinkColor = ContextCompat.getColor(mContext, R.color.colorAccent);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        setBackgroundColor(Color.parseColor("#7A56E7"));
    }

    private Bitmap drawRectBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(BlueColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawRect(new RectF(0, 0, getWidth(), getHeight()), paint);
        return bm;
    }

    private Bitmap drawCircleBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(PinkColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(100, 100, 50, paint);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int y = getHeight() / 2;
        int x = getWidth() / 2;
      /*  canvas.drawColor(0xffffffff);
        mPaint.setColor(Color.RED);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        // canvas.drawRoundRect(drawCircleBm(), x, y, mPaint);
        canvas.drawRoundRect(new RectF(100, 100, 300, 300), 30, 30, mPaint);*/

        // mPaint.setXfermode(null);
        // 还原画布
        //canvas.restoreToCount(sc);
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        mPaint.setXfermode(null);
        canvas.drawBitmap(drawRectBm(), x, y, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        canvas.drawBitmap(drawCircleBm(), x, y, mPaint);
        mPaint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);
    }
}
