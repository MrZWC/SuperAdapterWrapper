package com.example.superadapterwrapper.moudle.audio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.widget.dialog.AudioDialog;

public class AudioActivity extends BaseActivity {
    private TextView mTextview;

    public static void start(Context context) {
        Intent intent = new Intent(context, AudioActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_audio);

    }

    @Override
    protected void initView() {
        mTextview = getView(R.id.mtextview);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initLinstener() {
        super.initLinstener();
        mTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioDialog dialog = new AudioDialog(AudioActivity.this);
                dialog.show();
            }
        });
    }
}
