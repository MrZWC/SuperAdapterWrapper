package com.example.superadapterwrapper.moudle.thread;

import android.os.HandlerThread;

/**
 * ClassName MyHandlerThread
 * User: zuoweichen
 * Date: 2023/2/14 11:55
 * Description: 描述
 */
public class MyHandlerThread extends HandlerThread {
    public MyHandlerThread(String name) {
        super(name);
    }

    public MyHandlerThread(String name, int priority) {
        super(name, priority);
    }

    @Override
    public void run() {
        super.run();
    }
}
