package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/9.
 */

public class MediaEntity implements Serializable{
    private List<VideoItem> videoItems;
    private List<PhotoItem> photoItems;
    private List<AudioItem> audioItems;

    public List<VideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    public List<PhotoItem> getPhotoItems() {
        return photoItems;
    }

    public void setPhotoItems(List<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }

    public List<AudioItem> getAudioItems() {
        return audioItems;
    }

    public void setAudioItems(List<AudioItem> audioItems) {
        this.audioItems = audioItems;
    }
}
