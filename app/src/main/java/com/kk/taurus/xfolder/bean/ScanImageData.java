package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/21.
 */

public class ScanImageData implements Serializable ,HolderData{

    private int currPosition;
    private List<PhotoItem> photoItems;

    public int getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(int currPosition) {
        this.currPosition = currPosition;
    }

    public List<PhotoItem> getPhotoItems() {
        return photoItems;
    }

    public void setPhotoItems(List<PhotoItem> photoItems) {
        this.photoItems = photoItems;
    }
}
