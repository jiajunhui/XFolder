package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.media.AudioManager;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.playerbase.player.AndroidMediaPlayer;
import com.kk.taurus.playerbase.player.IMediaPlayer;
import com.kk.taurus.playerbase.setting.VideoData;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.ScanAudioData;
import com.kk.taurus.xfolder.config.ThumbnailCache;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.List;

import ak.sh.ay.musicwave.MusicWave;

/**
 * Created by Taurus on 2017/5/19.
 */

public class ScanAudioHolder extends ContentHolder<ScanAudioData> {

    private MusicWave mMusicWave;
    private ImageView mCover;
    private ImageView mStateView;
    private AndroidMediaPlayer mPlayer;

    private Visualizer mVisualizer;

    private final int STATE_PAUSE = 1;
    private final int STATE_START = 2;
    private int mState = STATE_START;

    private ThumbnailCache thumbnailCache;

    public ScanAudioHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_scan_audio);

        thumbnailCache = new ThumbnailCache(mContext);

        mMusicWave = getViewById(R.id.musicWave);
        mStateView = getViewById(R.id.iv_state);
        mCover = getViewById(R.id.iv_cover);
        mStateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mPlayer!=null){
                    if(mState==STATE_START){
                        setState(STATE_PAUSE);
                        mPlayer.pause();
                    }else{
                        setState(STATE_START);
                        mPlayer.start();
                    }
                }
            }
        });
    }

    private void setState(int state){
        mState = state;
        mStateView.setImageResource(state==STATE_PAUSE?R.mipmap.icon_audio_start:R.mipmap.icon_audio_pause);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mPlayer = new AndroidMediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        mPlayer.setAudioStreamType(AudioManager.AUDIO_SESSION_ID_GENERATE);
        updateVisualizer();
        mPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mPlayer.start();
            }
        });

        mPlayer.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {

                return false;
            }
        });
    }

    @Override
    public void refreshView() {
        super.refreshView();
        MAudioItem item = mData.getAudioItem();
        VideoData data = new VideoData(item.getPath());
        data.setName(item.getDisplayName());
        loadAudioCover(item);
        try {
            mPlayer.setDataSource(data.getData());
            mPlayer.prepareAsync();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadAudioCover(final MAudioItem item) {

        if(!TextUtils.isEmpty(item.getAudioCover())){
            ImageDisplayEngine.display(mContext,mCover,item.getAudioCover(),R.mipmap.icon_scan_audio);
        }else{
            if(TextUtils.isEmpty(item.getPath()))
                return;
            MediaLoader.getLoader().loadAudios((FragmentActivity) getActivity(), new OnAudioLoaderCallBack() {
                @Override
                public String getSelections() {
                    return MediaStore.Audio.AudioColumns.DATA + " = ? ";
                }

                @Override
                public String[] getSelectionsArgs() {
                    return new String[]{item.getPath()};
                }

                @Override
                public void onResult(AudioResult result) {
                    List<AudioItem> items = result.getItems();
                    if(items.size()>0){
                        AudioItem audioItem = items.get(0);
                        String cache = thumbnailCache.getAudioCover(audioItem.getPath());
                        ImageDisplayEngine.display(mContext,mCover,cache,R.mipmap.icon_scan_audio);
                    }
                }
            });
        }

    }

    private void updateVisualizer() {
        int audioSessionId = mPlayer.getAudioSessionId();
        mVisualizer = new Visualizer(audioSessionId);
        int size = Visualizer.getCaptureSizeRange()[1];
        mVisualizer.setCaptureSize(size);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        mMusicWave.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);
        mVisualizer.setEnabled(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPlayer!=null){
            mPlayer.release();
        }
        if(mVisualizer!=null){
            mVisualizer.setEnabled(false);
        }
    }
}
