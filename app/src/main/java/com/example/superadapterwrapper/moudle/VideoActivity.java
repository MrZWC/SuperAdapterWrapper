package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.widget.video.MyVideoView;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

/**
 * 视频播放组件实现类
 * <p>
 * 主要功能为接受传递参数（json）播放视频
 */
public class VideoActivity extends BaseActivity {
    private MyVideoView videoPlayer;
    OrientationUtils orientationUtils;

    public static void start(Context context) {
        Intent intent = new Intent(context, VideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_video);

    }

    @Override
    protected void initView() {
        videoPlayer = getView(R.id.video_player);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String source1 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
        String source2 = "http:/";
        String source3 = "https://cdynkf.ynedut.com/ynedut/cdynkf_ynedut/home/backPakage/ynedut_consumer/V10.0.200527.1-20200527020000-20200616115537/ynedut_consumer/project/WEB-INF/upload/b220031c1c03465996488dfa46eee5e0.mp4";
        String musicUrl = "https://webfs.yun.kugou.com/202006191130/074d3ab58d1ceecc4f96ae3e29c6cc32/part/0/971802/G201/M00/04/14/aYcBAF5Vh1-AEssiAD_h7yIF6lw785.mp3";
        String musimp = "https://cdynkf.ynedut.com/ynedut/cdynkf_ynedut/home/d504df7470df49ee95e0cb4d9815fe78.mp4";
        String h264 = "http://10.6.0.244:8081/iot/h264.mp4";
        String mkv = "http://10.6.0.244:8081/iot/1.mkv";
        String rmvb = "http://10.6.0.244:8081/iot/2.rmvb";
        videoPlayer.setVideoSize(122545455L);
        videoPlayer.setUp(rmvb, false, "测试视频");
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load("").centerCrop().into(imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        videoPlayer.setNeedLockFull(true);
        videoPlayer.setIfCurrentIsFullscreen(true);
        videoPlayer.setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (lock) {
                    if (orientationUtils != null) {
                        videoPlayer.setRotateViewAuto(false);
                        orientationUtils.setRotateWithSystem(false);
                        orientationUtils.setEnable(false);
                    }
                } else {
                    if (orientationUtils != null) {
                        videoPlayer.setRotateViewAuto(true);
                        orientationUtils.setRotateWithSystem(true);
                        orientationUtils.setEnable(true);
                    }
                }
            }
        });
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(false);
        videoPlayer.setIsTouchWigetFull(false);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        videoPlayer.clickStartIcon();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils != null && orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }
}
