package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListData implements HolderData,Serializable {
    private List<PhotoItem> photoItems;

    public List<PhotoItem> getPhotoItems() {
        return photoItems;
    }

    public void setPhotoItems(List<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }
}
