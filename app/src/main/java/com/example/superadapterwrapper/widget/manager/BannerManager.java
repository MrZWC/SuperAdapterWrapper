package com.example.superadapterwrapper.widget.manager;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.util.DensityUtils;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/1 001
 * Time: 14:52
 */
public class BannerManager extends RecyclerView.LayoutManager {
    //RecyclerView从右往左滑动时，新出现的child添加在右边
    private static int ADD_RIGHT = 1;
    //RecyclerView从左往右滑动时，新出现的child添加在左边
    private static int ADD_LEFT = -1;
    //SDK中的方法，帮助我们计算一些layout childView 所需的值，详情看源码，解释的很明白
    private OrientationHelper helper;
    //动用 scrollToPosition 后保存去到childView的位置，然后重新布局即调用onLayoutChildren
    private int mPendingScrollPosition = 0;
    private int itemSpace;
    private int itemLastSpace;
    private Context context;

    public BannerManager(Context context) {
        this.context = context;
        this.itemSpace = DensityUtils.dp2px(context, 12);
        this.itemLastSpace = DensityUtils.dp2px(context, 60);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        //初始化OrientationHelper
        ensureStatus();


        int offset = itemLastSpace;
        //计算RecyclerView 可用布局宽度  具体实现 mLayoutManager.getWidth() - mLayoutManager.getPaddingLeft()
        //- mLayoutManager.getPaddingRight();
        int mAvailable = helper.getTotalSpace();
        //调用notifyDataSetChanged 才有 getChildCount() != 0
        if (getChildCount() != 0) {
            //得到第一个可见的childView
            View firstView = getChildAt(0);
            //得到第一个可见childView左边的位置
            offset = helper.getDecoratedStart(firstView) + itemLastSpace;
            //获取第一个可见childView在Adapter中的position（位置）
            mPendingScrollPosition = getPosition(firstView);
            //offset的值为负数，在可见区域的左边，那么当重新布局事得考虑正偏移
            mAvailable += Math.abs(offset);
        }
        //移除所有的view，
        detachAndScrapAttachedViews(recycler);
        //将可见区域的childView layout出来
        layoutScrap(recycler, state, offset, mAvailable);

    }

    private void ensureStatus() {
        if (helper == null) {
            helper = OrientationHelper.createHorizontalHelper(this);
        }
    }

    /**
     * 将可见区域的childView layout出来
     */
    private void layoutScrap(RecyclerView.Recycler recycler, RecyclerView.State state, int offset, int mAvailable) {
        for (int i = mPendingScrollPosition; i < state.getItemCount(); i++) {
            //可用布局宽度不足，跳出循环
            if (mAvailable <= 0) {
                break;
            }
            //在右边添加新的childView
            int childWidth = layoutScrapRight(recycler, state, i, offset);
            mAvailable -= childWidth;
            offset += childWidth + itemSpace;
        }
    }

    /**
     * RecyclerView从右往左滑动时，新出现的child添加在右边
     */
    private int layoutScrapRight(RecyclerView.Recycler recycler, RecyclerView.State state, int position, int offset) {
        return layoutScrap(recycler, state, position, offset, ADD_RIGHT);
    }

    /**
     * RecyclerView从右往左滑动时，新出现的child添加在右边
     */
    private int layoutScrapleft(RecyclerView.Recycler recycler, RecyclerView.State state, int position, int offset) {
        return layoutScrap(recycler, state, position, offset, ADD_LEFT);
    }

    /**
     * RecyclerView从右往左滑动时，添加新的child
     */
    private int layoutScrap(RecyclerView.Recycler recycler, RecyclerView.State state, int position, int offset, int direction) {
        //从 recycler 中取到将要出现的childView
        View childPosition = recycler.getViewForPosition(position);
        if (direction == ADD_RIGHT) {
            addView(childPosition);
        } else {
            addView(childPosition, 0);
        }
        //计算childView的大小
        measureChildWithMargins(childPosition, 0, 0);
        int childWidth = getDecoratedMeasuredWidth(childPosition);
        int childHeigth = getDecoratedMeasuredHeight(childPosition);
        if (direction == ADD_RIGHT) {
            //layout childView
            layoutDecorated(childPosition, offset, 0, offset + childWidth, childHeigth);
            if (state.getItemCount() - 1 == position) {

            }
        } else {
            layoutDecorated(childPosition, offset - childWidth, 0, offset, childHeigth);
        }
        return childWidth;
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //回收不可见的childview
        recylerUnVisiableView(recycler, dx);
        //将新出现的childview layout 出来
        int willScroll = fillChild(recycler, dx, state);
        //水平方向移动childview
        offsetChildrenHorizontal(-willScroll);
        return willScroll;
    }

    private int fillChild(RecyclerView.Recycler recycler, int dx, RecyclerView.State state) {
        if (dx > 0) {//RecyclerView从右往左滑动时
            //得到最后一个可见childview
            View lastView = getChildAt(getChildCount() - 1);
            //得到将显示的childView 在adapter 中的position
            int position = getPosition(lastView) + 1;
            //得到最后一个可见childView右边的偏移
            int offset = helper.getDecoratedEnd(lastView) + itemSpace;
            //判断是否有足够的空间
            if (offset - dx < getWidth() - getPaddingRight()) {
                //item 足够
                if (position < state.getItemCount()) {
                    layoutScrapRight(recycler, state, position, offset);
                } else {
                    //item 不足 返回新的可滚动的宽度
                    return offset - getWidth() + getPaddingRight();
                }
            }
        } else {//RecyclerView从左往右滑动时
            //得到第一个可见childview
            View firstView = getChildAt(0);
            //得到将显示的childView 在adapter 中的position
            int position = getPosition(firstView) - 1;
            //得到第一个可见childView左边的偏移
            int offset = helper.getDecoratedStart(firstView) - itemSpace;
            //判断是否有足够的空间
            if (offset - dx > getPaddingLeft()) {
                //item 足够
                if (position >= 0) {
                    layoutScrapleft(recycler, state, position, offset);
                } else {
                    //item 不足 返回新的可滚动的宽度
                    return offset + itemSpace - getPaddingLeft();
                }
            }
        }
        return dx;
    }

    /**
     * 回收不可见的childview
     */
    private void recylerUnVisiableView(RecyclerView.Recycler recycler, int dx) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (dx > 0) {//RecyclerView从右往左滑动时
                //将左边消失的childView 回收掉
                if (helper.getDecoratedEnd(child) - dx < getPaddingLeft()) {
                    removeAndRecycleView(child, recycler);
                    break;
                }
            } else {//RecyclerView从左往右滑动时
                //将右边的childView 回收掉
                if (helper.getDecoratedStart(child) + itemLastSpace - dx > getWidth() - getPaddingRight()) {
                    removeAndRecycleView(child, recycler);
                    break;
                }
            }
        }
    }
}
