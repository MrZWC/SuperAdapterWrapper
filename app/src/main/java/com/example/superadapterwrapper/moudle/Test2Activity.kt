package com.example.superadapterwrapper.moudle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.R
import com.example.superadapterwrapper.databinding.ActivityTest2Binding
import com.example.superadapterwrapper.databinding.ActivityTestBinding
import com.socks.library.KLog

class Test2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityTest2Binding
    companion object {
        const val TAG = "Test2Activity"
        fun start(context: Activity) {
            val intent = Intent(context, Test2Activity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTest2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        KLog.i(TAG,"onCreate")
        binding.startBtn.setOnClickListener{
            TestActivity.start(this)
        }
    }

    override fun onStart() {
        super.onStart()
        KLog.i(TAG,"onStart")
    }

    override fun onResume() {
        super.onResume()
        KLog.i(TAG,"onResume")
    }

    override fun onRestart() {
        super.onRestart()
        KLog.i(TAG,"onRestart")
    }

    override fun onPause() {
        super.onPause()
        KLog.i(TAG,"onPause")
    }

    override fun onStop() {
        super.onStop()
        KLog.i(TAG,"onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        KLog.i(TAG,"onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        KLog.i(TAG,"onSaveInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        KLog.i(TAG,"onSaveInstanceState")
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        KLog.i(TAG,"onRestoreInstanceState")
    }
}