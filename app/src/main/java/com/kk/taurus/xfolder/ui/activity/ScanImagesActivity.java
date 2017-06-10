package com.kk.taurus.xfolder.ui.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.graphics.Palette;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.ScanImageData;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;
import com.kk.taurus.xfolder.holder.ScanImageHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class ScanImagesActivity extends ToolBarActivity<ScanImageData,ScanImageHolder> implements ScanImageHolder.OnScanImageListener {

    private Runnable mRunnable;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    public ScanImageHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ScanImageHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setElevation(0f);
        mContentHolder.setOnScanImageListener(this);
    }

    @Override
    public void loadState() {
        setData(PhotoDataHelper.getScanImageData());
        setPageState(PageState.success());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoDataHelper.clearScanImageData();
        if(mRunnable!=null){
            mHandler.removeCallbacks(mRunnable);
        }
    }

    @Override
    public void onCardSelected(final PhotoItem item, int position, int count) {
        setCenterTitle((position + 1) + "/" + count);
        if(mRunnable!=null){
            mHandler.removeCallbacks(mRunnable);
        }
        mHandler.postDelayed(mRunnable = new Runnable() {
            @Override
            public void run() {
                ImageDisplayEngine.displayAsBitmap(getApplicationContext(), item.getPath(), new ImageDisplayEngine.OnBitmapResourceCallBack() {
                    @Override
                    public void onResourceReady(Bitmap bitmap) {
                        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch swatch = palette.getVibrantSwatch();
                                if(swatch!=null){
                                    changeColor(swatch.getRgb());
                                }
                            }
                        });
                    }
                });
            }
        }, 100);
    }

    private void changeColor(int color){
        setStatusBarColor(color);
        getToolBar().setBackgroundColor(color);
        mContentHolder.setBackgroundColor(color);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setNavigationBarColor(color);
        }
    }
}
