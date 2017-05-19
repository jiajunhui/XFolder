package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.VideoListData;
import com.kk.taurus.xfolder.holder.VideoListHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class VideoListActivity extends ToolBarActivity<VideoListData,VideoListHolder> {

    public static final String KEY_VIDEO_LIST_DATA = "video_list_data";

    @Override
    public VideoListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new VideoListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setToolBarTitle("视频");
    }
}
