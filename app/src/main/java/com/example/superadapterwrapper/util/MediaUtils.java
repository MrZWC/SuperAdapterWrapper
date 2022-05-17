package com.example.superadapterwrapper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.example.superadapterwrapper.SuperAppliction;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import timber.log.Timber;


/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/8/29
 * Time: 15:32
 */
public class MediaUtils {


    /**
     * @param context
     * @param mediaPath
     */
    public static void getMediaInfo(Context context, String mediaPath) {
        File file = new File(mediaPath);
        if (!file.exists()) {
            return;
        }
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(mediaPath).getAbsolutePath());
            retriever.setDataSource(inputStream.getFD());
            String durationString = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            long timeMs = 20;
            if (!TextUtils.isEmpty(durationString)) {
                float duration = Float.parseFloat(durationString);
                if (timeMs > duration) {
                    timeMs = (long) (duration /1000L / 2L);
                }
            }
            decodeFrame(context, retriever, timeMs);
            retriever.release();
        } catch (Exception e) {
            Timber.e(e);
        }



    }

    public static Bitmap decodeFrame(Context context, MediaMetadataRetriever retriever, long timeMs) {
        if (retriever == null) {
            return null;
        }
        Bitmap bitmap = retriever.getFrameAtTime(timeMs * 1000, MediaMetadataRetriever.OPTION_CLOSEST);
        if (bitmap != null) {
            BitmapUtils.Companion.saveBitmap(context, "死侍2", bitmap);
            return bitmap;
        }
        return null;
    }
}
