package com.kk.taurus.xfolder.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.VideoResult;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.MVideoItem;
import com.kk.taurus.xfolder.bean.VideoListData;
import com.kk.taurus.xfolder.config.ThumbnailCache;
import com.kk.taurus.xfolder.holder.VideoListHolder;

import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class VideoListActivity extends ToolBarActivity<VideoListData,VideoListHolder> implements VideoListHolder.OnVideoListListener {

    private ThumbnailCache thumbnailCache;
    private AsyncTask mTask;

    @Override
    public VideoListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new VideoListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        thumbnailCache = new ThumbnailCache(this);
        setToolBarTitle("视频");
        mContentHolder.setOnVideoListListener(this);
    }

    @Override
    public void loadState() {
        setPageState(PageState.loading());
        MediaLoader.getLoader().loadVideos(this, new OnVideoLoaderCallBack() {
            @Override
            public void onResult(VideoResult result) {
                VideoListData data = new VideoListData();
                List<MVideoItem> videoItems = VideoListData.trans(thumbnailCache,result.getItems());
                data.setVideoItems(videoItems);
                setData(data);
                setPageState(PageState.success());
                mTask = thumbnailCache.generatorThumbnail(videoItems, new ThumbnailCache.OnVideoThumbnailListener() {
                    @Override
                    public void onThumbnailFinish() {
                        mContentHolder.notifyDataChange();
                    }
                });
            }
        });
    }

    @Override
    public void onIntentToPlayer(MVideoItem item, int position) {
        FileItem fileItem = new FileItem();
        fileItem.setPath(item.getPath());
        fileItem.setName(item.getDisplayName());
        fileItem.setSize(item.getSize());
        Bundle bundle = new Bundle();
        bundle.putSerializable(ScanVideoActivity.KEY_SCAN_VIDEO_DATA,fileItem);
        intentTo(ScanVideoActivity.class,bundle);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTask!=null){
            mTask.cancel(true);
        }
    }
}
