package com.example.superadapterwrapper.animation;


import android.view.View;

import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.view.ViewPropertyAnimatorListener;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by ivy on 2017/3/22.
 * Description：
 */

public class FadeItemAnimator extends BaseItemAnimator {
    /**
     * 执行移除动画
     *
     * @param holder   被移除的ViewHolder
     * @param animator 被移动的ViewHolder对应动画对象
     */
    @Override
    public void setRemoveAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        animator.alpha(0);
    }

    /**
     * 执行移除动画结束，执行还原，因为该ViewHolder会被复用
     *
     * @param view 被移除的ViewHolder
     */
    @Override
    public void removeAnimationEnd(RecyclerView.ViewHolder view) {
        view.itemView.setAlpha(1);
    }

    /**
     * 执行添加动画初始化 这里设置透明为0添加时就会有渐变效果当然你可以在执行动画代码之前执行
     *
     * @param holder 添加的ViewHolder
     */
    @Override
    public void addAnimationInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0f);
        holder.itemView.setScaleX(0f);
        holder.itemView.setScaleY(0f);
    }

    /**
     * 执行添加动画
     *
     * @param holder   添加的ViewHolder
     * @param animator 添加的ViewHolder对应动画对象
     */
    @Override
    public void setAddAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        animator.alpha(1);
        animator.scaleX(1f);
        animator.scaleY(1f);
        animator.translationX(-100f);
        animator.setListener(new ViewPropertyAnimatorListener() {
            @Override
            public void onAnimationStart(View view) {
              /*  if (animatiorStateListener != null) {
                    animatiorStateListener.addAnimationStart();
                }*/
            }

            @Override
            public void onAnimationEnd(View view) {

            }

            @Override
            public void onAnimationCancel(View view) {

            }
        });
    }

    /**
     * 取消添加还原状态以复用
     *
     * @param holder 添加的ViewHolder
     */
    @Override
    public void addAnimationCancel(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(1f);
        holder.itemView.setScaleY(1f);
        holder.itemView.setScaleY(1f);
    }

    /**
     * 更新时旧的ViewHolder动画
     *
     * @param holder   旧的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setOldChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        animator.alpha(0);
        animator.scaleY(0f);
        animator.scaleX(0f);
    }

    /**
     * 更新时旧的ViewHolder动画结束，执行还原
     *
     * @param holder
     */
    @Override
    public void oldChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(1);
    }

    /**
     * 更新时新的ViewHolder初始化
     *
     * @param holder 更新时新的ViewHolder
     */
    @Override
    public void newChangeAnimationInit(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(0);
    }

    /**
     * 更新时新的ViewHolder动画
     *
     * @param holder   新的ViewHolder
     * @param animator ViewHolder对应动画对象
     */
    @Override
    public void setNewChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {
        animator.alpha(1);
        animator.scaleX(1f);
        animator.scaleY(1f);
    }

    /**
     * 更新时新的ViewHolder动画结束，执行还原
     *
     * @param holder
     */
    @Override
    public void newChangeAnimationEnd(RecyclerView.ViewHolder holder) {
        holder.itemView.setAlpha(1);
        holder.itemView.setScaleX(1f);
        holder.itemView.setScaleY(1f);
    }

    private AnimatiorStateListener animatiorStateListener;

    public void setAnimatiorStateListener(AnimatiorStateListener animatiorStateListener) {
        this.animatiorStateListener = animatiorStateListener;
    }

    public interface AnimatiorStateListener {
        void addAnimationStart();
    }
}
