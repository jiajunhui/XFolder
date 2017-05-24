package com.kk.taurus.xfolder.utils;

import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FolderItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Taurus on 2016/12/21.
 */

public class ExtensionUtils {

    private static Map<String,Integer> mImageResMap = new HashMap<String,Integer>(){
        {
            put(".mp4",R.mipmap.icon_video);
            put(".ts",R.mipmap.icon_video);
            put(".wmv",R.mipmap.icon_video);
            put(".mkv",R.mipmap.icon_video);
            put(".flv",R.mipmap.icon_video);
            put(".rmvb",R.mipmap.icon_video);

            put(".mp3",R.mipmap.icon_music);
            put(".wma",R.mipmap.icon_music);
            put(".wav",R.mipmap.icon_music);
            put(".aac",R.mipmap.icon_music);

            put(".txt",R.mipmap.icon_txt);
            put(".log",R.mipmap.icon_log);

            put(".doc",R.mipmap.icon_word);
            put(".docx",R.mipmap.icon_word);

            put(".html",R.mipmap.icon_html);

            put(".jpg",R.mipmap.icon_image);
            put(".jpeg",R.mipmap.icon_image);
            put(".png",R.mipmap.icon_image);
            put(".bmp",R.mipmap.icon_image);
            put(".gif",R.mipmap.icon_image);

            put(".zip",R.mipmap.icon_zip);
            put(".rar",R.mipmap.icon_rar);
            put(".apk",R.mipmap.icon_apk);
            put(".ppt",R.mipmap.icon_ppt);
            put(".xls",R.mipmap.icon_excel);
            put(".pdf",R.mipmap.icon_pdf);

        }
    };

    public static int getImageRes(BaseItem item){
        if(item!=null){
            if(item instanceof FolderItem){
                return R.mipmap.icon_folder;
            }
            String extensionName = getExtension(item.getName());
            Integer resId = mImageResMap.get(extensionName);
            if(resId==null || resId==0){
                return R.mipmap.icon_unknow;
            }
            return resId;
        }
        return R.mipmap.icon_unknow;
    }

    public static String getExtension(String name){
        int i = name.lastIndexOf('.');
        if (i != -1) {
            name = name.substring(i);
        }
        return name;
    }
}
