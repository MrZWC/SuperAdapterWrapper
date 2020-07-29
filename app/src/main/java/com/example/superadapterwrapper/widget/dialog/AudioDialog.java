package com.example.superadapterwrapper.widget.dialog;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.widget.AudioView;
import com.idonans.lang.thread.Threads;
import com.zwc.viewdialog.ViewDialog;

/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/19
 * Time: 10:06
 */
public class AudioDialog {
    private ViewDialog mViewDialog;
    private Activity mActivity;
    private AudioView mAudioView;

    public AudioDialog(Activity activity) {
        mActivity = activity;
        ViewGroup parentView = mActivity.findViewById(Window.ID_ANDROID_CONTENT);
        mViewDialog = new ViewDialog.Builder(mActivity)
                .setParentView(parentView)
                .setCancelable(true)
                .dimBackground(true)
                .setContentView(R.layout.dialog_audio_layout)
                .create();
        mAudioView = mViewDialog.findViewById(R.id.audio_view);
        initData();
    }

    private void initData() {
        mAudioView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Threads.postUi(new Runnable() {
            @Override
            public void run() {
                String musicUrl = "https://webfs.yun.kugou.com/202006221045/6486f5a71323e4d4b3bc8961e0adde02/G215/M02/03/15/Fw4DAF7WRrCAQneCADOpeg3YN-g333.mp3";
                mAudioView.setDataSources(musicUrl);
                mAudioView.play();
            }
        });
    }


    public void show() {
        mViewDialog.show();
    }

    public void hide() {
        mViewDialog.hide(false);
    }
}
