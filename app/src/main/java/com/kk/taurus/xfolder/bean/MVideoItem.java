package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.VideoItem;

/**
 * Created by Taurus on 2017/5/20.
 */

public class MVideoItem extends VideoItem {

    private String thumbnail;

    public MVideoItem() {
    }

    public MVideoItem(int id, String displayName, String path, long duration, long size) {
        super(id, displayName, path, duration, size);
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
