package com.example.superadapterwrapper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
public class BannerGridAdapter extends RecyclerView.Adapter<BannerGridAdapter.VH> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<BannerBean> mData;

    public BannerGridAdapter(Context context, List<BannerBean> mData) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) mLayoutInflater.inflate(R.layout.item_banner_grid_layout, parent, false);
       /* ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth(mContext) - DensityUtils.dp2px(mContext, 60) * 2;*/
        return new BannerGridAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.title.setText(mData.get(position).num);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemOnclickListener != null) {
                    itemOnclickListener.ItemClick(mData.get(position), position);
                }
            }
        });
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

    private ItemOnclickListener itemOnclickListener;

    public void setItemOnclickListener(ItemOnclickListener itemOnclickListener) {
        this.itemOnclickListener = itemOnclickListener;
    }

    public interface ItemOnclickListener {
        void ItemClick(BannerBean bannerBean, int postion);
    }
}
