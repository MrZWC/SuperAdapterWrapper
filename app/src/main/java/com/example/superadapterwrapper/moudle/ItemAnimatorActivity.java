package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.CustomItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.MyLinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.adapter.AnimatorAdapter;
import com.example.superadapterwrapper.animation.FadeItemAnimator;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemAnimatorActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private Button addBt;
    private Button removeBt;
    private AnimatorAdapter mAdapter;
    private List<String> mData;
    private int itemDecoration;
    private LinearSnapHelper pagerSnapHelper;
    private FadeItemAnimator fadeItemAnimator;

    public static void start(Context context) {
        Intent intent = new Intent(context, ItemAnimatorActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_item_animator);
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
        addBt = getView(R.id.add_bt);
        removeBt = getView(R.id.remove_bt);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        itemDecoration = DensityUtils.dp2px(getContext(), 12);
        MyLinearLayoutManager linearLayoutManager = new MyLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        fadeItemAnimator = new FadeItemAnimator();
        //
        mRecyclerView.setItemAnimator(fadeItemAnimator);
        //mRecyclerView.setItemAnimator(new CustomItemAnimator());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position != 0) {
                    outRect.left = itemDecoration;
                }


            }
        });
     /*   LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(mRecyclerView);
*/
        pagerSnapHelper = new LinearSnapHelper();
        pagerSnapHelper.attachToRecyclerView(mRecyclerView);
        mData = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            mData.add(i + "");
        }
        mAdapter = new AnimatorAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initLinstener() {
        addBt.setOnClickListener(this);
        removeBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_bt:
                mData.add("我是添加的" + mData.size());
                mAdapter.notifyItemInserted(mAdapter.getItemCount() - 1);
               /* mData.add(mData.size() - 1, "我是添加的" + mData.size());
                mAdapter.notifyItemInserted(mAdapter.getItemCount() - 2);*/
                mRecyclerView.postOnAnimation(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                    }
                });
                break;
            case R.id.remove_bt:
                mData.remove(mData.size() - 1);
                mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                break;
            default:
        }
    }
}
