package com.kk.taurus.xfolder.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by Taurus on 2017/5/20.
 */

public class ImageDisplayEngine {

    public static void display(Context context, ImageView view,String path, int defaultHolder){
        Glide.with(context)
                .load(path)
                .centerCrop()
                .placeholder(defaultHolder)
                .crossFade()
                .into(view);
    }

    public static void displayAsBitmap(Context context, ImageView view,String path, int defaultHolder){
        Glide.with(context)
                .load(path)
                .asBitmap()
                .centerCrop()
                .placeholder(defaultHolder)
                .into(view);
    }

    public static void displayAsBitmap(Context context, String path, final OnBitmapResourceCallBack callBack){
        Glide.with(context)
                .load(path)
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(callBack!=null){
                            callBack.onResourceReady(resource);
                        }
                    }
                });
//                .into(new BitmapImageViewTarget(new ImageView(context)){
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        if(callBack!=null){
//                            callBack.onResourceReady(resource);
//                        }
//                    }
//                });
    }

    public interface OnBitmapResourceCallBack{
        void onResourceReady(Bitmap bitmap);
    }

}
