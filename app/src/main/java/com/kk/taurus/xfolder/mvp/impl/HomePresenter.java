package com.kk.taurus.xfolder.mvp.impl;

import android.support.v4.app.FragmentActivity;

import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.bean.FileType;
import com.jiajunhui.xapp.medialoader.bean.PhotoResult;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnFileLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.mvp.inter.model.IHomeModel;
import com.kk.taurus.xfolder.mvp.inter.presenter.IHomePresenter;
import com.kk.taurus.xfolder.mvp.inter.view.IHomeView;

import java.util.List;

/**
 * Created by Taurus on 2017/6/7.
 */

public class HomePresenter implements IHomePresenter{

    private IHomeModel mHomeModel;
    private IHomeView mHomeView;

    public HomePresenter(IHomeView homeView){
        this.mHomeView = homeView;
        this.mHomeModel = new HomeModel();
    }

    @Override
    public void onStart(FragmentActivity activity) {
        mHomeView.animation();
        mHomeModel.loadMediaImage(activity, new OnPhotoLoaderCallBack() {
            @Override
            public void onResult(PhotoResult result) {
                mHomeView.updateImageInfo(result);
            }
        });
        mHomeModel.loadMediaVideo(activity, new OnVideoLoaderCallBack() {
            @Override
            public void onResult(VideoResult result) {
                mHomeView.updateVideoInfo(result);
            }
        });
        mHomeModel.loadMediaAudio(activity, new OnAudioLoaderCallBack() {
            @Override
            public void onResult(AudioResult result) {
                mHomeView.updateAudioInfo(result);
            }
        });
        mHomeModel.loadFileApk(activity, new OnFileLoaderCallBack(FileType.APK) {
            @Override
            public void onResult(FileResult result) {
                mHomeView.updateApkInfo(result);
            }
        });
        mHomeModel.loadFileZip(activity, new OnFileLoaderCallBack(FileType.ZIP) {
            @Override
            public void onResult(FileResult result) {
                mHomeView.updateZipInfo(result);
            }
        });
        mHomeModel.loadFileDoc(activity, new OnFileLoaderCallBack(FileType.DOC) {
            @Override
            public void onResult(FileResult result) {
                mHomeView.updateDocInfo(result);
            }
        });
        mHomeModel.loadStorageList(activity, new IHomeModel.OnLoadStorageListener() {
            @Override
            public void onLoadFinish(List<Storage> storageList) {
                mHomeView.updateStorageInfo(storageList);
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
