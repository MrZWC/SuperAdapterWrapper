package com.example.superadapterwrapper.moudle

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.superadapterwrapper.R
import com.socks.library.KLog

class Test2Activity : AppCompatActivity() {
    companion object {
        const val TAG = "Test2Activity"
        fun start(context: Activity) {
            val intent = Intent(context, Test2Activity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)
        KLog.i(TAG,"onCreate")
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
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        KLog.i(TAG,"onRestoreInstanceState")
    }
}