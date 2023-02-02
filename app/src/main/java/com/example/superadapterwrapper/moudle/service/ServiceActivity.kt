package com.example.superadapterwrapper.moudle.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.superadapterwrapper.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding

    companion object {
        const val TAG = "TestActivity"
        fun start(context: Context) {
            val intent = Intent(context, ServiceActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startBtn.setOnClickListener {
            startMyService()
        }
    }

    private fun startMyService() {
        val intent = Intent(this, MyService::class.java)
        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
}