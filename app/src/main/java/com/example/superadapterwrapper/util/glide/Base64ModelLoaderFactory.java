package com.example.superadapterwrapper.util.glide;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * ClassName Base64ModelLoaderFactory
 * User: zuoweichen
 * Date: 2022/9/26 10:09
 * Description: 描述
 */
public class Base64ModelLoaderFactory implements ModelLoaderFactory<String, InputStream> {
    private final ModelCache<String, GlideUrl> modelCache = new ModelCache<>(500);

    @NonNull
    @Override
    public ModelLoader<String, InputStream> build(@NonNull MultiModelLoaderFactory multiFactory) {
        ModelLoader<GlideUrl, InputStream> build = multiFactory.build(GlideUrl.class, InputStream.class);
        return new MyBaseGlideUrlLoader(build, modelCache);
    }

    @Override
    public void teardown() {

    }
}
