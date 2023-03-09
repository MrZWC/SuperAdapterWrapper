package com.example.superadapterwrapper.moudle

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.R
import com.example.superadapterwrapper.SuperAppliction
import com.example.superadapterwrapper.databinding.ActivityTestBinding
import com.google.gson.Gson
import com.socks.library.KLog

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    companion object {
        const val TAG = "TestActivity"
        fun start(context: Context) {
            val intent = Intent(context, TestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        KLog.i(TAG, "onCreate")
       /* binding.startBtn.setOnClickListener {
            KLog.i("btn", "")
        }*/
        binding.layoutBtn.setOnClickListener {
            KLog.i("layout", "startBtn="+binding.startBtn.isClickable)

        }
        binding.layoutBtn.setOnTouchListener { v, event ->
            when(event.action){
                MotionEvent.ACTION_DOWN->{
                    KLog.i("TestActivity","ACTION_DOWN")
                }
                 MotionEvent.ACTION_MOVE->{
                    KLog.i("TestActivity","ACTION_MOVE")
                }
                 MotionEvent.ACTION_UP->{
                    KLog.i("TestActivity","ACTION_UP")
                }
                 MotionEvent.ACTION_CANCEL->{
                    KLog.i("TestActivity","ACTION_CANCEL")
                }

            }
            return@setOnTouchListener false
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