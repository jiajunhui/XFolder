package com.kk.taurus.xfolder.mvp.impl;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnFileLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.kk.taurus.filebase.engine.StorageEngine;
import com.kk.taurus.xfolder.mvp.inter.model.IHomeModel;

/**
 * Created by Taurus on 2017/6/7.
 */

public class HomeModel implements IHomeModel {
    @Override
    public void loadMediaImage(FragmentActivity activity, OnPhotoLoaderCallBack onPhotoLoaderCallBack) {
        MediaLoader.getLoader().loadPhotos(activity, onPhotoLoaderCallBack);
    }

    @Override
    public void loadMediaVideo(FragmentActivity activity, OnVideoLoaderCallBack onVideoLoaderCallBack) {
        MediaLoader.getLoader().loadVideos(activity, onVideoLoaderCallBack);
    }

    @Override
    public void loadMediaAudio(FragmentActivity activity, OnAudioLoaderCallBack onAudioLoaderCallBack) {
        MediaLoader.getLoader().loadAudios(activity, onAudioLoaderCallBack);
    }

    @Override
    public void loadFileApk(FragmentActivity activity, OnFileLoaderCallBack onFileLoaderCallBack) {
        MediaLoader.getLoader().loadFiles(activity, onFileLoaderCallBack);
    }

    @Override
    public void loadFileZip(FragmentActivity activity, OnFileLoaderCallBack onFileLoaderCallBack) {
        MediaLoader.getLoader().loadFiles(activity, onFileLoaderCallBack);
    }

    @Override
    public void loadFileDoc(FragmentActivity activity, OnFileLoaderCallBack onFileLoaderCallBack) {
        MediaLoader.getLoader().loadFiles(activity, onFileLoaderCallBack);
    }

    @Override
    public void loadStorageList(Context context, OnLoadStorageListener onLoadStorageListener) {
        if(onLoadStorageListener!=null){
            onLoadStorageListener.onLoadFinish(StorageEngine.getStorageList(context));
        }
    }

}
