package com.example.superadapterwrapper.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import com.idonans.lang.util.FileUtil
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * ClassName BitmapUtils
 * User: zuoweichen
 * Date: 2021/12/30 17:29
 * Description: 描述
 */
class BitmapUtils {
    companion object {
        fun saveBitmap(context: Context, fileName: String, bitmap: Bitmap?) {
            var fileName = fileName
            if (bitmap == null) {
                return
            }
            if (TextUtils.isEmpty(fileName)) {
                return
            }
            fileName = "$fileName.jpg"
            val file = File(FileUtil.getAppFilesDir(), fileName) //生成文件路径
            try {
                val fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                // 最后通知图库更新
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(file.toString()),
                    arrayOf(file.name),
                    null
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}