package com.example.superadapterwrapper.util;

import android.graphics.Point;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.util.Size;
import android.view.Display;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/7
 * Time: 17:43
 */
public class CameraSizes {
    private static SmartSize SIZE_1080P = new SmartSize(1920, 1080);

    public static class SmartSize {
        private int mlong;
        private int mshort;
        private Size mSize;

        public SmartSize(int width, int height) {
            mSize = new Size(width, height);
            mlong = Math.max(mSize.getWidth(), mSize.getHeight());
            mshort = Math.min(mSize.getWidth(), mSize.getHeight());
        }

        @Override
        public String toString() {
            return "SmartSize{" +
                    "mlong=" + mlong +
                    ", mshort=" + mshort +
                    '}';
        }
    }

    public static SmartSize getDisplaySmartSize(Display display) {
        Point outPoint = new Point();
        display.getRealSize(outPoint);
        return new SmartSize(outPoint.x, outPoint.y);
    }

    public static <T> Size getPreviewOutputSize(Display display, CameraCharacteristics characteristics, Class<T> targetClass, Integer format) {

        // Find which is smaller: screen or 1080p
        SmartSize screenSize = getDisplaySmartSize(display);
        boolean hdScreen = screenSize.mlong >= SIZE_1080P.mlong || screenSize.mshort >= SIZE_1080P.mshort;
        SmartSize maxSize = hdScreen ? SIZE_1080P : screenSize;

        // If image format is provided, use it to determine supported sizes; else use target class
        StreamConfigurationMap config = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        Size[] allSizes;
        if (format == null) {
            allSizes = config.getOutputSizes(targetClass);
        } else {
            allSizes = config.getOutputSizes(format);
        }

        // Get available sizes and sort them by area from largest to smallest
        List<SmartSize> smartSizeList = insertSor(allSizes);
        // Then, get the largest output size that is smaller or equal than our max size
        return getFirstSize(smartSizeList, maxSize).mSize;
    }

    private static List<SmartSize> insertSor(Size[] sizes) {
        List<SmartSize> smartSizeList = new ArrayList<>();
        Size tmp;
        for (int i = 1; i < sizes.length; i++) {
            for (int j = i; j > 0; j--) {
                if (sizes[j].getWidth() * sizes[j].getHeight() > sizes[j - 1].getWidth() * sizes[j - 1].getHeight()) {
                    tmp = sizes[j - 1];
                    sizes[j - 1] = sizes[j];
                    sizes[j] = tmp;
                }
            }
        }
        for (Size size : sizes) {
            smartSizeList.add(new SmartSize(size.getWidth(), size.getHeight()));
        }
        return smartSizeList;
    }

    private static SmartSize getFirstSize(List<SmartSize> smartSizes, SmartSize maxSize) {
        for (SmartSize smartSize : smartSizes) {
            if (smartSize.mlong <= maxSize.mlong && smartSize.mshort <= maxSize.mshort) {
                return smartSize;
            }
        }
        return new SmartSize(0, 0);
    }
}
