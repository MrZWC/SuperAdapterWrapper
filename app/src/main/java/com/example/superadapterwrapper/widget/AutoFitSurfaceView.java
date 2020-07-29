package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/7
 * Time: 14:30
 */
public class AutoFitSurfaceView extends SurfaceView {
    private String TAG = AutoFitSurfaceView.this.getClass().getSimpleName();
    private float mAspectRatio = 0f;

    public AutoFitSurfaceView(Context context) {
        super(context);
    }

    public AutoFitSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AutoFitSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAspectRatio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        mAspectRatio = 1f * width / height;
        getHolder().setFixedSize(width, height);
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mAspectRatio == 0f) {
            setMeasuredDimension(width, height);
        } else {

            // Performs center-crop transformation of the camera frames
            int newWidth;
            int newHeight;
            float actualRatio = (width > height) ? mAspectRatio : 1f / mAspectRatio;
            if (width < height * actualRatio) {
                newHeight = height;
                newWidth = (int) (height * actualRatio);
            } else {
                newWidth = width;
                newHeight = (int) (width / actualRatio);
            }

            Log.d(TAG, "Measured dimensions set: $newWidth x $newHeight");
            setMeasuredDimension(newWidth, newHeight);
        }
    }
}
