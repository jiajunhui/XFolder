package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.loader.MediaLoader;
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
        MediaLoader.loadVideos(this, new OnVideoLoaderCallBack() {
            @Override
            public void onResultList(List<VideoItem> items) {
                VideoListData data = new VideoListData();
                List<MVideoItem> videoItems = VideoListData.trans(thumbnailCache,items);
                data.setVideoItems(videoItems);
                setData(data);
                setPageState(PageState.success());
                thumbnailCache.generatorThumbnail(videoItems, new ThumbnailCache.OnVideoThumbnailListener() {
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
}
