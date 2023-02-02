package com.example.superadapterwrapper.moudle

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.superadapterwrapper.R
import com.example.superadapterwrapper.databinding.ActivityMediaInfoBinding

class MediaInfoActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMediaInfoBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MediaInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_media_info)
        initListener()
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        dataBinding.selectImageBtn.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.yineng.ynmessager")
            startActivity(intent)
        }

    }
}