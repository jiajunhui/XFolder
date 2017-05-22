package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.baseframe.manager.SharedPrefer;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.config.SettingConfig;

/**
 * Created by Taurus on 2017/5/19.
 */

public class SettingHolder extends ContentHolder<HolderData> {

    public static final String KEY_VIDEO_ASPECT = "video_aspect";
    public static final String VIDEO_ASPECT_VALUE_FILL = "video_aspect_fill";
    public static final String VIDEO_ASPECT_VALUE_16_9 = "video_aspect_16_9";
    public static final String VIDEO_ASPECT_VALUE_4_3 = "video_aspect_4_3";
    public static final String VIDEO_ASPECT_VALUE_WRAP = "video_aspect_wrap";

    private SwitchCompat mSwitchCompat;
    private RadioGroup mRadioGroupVideoAspect;

    public SettingHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_setting);
        mSwitchCompat = getViewById(R.id.switch_hidden_file);
        mRadioGroupVideoAspect = getViewById(R.id.rg_video_aspect);
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
        String aspect = SharedPrefer.getInstance().getString(mContext,KEY_VIDEO_ASPECT,VIDEO_ASPECT_VALUE_FILL);
        settingConfig(aspect);
        mRadioGroupVideoAspect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                setRadioButtonCheck(checkedId,true,getAspect(checkedId));
            }
        });
    }

    private void setRadioButtonCheck(int id, boolean checked, String value){
        RadioButton button = getViewById(id);
        button.setChecked(checked);
        if(checked){
            SharedPrefer.getInstance().saveString(mContext,KEY_VIDEO_ASPECT,value);
        }
    }

    private void settingConfig(String aspect){
        switch (aspect){
            case VIDEO_ASPECT_VALUE_FILL:
                setRadioButtonCheck(R.id.rb_aspect_fill,true,VIDEO_ASPECT_VALUE_FILL);
                break;
            case VIDEO_ASPECT_VALUE_16_9:
                setRadioButtonCheck(R.id.rb_aspect_16_9,true,VIDEO_ASPECT_VALUE_16_9);
                break;
            case VIDEO_ASPECT_VALUE_4_3:
                setRadioButtonCheck(R.id.rb_aspect_4_3,true,VIDEO_ASPECT_VALUE_4_3);
                break;
            case VIDEO_ASPECT_VALUE_WRAP:
                setRadioButtonCheck(R.id.rb_aspect_wrap,true,VIDEO_ASPECT_VALUE_WRAP);
                break;
        }
    }

    private String getAspect(int checkedId){
        switch (checkedId){
            case R.id.rb_aspect_fill:
                return VIDEO_ASPECT_VALUE_FILL;
            case R.id.rb_aspect_16_9:
                return VIDEO_ASPECT_VALUE_16_9;
            case R.id.rb_aspect_4_3:
                return VIDEO_ASPECT_VALUE_4_3;
            case R.id.rb_aspect_wrap:
                return VIDEO_ASPECT_VALUE_WRAP;
            default:
                return VIDEO_ASPECT_VALUE_FILL;
        }
    }
}
