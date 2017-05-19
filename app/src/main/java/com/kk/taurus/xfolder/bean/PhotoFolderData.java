package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;
import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoFolderData implements HolderData,Serializable {
    private List<PhotoFolder> folders;

    public List<PhotoFolder> getFolders() {
        return folders;
    }

    public void setFolders(List<PhotoFolder> folders) {
        this.folders = folders;
    }
}
