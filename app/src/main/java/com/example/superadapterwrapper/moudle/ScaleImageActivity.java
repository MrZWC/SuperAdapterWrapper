package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.base.bean.ImNoticeLiveInfo;
import com.example.superadapterwrapper.widget.scale.ScaleView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ScaleImageActivity extends BaseActivity {
    private ScaleView mScaleView;
    private ImageView load_image;

    public static void start(Context context) {
        Intent intent = new Intent(context, ScaleImageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_scale_image);
    }

    @Override
    protected void initView() {
        mScaleView = getView(R.id.image);
        load_image = getView(R.id.load_image);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        List<String> strings=new ArrayList<>();
        strings.add("1");
        strings.add("12");
        strings.add("13");
        strings.add("14");
        String[] ss={"1","2","3"};
        KLog.i(ss.toString());
        KLog.i(new Gson().toJson(strings));
        KLog.i(new Gson().toJson(ss));
        String url = "http://oa.wlzjx.net/ynedut/third/file/download.htm?fileId=M00/03/1E/ChISGmFKjImEUAPPAAAAAOWIoek857.jpg";
        RequestOptions options = new RequestOptions();
        options.placeholder(R.mipmap.defalut_image_large)
                .error(R.mipmap.defalut_image_break);
        Glide.with(mScaleView).load(url)
                .apply(options).into(new CustomTarget<Drawable>() {
            @Override
            public void onLoadStarted(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {
                super.onLoadStarted(placeholder);
                load_image.setVisibility(View.VISIBLE);
                load_image.setImageResource(R.drawable.progress_bar);
            }

            @Override
            public void onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                load_image.setImageResource(R.mipmap.defalut_image_break);
            }

            @Override
            public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                try {
                    load_image.setVisibility(View.GONE);
                    mScaleView.setImageDrawable(resource);
                } catch (Exception e) {
                    Timber.e(e);
                }
            }

            @Override
            public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {

            }
        });


    }
}