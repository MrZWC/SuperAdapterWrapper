package com.example.superadapterwrapper.util.glide

import android.net.Uri
import android.text.TextUtils
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.Headers
import java.net.URL

/**
 * ClassName MyFlideUrl
 * User: zuoweichen
 * Date: 2022/8/22 15:42
 * Description: 描述
 */
class MyGlideUrl : GlideUrl {
    private val ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%;$"
    constructor(url: URL?) : super(url)
    constructor(url: String?) : super(url)
    constructor(url: URL?, headers: Headers?) : super(url, headers)
    constructor(url: String?, headers: Headers?) : super(url, headers)

    override fun getCacheKey(): String {
        val cacheKey = super.getCacheKey()
        return cacheKey
    }

    override fun toStringUrl(): String {
        val url = super.toStringUrl()
        return url
    }

    override fun toURL(): URL {
        return URL(getSafeStringUrl())
    }
    private fun getSafeStringUrl(): String {
        //var safeStringUrl = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ffuturestud.io%2Fimages%2Fbooks%2Fglide.png&refer=http%3A%2F%2Ffuturestud.io&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1663751775&t=af54a2bdb3cdd8c55527ccffe808d9c6"
        var safeStringUrl="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.mp.itc.cn%2Fq_70%2Cc_zoom%2Cw_640%2Fupload%2F20170725%2Fa7e743567ad74749828a6412913adcea_th.jpg&refer=http%3A%2F%2Fimg.mp.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666753732&t=db414f94a7b17f5889ee816cadfa41db"
        if (TextUtils.isEmpty(safeStringUrl)) {
            var unsafeStringUrl = safeStringUrl
            safeStringUrl = Uri.encode(unsafeStringUrl, ALLOWED_URI_CHARS)
        }
        return safeStringUrl
    }
}