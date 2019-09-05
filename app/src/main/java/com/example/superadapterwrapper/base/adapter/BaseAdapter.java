package com.example.superadapterwrapper.base.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/8/30 030
 * Time: 13:30
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mDatas = new ArrayList<>();

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        ((BaseHolder) holder).bindData(mDatas.get(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClick(mDatas.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener<T> {
        void OnItemClick(T data, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
