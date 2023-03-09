package com.example.superadapterwrapper.moudle.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.example.superadapterwrapper.ITestCallBack
import com.example.superadapterwrapper.ITestInterface
import com.example.superadapterwrapper.util.TimerTick
import java.io.FileDescriptor
import java.io.PrintWriter

/**
 * ClassName MyService
 * User: zuoweichen
 * Date: 2023/2/2 10:46
 * Description: 描述
 */
class MyService : Service() {
    private val mBinder = object : ITestInterface.Stub() {
        override fun registerCalback(callback: ITestCallBack?) {
            mCallback = callback
        }

    }
    private var num = 0
    private var mCallback: ITestCallBack? = null
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        startTimer()
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        clear()
        return super.onUnbind(intent)
    }

    override fun dump(fd: FileDescriptor?, writer: PrintWriter?, args: Array<out String>?) {
        TODO("Not yet implemented")
    }


    private fun clear() {
        timerTick?.stop()
        timerTick = null
    }

    private var timerTick: TimerTick? = null
    private fun startTimer() {
        if (timerTick == null) {
            timerTick = object : TimerTick(this) {
                override fun onTick() {
                    num += 1
                    mCallback?.setNum(num)
                }

            }
        }
        timerTick?.start()
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