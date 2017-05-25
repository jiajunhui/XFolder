package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.AudioResult;
import com.jiajunhui.xapp.medialoader.bean.FileItem;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.bean.FileType;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.bean.PhotoResult;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnFileLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.filebase.engine.StorageEngine;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.FileListData;
import com.kk.taurus.xfolder.bean.MainHolderData;
import com.kk.taurus.xfolder.bean.MediaEntity;
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
        setToolBarTitle("XFolder");
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
            case R.id.main_search:
                intentTo(SearchActivity.class);
                break;
            case R.id.setting:
                intentTo(SettingActivity.class);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void loadState() {
        setPageState(PageState.loading());

        final MainHolderData data = new MainHolderData();
        final MediaEntity entity = new MediaEntity();
        data.setMediaEntity(entity);
        List<Storage> storageList = StorageEngine.getStorageList(getApplicationContext());
        data.setStorageList(storageList);
        setData(data);
        setPageState(PageState.success());

        MediaLoader.getLoader().loadPhotos(this, new OnPhotoLoaderCallBack() {
            @Override
            public void onResult(PhotoResult result) {
                entity.setPhotoResult(result);
                setData(data);
            }
        });

        MediaLoader.getLoader().loadVideos(this, new OnVideoLoaderCallBack() {
            @Override
            public void onResult(VideoResult result) {
                entity.setVideoResult(result);
                setData(data);
            }
        });

        MediaLoader.getLoader().loadAudios(this, new OnAudioLoaderCallBack() {
            @Override
            public void onResult(AudioResult result) {
                entity.setAudioResult(result);
                setData(data);
            }
        });

        MediaLoader.getLoader().loadFiles(this, new OnFileLoaderCallBack(FileType.APK) {
            @Override
            public void onResult(FileResult result) {
                entity.setApkResult(result);
                setData(data);
            }
        });

        MediaLoader.getLoader().loadFiles(this, new OnFileLoaderCallBack(FileType.ZIP) {
            @Override
            public void onResult(FileResult result) {
                entity.setZipResult(result);
                setData(data);
            }
        });

        MediaLoader.getLoader().loadFiles(this, new OnFileLoaderCallBack(FileType.DOC) {
            @Override
            public void onResult(FileResult result) {
                entity.setDocResult(result);
                setData(data);
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
        intentTo(VideoListActivity.class);
    }

    @Override
    public void intentToAudioList(List<AudioItem> audioItems) {
        intentTo(AudioListActivity.class);
    }

    @Override
    public void intentToPhotoList(List<PhotoItem> photoItems) {
        intentTo(PhotoListActivity.class);
    }

    @Override
    public void intentToApkList(List<FileItem> items) {
        FileListData data = new FileListData();
        data.setTitle("安装包");
        data.setType(FileType.APK);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FileListActivity.KEY_FILE_LIST_DATA,data);
        intentTo(FileListActivity.class,bundle);
    }

    @Override
    public void intentToZipList(List<FileItem> items) {
        FileListData data = new FileListData();
        data.setTitle("压缩包");
        data.setType(FileType.ZIP);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FileListActivity.KEY_FILE_LIST_DATA,data);
        intentTo(FileListActivity.class,bundle);
    }

    @Override
    public void intentToDocList(List<FileItem> items) {
        FileListData data = new FileListData();
        data.setTitle("文档");
        data.setType(FileType.DOC);
        Bundle bundle = new Bundle();
        bundle.putSerializable(FileListActivity.KEY_FILE_LIST_DATA,data);
        intentTo(FileListActivity.class,bundle);
    }
}
