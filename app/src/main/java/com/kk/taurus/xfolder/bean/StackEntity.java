package com.kk.taurus.xfolder.bean;

import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/6.
 */

public class StackEntity implements Serializable ,HolderData {
    private String name;
    private String path;
    private List<BaseItem> items;
    private StateRecord stateRecord;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<BaseItem> getItems() {
        return items;
    }

    public void setItems(List<BaseItem> items) {
        this.items = items;
    }

    public StateRecord getStateRecord() {
        return stateRecord;
    }

    public void setStateRecord(StateRecord stateRecord) {
        this.stateRecord = stateRecord;
    }
}
