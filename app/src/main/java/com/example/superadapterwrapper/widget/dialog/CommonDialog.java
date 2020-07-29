package com.example.superadapterwrapper.widget.dialog;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.superadapterwrapper.R;
import com.example.superadapterwrapper.widget.roundview.RoundTextView;
import com.idonans.lang.util.ViewUtil;
import com.zwc.viewdialog.ViewDialog;


/**
 * Created by Android Studio.
 * User: zuoweichen
 * Date: 2020/6/1
 * Time: 16:43
 */
public class CommonDialog {
    private ViewDialog mViewDialog;
    private Activity mActivity;
    private TextView mTitle;
    private TextView mContent;
    protected RoundTextView mLeftBtn;
    private RoundTextView mRightBtn;


    private CommonDialog(Builder builder) {
        if (builder == null) {
            return;
        }
        mActivity = builder.activity;
        ViewGroup parentView = mActivity.findViewById(Window.ID_ANDROID_CONTENT);
        mViewDialog = new ViewDialog.Builder(mActivity)
                .setParentView(parentView)
                .setCancelable(builder.cancelable)
                .dimBackground(true)
                .setContentView(R.layout.dialog_common_layout)
                .create();
        initView();
        initData(builder);

    }


    private void initView() {
        mTitle = mViewDialog.findViewById(R.id.title);
        mContent = mViewDialog.findViewById(R.id.content);
        mLeftBtn = mViewDialog.findViewById(R.id.left_btn);
        mRightBtn = mViewDialog.findViewById(R.id.right_btn);
    }

    private void initData(Builder builder) {
        if (builder.leftDark) {
            setLeftDark();
        } else {
            setRightDark();
        }

        if (!TextUtils.isEmpty(builder.titleString)) {
            mTitle.setText(builder.titleString);
        }
        if (!TextUtils.isEmpty(builder.contentString)) {
            mContent.setText(builder.contentString);
        }
        if (!TextUtils.isEmpty(builder.letfString)) {
            mLeftBtn.setText(builder.letfString);
        }
        if (!TextUtils.isEmpty(builder.rightString)) {
            mRightBtn.setText(builder.rightString);
        }
        ViewUtil.onClick(mLeftBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.leftListener != null) {
                    builder.leftListener.onBtnLeftClick(CommonDialog.this);
                } else {
                    hide();
                }
            }
        });
        ViewUtil.onClick(mRightBtn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (builder.rightListener != null) {
                    builder.rightListener.onBtnRightClick(CommonDialog.this);
                } else {
                    hide();
                }
            }
        });

    }

    public static Builder builder(Activity activity) {
        return new Builder(activity);
    }

    public static class Builder {
        private Activity activity;
        private boolean leftDark = false;
        private OnBtnLeftClickListener leftListener;
        private OnBtnRightClickListener rightListener;
        private boolean cancelable = true;
        private String titleString;
        private String contentString;
        private String letfString;
        private String rightString;

        public Builder(Activity activity) {
            this.activity = activity;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setTitleString(String titleString) {
            this.titleString = titleString;
            return this;
        }

        public Builder setContentString(String contentString) {
            this.contentString = contentString;
            return this;
        }

        public Builder setLeftString(String letfString) {
            this.letfString = letfString;
            return this;
        }

        public Builder setRightString(String rightString) {
            this.rightString = rightString;
            return this;
        }

        public Builder setLeftDark() {
            leftDark = true;
            return this;
        }

        public Builder setRightDark() {
            leftDark = false;
            return this;
        }

        public Builder setLeftListener(OnBtnLeftClickListener leftListener) {
            this.leftListener = leftListener;
            return this;
        }

        public Builder setRightlListener(OnBtnRightClickListener rightListener) {
            this.rightListener = rightListener;
            return this;
        }

        public CommonDialog build() {
            return new CommonDialog(this);
        }
    }

    /**
     * 设置左按钮为深色 右按钮为浅色
     *
     * @return
     */
    public CommonDialog setLeftDark() {
        mLeftBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.C12B5B0));
        mRightBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.white));
        mLeftBtn.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
        mRightBtn.setTextColor(ContextCompat.getColor(mActivity, R.color.C12B5B0));
        return this;
    }

    /**
     * 设置右按钮为深色  左按钮为浅色
     *
     * @return
     */
    private CommonDialog setRightDark() {
        mRightBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.C12B5B0));
        mLeftBtn.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.white));
        mRightBtn.setTextColor(ContextCompat.getColor(mActivity, R.color.white));
        mLeftBtn.setTextColor(ContextCompat.getColor(mActivity, R.color.C12B5B0));
        return this;
    }
    public void show() {
        mViewDialog.show();
    }

    public void hide() {
        mViewDialog.hide(false);
    }

    public interface OnBtnRightClickListener {
        void onBtnRightClick(CommonDialog dialog);
    }

    public interface OnBtnLeftClickListener {
        void onBtnLeftClick(CommonDialog dialog);
    }

}
