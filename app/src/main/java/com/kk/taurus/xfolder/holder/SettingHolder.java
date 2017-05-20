package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.baseframe.manager.SharedPrefer;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.config.SettingConfig;

/**
 * Created by Taurus on 2017/5/19.
 */

public class SettingHolder extends ContentHolder<HolderData> {

    private SwitchCompat mSwitchCompat;

    public SettingHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_setting);
        mSwitchCompat = getViewById(R.id.switch_hidden_file);
        mSwitchCompat.setChecked(SettingConfig.isShowHiddenFiles(mContext));
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mSwitchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPrefer.getInstance().saveBoolean(mContext, SettingConfig.KEY_IS_SHOW_HIDDEN_FILES,compoundButton.isChecked());
            }
        });
    }
}
