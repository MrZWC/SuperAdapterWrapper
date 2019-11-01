package com.example.superadapterwrapper.widget.manager;


import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/9/11 011
 * Time: 17:03
 */
public interface OnCardSwipeListener<T> {

    /**
     * 卡片还在滑动时回调
     *
     * @param viewHolder 该滑动卡片的viewHolder
     * @param ratio      滑动进度的比例
     * @param direction  卡片滑动的方向，CardConfig.SWIPING_LEFT 为向左滑，CardConfig.SWIPING_RIGHT 为向右滑，
     *                   CardConfig.SWIPING_NONE 为不偏左也不偏右
     */
    void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio, int direction);

    /**
     * 卡片完全滑出时回调
     *
     * @param viewHolder 该滑出卡片的viewHolder
     * @param direction  卡片滑出的方向，CardConfig.SWIPED_LEFT 为左边滑出；CardConfig.SWIPED_RIGHT 为右边滑出
     */
    void onSwiped(RecyclerView.ViewHolder viewHolder, int position, int direction);

    /**
     * 所有的卡片全部滑出时回调
     */
    void onSwipedClear();
}
