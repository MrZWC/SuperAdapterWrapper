package com.example.superadapterwrapper.base;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/8/30 030
 * Time: 13:08
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initData(savedInstanceState);
        initLinstener();
    }
    /**
     * 设置视图
     */
    protected abstract void setContentView();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 处理初始化的接口
     */
    protected void initLinstener() {
    }

    ;

    /**
     * 处理数据
     *
     * @param savedInstanceState 数据状态恢复
     */
    protected abstract void initData(Bundle savedInstanceState);


    //View ID 绑定
    public <T extends View> T getView(int viewId) {
        View view = findViewById(viewId);
        return (T) view;
    }
    public Context getContext() {
        return this;
    }
}
