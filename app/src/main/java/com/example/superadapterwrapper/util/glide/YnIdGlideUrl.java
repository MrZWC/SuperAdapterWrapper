package com.example.superadapterwrapper.util.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Key;
import com.bumptech.glide.util.Preconditions;

import java.security.MessageDigest;

/**
 * ClassName YnIdGlieUrl
 * User: zuoweichen
 * Date: 2022/9/26 14:32
 * Description: 描述
 */
public class YnIdGlideUrl implements Key {
    @Nullable
    private String imageId;
    @Nullable
    private volatile byte[] cacheKeyBytes;

    public YnIdGlideUrl(@Nullable String imageId) {
        this.imageId = imageId;
    }

    public String getCacheKey() {
        return imageId != null ? imageId : Preconditions.checkNotNull(imageId).toString();
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(getCacheKeyBytes());
    }

    private byte[] getCacheKeyBytes() {
        if (cacheKeyBytes == null) {
            cacheKeyBytes = getCacheKey().getBytes(CHARSET);
        }
        return cacheKeyBytes;
    }
}
