package com.example.superadapterwrapper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.R;

import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/9/2 002
 * Time: 17:45
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.VH> {
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<String> mData;

    public MainAdapter(Context context, List<String> mData) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainAdapter.VH(mLayoutInflater.inflate(R.layout.item_main_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final @NonNull VH holder,final int position) {
        holder.title.setText(mData.get(position));
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class VH extends RecyclerView.ViewHolder {
        private TextView title;

        public VH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }
    }
    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}
