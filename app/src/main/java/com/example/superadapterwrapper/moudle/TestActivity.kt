package com.example.superadapterwrapper.moudle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.R
import com.google.gson.Gson
import com.socks.library.KLog

class TestActivity : AppCompatActivity() {
    companion object {
        const val TAG = "TestActivity"
        fun start(context: Context) {
            val intent = Intent(context, TestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        KLog.i(TAG, "onCreate")
        findViewById<Button>(R.id.start_btn).setOnClickListener {
            Test2Activity.start(this)
        }
    }

    override fun onStart() {
        super.onStart()
        KLog.i(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        KLog.i(TAG, "onResume")
    }

    override fun onRestart() {
        super.onRestart()
        KLog.i(TAG, "onRestart")
    }

    override fun onPause() {
        super.onPause()
        KLog.i(TAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        KLog.i(TAG, "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        KLog.i(TAG, "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        KLog.i(TAG, "onSaveInstanceState")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        KLog.i(TAG, "onRestoreInstanceState")
    }
}