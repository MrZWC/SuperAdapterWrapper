package com.example.superadapterwrapper.base.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/8/30 030
 * Time: 13:41
 */
public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    public BaseHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bindData(T data);

    public abstract int getContentView(Context context, ViewGroup parent);

}
