package com.kk.taurus.xfolder.holder;

import android.content.Context;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.VideoListData;

/**
 * Created by Taurus on 2017/5/19.
 */

public class VideoListHolder extends ContentHolder<VideoListData> {

    public VideoListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_video_list);
    }

}
