package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.layoutmanagerlib.layoutmanager.tantantest.TanTanControl;
import com.example.layoutmanagerlib.layoutmanager.tantantest.TanTanLayoutManager;
import com.example.layoutmanagerlib.layoutmanager.tantantest.TanTanTouchCallback;
import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.widget.manager.TanLayoutManager;
import com.example.superadapterwrapper.widget.manager.TanTouchCallBack;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TanActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private CardView cardView;
    //
    private CommonAdapter<String> mAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, TanActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_tan);

    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("数据源" + i);
        }
        mAdapter = new CommonAdapter<String>(this, R.layout.item_tan_layout, list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView view = holder.getView(R.id.numText);
                view.setText(s);
            }
        };
        TanTanControl tanTanControl = new TanTanControl(4
                , 0.05f
                , (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
        mRecyclerView.setLayoutManager(new TanLayoutManager());
        //ItemTouchHelper.Callback callback = new TanTanTouchCallback(mRecyclerView, mAdapter, list, tanTanControl);
        TanTouchCallBack callback=new TanTouchCallBack(mRecyclerView);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }
}
