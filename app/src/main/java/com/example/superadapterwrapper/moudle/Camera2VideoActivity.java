package com.example.superadapterwrapper.moudle;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.params.MeteringRectangle;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.base.BaseActivity;
import com.example.superadapterwrapper.util.CameraSizes;
import com.example.superadapterwrapper.util.OrientationLiveData;
import com.example.superadapterwrapper.widget.AutoFitSurfaceView;
import com.idonans.lang.thread.Threads;
import com.socks.library.KLog;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Camera2VideoActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();
    private AutoFitSurfaceView viewFinder;
    private View overlay;
    private ImageButton capture_button;
    //
    private CameraManager mCameraManager;
    private CameraCharacteristics characteristics;
    private String mCameraId;
    private File outputFile;
    private HandlerThread cameraThread;
    private Handler cameraHandler;
    private CameraCaptureSession mSession;
    private CameraDevice mCameraDevice;
    private CaptureRequest mPreviewRequest;
    private CaptureRequest recordRequest;
    private static final int RECORDER_VIDEO_BITRATE = 10_000_000;

    private long recordingStartMillis = 0L;
    private OrientationLiveData relativeOrientation;
    private static final String TAG_PREVIEW = "预览";
    private CaptureRequest.Builder mPreviewRequestBuilder;
    private MediaRecorder mMediaRecorder;
    private boolean mIsRecordingVideo;
    private HandlerThread mBackgroundThread;
    private Handler mBackgroundHandler;

    public static void start(Context context) {
        Intent intent = new Intent(context, Camera2VideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_camera2_video);

    }

    @Override
    protected void initView() {
        viewFinder = getView(R.id.view_finder);
        overlay = getView(R.id.overlay);
        capture_button = getView(R.id.capture_button);
        capture_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!mIsRecordingVideo) {
                    Toast.makeText(getContext(), "开始录制", Toast.LENGTH_SHORT).show();
                    startRecordingVideo();
                } else {
                    stopRecordingVideo();
                }

            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        outputFile = createFile(getContext(), "mp4");
    }

    @Override
    protected void onResume() {
        super.onResume();
        startBackgroundThread();
        /*if (viewFinder.getHolder().getSurface() != null) {
            openCamera();
        } else {
            initCarmera();
        }*/
        initCarmera();
    }

    private void startBackgroundThread() {
        mBackgroundThread = new HandlerThread("CameraBackground");
        mBackgroundThread.start();
        mBackgroundHandler = new Handler(mBackgroundThread.getLooper());
    }


    private void initCarmera() {
        viewFinder.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                openCamera();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void openCamera() {
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        selectCamera();
        Size previewOutputSize = CameraSizes.getPreviewOutputSize(viewFinder.getDisplay()
                , characteristics
                , SurfaceHolder.class, null);
        viewFinder.setAspectRatio(previewOutputSize.getWidth(), previewOutputSize.getHeight());
        //获取相机的管理者CameraManager
        CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        //检查权限
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //打开相机，第一个参数指示打开哪个摄像头，第二个参数stateCallback为相机的状态回调接口，第三个参数用来确定Callback在哪个线程执行，为null的话就在当前线程执行
            manager.openCamera(mCameraId, mStateCallback, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void selectCamera() {
        if (mCameraManager == null) {
            Log.e(TAG, "selectCamera: CameraManager is null");
        }
        try {
            String[] cameraIdList = mCameraManager.getCameraIdList();

            for (String cameraId : cameraIdList) {
                CameraCharacteristics characteristics = mCameraManager.getCameraCharacteristics(cameraId);
                Integer facing = characteristics.get(CameraCharacteristics.LENS_FACING);
                if (facing == CameraCharacteristics.LENS_FACING_BACK) {
                    mCameraId = cameraId;
                }
            }
            characteristics = mCameraManager.getCameraCharacteristics(mCameraId);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private final CameraDevice.StateCallback mStateCallback = new CameraDevice.StateCallback() {
        @Override
        public void onOpened(CameraDevice camera) {
            mCameraDevice = camera;
            getPreviewRequestBuilder(viewFinder.getHolder().getSurface());
            //开启预览
            startPreview();
        }

        @Override
        public void onDisconnected(@NonNull CameraDevice camera) {

        }

        @Override
        public void onError(@NonNull CameraDevice camera, int error) {

        }
    };

    private void startPreview() {
        try {
            mCameraDevice.createCaptureSession(Collections.singletonList(viewFinder.getHolder().getSurface()), new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mSession = session;
                    repeatPreview();
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, mBackgroundHandler);

        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void repeatPreview() {

        mPreviewRequest = mPreviewRequestBuilder.build();
        //设置反复捕获数据的请求，这样预览界面就会一直有数据显示
        try {
            //cameraHandler = new Handler(cameraThread.getLooper());
            mSession.setRepeatingRequest(mPreviewRequest, null, mBackgroundHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始录制
     */
    private void startRecordingVideo() {
        closePreviewSession();
        createRecorder();
        CaptureRequest.Builder previewRequestBuilder = getPreviewRequestBuilder(mMediaRecorder.getSurface());
        //CaptureRequest captureRequest = previewRequestBuilder.build();
        try {
            //mSession.setRepeatingRequest(captureRequest, null, mBackgroundHandler);
            relativeOrientation = new OrientationLiveData(Camera2VideoActivity.this, characteristics);
            if (relativeOrientation.getValue() != null) {
                mMediaRecorder.setOrientationHint(relativeOrientation.getValue());
            }
            List<Surface> surfaces = new ArrayList<>();
            surfaces.add(viewFinder.getHolder().getSurface());
            previewRequestBuilder.addTarget(viewFinder.getHolder().getSurface());
            Surface recorderSurface = mMediaRecorder.getSurface();
            surfaces.add(recorderSurface);
            previewRequestBuilder.addTarget(recorderSurface);
            mCameraDevice.createCaptureSession(surfaces, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mSession = session;
                    repeatPreview();
                    mIsRecordingVideo = true;
                    Threads.postUi(new Runnable() {
                        @Override
                        public void run() {
                            mMediaRecorder.start();
                        }
                    });
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, mBackgroundHandler);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void stopRecordingVideo() {
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.reset();
            mIsRecordingVideo = false;
        }
        startPreview();
    }

    private void closePreviewSession() {
        if (mSession != null) {
            mSession.close();
            mSession = null;
        }
    }

    // 创建预览请求的Builder（TEMPLATE_PREVIEW表示预览请求）
    private CaptureRequest.Builder getPreviewRequestBuilder(Surface surface) {
        try {
            mPreviewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
        //设置预览的显示界面
        mPreviewRequestBuilder.addTarget(surface);

        MeteringRectangle[] meteringRectangles = mPreviewRequestBuilder.get(CaptureRequest.CONTROL_AF_REGIONS);
        if (meteringRectangles != null && meteringRectangles.length > 0) {
            Log.d(TAG, "PreviewRequestBuilder: AF_REGIONS=" + meteringRectangles[0].getRect().toString());
        }
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_MODE, CaptureRequest.CONTROL_MODE_AUTO);
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CaptureRequest.CONTROL_AF_TRIGGER_IDLE);
        //所需的摄像装置的自动曝光算法的antibanding补偿设置。 白炽灯下为50HZ 不设置 视频预览会出现水波纹
        mPreviewRequestBuilder.set(CaptureRequest.CONTROL_AE_ANTIBANDING_MODE, CameraMetadata.CONTROL_AE_ANTIBANDING_MODE_50HZ);
        return mPreviewRequestBuilder;
    }

    private MediaRecorder createRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mMediaRecorder.setOutputFile(outputFile.getAbsolutePath());
        KLog.i("videoPath", outputFile.getAbsolutePath());
        mMediaRecorder.setVideoEncodingBitRate(RECORDER_VIDEO_BITRATE);
        mMediaRecorder.setVideoFrameRate(30);
        mMediaRecorder.setVideoSize(1280, 720);
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mMediaRecorder;
    }

    private File createFile(Context context, String extension) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS", Locale.US);
        String format = sdf.format(new Date());
        return new File(context.getFilesDir(), "VID_" + format + "." + extension);
    }
   /* private void initCarmera() {
        try {
            mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            selectCamera();
            characteristics = mCameraManager.getCameraCharacteristics(mCurrentSelectCamera);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                recorderSurface = MediaCodec.createPersistentInputSurface();
                MediaRecorder recorder = createRecorder(recorderSurface);

                recorder.prepare();
                recorder.release();

            }
            recorder = createRecorder(recorderSurface);
            cameraHandler = new Handler(cameraThread.getLooper());

            CaptureRequest.Builder previewCaptureRequest = mSession.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            previewCaptureRequest.addTarget(viewFinder.getHolder().getSurface());
            previewRequest = previewCaptureRequest.build();

            CaptureRequest.Builder recordCaptureRequest = mSession.getDevice().createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
            recordCaptureRequest.addTarget(viewFinder.getHolder().getSurface());
            recordCaptureRequest.addTarget(recorderSurface);
            recordCaptureRequest.set(CaptureRequest.CONTROL_AE_TARGET_FPS_RANGE, new Range(30, 30));
            recordRequest = recordCaptureRequest.build();

            viewFinder.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    // Selects appropriate preview size and configures view finder
                    Size previewSize = CameraSizes.getPreviewOutputSize(
                            viewFinder.getDisplay(), characteristics, SurfaceHolder.class, null);
                    Log.d(TAG, "View finder size: ${viewFinder.width} x ${viewFinder.height}");
                    Log.d(TAG, "Selected preview size: $previewSize");
                    viewFinder.setAspectRatio(previewSize.getWidth(), previewSize.getHeight());

                    // To ensure that size is set, initialize camera in the view's thread
                    viewFinder.post(() -> initializeCamera());
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    holder.toString();
                }
            });
            relativeOrientation = new OrientationLiveData(getContext(), characteristics);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final Runnable animationTask = new Runnable() {
        @Override
        public void run() {
            overlay.setBackground(new ColorDrawable(Color.argb(150, 255, 255, 255)));
            // Wait for ANIMATION_FAST_MILLIS
            overlay.postDelayed(new Runnable() {
                @Override
                public void run() {
                    overlay.setBackground(null);
                    // Restart animation recursively
                    overlay.postDelayed(animationTask, 50L);
                }
            }, 50L);
        }
    };

    private MediaRecorder createRecorder(Surface surface) {
        MediaRecorder mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(outputFile.getAbsolutePath());
        mediaRecorder.setVideoEncodingBitRate(RECORDER_VIDEO_BITRATE);
        mediaRecorder.setVideoSize(1280, 720);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaRecorder.setInputSurface(surface);
        }
        return mediaRecorder;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeCamera() {
        openCamera(mCameraManager, mCurrentSelectCamera, cameraHandler);

    }

    @SuppressLint("MissingPermission")
    private void openCamera(CameraManager manager, String cameraId, Handler handler) {
        try {
            manager.openCamera(cameraId, new CameraDevice.StateCallback() {
                @Override
                public void onOpened(@NonNull CameraDevice camera) {
                    mCameraDevice = camera;
                }

                @Override
                public void onDisconnected(@NonNull CameraDevice camera) {

                }

                @Override
                public void onError(@NonNull CameraDevice camera, int error) {

                }
            }, handler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void createCaptureSession(CameraDevice device, List<Surface> targets) {
        try {
            device.createCaptureSession(targets, new CameraCaptureSession.StateCallback() {
                @Override
                public void onConfigured(@NonNull CameraCaptureSession session) {
                    mSession = session;
                }

                @Override
                public void onConfigureFailed(@NonNull CameraCaptureSession session) {

                }
            }, null);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }*/
}
