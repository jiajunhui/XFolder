package com.kk.taurus.xfolder.ui.activity;

import android.content.res.Configuration;
import android.os.Bundle;

import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.ScanVideoData;
import com.kk.taurus.xfolder.holder.ScanVideoHolder;

/**
 * Created by Taurus on 2017/5/18.
 */

public class ScanVideoActivity extends ToolBarActivity<ScanVideoData,ScanVideoHolder> implements ScanVideoHolder.OnScanVideoListener {

    public static final String KEY_SCAN_VIDEO_DATA = "video_data";
    private boolean isLandscape;

    @Override
    public ScanVideoHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ScanVideoHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        mContentHolder.setOnScanVideoListener(this);
    }

    @Override
    public void loadState() {
        FileItem item = (FileItem) getIntent().getSerializableExtra(KEY_SCAN_VIDEO_DATA);
        setToolBarTitle(item.getName());
        ScanVideoData data = new ScanVideoData();
        data.setItem(item);
        setData(data);
        super.loadState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            isLandscape = true;
            fullScreen();
        }else{
            isLandscape = false;
            quitFullScreen();
        }
    }

    @Override
    public void onBackPressed() {
        if(isLandscape){
            mContentHolder.screenPortrait();
        }else{
            super.onBackPressed();
        }
    }

}
