package com.kk.taurus.xfolder.bean;

import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;

/**
 * Created by Taurus on 2017/5/21.
 */

public class ScanAudioData implements HolderData ,Serializable{
    private MAudioItem audioItem;

    public MAudioItem getAudioItem() {
        return audioItem;
    }

    public void setAudioItem(MAudioItem audioItem) {
        this.audioItem = audioItem;
    }
}
