package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.base.bean.BaseBean;
import com.example.superadapterwrapper.base.bean.TestBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.socks.library.KLog;

import java.util.ArrayList;

public class TextActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent = new Intent(context, TextActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_text);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String text = "测试状态-PC专用流程-管理员-2021-06-07 19:59:37";
        String json="{\"code\":\"1\",\"msg\":\"test string\",\"obj\":[{\"name\":\"小明\",\"title\":\"this is a title\"},{\"name\":\"小明1\",\"title\":\"this is a title1\"}]}";
        BaseBean<ArrayList<TestBean>> data=new Gson().fromJson(json,new TypeToken<BaseBean<ArrayList<TestBean>>>() {
        }.getType());
        KLog.i(data);
    }
}