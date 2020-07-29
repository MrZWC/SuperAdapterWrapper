package com.example.superadapterwrapper.moudle;

import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.TextureView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.superadapterwrapper.R;

import java.io.File;
import java.io.IOException;

public class MediaRecorderActivity extends AppCompatActivity {
    private static final String TAG = "mMediaRecorderActivity";
    private MediaRecorder mMediaRecorder;
    private TextureView mTextureView;
    private SurfaceTexture mSurface;
    private int current_camera;
    private Camera camera;
    private Camera.Parameters parameters;

    public static void start(Context context) {
        Intent intent = new Intent(context, MediaRecorderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_recorder);
        mTextureView = findViewById(R.id.mTextureView);
        initmMediaRecorder();
    }

    private void initmMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                openCamera(Camera.CameraInfo.CAMERA_FACING_BACK, surface);
                mSurface = surface;
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });
        //configmMediaRecorder();
    }
    /**
     * 打开摄像头
     * @param position 摄像头位置
     */
    public void openCamera(int position,SurfaceTexture surface){
        current_camera = position;
        camera = Camera.open(position);//打开摄像头
        parameters= camera.getParameters();//设置参数   parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//设置自动对焦
        try {
            parameters.setPreviewSize(1920,1080);//设置预览尺寸，为了全屏展示，我们选择最大尺寸，同时TextureView也应该是match_parent全屏
            camera.setParameters(parameters);//设置相机的参数
            camera.setDisplayOrientation(90);//设置显示翻转，为0则是水平录像，90为竖屏
            camera.setPreviewTexture(surface);//将onSurfaceTextureAvailable监听中的surface传入进来，设置预览的控件
        } catch (IOException t) {
            Log.d(TAG, "onSurfaceTextureAvailable: IO异常");
        }
        camera.startPreview();//开始预览
        mTextureView.setAlpha(1.0f);
    }
    /**
     * 录制视频
     */
    public void record(){
        Log.d(TAG, "record: 开始录制");
        mMediaRecorder = new MediaRecorder();
        camera.unlock();
        mMediaRecorder.setCamera(camera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
//        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH)); //setProfile不能和后面的setOutputFormat等方法一起使用
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);  // 设置视频的输出格式 为MP4

        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT); // 设置音频的编码格式
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264); // 设置视频的编码格式
//        mMediaRecorder.setVideoSize(176, 144);  // 设置视频大小
//        mMediaRecorder.setVideoSize(320, 240);  // 设置视频大小
        mMediaRecorder.setVideoSize(1920, 1080);  // 设置视频大小
        mMediaRecorder.setVideoEncodingBitRate(5*1024*1024);
        mMediaRecorder.setVideoFrameRate(60); // 设置帧率

        /*
         * 设置视频文件的翻转角度
         * 改变保存后的视频文件播放时是否横屏(不加这句，视频文件播放的时候角度是反的)
         * */
        if (current_camera == Camera.CameraInfo.CAMERA_FACING_FRONT){
            mMediaRecorder.setOrientationHint(270);
        }else if (current_camera == Camera.CameraInfo.CAMERA_FACING_BACK){
            mMediaRecorder.setOrientationHint(90);
        }

//        mRecorder.setMaxDuration(10000); //设置最大录像时间为10s
//        mMediaRecorder.setPreviewDisplay();
//        mMediaRecorder.setPreviewDisplay(myTexture);


        //设置视频存储路径
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES) + File.separator + "yuewu");
        if (!file.exists()) {
            //多级文件夹的创建
            file.mkdirs();
        }

        Log.d(TAG, "record: path " +file.getPath() + File.separator + "乐舞_" + System.currentTimeMillis() + ".mp4");
        mMediaRecorder.setOutputFile(file.getPath() + File.separator + "乐舞_" + System.currentTimeMillis() + ".mp4");

        //开始录制
        try {
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 停止录制
     */
    public void stop(){
//        mediaRecorder.setOnErrorListener(null);
//        mediaRecorder.setOnInfoListener(null);
        camera.lock();

        mMediaRecorder.stop();
        mMediaRecorder.release();

        openCamera(Camera.CameraInfo.CAMERA_FACING_BACK,mSurface);//启动预览,可以判断之前是前置摄像头还是后置摄像头来继续启动预览
        Log.d(TAG, "stop: 录制完成");
    }

   /* private void configmMediaRecorder() {
        File videoFile = new File(getExternalCacheDir(), "test_video.mp4");
        Log.e(TAG, "文件路径=" + videoFile.getAbsolutePath());
        if (videoFile.exists()) {
            videoFile.delete();
        }
        Camera camera = Camera.open();
        camera.unlock();
        mmMediaRecorder.setCamera(camera);
        mmMediaRecorder.setAudioSource(mMediaRecorder.AudioSource.MIC);//设置音频输入源  也可以使用 mMediaRecorder.AudioSource.MIC
        mmMediaRecorder.setVideoSource(mMediaRecorder.VideoSource.CAMERA);//设置视频输入源
        mmMediaRecorder.setOutputFormat(mMediaRecorder.OutputFormat.MPEG_4);//音频输出格式
        mmMediaRecorder.setAudioEncoder(mMediaRecorder.AudioEncoder.DEFAULT);//设置音频的编码格式
        mmMediaRecorder.setVideoEncoder(mMediaRecorder.VideoEncoder.MPEG_4_SP);//设置图像编码格式

//        mmMediaRecorder.setVideoFrameRate(30);//要录制的视频帧率 帧率越高视频越流畅 如果设置设备不支持的帧率会报错  按照注释说设备会支持自动帧率所以一般情况下不需要设置
//        mmMediaRecorder.setVideoSize(1280,1920);//设置录制视频的分辨率  如果设置设备不支持的分辨率会报错
        mmMediaRecorder.setVideoEncodingBitRate(8 * 1920 * 1080);//设置比特率,比特率是每一帧所含的字节流数量,比特率越大每帧字节越大,画面就越清晰,算法一般是 5 * 选择分辨率宽 * 选择分辨率高,一般可以调整5-10,比特率过大也会报错
        mmMediaRecorder.setOrientationHint(90);//设置视频的摄像头角度 只会改变录制的视频文件的角度(对预览图像角度没有效果)
        mTextureView.post(new Runnable() {
            @Override
            public void run() {
                if (mTextureView.isAvailable()) {
                    Surface surface = new Surface(mTextureView.getSurfaceTexture());
                    mmMediaRecorder.setPreviewDisplay(surface);//设置拍摄预览
                    mmMediaRecorder.setOutputFile(videoFile.getAbsolutePath());//MP4文件保存路径
                }
                try {
                    mmMediaRecorder.prepare();
                    //  mmMediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }*/
}
