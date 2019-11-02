package com.example.superadapterwrapper.animation;

import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.animation.BaseItemAnimator;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/1 001
 * Time: 11:09
 */
public class TestItemAnimator extends BaseItemAnimator {
    @Override
    public void setRemoveAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {

    }

    @Override
    public void removeAnimationEnd(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void addAnimationInit(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void setAddAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {

    }

    @Override
    public void addAnimationCancel(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void setOldChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {

    }

    @Override
    public void oldChangeAnimationEnd(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void newChangeAnimationInit(RecyclerView.ViewHolder holder) {

    }

    @Override
    public void setNewChangeAnimation(RecyclerView.ViewHolder holder, ViewPropertyAnimatorCompat animator) {

    }

    @Override
    public void newChangeAnimationEnd(RecyclerView.ViewHolder holder) {

    }
}
