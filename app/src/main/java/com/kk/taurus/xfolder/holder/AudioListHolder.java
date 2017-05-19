package com.kk.taurus.xfolder.holder;

import android.content.Context;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.AudioListData;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListHolder extends ContentHolder<AudioListData> {

    public AudioListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_audio_list);
    }

}
