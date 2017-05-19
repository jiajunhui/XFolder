package com.kk.taurus.xfolder.utils;

import android.content.Context;
import android.content.Intent;

import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.ui.activity.ScanTextActivity;
import com.kk.taurus.xfolder.ui.activity.ScanVideoActivity;

import java.util.List;

/**
 * Created by Taurus on 2017/5/8.
 */

public class IntentUtils {

    public static Intent getIntent(Context context, List<BaseItem> items, FileItem item, int position){
        Intent intent = null;
        String extension = item.getExtensionName();
        if(".txt".equalsIgnoreCase(extension)|| ".log".equalsIgnoreCase(extension)){
            intent = new Intent(context,ScanTextActivity.class);
            intent.putExtra(ScanTextActivity.KEY_SCAN_TEXT_DATA,item);
        }else if(".mp4".equalsIgnoreCase(extension) || ".ts".equalsIgnoreCase(extension)){
            intent = new Intent(context,ScanVideoActivity.class);
            intent.putExtra(ScanVideoActivity.KEY_SCAN_VIDEO_DATA,item);
        }
        return intent;
    }

}
