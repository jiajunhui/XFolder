package com.kk.taurus.xfolder.holder;

import android.content.Context;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.xfolder.R;

/**
 * Created by Taurus on 2017/5/19.
 */

public class SettingHolder extends ContentHolder<HolderData> {

    public SettingHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_setting);
    }
}
