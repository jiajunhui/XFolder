package com.kk.taurus.xfolder.cover;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.kk.taurus.playerbase.callback.OnPlayerEventListener;
import com.kk.taurus.playerbase.cover.DefaultPlayerControllerCover;
import com.kk.taurus.playerbase.setting.VideoData;
import com.kk.taurus.xfolder.R;

/**
 * Created by Taurus on 2017/5/18.
 */

public class PlayerControllerCover extends DefaultPlayerControllerCover {

    private ImageView mIvShowType;
    private int mOrientation = Configuration.ORIENTATION_PORTRAIT;

    public PlayerControllerCover(Context context) {
        super(context);
    }

    @Override
    protected void findView() {
        super.findView();
        mIvShowType = findViewById(R.id.iv_show_type);
        mIvShowType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOrientation == Configuration.ORIENTATION_PORTRAIT){
                    getPlayer().setScreenOrientationLandscape(true);
                }else{
                    getPlayer().setScreenOrientationLandscape(false);
                }
            }
        });
    }

    private void updateScreenShowType(){
        mIvShowType.setImageResource(mOrientation==Configuration.ORIENTATION_PORTRAIT?R.mipmap.icon_full_screen:R.mipmap.icon_exit_full_screen);
    }

    @Override
    public void onNotifyConfigurationChanged(Configuration newConfig) {
        super.onNotifyConfigurationChanged(newConfig);
        mOrientation = newConfig.orientation;
        if(mOrientation==Configuration.ORIENTATION_PORTRAIT){
            setTopContainerState(false);
        }
        updateScreenShowType();
    }

    @Override
    protected void switchControllerState() {
        if(isVisibilityGone()){
            setControllerState(true);
            if(mOrientation == Configuration.ORIENTATION_PORTRAIT){
                setTopContainerState(false);
            }else{
                setTopContainerState(true);
            }
            setBottomContainerState(true);
        }else{
            setControllerState(false);
        }
    }

    @Override
    public void onNotifyPlayEvent(int eventCode, Bundle bundle) {
        super.onNotifyPlayEvent(eventCode, bundle);
        switch (eventCode){
            case OnPlayerEventListener.EVENT_CODE_PLAYER_ON_SET_VIDEO_DATA:
                VideoData data = (VideoData) bundle.getSerializable(OnPlayerEventListener.BUNDLE_KEY_VIDEO_DATA);
                if(data!=null){
                    setVideoTitle(data.getName());
                }
                break;
        }
    }

    @Override
    public View initCoverLayout(Context context) {
        return View.inflate(context, R.layout.layout_player_controller_cover,null);
    }
}
