package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;

public class SmallFileActivity extends BaseActivity {
    private EditText mEditText;

    public static void start(Context context) {
        Intent intent = new Intent(context, SmallFileActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_small_file);

    }

    @Override
    protected void initView() {
        mEditText = findViewById(R.id.mEditText);
        findViewById(R.id.start_happy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HappyActivity.start(getContext());
                String trim = mEditText.getText().toString().trim();
                if (trim.equals("zz123")) {
                    HappyActivity.start(getContext());
                }
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }
}
