package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.filebase.engine.StorageEngine;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.AudioListData;
import com.kk.taurus.xfolder.bean.MainHolderData;
import com.kk.taurus.xfolder.bean.MediaEntity;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.bean.VideoListData;
import com.kk.taurus.xfolder.callback.OnMediaLoadListener;
import com.kk.taurus.xfolder.engine.MediaManager;
import com.kk.taurus.xfolder.holder.MainHolder;

import java.util.List;

public class MainActivity extends ToolBarActivity<MainHolderData,MainHolder> implements MainHolder.OnMainListener {

    @Override
    public MainHolder getContentViewHolder(Bundle savedInstanceState) {
        return new MainHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setNavigationIcon(null);
        setCenterTitle("XFolder");
        mContentHolder.setOnMainListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.setting:
                intentTo(SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadState() {
        setPageState(PageState.loading());
        MediaManager.loadMedia(this, new OnMediaLoadListener() {
            @Override
            public void onLoadFinish(MediaEntity mediaEntity) {
                MainHolderData data = new MainHolderData();
                data.setMediaEntity(mediaEntity);
                List<Storage> storageList = StorageEngine.getStorageList(getApplicationContext());
                data.setStorageList(storageList);
                setData(data);
                setPageState(PageState.success());
            }
        });
    }

    @Override
    public void intentToExplorer(Storage storage) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ExplorerActivity.KEY_STORAGE_DATA,storage);
        intentTo(ExplorerActivity.class,bundle);
    }

    @Override
    public void intentToVideoList(List<VideoItem> videoItems) {
        VideoListData data = new VideoListData();
        data.setVideoItems(videoItems);
        Bundle bundle = new Bundle();
        bundle.putSerializable(VideoListActivity.KEY_VIDEO_LIST_DATA,data);
        intentTo(VideoListActivity.class,bundle);
    }

    @Override
    public void intentToAudioList(List<AudioItem> audioItems) {
        AudioListData data = new AudioListData();
        data.setAudioItems(audioItems);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MusicListActivity.KEY_AUDIO_LIST_DATA,data);
        intentTo(MusicListActivity.class,bundle);
    }

    @Override
    public void intentToPhotoList(List<PhotoItem> photoItems) {
        PhotoListData data = new PhotoListData();
        data.setPhotoItems(photoItems);
        Bundle bundle = new Bundle();
        bundle.putSerializable(PhotoListActivity.KEY_PHOTO_LIST_DATA,data);
        intentTo(PhotoListActivity.class,bundle);
    }
}
