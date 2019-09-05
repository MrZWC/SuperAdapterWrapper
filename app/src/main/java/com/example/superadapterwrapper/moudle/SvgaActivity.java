package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.opensource.svgaplayer.SVGAParser;

public class SvgaActivity extends BaseActivity {
    public static void start(Context context) {
        Intent intent=new Intent(context,SvgaActivity.class);
       context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_svga);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        SVGAParser svgaParser = new SVGAParser(this);
    }
}
