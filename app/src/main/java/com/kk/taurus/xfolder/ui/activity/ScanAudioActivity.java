package com.kk.taurus.xfolder.ui.activity;

import android.media.AudioManager;
import android.os.Bundle;

import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.ScanAudioData;
import com.kk.taurus.xfolder.holder.ScanAudioHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class ScanAudioActivity extends BaseProjectActivity<ScanAudioData,ScanAudioHolder> {

    public static final String KEY_AUDIO_DATA = "audio_data";

    @Override
    public ScanAudioHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ScanAudioHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.scan_audio_colorPrimary));
        setElevation(0f);
    }

    @Override
    public void loadState() {
        ScanAudioData data = (ScanAudioData) getIntent().getSerializableExtra(KEY_AUDIO_DATA);
        if(data!=null){
            setData(data);
            setPageState(PageState.success());
        }
    }

    @Override
    public void setData(ScanAudioData data) {
        super.setData(data);
        MAudioItem item = data.getAudioItem();
        setCenterTitle(item.getDisplayName());
    }
}
