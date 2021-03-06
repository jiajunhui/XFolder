package com.kk.taurus.xfolder.bean;

/**
 * Created by Taurus on 2017/5/6.
 */

public class FileItem extends BaseItem {

    private String extensionName;
    private String mime;
    private long size;

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
