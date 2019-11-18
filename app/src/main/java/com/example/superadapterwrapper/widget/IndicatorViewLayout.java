package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/14 014
 * Time: 11:08
 */
public class IndicatorViewLayout extends FrameLayout {
    private RecyclerView mRecyclerView;
    //
    private List<IndicatorBean> indicatorList = new ArrayList<>();

    public IndicatorViewLayout(Context context) {
        super(context);
        initView(context, null);
    }

    public IndicatorViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public IndicatorViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_indicator_layout, this);
        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        PagerSnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(new IndicatorAdapter());
    }

    class IndicatorAdapter extends RecyclerView.Adapter<IndicatorAdapter.VH> {

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new IndicatorAdapter.VH(LayoutInflater.from(getContext()).inflate(R.layout.item_indicator_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            boolean isCheck = indicatorList.get(position).isCheck;
            holder.mCircleView.setBgColor(isCheck ? 0xFF333333 : 0xFFD8D8D8);
        }


        @Override
        public int getItemCount() {
            return indicatorList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            private CircleView mCircleView;

            public VH(View itemView) {
                super(itemView);
                mCircleView = itemView.findViewById(R.id.mCircleView);
            }
        }
    }

    class IndicatorBean {
        public boolean isCheck;
    }

    public void bindView(final RecyclerView bannerRecyclerView) {
        if (bannerRecyclerView == null
                || bannerRecyclerView.getAdapter() == null
                || bannerRecyclerView.getAdapter().getItemCount() == 0) {
            return;
        }
        indicatorList.clear();
        int itemCount = bannerRecyclerView.getAdapter().getItemCount();
        for (int i = 0; i < itemCount; i++) {
            IndicatorBean indicatorBean = new IndicatorBean();
            indicatorList.add(indicatorBean);
        }
        bannerRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    if (bannerRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                        LinearLayoutManager manager = (LinearLayoutManager) bannerRecyclerView.getLayoutManager();
                        int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                        Log.i("positionNum", firstVisibleItemPosition + "");
                        for (int i = 0; i < indicatorList.size(); i++) {
                            indicatorList.get(i).isCheck = false;
                        }
                        indicatorList.get(firstVisibleItemPosition).isCheck = true;
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            }
        });
    }
}
