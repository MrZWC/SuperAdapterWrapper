package com.example.superadapterwrapper.util;

import android.content.Context;
import android.hardware.camera2.CameraCharacteristics;
import android.view.OrientationEventListener;
import android.view.Surface;

import androidx.lifecycle.LiveData;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/7/7
 * Time: 16:58
 */
public class OrientationLiveData extends LiveData<Integer> {
    private Context mContext;
    private CameraCharacteristics characteristics;
    private OrientationEventListener listener;

    public OrientationLiveData(Context context, CameraCharacteristics characteristics) {
        mContext = context;
        this.characteristics = characteristics;
        listener = new OrientationEventListener(mContext.getApplicationContext()) {
            @Override
            public void onOrientationChanged(int orientation) {
                int rotation;
                switch (orientation) {
                    case 45:
                        rotation = Surface.ROTATION_0;
                        break;
                    case 135:
                        rotation = Surface.ROTATION_90;
                        break;
                    case 225:
                        rotation = Surface.ROTATION_180;
                        break;
                    case 315:
                        rotation = Surface.ROTATION_270;
                        break;
                    default:
                        rotation = Surface.ROTATION_0;
                        break;
                }

                int relative = computeRelativeRotation(characteristics, rotation);
                if (relative != getValue()) {
                    postValue(relative);
                }
            }
        };
    }

    @Override
    protected void onActive() {
        super.onActive();
        if (listener != null) {
            listener.enable();
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (listener != null) {
            listener.disable();
        }
    }

    private int computeRelativeRotation(CameraCharacteristics characteristics, int surfaceRotation) {
        Integer sensorOrientationDegrees = characteristics.get(CameraCharacteristics.SENSOR_ORIENTATION);
        if (sensorOrientationDegrees == null) {
            return 0;
        }
        int deviceOrientationDegrees;
        switch (surfaceRotation) {
            case Surface.ROTATION_0:
                deviceOrientationDegrees = 0;
                break;
            case Surface.ROTATION_90:
                deviceOrientationDegrees = 90;
                break;
            case Surface.ROTATION_180:
                deviceOrientationDegrees = 180;
                break;
            case Surface.ROTATION_270:
                deviceOrientationDegrees = 270;
                break;
            default:
                deviceOrientationDegrees = 0;
                break;
        }
        // Reverse device orientation for front-facing cameras
        int sign = (characteristics.get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_FRONT) ? 1 : -1;

        // Calculate desired JPEG orientation relative to camera orientation to make
        // the image upright relative to the device orientation
        return (sensorOrientationDegrees - (deviceOrientationDegrees * sign) + 360) % 360;
    }


}
