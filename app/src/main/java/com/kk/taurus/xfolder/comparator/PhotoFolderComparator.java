package com.kk.taurus.xfolder.comparator;

import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;

import java.util.Comparator;

/**
 * Created by Taurus on 2017/5/20.
 */

public class PhotoFolderComparator implements Comparator<PhotoFolder> {
    @Override
    public int compare(PhotoFolder folder, PhotoFolder t1) {
        int folderSize = folder.getItems().size();
        int t1Size = t1.getItems().size();
        if(folderSize > t1Size){
            return -1;
        }
        if(folderSize < t1Size){
            return 1;
        }
        return 0;
    }
}
