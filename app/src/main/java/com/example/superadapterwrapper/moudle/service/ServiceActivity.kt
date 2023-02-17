package com.example.superadapterwrapper.moudle.service

import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.databinding.ActivityServiceBinding
import com.example.superadapterwrapper.moudle.service.client.ClientActivity

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
        binding.startBtn1.setOnClickListener {
            ServiceBinderActivity.start(this)
        }

        binding.startBtn2.setOnClickListener {
            ClientActivity.start(this)
        }
    }
}