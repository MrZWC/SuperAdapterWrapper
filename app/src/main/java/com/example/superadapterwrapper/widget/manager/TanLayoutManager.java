package com.example.superadapterwrapper.widget.manager;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.common.CardConfig;

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

        fill(recycler, state);
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollHorizontallyBy(dx, recycler, state);
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    private void fill(RecyclerView.Recycler recycler, RecyclerView.State state) {
        drawView(recycler, state);
        recycleChildren(recycler);
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


    public void drawView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        int itemCount = getItemCount();
        if (itemCount < 1) {
            return;
        }
        if (itemCount > CardConfig.DEFAULT_SHOW_ITEM) {
            for (int i = CardConfig.DEFAULT_SHOW_ITEM; i >= 0; i--) {
                addHolderView(recycler, i);
            }
        } else {
            for (int i = itemCount - 1; i >= 0; i--) {
                addHolderView(recycler, i);
            }
        }

    }

    private void addHolderView(RecyclerView.Recycler recycler, int position) {
        final View view = recycler.getViewForPosition(position);
        addView(view);
        measureChildWithMargins(view, 0, 0);
        int widthSpace = getWidth() - getDecoratedMeasuredWidth(view);
        int heightSpace = getHeight() - getDecoratedMeasuredHeight(view);
        // recyclerview 布局
        layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2,
                widthSpace / 2 + getDecoratedMeasuredWidth(view),
                heightSpace / 2 + getDecoratedMeasuredHeight(view));

        if (position == CardConfig.DEFAULT_SHOW_ITEM) {
            view.setScaleX(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
            view.setScaleY(1 - (position - 1) * CardConfig.DEFAULT_SCALE);
        } else if (position > 0) {
            view.setScaleX(1 - position * CardConfig.DEFAULT_SCALE);
            view.setScaleY(1 - position * CardConfig.DEFAULT_SCALE);
        }
    }
}
