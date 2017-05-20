package com.kk.taurus.xfolder.bean;

import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;

/**
 * Created by Taurus on 2017/5/8.
 */

public class ScanVideoData implements Serializable ,HolderData {

    private FileItem item;

    public FileItem getItem() {
        return item;
    }

    public void setItem(FileItem item) {
        this.item = item;
    }
}
