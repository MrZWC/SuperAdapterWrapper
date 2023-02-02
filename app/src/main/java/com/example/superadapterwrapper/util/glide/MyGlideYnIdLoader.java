package com.example.superadapterwrapper.util.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

/**
 * ClassName MyGlideYnIdLoader
 * User: zuoweichen
 * Date: 2022/9/26 13:58
 * Description: 描述
 */
public class MyGlideYnIdLoader implements ModelLoader<YnIdGlideUrl, InputStream> {

    public MyGlideYnIdLoader(@Nullable ModelCache<YnIdGlideUrl, YnIdGlideUrl> modelCache) {
    }

    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(@NonNull YnIdGlideUrl ynIdGlideUrl, int width, int height, @NonNull Options options) {
        return null;
    }

    @Override
    public boolean handles(@NonNull YnIdGlideUrl ynIdGlideUrl) {
        return true;
    }

    public static class Factory implements ModelLoaderFactory<YnIdGlideUrl, InputStream> {
        private final ModelCache<YnIdGlideUrl, YnIdGlideUrl> modelCache = new ModelCache<>(500);

        @NonNull
        @Override
        public ModelLoader<YnIdGlideUrl, InputStream> build(MultiModelLoaderFactory multiFactory) {
            return new MyGlideYnIdLoader(modelCache);
        }

        @Override
        public void teardown() {
            // Do nothing.
        }
    }
}
