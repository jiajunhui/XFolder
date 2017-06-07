package com.kk.taurus.xfolder.mvp.inter.view;

import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.bean.PhotoResult;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;
import com.kk.taurus.filebase.entity.Storage;

import java.util.List;

/**
 * Created by Taurus on 2017/6/7.
 */

public interface IHomeView {

    void animation();

    void updateImageInfo(PhotoResult result);
    void updateVideoInfo(VideoResult result);
    void updateAudioInfo(AudioResult result);
    void updateApkInfo(FileResult result);
    void updateZipInfo(FileResult result);
    void updateDocInfo(FileResult result);

    void updateStorageInfo(List<Storage> storageList);

}
