package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.util.DensityUtils;
import com.example.superadapterwrapper.widget.CircleView;
import com.example.superadapterwrapper.widget.ShadowDrawable;

public class ShadowActivity extends BaseActivity {
    private View mCircleView;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShadowActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_shadow);
    }

    @Override
    protected void initView() {
        mCircleView = getView(R.id.mCircleView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ShadowDrawable.setShadowDrawable(mCircleView, ShadowDrawable.SHAPE_CIRCLE,Color.parseColor("#ffffff"), DensityUtils.dp2px(getContext(),8),
                Color.parseColor("#4775F6E5"),DensityUtils.dp2px(getContext(),10), 0, 0);
    }
}
