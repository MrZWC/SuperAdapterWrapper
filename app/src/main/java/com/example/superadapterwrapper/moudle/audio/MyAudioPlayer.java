package com.example.superadapterwrapper.moudle.audio;

import android.content.Context;
import android.media.MediaPlayer;

import com.socks.library.KLog;

import java.io.IOException;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/19
 * Time: 15:24
 */
public class MyAudioPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer mPlayer;
    private boolean hasPrepared;

    private void initIfNecessary() {
        if (null == mPlayer) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnErrorListener(this);
            mPlayer.setOnCompletionListener(this);
            mPlayer.setOnPreparedListener(this);
            mPlayer.setOnBufferingUpdateListener(this);
        }
    }

    public void play(Context context, String url) {
        hasPrepared = false; // 开始播放前讲Flag置为不可操作
        initIfNecessary(); // 如果是第一次播放/player已经释放了，就会重新创建、初始化
        try {
            mPlayer.reset();
            mPlayer.setDataSource(url); // 设置曲目资源
            mPlayer.prepareAsync(); // 异步的准备方法
            if (mOnAudioListener != null) {
                mOnAudioListener.onStartPrepared(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void rePaly() {
        seekTo(0);
        start();
    }

    public void start() {
        // release()会释放player、将player置空，所以这里需要判断一下
        if (null != mPlayer && hasPrepared) {
            mPlayer.start();
            if (mOnAudioListener != null) {
                mOnAudioListener.onStart(this);
            }
        }
    }

    public void pause() {
        if (null != mPlayer && hasPrepared) {
            mPlayer.pause();
            if (mOnAudioListener != null) {
                mOnAudioListener.onPause(this);
            }
        }
    }

    public void seekTo(int position) {
        if (null != mPlayer && hasPrepared) {
            mPlayer.seekTo(position);
        }
    }

    public void release() {
        hasPrepared = false;
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    public int getDuration() {

        if (mPlayer != null) {
            return mPlayer.getDuration();
        }
        return 0;
    }

    public int getCurrentPosition() {

        if (mPlayer != null) {
            return mPlayer.getCurrentPosition();
        }
        return 0;
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        hasPrepared = true; // 准备完成后回调到这里
        if (mOnAudioListener != null) {
            mOnAudioListener.onPrepared(this);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mOnAudioListener != null) {
            mOnAudioListener.onCompletion(this);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        hasPrepared = false;
        if (mOnAudioListener != null) {
            mOnAudioListener.onError(this, what, extra);
        }
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        KLog.i("BufferingUpdate", percent + "");
        if (mOnAudioListener != null) {
            mOnAudioListener.onBufferingUpdate(this, percent);
        }
    }

    private OnAudioListener mOnAudioListener;

    public void setOnAudioListener(OnAudioListener onAudioListener) {
        mOnAudioListener = onAudioListener;
    }


    public interface OnAudioListener {
        void onError(MyAudioPlayer mp, int what, int extra);


        /**
         * 开始准备
         *
         * @param mp
         */
        void onStartPrepared(MyAudioPlayer mp);

        /**
         * 准备完成
         *
         * @param mp
         */
        void onPrepared(MyAudioPlayer mp);

        /**
         * 缓冲进度回调
         *
         * @param mp
         * @param percent
         */
        void onBufferingUpdate(MyAudioPlayer mp, int percent);

        /**
         * 暂停
         */
        void onStart(MyAudioPlayer mp);

        /**
         * 暂停
         */
        void onPause(MyAudioPlayer mp);


        /**
         * 播放结束
         */
        void onCompletion(MyAudioPlayer mp);
    }
}
