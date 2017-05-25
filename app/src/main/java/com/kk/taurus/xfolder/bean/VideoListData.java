package com.kk.taurus.xfolder.bean;

import android.text.TextUtils;

import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.xfolder.engine.CacheEngine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class VideoListData implements HolderData,Serializable {

    private List<MVideoItem> videoItems;

    public List<MVideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<MVideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    public static List<MVideoItem> trans(List<VideoItem> videoItems){
        List<MVideoItem> mVideoItems = new ArrayList<>();
        MVideoItem mVideoItem;
        for(VideoItem item : videoItems){
            mVideoItem = new MVideoItem(item.getId(),item.getDisplayName(),item.getPath(),item.getDuration(),item.getSize());
            String cachePath = CacheEngine.getInstance().getVideoThumbnailCachePath(item.getPath());
            if(!TextUtils.isEmpty(cachePath)){
                mVideoItem.setThumbnail(cachePath);
            }
            mVideoItems.add(mVideoItem);
        }
        return mVideoItems;
    }
}
