package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.jiajunhui.xapp.medialoader.loader.MediaLoader;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.AudioListData;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.ScanAudioData;
import com.kk.taurus.xfolder.config.ThumbnailCache;
import com.kk.taurus.xfolder.holder.AudioListHolder;

import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListActivity extends ToolBarActivity<AudioListData,AudioListHolder> implements AudioListHolder.OnAudioListListener {

    private ThumbnailCache thumbnailCache;

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
        MediaLoader.loadAudios(this, new OnAudioLoaderCallBack() {
            @Override
            public void onResultList(List<AudioItem> items) {
                AudioListData data = new AudioListData();
                data.setAudioItems(AudioListData.trans(thumbnailCache,items));
                setData(data);
                setPageState(PageState.success());
                thumbnailCache.generatorAudioCover(data.getAudioItems(), new ThumbnailCache.OnAudioCoverListener() {
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
}
