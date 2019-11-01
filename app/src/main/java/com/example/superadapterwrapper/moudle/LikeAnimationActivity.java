package com.example.superadapterwrapper.moudle;


import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;

public class LikeAnimationActivity extends BaseActivity {
    private Button test_bt;

    public static void start(Context context) {
        Intent intent = new Intent(context, LikeAnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_like_animation);
    }

    @Override
    protected void initView() {
        test_bt = getView(R.id.test_bt);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        test_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator alphaB = ObjectAnimator.ofFloat(test_bt, "rotation", 0f, -360f);
                alphaB.setDuration(10000);
                alphaB.setStartDelay(5000);
                AnimatorSet animatorSet = new AnimatorSet();
                int colorA = Color.parseColor("#ffffff");
                int colorB = Color.parseColor("#F74165");
                ObjectAnimator rotationB = ObjectAnimator.ofInt(test_bt, "backgroundColor", colorA, colorB);
                rotationB.setDuration(5000);
                rotationB.setEvaluator(new ArgbEvaluator());
                animatorSet.play(rotationB).with(alphaB);
                animatorSet.start();
            }
        });
    }
}
