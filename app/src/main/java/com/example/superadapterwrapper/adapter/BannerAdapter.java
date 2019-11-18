package com.example.superadapterwrapper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.BannerBean;
import com.example.superadapterwrapper.R;

import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/1 001
 * Time: 10:15
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.VH> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<List<BannerBean>> mData;

    public BannerAdapter(Context context, List<List<BannerBean>> mData) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) mLayoutInflater.inflate(R.layout.item_banner_layout, parent, false);
        return new BannerAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int groupPosition) {
        holder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        BannerGridAdapter gridAdapter = new BannerGridAdapter(mContext, mData.get(groupPosition));
        holder.mRecyclerView.setAdapter(gridAdapter);
        gridAdapter.setItemOnclickListener(new BannerGridAdapter.ItemOnclickListener() {
            @Override
            public void ItemClick(BannerBean bannerBean, int postion) {
                if (itemOnclickListener != null) {
                    itemOnclickListener.ItemClick(bannerBean, groupPosition, postion);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class VH extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;

        public VH(View itemView) {
            super(itemView);
            mRecyclerView = itemView.findViewById(R.id.mRecyclerView);
        }
    }

    private ItemOnclickListener itemOnclickListener;

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    public interface ItemOnclickListener {
        void ItemClick(BannerBean bannerBean, int groupPostion, int childPostion);
    }
}
