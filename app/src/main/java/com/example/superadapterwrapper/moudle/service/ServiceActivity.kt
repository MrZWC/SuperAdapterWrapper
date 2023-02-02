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
    private var connection: ServiceConnection? = null

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
        binding.unbindBtn.setOnClickListener {
            connection?.apply {
                unbindService(connection!!)
                connection=null
            }
        }
        binding.resetBtn.setOnClickListener {
            mService?.reset()
        }
    }

    private var mService: MyService? = null
    private fun startMyService() {
        val intent = Intent(this, MyService::class.java)
        connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                if (service is MyService.MyBinder) {
                    mService = service.getService()
                    mService?.setOnTimerListener(object : MyService.OnTimerListener {
                        override fun onTimer(num: Int) {
                            binding.textBtn.text = num.toString()
                        }

                    })
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }
        bindService(intent, connection!!, Context.BIND_AUTO_CREATE)
    }
}