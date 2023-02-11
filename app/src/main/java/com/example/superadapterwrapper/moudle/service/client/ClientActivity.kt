package com.example.superadapterwrapper.moudle.service.client

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.databinding.ActivityClientBinding
import com.example.superadapterwrapper.moudle.service.Book
import com.example.superadapterwrapper.moudle.service.server.BookManager
import com.example.superadapterwrapper.moudle.service.server.RemoteService
import com.example.superadapterwrapper.moudle.service.server.Stub
import com.socks.library.KLog

class ClientActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var binding: ActivityClientBinding
    private var bookManager: BookManager? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ClientActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        attemptToBindService()
        binding.btn.setOnClickListener {
            if (bookManager == null) {
                return@setOnClickListener
            }
            bookManager!!.addBook(Book(1001, "编码"))
        }
    }

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            bookManager = Stub.asInterface(service)
            if (bookManager != null) {
                val books = bookManager?.books
                KLog.i(TAG, books.toString())
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }

    private fun attemptToBindService() {
        val intent = Intent(this, RemoteService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        unbindService(serviceConnection)
        super.onDestroy()
    }
}