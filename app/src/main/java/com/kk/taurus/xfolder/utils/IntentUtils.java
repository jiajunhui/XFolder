package com.kk.taurus.xfolder.utils;

import android.content.Context;
import android.content.Intent;

import com.jiajunhui.xapp.medialoader.filter.AudioFilter;
import com.jiajunhui.xapp.medialoader.filter.VideoFilter;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.ScanAudioData;
import com.kk.taurus.xfolder.filter.TextFileFilter;
import com.kk.taurus.xfolder.ui.activity.ScanAudioActivity;
import com.kk.taurus.xfolder.ui.activity.ScanTextActivity;
import com.kk.taurus.xfolder.ui.activity.ScanVideoActivity;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/8.
 */

public class IntentUtils {

    public static Intent getIntent(Context context, List<BaseItem> items, FileItem item, int position){
        Intent intent = null;
        if(new TextFileFilter().accept(new File(item.getPath()))){
            intent = new Intent(context,ScanTextActivity.class);
            intent.putExtra(ScanTextActivity.KEY_SCAN_TEXT_DATA,item);
        }else if(new AudioFilter().accept(new File(item.getPath()))){
            ScanAudioData data = new ScanAudioData();
            MAudioItem audioItem = new MAudioItem();
            audioItem.setPath(item.getPath());
            audioItem.setDisplayName(item.getName());
            data.setAudioItem(audioItem);
            intent = new Intent(context,ScanAudioActivity.class);
            intent.putExtra(ScanAudioActivity.KEY_AUDIO_DATA,data);
        }else if(new VideoFilter().accept(new File(item.getPath()))){
            intent = new Intent(context,ScanVideoActivity.class);
            intent.putExtra(ScanVideoActivity.KEY_SCAN_VIDEO_DATA,item);
        }
        return intent;
    }

}
