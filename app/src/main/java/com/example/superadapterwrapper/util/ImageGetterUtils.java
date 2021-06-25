package com.example.superadapterwrapper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * ClassName ImageGetterUtils
 * User: zuoweichen
 * Date: 2021/5/24 14:04
 * Description: 描述
 */
public class ImageGetterUtils {

    public static MyImageGetter getImageGetter(Context context, TextView textView) {
        MyImageGetter myImageGetter = new MyImageGetter(context, textView);
        return myImageGetter;
    }

    public static class MyImageGetter implements Html.ImageGetter {

        private URLDrawable urlDrawable = null;
        private TextView textView;
        private Context context;

        public MyImageGetter(Context context, TextView textView) {
            this.textView = textView;
            this.context = context;
        }

        @Override
        public Drawable getDrawable(final String source) {
            urlDrawable = new URLDrawable();
            Glide.with(context).asBitmap().load(source).into(new CustomTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull @NotNull Bitmap resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Bitmap> transition) {
                    urlDrawable.bitmap =changeBitmapSize(resource) ;
                    Logger.getLogger("加载的图片，Width：\" + resource.getWidth() + \"，Height：\" + resource.getHeight()");
                    urlDrawable.setBounds(0, 0, changeBitmapSize(resource).getWidth(),changeBitmapSize(resource).getHeight());

                    textView.invalidate();
                    textView.setText(textView.getText());//不加这句显示不出来图片，原因不详
                }

                @Override
                public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {

                }
            });
            return urlDrawable;
        }

        public class URLDrawable extends BitmapDrawable {
            public Bitmap bitmap;

            @Override
            public void draw(Canvas canvas) {
                super.draw(canvas);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
                }
            }
        }

        private Bitmap changeBitmapSize(Bitmap bitmap) {


            int width = bitmap.getWidth();

            int height = bitmap.getHeight();

            Log.e("width","width:"+width);

            Log.e("height","height:"+height);
//设置想要的大小
            int newWidth=40;
            int newHeight=40;
//计算压缩的比率
            float scaleWidth=((float)newWidth)/width;
            float scaleHeight=((float)newHeight)/height;
//获取想要缩放的matrix
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth,scaleHeight);

//获取新的bitmap
            bitmap=Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);

            bitmap.getWidth();

            bitmap.getHeight();

            Log.e("newWidth","newWidth"+bitmap.getWidth());

            Log.e("newHeight","newHeight"+bitmap.getHeight());

            return bitmap;

        }


    }
}
