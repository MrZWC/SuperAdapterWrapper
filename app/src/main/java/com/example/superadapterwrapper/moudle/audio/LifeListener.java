package com.example.superadapterwrapper.moudle.audio;

import android.os.Bundle;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/19
 * Time: 12:00
 *
 * view生命周期回调
 */
public interface LifeListener {
    void onCreate(Bundle bundle);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
