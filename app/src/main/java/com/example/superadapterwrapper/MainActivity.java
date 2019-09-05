package com.example.superadapterwrapper;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.adapter.MainAdapter;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.moudle.SvgaActivity;
import com.example.superadapterwrapper.moudle.TanActivity;
import com.example.superadapterwrapper.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> strings;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        strings = new ArrayList<>();
        strings.add("svga加载测试");
        strings.add("探探layout");
        ShowDataView();
    }

    private void ShowDataView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.top = DensityUtils.dp2px(MainActivity.this, 15);
            }
        });
        MainAdapter mainAdapter = new MainAdapter(this, strings);
        mRecyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        SvgaActivity.start(getContext());
                        break;
                    case 1:
                        TanActivity.start(getContext());
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void initLinstener() {

    }
}
