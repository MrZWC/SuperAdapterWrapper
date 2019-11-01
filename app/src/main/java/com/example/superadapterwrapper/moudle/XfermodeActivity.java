package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;

public class XfermodeActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, XfermodeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_xfermode);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
