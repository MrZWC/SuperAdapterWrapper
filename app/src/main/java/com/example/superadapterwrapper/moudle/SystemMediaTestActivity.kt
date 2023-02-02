package com.example.superadapterwrapper.moudle

import android.Manifest
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.superadapterwrapper.databinding.ActivitySystemMediaTestBinding
import com.idonans.lang.util.ToastUtil
import com.socks.library.KLog
import com.tbruyelle.rxpermissions3.RxPermissions
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class SystemMediaTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySystemMediaTestBinding

    companion object {
        const val TAG = "SystemMediaTest"
        fun start(context: Context) {
            val intent = Intent(context, SystemMediaTestActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemMediaTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.checkPermission.setOnClickListener {
            checkPermission()
        }
        binding.queryFileBtn.setOnClickListener {
            loadFile()
        }
        binding.queryDocumentBtn.setOnClickListener {
            loadDocument()
        }
        binding.queryImageBtn.setOnClickListener {
            loadImage()
        }
        binding.queryVideoBtn.setOnClickListener {
            loadVideo()
        }
    }

    private fun checkPermission() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .subscribe({
                if (it) {
                    ToastUtil.show("已有权限")
                } else {
                    ToastUtil.show("无权限")
                }
            }, {
                ToastUtil.show("失败")
            })
    }

    private fun loadFile() {
        Observable.just("")
            .map {
                val select =
                    "(" + MediaStore.Files.FileColumns.DISPLAY_NAME + " LIKE '%.apk'" + " or " + MediaStore.Files.FileColumns.DISPLAY_NAME + " LIKE '%.docx'" + ")"
                val cursor = contentResolver.query(
                    MediaStore.Files.getContentUri("external"),
                    null,
                    select,
                    null,
                    null
                )
                if (cursor == null) {
                    throw NullPointerException()
                }
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        val indexPath =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                        val indexType =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
                        KLog.i(TAG, "Path=" + cursor.getString(indexPath))
                        KLog.i(TAG, "MIME_TYPE=" + cursor.getString(indexType))
                        /* cursorToLocalMedia(cursor)*/
                    } while (cursor.moveToNext())
                }
                cursor.close()
                return@map ""
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Timber.e(it)
            })
    }

    private fun loadDocument() {
        Observable.just("")
            .map {
                val select =
                    MediaStore.Files.FileColumns.DISPLAY_NAME +
                            " LIKE '%.docx'"  +
                            " AND " + MediaStore.Files.FileColumns.SIZE + ">0"
                val cursor = contentResolver.query(
                    MediaStore.Files.getContentUri("external"),
                    arrayOf(
                        MediaStore.Files.FileColumns._ID,
                        MediaStore.Files.FileColumns.DATA,
                        MediaStore.Files.FileColumns.TITLE,
                        MediaStore.Files.FileColumns.MIME_TYPE,
                        MediaStore.Files.FileColumns.DISPLAY_NAME
                    ),
                    null,
                    null,
                    null
                )
                if (cursor == null) {
                    throw NullPointerException()
                }
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        val indexPath =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                        val indexType =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
                        val indexDisplayName =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
                        KLog.i(TAG, "Path=" + cursor.getString(indexPath))
                        KLog.i(TAG, "MIME_TYPE=" + cursor.getString(indexType))
                        KLog.i(TAG, "DISPLAY_NAME=" + cursor.getString(indexDisplayName))
                        KLog.i(TAG, "--------------------------------------------------------")
                    } while (cursor.moveToNext())
                }
                cursor.close()
                return@map ""
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Timber.e(it)
            })
    }

    private fun loadVideo() {
        Observable.just("")
            .map {

                val cursor = contentResolver.query(
                    MediaStore.Files.getContentUri("external"),
                    arrayOf(MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.TITLE),
                    MediaStore.Files.FileColumns.MIME_TYPE + "=?",
                    arrayOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO.toString()),
                    null
                )
                if (cursor == null) {
                    throw NullPointerException()
                }
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        cursorToLocalMedia(cursor)
                    } while (cursor.moveToNext())
                }
                cursor.close()
                return@map ""
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Timber.e(it)
            })
    }

    private fun loadImage() {
        Observable.just("")
            .map {
                val cursor = contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    arrayOf(
                        MediaStore.Images.Media.DATA,
                        MediaStore.Files.FileColumns.MIME_TYPE,
                        MediaStore.Files.FileColumns.TITLE
                    ),
                    null,
                    null,
                    null
                )
                if (cursor == null) {
                    throw NullPointerException()
                }
                if (cursor.count > 0) {
                    cursor.moveToFirst()
                    do {
                        val indexPath =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                        val indexType =
                            cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)
                        KLog.i(TAG, "Path=" + cursor.getString(indexPath))
                        KLog.i(TAG, "MIME_TYPE=" + cursor.getString(indexType))
                    } while (cursor.moveToNext())
                }
                cursor.close()
                return@map ""
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {
                Timber.e(it)
            })
    }

    private fun cursorToLocalMedia(cursor: Cursor) {
        var index = -1
        var data1 = cursor.getString(++index);
        KLog.i(TAG, data1)
        var data2 = cursor.getString(++index);
        //KLog.i(TAG, data2)
    }
}