package com.example.superadapterwrapper.util.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * ClassName MyAppGlideModule
 * User: zuoweichen
 * Date: 2022/9/26 10:36
 * Description: 描述
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.prepend(String.class, InputStream.class, new Base64ModelLoaderFactory());
        registry.prepend(YnIdGlideUrl.class, InputStream.class, new MyGlideYnIdLoader.Factory());

    }
}
