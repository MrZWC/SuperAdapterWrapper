package com.example.superadapterwrapper

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superadapterwrapper.adapter.MainAdapter
import com.example.superadapterwrapper.base.BaseActivity
import com.example.superadapterwrapper.moudle.*
import com.example.superadapterwrapper.moudle.audio.AudioActivity
import com.example.superadapterwrapper.moudle.executor.ExecutorActivity
import com.example.superadapterwrapper.moudle.sensor.SensorActivity
import com.example.superadapterwrapper.moudle.service.ServiceActivity
import com.example.superadapterwrapper.util.DensityUtils
import com.example.superadapterwrapper.util.FileDirManager
import com.example.superadapterwrapper.util.UpdateH5Util
import com.example.superadapterwrapper.util.ZipUtil
import kotlin.collections.ArrayList

class MainActivity : BaseActivity() {
    var mRecyclerView: RecyclerView? = null
    val strings: MutableList<String> = ArrayList()
    override fun setContentView() {
        setContentView(R.layout.activity_main)
    }

    override fun initView() {
        mRecyclerView = getView(R.id.mRecyclerView)
    }

    override fun initData(savedInstanceState: Bundle?) {
        strings.add("svga加载测试")
        strings.add("探探layout")
        strings.add("喜欢点击动画")
        strings.add("view阴影")
        strings.add("XfermodeTest")
        strings.add("自定义RecycleView动画")
        strings.add("RecycleView轮播实现")
        strings.add("webview视频测试")
        strings.add("X5webview视频测试")
        strings.add("作为一个浏览器的示例展示出来，采用android+web的模式")
        strings.add("用于展示在web端<input type=text>的标签被选择之后，文件选择器的制作和生成")
        strings.add("用于演示X5webview实现视频的全屏播放功能 其中注意 X5的默认全屏方式 与 android 系统的全屏方式")
        strings.add("agentweb")
        strings.add("video播放")
        strings.add("audio播放")
        strings.add("Camera2Video")
        strings.add("MediaRecorder视频录制")
        strings.add("wifi扫描")
        strings.add(" ")
        strings.add("cordovaTest")
        strings.add("h5下载更新")
        strings.add("h5解压更新")
        strings.add("生物识别")
        strings.add("内存相关")
        strings.add("RSA加解密")
        strings.add("TextView换行测试")
        strings.add("Smack测试")
        strings.add("大图查看")
        strings.add("获取图片信息")
        strings.add("系统数据库查询测试")
        strings.add("测试")
        strings.add("service")
        strings.add("线程池")
        strings.add("水平仪")
        ShowDataView()
        test()
    }

    private fun test() {
        /*String oldstring = "C8DF1716A89407F4BDE98D9A3EC5FC4F50461E554C729887C1186D31F4D6B80F6C36ADB138670EE867F7DB2E7882C15AB26B9885F5D833B1B515AD697ECD513F868AAD1A16B9CB739C9EABC5086E6093030AD92DD50C33B826A39F88B4ECE3FC09D32D79B9F5C886BE62A55DDEC8D38FC68D27129D7FDA6E9DAC00BE824FB4F89B7AF38CDD3AFBE17FB81AADC9EBC0324C1F64822147A16FAD3BFD5CB6F9ABD2";
        KLog.d("CompressEncodeing", oldstring.length());
        String compress = DeflaterUtils.zipString(oldstring);
        KLog.d("CompressEncodeing", compress.length());
        KLog.d("CompressEncodeing", compress);
        String uncompress = DeflaterUtils.unzipString(compress);
        KLog.d("CompressEncodeing", uncompress.length());
        KLog.d("CompressEncodeing", uncompress);*/
        val old = "asdasdasdas/dasdasdasdas"
    }

    private fun ShowDataView() {
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildAdapterPosition(view)
                outRect.top = DensityUtils.dp2px(this@MainActivity, 15f)
            }
        })
        val mainAdapter = MainAdapter(this, strings)
        mRecyclerView!!.adapter = mainAdapter
        mainAdapter.setOnItemClickLitener { view, position ->
            when (position) {
                0 -> SvgaActivity.start(context)
                1 -> TanActivity.start(context)
                2 -> LikeAnimationActivity.start(context)
                3 -> ShadowActivity.start(context)
                4 -> XfermodeActivity.start(context)
                5 -> ItemAnimatorActivity.start(context)
                6 -> RecyclerBannerActivity.start(context)
                7 -> WebViewActivity.start(context)
                8 -> X5TencentWebViewActivity.start(context)
                9 -> BrowserActivity.start(context)
                10 -> FilechooserActivity.start(context)
                11 -> FullScreenActivity.start(context)
                12 -> AgentWebActivity.start(context)
                13 -> VideoActivity.start(context)
                14 -> AudioActivity.start(context)
                15 -> Camera2VideoNewActivity.start(context)
                16 -> MediaRecorderActivity.start(context)
                17 -> WifiActivity.start(context)
                18 -> SmallFileActivity.start(context)
                19 -> CordovaTestActivity.start(context)
                20 -> {
                    val updateH5Util = UpdateH5Util()
                    updateH5Util.update("http://10.6.30.117:8099/zuo/www.zip") { bytesRead, contentLength, done -> }
                }
                21 -> {
                    val h5CacheDir = FileDirManager.getInstance().h5CacheDir
                    ZipUtil.unZipFile(
                        h5CacheDir,
                        "/storage/emulated/0/Android/data/com.example.superadapterwrapper/files/h5/www.zip"
                    )
                }
                22 -> BiometricActivity.start(context)
                23 -> StorageActivity.start(context)
                24 -> RsaActivity.start(context)
                25 -> TextActivity.start(context)
                26 -> {
                    SmackActivity.start(context);
                    /* val filesDir =
                         File(getExternalFilesDir(null)!!.absolutePath + File.separator + "download")
                     if (!filesDir.exists()) {
                         filesDir.mkdirs()
                     }*/
                }
                27 -> {
                    ScaleImageActivity.start(context)
                }
                28 -> {
                    MediaInfoActivity.start(context)
                }
                29 -> {
                    SystemMediaTestActivity.start(context)
                }
                30 -> {
                    TestActivity.start(context)
                }
                31 -> {
                    ServiceActivity.start(context)
                }
                32 -> {
                    ExecutorActivity.start(context)
                }
                33 -> {
                    SensorActivity.start(context)
                }
                else -> {
                }
            }
        }
    }

    override fun initLinstener() {}
}