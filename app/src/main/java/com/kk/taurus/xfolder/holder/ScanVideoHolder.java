package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.content.res.Configuration;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.manager.SharedPrefer;
import com.kk.taurus.filebase.tools.BytesTool;
import com.kk.taurus.playerbase.DefaultPlayer;
import com.kk.taurus.playerbase.callback.OnPlayerEventListener;
import com.kk.taurus.playerbase.cover.DefaultReceiverCollections;
import com.kk.taurus.playerbase.cover.base.BasePlayerControllerCover;
import com.kk.taurus.playerbase.setting.AspectRatio;
import com.kk.taurus.playerbase.setting.VideoData;
import com.kk.taurus.playerbase.utils.TimeUtil;
import com.kk.taurus.playerbase.widget.BasePlayer;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.ScanVideoData;
import com.kk.taurus.xfolder.cover.PlayerControllerCover;

import ak.sh.ay.musicwave.MusicWave;

/**
 * Created by Taurus on 2017/5/18.
 */

public class ScanVideoHolder extends ContentHolder<ScanVideoData> implements OnPlayerEventListener {

    private RelativeLayout mContainer;
    private MusicWave mWave;
    private TextView mTvInfo;
    private Visualizer mVisualizer;
    private BasePlayer mPlayer;
    private int mHeight;

    private OnScanVideoListener onScanVideoListener;

    public void setOnScanVideoListener(OnScanVideoListener onScanVideoListener) {
        this.onScanVideoListener = onScanVideoListener;
    }

    public ScanVideoHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_scan_video);
        mContainer = getViewById(R.id.container);
        mWave = getViewById(R.id.musicWave);
        mTvInfo = getViewById(R.id.tv_info);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mPlayer = new DefaultPlayer(mContext);
        mPlayer.setOnPlayerEventListener(this);
        String configAspect = SharedPrefer.getInstance().getString(mContext,SettingHolder.KEY_VIDEO_ASPECT,SettingHolder.VIDEO_ASPECT_VALUE_FILL);
        mPlayer.setAspectRatio(getAspect(configAspect));
        DefaultReceiverCollections receiverCollections = new DefaultReceiverCollections(mContext);
        PlayerControllerCover playerControllerCover = new PlayerControllerCover(mContext);
        receiverCollections.buildDefault().addCover(BasePlayerControllerCover.KEY,playerControllerCover);
        playerControllerCover.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.setScreenOrientationLandscape(false);
            }
        });
        mPlayer.bindCoverCollections(receiverCollections);
        int w = getScreenWidth();
        int h = w * 9/16;
        mContainer.addView(mPlayer,new ViewGroup.LayoutParams(-1, -1));
        ViewGroup.LayoutParams params = mContainer.getLayoutParams();
        params.height = h;
        mContainer.setLayoutParams(params);
        mHeight = h;
    }

    public void screenPortrait(){
        mPlayer.setScreenOrientationLandscape(false);
    }

    @Override
    public void refreshView() {
        super.refreshView();
        FileItem item = mData.getItem();
        VideoData data = new VideoData(item.getPath());
        data.setName(item.getName());
        mPlayer.setDataSource(data);
        mPlayer.start();
        updateVideoInfo();
    }

    private void updateVideoInfo(){
        StringBuilder sb = new StringBuilder("");
        if(mData!=null){
            FileItem item = mData.getItem();
            sb.append("文件：" + item.getPath()).append("\n");
            sb.append("大小：" + BytesTool.formatBytes(item.getSize())).append("\n");
            sb.append("时长：" + TimeUtil.getTime(mPlayer.getDuration()));
        }
        mTvInfo.setText(sb.toString());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            ViewGroup.LayoutParams params = mContainer.getLayoutParams();
            params.width = -1;
            params.height = mHeight;
            mContainer.setLayoutParams(params);
        }else{
            ViewGroup.LayoutParams layoutParams = mContainer.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            mContainer.setLayoutParams(layoutParams);
        }
        if(mPlayer!=null){
            mPlayer.onNotifyConfigurationChanged(newConfig);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mPlayer!=null){
            mPlayer.pause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPlayer!=null){
            mPlayer.resume();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer!=null){
            mPlayer.destroy();
        }
    }

    @Override
    public void onPlayerEvent(int eventCode, Bundle bundle) {
        switch (eventCode){
            case OnPlayerEventListener.EVENT_CODE_RENDER_START:
                updateVideoInfo();
                updateVisualizer();
                break;
        }
    }

    private AspectRatio getAspect(String configAspect){
        switch (configAspect){
            case SettingHolder.VIDEO_ASPECT_VALUE_FILL:
                return AspectRatio.AspectRatio_FILL_PARENT;
            case SettingHolder.VIDEO_ASPECT_VALUE_16_9:
                return AspectRatio.AspectRatio_16_9;
            case SettingHolder.VIDEO_ASPECT_VALUE_4_3:
                return AspectRatio.AspectRatio_4_3;
            case SettingHolder.VIDEO_ASPECT_VALUE_WRAP:
                return AspectRatio.AspectRatio_ORIGIN;
            default:
                return AspectRatio.AspectRatio_FILL_PARENT;
        }
    }

    private void updateVisualizer() {
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        mWave.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }

    public interface OnScanVideoListener{

    }
}
