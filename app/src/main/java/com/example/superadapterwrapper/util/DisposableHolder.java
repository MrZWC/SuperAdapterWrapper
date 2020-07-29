package com.example.superadapterwrapper.util;

import androidx.annotation.Nullable;

import io.reactivex.rxjava3.disposables.Disposable;


public class DisposableHolder {

    private Disposable mDisposable;

    /**
     * close last disposable if found and hold new one.
     */
    public void set(@Nullable Disposable disposable) {
        if (mDisposable == disposable) {
            return;
        }
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
        mDisposable = disposable;
    }

    /**
     * close last disposable if found.
     */
    public void clear() {
        set(null);
    }

}
