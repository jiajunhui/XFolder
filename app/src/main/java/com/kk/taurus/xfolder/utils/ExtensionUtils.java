package com.kk.taurus.xfolder.utils;

import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FolderItem;

/**
 * Created by Taurus on 2016/12/21.
 */

public class ExtensionUtils {
    public static int getImageRes(BaseItem item){
        if(item!=null){
            if(item instanceof FolderItem){
                return R.mipmap.icon_folder;
            }
            String extensionName = getExtension(item.getName());
            if(".mp4".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_video;
            }else if(".mp3".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_mp3;
            }else if(".txt".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_txt;
            }else if(".html".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_html;
            }else if(".jpg".equalsIgnoreCase(extensionName)||".png".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_image;
            }else if(".zip".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_zip;
            }else if(".apk".equalsIgnoreCase(extensionName)){
                return R.mipmap.icon_apk;
            }
        }
        return R.mipmap.icon_unknow;
    }

    public static String getExtension(String name){
        int i = name.indexOf('.');
        if (i != -1) {
            name = name.substring(i);
        }
        return name;
    }
}
