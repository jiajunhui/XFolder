package com.kk.taurus.xfolder.bean;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.xfolder.config.ThumbnailCache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListData implements HolderData,Serializable {
    private List<MAudioItem> audioItems;

    public List<MAudioItem> getAudioItems() {
        return audioItems;
    }

    public void setAudioItems(List<MAudioItem> audioItems) {
        this.audioItems = audioItems;
    }

    public static List<MAudioItem> trans(ThumbnailCache thumbnailCache,List<AudioItem> items){
        List<MAudioItem> audioItems = new ArrayList<>();
        if(items!=null){
            MAudioItem audioItem;
            for(AudioItem item : items){
                audioItem = new MAudioItem();
                audioItem.setId(item.getId());
                audioItem.setDisplayName(item.getDisplayName());
                audioItem.setDuration(item.getDuration());
                audioItem.setPath(item.getPath());
                audioItem.setSize(item.getSize());
                String cachePath = thumbnailCache.getAudioCoverCachePath(item.getPath());
                audioItem.setAudioCover(cachePath);
                audioItems.add(audioItem);
            }
        }
        return audioItems;
    }
}
