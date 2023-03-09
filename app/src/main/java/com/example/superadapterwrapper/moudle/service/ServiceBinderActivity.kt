package com.example.superadapterwrapper.moudle.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.ITestCallBack
import com.example.superadapterwrapper.ITestInterface
import com.example.superadapterwrapper.databinding.ActivityServiceBinderBinding

class ServiceBinderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinderBinding
    private var connection: ServiceConnection? = null
    private val TAG = this.javaClass.simpleName

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ServiceBinderActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.startBtn.setOnClickListener {
            startMyService()
        }
        binding.unbindBtn.setOnClickListener {
            connection?.apply {
                unbindService(connection!!)
                connection = null
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
                val asInterface = ITestInterface.Stub.asInterface(service)
                asInterface.registerCalback(object : ITestCallBack.Stub() {
                    override fun setNum(num: Int) {
                        Handler(Looper.getMainLooper()).post{
                            binding.textBtn.text = num.toString()
                        }
                    }

                })
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }

        }
        bindService(intent, connection!!, Context.BIND_AUTO_CREATE)
    }
}