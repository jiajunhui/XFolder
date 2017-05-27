package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.xfolder.holder.SettingHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class SettingActivity extends BaseProjectActivity<HolderData,SettingHolder> {
    @Override
    public SettingHolder getContentViewHolder(Bundle savedInstanceState) {
        return new SettingHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setToolBarTitle("设置");
    }
}
