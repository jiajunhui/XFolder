package com.kk.taurus.xfolder.bean;

import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.filebase.entity.Storage;

import java.util.List;

/**
 * Created by Taurus on 2017/5/18.
 */

public class MainHolderData implements HolderData {
    private MediaEntity mediaEntity;
    private List<Storage> storageList;

    public MediaEntity getMediaEntity() {
        return mediaEntity;
    }

    public void setMediaEntity(MediaEntity mediaEntity) {
        this.mediaEntity = mediaEntity;
    }

    public List<Storage> getStorageList() {
        return storageList;
    }

    public void setStorageList(List<Storage> storageList) {
        this.storageList = storageList;
    }
}
