package com.example.superadapterwrapper.util;

import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import com.idonans.lang.WeakAbortSignal;
import com.idonans.lang.thread.Threads;

import timber.log.Timber;

/**
 * 通用计时timer
 */
@UiThread
public abstract class TimerTick extends WeakAbortSignal {

    private long mDelay = 1000L;
    private TickRunnable mTickRunnable;

    public TimerTick(@Nullable Object object) {
        super(object);
    }

    @UiThread
    public void setDelay(long delay) {
        mDelay = delay;
    }

    @UiThread
    public void start() {
        if (mTickRunnable == null) {
            mTickRunnable = new TickRunnable();
            Threads.postUi(mTickRunnable, mDelay);
        }
    }

    @UiThread
    public void stop() {
        mTickRunnable = null;
    }

    @UiThread
    public abstract void onTick();

    private class TickRunnable implements Runnable {

        @Override
        public void run() {
            try {
                if (mTickRunnable != TickRunnable.this) {
                    return;
                }
                if (TimerTick.this.isAbort()) {
                    return;
                }

                onTick();
                Threads.postUi(this, mDelay);
            } catch (Throwable e) {
                Timber.e(e);
            }
        }

    }

}
