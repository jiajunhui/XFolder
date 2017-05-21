package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.ScanAudioData;
import com.kk.taurus.xfolder.holder.ScanAudioHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class ScanAudioActivity extends ToolBarActivity<ScanAudioData,ScanAudioHolder> {

    public static final String KEY_AUDIO_DATA = "audio_data";

    @Override
    public ScanAudioHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ScanAudioHolder(this);
    }

    @Override
    public void loadState() {
        ScanAudioData data = (ScanAudioData) getIntent().getSerializableExtra(KEY_AUDIO_DATA);
        if(data!=null){
            setData(data);
            setPageState(PageState.success());
        }
    }
}
