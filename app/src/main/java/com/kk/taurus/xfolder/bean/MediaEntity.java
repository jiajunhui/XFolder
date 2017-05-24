package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.bean.PhotoResult;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;

import java.io.Serializable;

/**
 * Created by Taurus on 2017/5/9.
 */

public class MediaEntity implements Serializable{

    private PhotoResult photoResult;
    private VideoResult videoResult;
    private AudioResult audioResult;
    private FileResult apkResult;
    private FileResult zipResult;
    private FileResult docResult;

    public PhotoResult getPhotoResult() {
        return photoResult;
    }

    public void setPhotoResult(PhotoResult photoResult) {
        this.photoResult = photoResult;
    }

    public VideoResult getVideoResult() {
        return videoResult;
    }

    public void setVideoResult(VideoResult videoResult) {
        this.videoResult = videoResult;
    }

    public AudioResult getAudioResult() {
        return audioResult;
    }

    public void setAudioResult(AudioResult audioResult) {
        this.audioResult = audioResult;
    }

    public FileResult getApkResult() {
        return apkResult;
    }

    public void setApkResult(FileResult apkResult) {
        this.apkResult = apkResult;
    }

    public FileResult getZipResult() {
        return zipResult;
    }

    public void setZipResult(FileResult zipResult) {
        this.zipResult = zipResult;
    }

    public FileResult getDocResult() {
        return docResult;
    }

    public void setDocResult(FileResult docResult) {
        this.docResult = docResult;
    }
}
