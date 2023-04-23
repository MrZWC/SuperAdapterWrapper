package com.example.superadapterwrapper.moudle.executor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.databinding.ActivityExecutorBinding
import com.example.superadapterwrapper.util.TimerTick
import com.socks.library.KLog
import java.util.concurrent.*

class ExecutorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExecutorBinding
    private var executorService: ExecutorService? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ExecutorActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExecutorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            getExecutorService().execute(object : Runnable {
                override fun run() {
                    aa()
                }

            })
        }
    }

    private fun aa() {
        for (i in 0..30) {
            val runnable = object : Runnable {
                override fun run() {
                    SystemClock.sleep(2000);
                    KLog.d("google_lenve_fb", "run: " + i);
                }
            }
            getExecutorService().execute(runnable)
        }
    }

    private fun startTimer() {
        var num = 0
        var timerTick: TimerTick
        timerTick = object : TimerTick(this) {
            override fun onTick() {
                num += 1
                binding.numText.setText(num.toString())
            }

        }
        timerTick.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        getExecutorService().shutdownNow()
    }
    private fun getExecutorService(): ExecutorService {
        if (executorService == null) {
            executorService = ThreadPoolExecutor(3,30,1,TimeUnit.SECONDS,
                LinkedBlockingDeque<Runnable>(6)
            )
        }
        return executorService!!
    }
}