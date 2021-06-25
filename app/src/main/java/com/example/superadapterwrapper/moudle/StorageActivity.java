package com.example.superadapterwrapper.moudle;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StatFs;
import android.widget.EditText;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;

public class StorageActivity extends BaseActivity {
    private TextView total_size_text;
    public static void start(Context context) {
        Intent intent = new Intent(context, StorageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_storage);

    }

    @Override
    protected void initView() {
        total_size_text = getView(R.id.total_size_text);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        total_size_text.setText(getDataTotalSize(this) + "");
    }

    public String getDataTotalSize(Context context) {
        String root = context.getCacheDir().getAbsolutePath();
        StatFs sf = new StatFs(root);
        long blockSize = sf.getBlockSizeLong();
        long blockCount = sf.getBlockCountLong();
        long availCount = sf.getAvailableBlocksLong();
        String s1 = "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + 1.f * blockSize * blockCount / 1024 / 1024 / 1024 + "GB  ";
        String s2 = "可用的block数目：:" + availCount + ",可用大小:" + 1.f * availCount * blockSize / 1024 / 1024 / 1024 + "GB";
        return s1 + s2;
    }
}