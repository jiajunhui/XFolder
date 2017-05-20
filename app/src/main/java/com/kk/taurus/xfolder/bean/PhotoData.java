package com.kk.taurus.xfolder.bean;

import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;

/**
 * Created by Taurus on 2017/5/20.
 */

public class PhotoData implements HolderData,Serializable {
    private PhotoFolderData folderData;
    private PhotoListData listData;

    public PhotoFolderData getFolderData() {
        return folderData;
    }

    public void setFolderData(PhotoFolderData folderData) {
        this.folderData = folderData;
    }

    public PhotoListData getListData() {
        return listData;
    }

    public void setListData(PhotoListData listData) {
        this.listData = listData;
    }
}
