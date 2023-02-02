package com.example.superadapterwrapper.util.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * ClassName MyBaseGlideUrlLoader
 * User: zuoweichen
 * Date: 2022/9/26 9:49
 * Description: 描述
 */
public class MyBaseGlideUrlLoader extends BaseGlideUrlLoader<String> {
    private static final String DATA_URI_PREFIX = "ynid:";

    protected MyBaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader) {
        super(concreteLoader);
    }

    protected MyBaseGlideUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader, @Nullable ModelCache<String, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
    }


    @Override
    protected String getUrl(String s, int width, int height, Options options) {
        //String url="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Ffuturestud.io%2Fimages%2Fbooks%2Fglide.png&refer=http%3A%2F%2Ffuturestud.io&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1663751775&t=af54a2bdb3cdd8c55527ccffe808d9c6";
        String url="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.mp.itc.cn%2Fq_70%2Cc_zoom%2Cw_640%2Fupload%2F20170725%2Fa7e743567ad74749828a6412913adcea_th.jpg&refer=http%3A%2F%2Fimg.mp.itc.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1666753732&t=db414f94a7b17f5889ee816cadfa41db";
        return url;
    }

    @Override
    public boolean handles(@NonNull String model) {
        return model.startsWith(DATA_URI_PREFIX);
    }
}
