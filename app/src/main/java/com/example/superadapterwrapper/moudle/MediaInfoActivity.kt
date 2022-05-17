package com.example.superadapterwrapper.moudle

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.superadapterwrapper.R
import com.example.superadapterwrapper.base.bean.BaseBean
import com.example.superadapterwrapper.base.bean.TestBean
import com.example.superadapterwrapper.databinding.ActivityMediaInfoBinding
import com.example.superadapterwrapper.util.MediaUtils
import com.google.gson.Gson

class MediaInfoActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivityMediaInfoBinding

    companion object {
        private val REQUEST_CODE_CHOOSE=1001
        fun start(context: Context) {
            val intent = Intent(context, MediaInfoActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_media_info)
        initListener()
        val json="{\"code\":\"1\",\"msg\":\"test string\",\"obj\":[{\"name\":\"小明\",\"title\":\"this is a title\"},{\"name\":\"小明1\",\"title\":\"this is a title1\"}]}"
        //val data=Gson().fromJson<BaseBean<ArrayList<TestBean>>>(json,BaseBean<ArrayList<TestBean>>.class.java)
        val datas = Gson().fromJson(json, BaseBean::class.java)
    }

    @SuppressLint("CheckResult")
    private fun initListener() {
        dataBinding.selectImageBtn.setOnClickListener {
           /* val rxPermissions = RxPermissions(this);
            rxPermissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
                .subscribe({
                    if (it) {
                        Matisse.from(this).choose(MimeType.ofAll())
                            .maxSelectable(1)
                            .thumbnailScale(0.85f)
                            .forResult(REQUEST_CODE_CHOOSE)
                    } else {
                        ToastUtil.show("无权限")
                    }
                }, {
                    ToastUtil.show("失败")
                })*/
            //val filepath="/storage/emulated/0/阳光电影www.ygdy8.com.死侍2.HD.720p.韩版中英双字幕.rmvb"
            val filepath="/storage/emulated/0/惠州技师.rmvb"
            //val filepath="/storage/emulated/0/camera2demo/video/6b1d40b1-131e-488b-9bfb-6a439363f051.mp4"
            MediaUtils.getMediaInfo(this,filepath)
        }
    }
}