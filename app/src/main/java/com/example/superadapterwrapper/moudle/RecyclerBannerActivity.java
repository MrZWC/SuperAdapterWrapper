package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.BannerBean;
import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.adapter.BannerAdapter;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.widget.IndicatorViewLayout;

import java.util.ArrayList;
import java.util.List;

public class RecyclerBannerActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private IndicatorViewLayout mIndicatorViewLayout;
    private BannerAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, RecyclerBannerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_recycler_banner);

    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
        mIndicatorViewLayout = getView(R.id.mIndicatorViewLayout);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position != 0) {
                    outRect.left = 10;
                }
            }
        });
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);
        List<List<BannerBean>> lists = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<BannerBean> list = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                BannerBean bean = new BannerBean();
                bean.num = i + "+" + j + "";
                list.add(bean);
            }
            lists.add(list);
        }
        adapter = new BannerAdapter(getContext(), lists);
        mRecyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
        });
        mIndicatorViewLayout.bindView(mRecyclerView);
        adapter.setItemOnclickListener(new BannerAdapter.ItemOnclickListener() {
            @Override
            public void ItemClick(BannerBean bannerBean, int groupPostion, int childPostion) {
                bannerBean.num = bannerBean.num + "onclik";
                adapter.notifyDataSetChanged();
            }
        });

    }
}
