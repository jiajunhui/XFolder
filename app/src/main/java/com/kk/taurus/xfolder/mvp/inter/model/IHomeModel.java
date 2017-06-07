package com.kk.taurus.xfolder.mvp.inter.model;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnFileLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.kk.taurus.filebase.entity.Storage;

import java.util.List;

/**
 * Created by Taurus on 2017/6/7.
 */

public interface IHomeModel {

    void loadMediaImage(FragmentActivity activity, OnPhotoLoaderCallBack onPhotoLoaderCallBack);
    void loadMediaVideo(FragmentActivity activity, OnVideoLoaderCallBack onVideoLoaderCallBack);
    void loadMediaAudio(FragmentActivity activity, OnAudioLoaderCallBack onAudioLoaderCallBack);
    void loadFileApk(FragmentActivity activity, OnFileLoaderCallBack onFileLoaderCallBack);
    void loadFileZip(FragmentActivity activity, OnFileLoaderCallBack onFileLoaderCallBack);
    void loadFileDoc(FragmentActivity activity, OnFileLoaderCallBack onFileLoaderCallBack);

    void loadStorageList(Context context, OnLoadStorageListener onLoadStorageListener);

    interface OnLoadStorageListener{
        void onLoadFinish(List<Storage> storageList);
    }

}
