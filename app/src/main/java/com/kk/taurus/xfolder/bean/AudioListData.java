package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.kk.taurus.baseframe.base.HolderData;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListData implements HolderData,Serializable {
    private List<AudioItem> audioItems;

    public List<AudioItem> getAudioItems() {
        return audioItems;
    }

    public void setAudioItems(List<AudioItem> audioItems) {
        this.audioItems = audioItems;
    }
}
