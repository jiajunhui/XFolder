package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoFolderLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.loader.MediaLoader;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.PhotoData;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoFolderData;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.holder.PhotoListHolder;

import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListActivity extends ToolBarActivity<PhotoData,PhotoListHolder> {

    @Override
    public PhotoListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new PhotoListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setToolBarTitle("图片");
    }

    @Override
    public void loadState() {
        setPageState(PageState.loading());
        final PhotoData data = new PhotoData();

        MediaLoader.loadPhotos(PhotoListActivity.this, new OnPhotoLoaderCallBack() {
            @Override
            public void onResultList(List<PhotoItem> items) {
                PhotoListData listData = new PhotoListData();
                listData.setPhotoItems(items);
                data.setListData(listData);
                MediaLoader.loadPhotoFolders(PhotoListActivity.this, new OnPhotoFolderLoaderCallBack() {
                    @Override
                    public void onResultFolders(List<PhotoFolder> folders) {
                        PhotoFolderData folderData = new PhotoFolderData();
                        folders.get(0).setName("所有图片");
                        folderData.setFolders(folders);
                        data.setFolderData(folderData);
                        setData(data);
                        setPageState(PageState.success());
                    }
                });
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoDataHelper.clear();
    }
}
