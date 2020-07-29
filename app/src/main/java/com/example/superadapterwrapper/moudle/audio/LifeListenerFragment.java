package com.example.superadapterwrapper.moudle.audio;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/19
 * Time: 11:59
 * 空白fragment 监听非activity view的生命周期
 */
public class LifeListenerFragment extends Fragment {
    private LifeListener mLifeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLifeListener != null) {
            mLifeListener.onCreate(savedInstanceState);
        }
    }

    public void addLifeListener(LifeListener listener) {
        mLifeListener = listener;
    }

    public void removeLifeListener() {
        mLifeListener = null;
    }


    @Override
    public void onStart() {
        super.onStart();
        if (mLifeListener != null) {
            mLifeListener.onStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mLifeListener != null) {
            mLifeListener.onPause();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mLifeListener != null) {
            mLifeListener.onStop();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mLifeListener != null) {
            mLifeListener.onResume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLifeListener != null) {
            mLifeListener.onDestroy();
        }
    }
}
