package com.example.superadapterwrapper.moudle.view

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.superadapterwrapper.SuperAppliction
import com.example.superadapterwrapper.base.BaseActivity
import com.example.superadapterwrapper.databinding.ActivityViewBinding
import com.socks.library.KLog

class ViewActivity : BaseActivity() {
    private var binding: ActivityViewBinding? = null

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ViewActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setContentView() {
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
    }

    override fun initView() {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun initData(savedInstanceState: Bundle?) {
       /* binding?.showWindowBtn?.setOnClickListener {
            if (!Settings.canDrawOverlays(this)) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, 0)
            } else {
                showView()
            }
        }*/
      /*  binding?.myView?.setOnTouchListener(object : OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                KLog.i("ViewActivity",MotionEvent.actionToString(event!!.action))
                return false
            }

        })*/
       /* binding?.myView?.setOnClickListener {
            KLog.i("ViewActivity","onclick")
        }*/
    }

    private fun showView() {
        //获取WindowManager实例，这里的App是继承自Application
        val wm: WindowManager =
            SuperAppliction.getApp().getSystemService(WINDOW_SERVICE) as WindowManager

        //设置LayoutParams属性
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.height = 400
        layoutParams.width = 400
        layoutParams.format = PixelFormat.RGBA_8888

        //窗口标记属性
        layoutParams.flags =
            (WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)

        //Window类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE
        }

        //构造TextView
        val textView = TextView(this)
        textView.setBackground(ColorDrawable(Color.WHITE))
        textView.setText("hello windowManager${textView.hashCode()}")

        //将textView添加到WindowManager
        wm.addView(textView, layoutParams)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}