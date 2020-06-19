package com.example.superadapterwrapper;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superadapterwrapper.adapter.MainAdapter;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.moudle.AgentWebActivity;
import com.example.superadapterwrapper.moudle.BrowserActivity;
import com.example.superadapterwrapper.moudle.FilechooserActivity;
import com.example.superadapterwrapper.moudle.FullScreenActivity;
import com.example.superadapterwrapper.moudle.ItemAnimatorActivity;
import com.example.superadapterwrapper.moudle.LikeAnimationActivity;
import com.example.superadapterwrapper.moudle.RecyclerBannerActivity;
import com.example.superadapterwrapper.moudle.ShadowActivity;
import com.example.superadapterwrapper.moudle.SvgaActivity;
import com.example.superadapterwrapper.moudle.TanActivity;
import com.example.superadapterwrapper.moudle.VideoActivity;
import com.example.superadapterwrapper.moudle.WebViewActivity;
import com.example.superadapterwrapper.moudle.X5TencentWebViewActivity;
import com.example.superadapterwrapper.moudle.XfermodeActivity;
import com.example.superadapterwrapper.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private List<String> strings;

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        mRecyclerView = getView(R.id.mRecyclerView);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        strings = new ArrayList<>();
        strings.add("svga加载测试");
        strings.add("探探layout");
        strings.add("喜欢点击动画");
        strings.add("view阴影");
        strings.add("XfermodeTest");
        strings.add("自定义RecycleView动画");
        strings.add("RecycleView轮播实现");
        strings.add("webview视频测试");
        strings.add("X5webview视频测试");
        strings.add("作为一个浏览器的示例展示出来，采用android+web的模式");
        strings.add("用于展示在web端<input type=text>的标签被选择之后，文件选择器的制作和生成");
        strings.add("用于演示X5webview实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式");
        strings.add("agentweb");
        strings.add("video");
        ShowDataView();
    }

    private void ShowDataView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                int position = parent.getChildAdapterPosition(view);
                outRect.top = DensityUtils.dp2px(MainActivity.this, 15);
            }
        });
        MainAdapter mainAdapter = new MainAdapter(this, strings);
        mRecyclerView.setAdapter(mainAdapter);
        mainAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0:
                        SvgaActivity.start(getContext());
                        break;
                    case 1:
                        TanActivity.start(getContext());
                        break;
                    case 2:
                        LikeAnimationActivity.start(getContext());
                        break;
                    case 3:
                        ShadowActivity.start(getContext());
                        break;
                    case 4:
                        XfermodeActivity.start(getContext());
                        break;
                    case 5:
                        ItemAnimatorActivity.start(getContext());
                        break;
                    case 6:
                        RecyclerBannerActivity.start(getContext());
                        break;
                    case 7:
                        WebViewActivity.start(getContext());
                        break;
                    case 8:
                        X5TencentWebViewActivity.start(getContext());
                        break;
                    case 9:
                        BrowserActivity.start(getContext());
                        break;
                    case 10:
                        FilechooserActivity.start(getContext());
                        break;
                    case 11:
                        FullScreenActivity.start(getContext());
                        break;
                    case 12:
                        AgentWebActivity.start(getContext());
                        break;
                    case 13:
                        VideoActivity.start(getContext());
                        break;
                    default:
                }
            }
        });
    }

    @Override
    protected void initLinstener() {

    }
}
