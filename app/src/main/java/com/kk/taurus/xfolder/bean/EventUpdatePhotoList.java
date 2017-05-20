package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;

import java.util.List;

/**
 * Created by Taurus on 2017/5/20.
 */

public class EventUpdatePhotoList {

    private String folderName;
    private List<PhotoItem> items;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }
}
