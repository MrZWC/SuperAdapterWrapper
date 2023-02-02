package com.example.superadapterwrapper.moudle.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.superadapterwrapper.util.TimerTick

/**
 * ClassName MyService
 * User: zuoweichen
 * Date: 2023/2/2 10:46
 * Description: 描述
 */
class MyService : Service() {
    private val mBinder = MyBinder()
    private var num = 0
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startTimer()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }

    class MyBinder : Binder() {

    }

    private fun startTimer() {
        val timerTick = object : TimerTick(this) {
            override fun onTick() {
                num += 1
                onTimerListener?.onTimer(num)
            }

        }
        timerTick.start()
    }

    interface OnTimerListener {
        fun onTimer(num: Int)
    }

    private var onTimerListener: OnTimerListener? = null
    fun setOnTimerListener(onTimerListener: OnTimerListener?) {
        this.onTimerListener = onTimerListener
    }

    fun reset() {
        num = 0
    }
}