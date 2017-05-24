package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.FileType;
import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/24.
 */

public class FileListData implements HolderData ,Serializable{

    private List<FileItem> items;
    private String title;
    private FileType type;

    public List<FileItem> getItems() {
        return items;
    }

    public void setItems(List<FileItem> items) {
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }
}
