package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
public class PorterDuffView extends View {

    Paint mPaint;
    Context mContext;
    int BlueColor;
    int PinkColor;
    int mWith;
    int mHeight;

    public PorterDuffView(Context context) {
        super(context);
        init(context);
    }

    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PorterDuffView(Context context, AttributeSet attrs, int defStyleAttr) {
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
    }

    private Bitmap drawRectBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(BlueColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawRect(new RectF(0, 0, 70, 70), paint);
        return bm;
    }

    private Bitmap drawCircleBm() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(PinkColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        Bitmap bm = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas cavas = new Canvas(bm);
        cavas.drawCircle(70, 70, 35, paint);
        return bm;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setFilterBitmap(false);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(20);
        RectF recf = new RectF(20, 20, 60, 60);
        mPaint.setColor(BlueColor);
        canvas.drawRect(recf, mPaint);
        mPaint.setColor(PinkColor);
        canvas.drawCircle(100, 40, 20, mPaint);
        int sc = canvas.saveLayer(0, 0, mWith, mHeight, null, Canvas.ALL_SAVE_FLAG);
        int y = 180;
        int x = 50;
        for (PorterDuff.Mode mode : PorterDuff.Mode.values()) {
            if (y >= 900) {
                y = 180;
                x += 200;
            }
            mPaint.setXfermode(null);
            canvas.drawText(mode.name(), x + 100, y, mPaint);
            canvas.drawBitmap(drawRectBm(), x, y, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(mode));
            canvas.drawBitmap(drawCircleBm(), x, y, mPaint);
            y += 120;
        }
        mPaint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);
    }
}
