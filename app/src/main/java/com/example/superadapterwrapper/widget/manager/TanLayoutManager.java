package com.example.superadapterwrapper.widget.manager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/9/9 009
 * Time: 11:00
 */
public class TanLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        //super.onLayoutChildren(recycler, state);
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }

        //onceCompleteScrollLength = -1;

        // 分离全部已有的view 放入临时缓存  mAttachedScrap 集合中
        detachAndScrapAttachedViews(recycler);

        fill(recycler, state, 0);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state, int dx) {
        int resultDelta = dx;
        drawView(recycler, state);
        //recycleChildren(recycler);
    }

    /**
     * 回收需回收的item
     */
    private void recycleChildren(RecyclerView.Recycler recycler) {
        List<RecyclerView.ViewHolder> scrapList = recycler.getScrapList();
        for (int i = 0; i < scrapList.size(); i++) {
            RecyclerView.ViewHolder holder = scrapList.get(i);
            removeAndRecycleView(holder.itemView, recycler);
        }
    }

    private int mLastVisiPos;

    public void drawView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        mLastVisiPos = itemCount - 1;
        for (int i = 0; i < mLastVisiPos; i++) {

            View view = recycler.getViewForPosition(i);
            addView(view, 0);
            measureChildWithMargins(view, 0, 0);
            int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
            int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 4,
                    widthSpace / 2 + getDecoratedMeasuredWidth(view),
                    heightSpace / 4 + getDecoratedMeasuredHeight(view));
            if (i != 0) {
                view.setScaleX(0.9f);
                view.setScaleY(0.9f);
            }

        }
    }
}
