package com.example.superadapterwrapper.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

/**
 * ClassName PersonService
 * User: zuoweichen
 * Date: 2022/5/17 17:27
 * Description: 描述
 */
public class PersonService extends Service {
    public PersonService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IPersonImpl();
    }
}
