package com.example.superadapterwrapper.widget;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.moudle.audio.LifeListener;
import com.example.superadapterwrapper.moudle.audio.LifeListenerFragment;
import com.example.superadapterwrapper.moudle.audio.MyAudioPlayer;
import com.idonans.lang.thread.Threads;
import com.idonans.lang.util.ViewUtil;
import com.socks.library.KLog;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/19
 * Time: 10:20
 */
public class AudioView extends FrameLayout implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener {
    //正常
    public static final int CURRENT_STATE_NORMAL = 0;
    //播放中
    public static final int CURRENT_STATE_PLAYING = 2;
    //开始缓冲
    public static final int CURRENT_STATE_PLAYING_BUFFERING_START = 3;
    //暂停
    public static final int CURRENT_STATE_PAUSE = 5;
    //自动播放结束
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    //错误状态
    public static final int CURRENT_STATE_ERROR = 7;

    //当前的播放状态
    protected int mCurrentState = CURRENT_STATE_NORMAL;
    protected boolean mPostProgress = false;
    //seek touch
    protected boolean mHadSeekTouch = false;

    private String mUrl;//播放资源
    private final String TAG = "AudioView";
    private LifeListenerFragment mLifeListenerFragment;
    private MyAudioPlayer mMediaPlayer;
    private TextView mTotal;
    private SeekBar mProgressBar;
    private ImageView mStartBtn;
    /**
     * 进度条是否能够拖动  当资源没准备好的时候不允许拖动
     */
    private boolean isCanTouchSeekBar = false;

    public AudioView(@NonNull Context context) {
        this(context, null);
    }

    public AudioView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AudioView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        LayoutInflater.from(getContext()).inflate(R.layout.widget_audio_view, this);
        mTotal = findViewById(R.id.total);
        mProgressBar = findViewById(R.id.progress);
        mStartBtn = findViewById(R.id.start_btn);
        initListener();
    }

    private void initListener() {
        ViewUtil.onClick(mStartBtn, new OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始播放
                if (mCurrentState == CURRENT_STATE_ERROR || mCurrentState == CURRENT_STATE_NORMAL) {
                    play();
                } else if (mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
                    if (mMediaPlayer != null) {
                        mMediaPlayer.rePaly();
                    }
                } else if (mCurrentState == CURRENT_STATE_PAUSE) {
                    //继续播放
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                    }
                } else if (mCurrentState == CURRENT_STATE_PLAYING) {
                    //暂停
                    if (mMediaPlayer != null) {
                        mMediaPlayer.pause();
                    }
                }
            }
        });
        mProgressBar.setOnTouchListener(this);
        mProgressBar.setOnSeekBarChangeListener(this);
    }

    /**
     * 设置播放资源
     *
     * @param url
     */
    public void setDataSources(String url) {
        mUrl = url;
    }

    public void play() {
        if (TextUtils.isEmpty(mUrl)) {
            Toast.makeText(getContext(), "请设置播放资源", Toast.LENGTH_SHORT).show();
            return;
        }
        mMediaPlayer = new MyAudioPlayer();
        mMediaPlayer.setOnAudioListener(new MyAudioPlayer.OnAudioListener() {
            @Override
            public void onError(MyAudioPlayer mp, int what, int extra) {
                mCurrentState = CURRENT_STATE_ERROR;
                isCanTouchSeekBar = false;
                Threads.postUi(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "音频加载失败或网络异常", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onPrepared(MyAudioPlayer mp) {
                KLog.i("AudioDuration", mp.getDuration() + "");
                int duration = mp.getDuration();
                setTotalTimeView(duration);
                isCanTouchSeekBar = true;
                mp.start();
            }


            @Override
            public void onStartPrepared(MyAudioPlayer mp) {
                mCurrentState = CURRENT_STATE_PLAYING_BUFFERING_START;
                Threads.postUi(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setSecondaryProgress(0);
                    }
                });
            }

            @Override
            public void onBufferingUpdate(MyAudioPlayer mp, int percent) {
                Threads.postUi(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setSecondaryProgress(percent);
                    }
                });
            }

            @Override
            public void onStart(MyAudioPlayer mp) {
                mPostProgress = true;
                mCurrentState = CURRENT_STATE_PLAYING;
                startProgressTimer();
                setPalyView(true);
            }

            @Override
            public void onPause(MyAudioPlayer mp) {
                mCurrentState = CURRENT_STATE_PAUSE;
                cancelProgressTimer();
                setPalyView(false);
            }


            @Override
            public void onCompletion(MyAudioPlayer mp) {
                cancelProgressTimer();
                setPalyView(false);
                setTotalTimeView(0);
                mCurrentState = CURRENT_STATE_AUTO_COMPLETE;
            }
        });
        mMediaPlayer.play(getContext(), mUrl);
        mCurrentState = CURRENT_STATE_NORMAL;

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        FragmentActivity activity = getActivity();
        if (activity != null) {
            addLifeListener(activity);
        }
    }

    /**
     * 因为当前生命周期绑定在一个空白生命周期监听fragment上的
     * 所以当view销毁是需要remove掉fragment
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeFagment();
    }

    /**
     * 添加Fragment
     *
     * @param activity
     */
    private void addLifeListener(FragmentActivity activity) {
        mLifeListenerFragment = getLifeListenerFragment(activity);
        mLifeListenerFragment.addLifeListener(mLifeListener);
    }

    /**
     * 移除Fragment
     */
    private void removeFagment() {
        if (mLifeListenerFragment != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(mLifeListenerFragment).commitAllowingStateLoss();
        }
    }

    private LifeListenerFragment getLifeListenerFragment(FragmentActivity activity) {
        FragmentManager manager = activity.getSupportFragmentManager();
        return getLifeListenerFragment(manager);
    }

    //添加空白fragment
    private LifeListenerFragment getLifeListenerFragment(FragmentManager manager) {
        FragmentTransaction mFragmentTransaction = manager.beginTransaction();
        LifeListenerFragment fragment = (LifeListenerFragment) manager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = new LifeListenerFragment();
            mFragmentTransaction.add(fragment, TAG).commitAllowingStateLoss();
        }
        return fragment;
    }

    private LifeListener mLifeListener = new LifeListener() {
        @Override
        public void onCreate(Bundle bundle) {
            Log.d(TAG, "onCreate");
        }

        @Override
        public void onStart() {
            Log.d(TAG, "onStart");
        }

        @Override
        public void onResume() {
            Log.d(TAG, "onResume");
            if (mMediaPlayer != null) {
                mMediaPlayer.start();
            }
        }

        @Override
        public void onPause() {
            if (mMediaPlayer != null) {
                mMediaPlayer.pause();
            }
            Log.d(TAG, "onPause");

        }

        @Override
        public void onStop() {
            Log.d(TAG, "onStop");
        }

        @Override
        public void onDestroy() {
            if (mMediaPlayer != null) {
                mMediaPlayer.release();
            }
            cancelProgressTimer();
            Log.d(TAG, "onDestroy");
        }
    };

    /**
     * 由于 getContext 获取到的不一定是activity  采用此方法获取
     *
     * @return
     */
    private FragmentActivity getActivity() {
        View parent = this;
        FragmentActivity activity = null;
        do {
            final Context context = parent.getContext();
            //Log.d(TAG, "view: " + parent + ", context: " + context);
            if (context != null && context instanceof FragmentActivity) {
                activity = (FragmentActivity) context;
                break;
            }
        } while ((parent = (View) parent.getParent()) != null);
        return activity;
    }

    Runnable progressTask = new Runnable() {
        @Override
        public void run() {
            if (mCurrentState == CURRENT_STATE_PLAYING) {
                setTextAndProgress();
            }
            if (mPostProgress) {
                postDelayed(this, 1000);
            }
        }
    };

    protected void startProgressTimer() {
        cancelProgressTimer();
        mPostProgress = true;
        postDelayed(progressTask, 300);
    }

    protected void cancelProgressTimer() {
        mPostProgress = false;
        removeCallbacks(progressTask);
    }

    protected void setTextAndProgress() {
        if (mHadSeekTouch) {
            return;
        }
        int position = mMediaPlayer.getCurrentPosition();
        int duration = mMediaPlayer.getDuration();
        int progress = position * 100 / (duration == 0 ? 1 : duration);
        setTotalTimeView(duration - position);
        mProgressBar.setProgress(progress);
    }

    protected void setPalyView(boolean isPlay) {
        mStartBtn.setImageResource(isPlay ? R.mipmap.ic_audio_pause : R.mipmap.ic_audio_start);
    }

    private void setTotalTimeView(int duration) {
        if (duration <= 0) {
            duration = 0;
        }
        int minute = duration / 1000 / 60;
        int second = duration / 1000 % 60;
        String timeString = (minute < 10 ? ("0" + minute) : minute) + ":" + (second < 10 ? ("0" + second) : second);
        mTotal.setText(timeString);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.progress:
                if (!isCanTouchSeekBar) {
                    return true;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        cancelProgressTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        startProgressTimer();
                        break;
                }
                break;
        }
        return false;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHadSeekTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mMediaPlayer != null) {
            int time = seekBar.getProgress() * mMediaPlayer.getDuration() / 100;
            mMediaPlayer.seekTo(time);
            if (mCurrentState == CURRENT_STATE_PAUSE || mCurrentState == CURRENT_STATE_AUTO_COMPLETE) {
                mMediaPlayer.start();
            }
        }

        mHadSeekTouch = false;
    }
}
