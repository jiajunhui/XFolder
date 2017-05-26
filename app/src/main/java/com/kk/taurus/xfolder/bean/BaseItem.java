package com.kk.taurus.xfolder.bean;

import java.io.Serializable;

/**
 * Created by Taurus on 2017/5/6.
 */

public class BaseItem implements Serializable {

    private int id;
    private String name;
    private String path;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
