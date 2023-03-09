package com.example.superadapterwrapper.moudle.executor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.databinding.ActivityExecutorBinding
import com.example.superadapterwrapper.util.TimerTick
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

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
                    startTimer()
                }

            })
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

    private fun getExecutorService(): ExecutorService {
        if (executorService == null) {
            executorService = Executors.newSingleThreadExecutor()
        }
        return executorService!!
    }
}