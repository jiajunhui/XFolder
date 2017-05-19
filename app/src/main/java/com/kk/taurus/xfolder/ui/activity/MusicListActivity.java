package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.AudioListData;
import com.kk.taurus.xfolder.holder.AudioListHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class MusicListActivity extends ToolBarActivity<AudioListData,AudioListHolder> {

    public static final String KEY_AUDIO_LIST_DATA = "audio_list_data";

    @Override
    public AudioListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new AudioListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setToolBarTitle("音频");
    }
}
