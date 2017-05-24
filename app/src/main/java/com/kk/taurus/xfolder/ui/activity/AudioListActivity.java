package com.kk.taurus.xfolder.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.AudioListData;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.ScanAudioData;
import com.kk.taurus.xfolder.config.ThumbnailCache;
import com.kk.taurus.xfolder.holder.AudioListHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListActivity extends ToolBarActivity<AudioListData,AudioListHolder> implements AudioListHolder.OnAudioListListener {

    private ThumbnailCache thumbnailCache;
    private AsyncTask mTask;

    @Override
    public AudioListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new AudioListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        thumbnailCache = new ThumbnailCache(this);
        mContentHolder.setOnAudioListListener(this);
        setToolBarTitle("音频");
    }

    @Override
    public void loadState() {
        setPageState(PageState.loading());
        MediaLoader.getLoader().loadAudios(this, new OnAudioLoaderCallBack() {
            @Override
            public void onResult(AudioResult result) {
                AudioListData data = new AudioListData();
                data.setAudioItems(AudioListData.trans(thumbnailCache,result.getItems()));
                setData(data);
                setPageState(PageState.success());
                mTask = thumbnailCache.generatorAudioCover(data.getAudioItems(), new ThumbnailCache.OnAudioCoverListener() {
                    @Override
                    public void onCoverFinish() {
                        mContentHolder.notifyDataChange();
                    }
                });
            }
        });
    }

    @Override
    public void onItemClick(MAudioItem item) {
        ScanAudioData data = new ScanAudioData();
        data.setAudioItem(item);
        Bundle bundle = new Bundle();
        bundle.putSerializable(ScanAudioActivity.KEY_AUDIO_DATA,data);
        intentTo(ScanAudioActivity.class,bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTask!=null){
            mTask.cancel(true);
        }
    }
}
