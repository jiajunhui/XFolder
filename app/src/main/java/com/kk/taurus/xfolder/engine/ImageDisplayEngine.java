package com.kk.taurus.xfolder.engine;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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

}
