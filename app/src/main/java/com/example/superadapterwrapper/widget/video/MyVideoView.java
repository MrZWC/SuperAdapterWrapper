package com.example.superadapterwrapper.widget.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/18
 * Time: 9:11
 * <p>
 * 视频播放组件 view 基于GsyPlayer link:https://github.com/CarGuo/GSYVideoPlayer
 */
public class MyVideoView extends StandardGSYVideoPlayer {
    private View mErrorView;
    private TextView mRePlay;

    public MyVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public MyVideoView(Context context) {
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * layout的xml文件的view  id 不能轻易改动关系到gsyplayer的逻辑实现 请阅读源码逻辑后在改动
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.widget_video_view_layout;
    }

    /**
     * 初始化view
     * @param context
     */
    @Override
    protected void init(Context context) {
        super.init(context);
        mErrorView = findViewById(R.id.error_view);
        mRePlay = findViewById(R.id.re_play);
    }

    @Override
    public int getEnlargeImageRes() {
        return R.mipmap.ic_video_rotate;
    }

    @Override
    public int getShrinkImageRes() {
        return R.mipmap.ic_video_rotate;
    }

    /**
     * 播放错误  view显示回调
     * <p>
     * 增加自定义错误页面（mErrorView）显示 以及重新加载按钮处理（mRePlay）
     */
    @Override
    protected void changeUiToError() {
        super.changeUiToError();
        setViewShowState(mTopContainer, View.VISIBLE);
        setViewShowState(mErrorView, View.VISIBLE);
        mRePlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStartIcon();
                setViewShowState(mErrorView, View.INVISIBLE);
            }
        });
    }
}
