package com.example.superadapterwrapper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.util.DensityUtils;
import com.example.superadapterwrapper.util.ScreenUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2019/11/1 001
 * Time: 10:15
 */
public class AnimatorAdapter extends RecyclerView.Adapter<AnimatorAdapter.VH> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> mData;

    public AnimatorAdapter(Context context, List<String> mData) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mData = mData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) mLayoutInflater.inflate(R.layout.item_animator_layout, parent, false);
       /* ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth(mContext) - DensityUtils.dp2px(mContext, 60) * 2;*/
        return new AnimatorAdapter.VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.title.setText(mData.get(position));
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
}
