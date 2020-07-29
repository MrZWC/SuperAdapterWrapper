package com.example.superadapterwrapper.widget.video;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.widget.dialog.CommonDialog;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.NetworkUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.text.DecimalFormat;

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
    private long mVideoSize;//单位b
    public static final long B = 1L;
    public static final long KB = 1024 * B;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;

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
     *
     * @return
     */
    @Override
    public int getLayoutId() {
        return R.layout.widget_video_view_layout;
    }

    /**
     * 初始化view
     *
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
        mErrorView.setOnClickListener(v -> {

        });
        mRePlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                clickStartIcon();
                setViewShowState(mErrorView, View.INVISIBLE);
            }
        });
    }

    @Override
    protected void showWifiDialog() {
        if (!NetworkUtils.isAvailable(mContext)) {
            //Toast.makeText(mContext, getResources().getString(R.string.no_net), Toast.LENGTH_LONG).show();
            startPlayLogic();
            return;
        }
        String dataSizeString = getDataSizeString(mVideoSize);
        String contentString = TextUtils.isEmpty(dataSizeString) ? "" : "您正在使用手机网络，继续播放将消耗流量，当前需要" + dataSizeString;
        CommonDialog.builder(CommonUtil.getActivityContext(getContext()))
                .setLeftString("停止播放")
                .setRightString("继续播放")
                .setContentString(contentString)
                .setRightlListener(dialog -> {
                    startPlayLogic();
                    dialog.hide();
                })
                .build().show();
    }

    public long getVideoSize() {
        return mVideoSize;
    }

    public void setVideoSize(long videoSize) {
        mVideoSize = videoSize;
    }

    private String getDataSizeString(long size) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        if (size < MB) {
            return "0M";
        }
        if (size < GB) {
            float l = 1f * size / MB;
            return decimalFormat.format(l) + "M";
        } else if (size >= GB) {
            float l = 1f * size / GB;
            return decimalFormat.format(l) + "G";
        }

        return "";
    }
/*

    @Override
    protected boolean setUp(String url, boolean cacheWithPlay, File cachePath, String title, boolean changeState) {
        boolean b = super.setUp(url, cacheWithPlay, cachePath, title, changeState);
        if (isShowNetConfirm()) {
            showWifiDialog();
            return false;
        }
        return b;
    }
*/

    @Override
    public void clickStartIcon() {
        super.clickStartIcon();
    }
}
