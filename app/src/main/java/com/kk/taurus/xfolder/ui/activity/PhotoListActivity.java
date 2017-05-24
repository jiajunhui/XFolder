package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;
import com.jiajunhui.xapp.medialoader.bean.PhotoResult;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.PhotoData;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoFolderData;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.holder.PhotoListHolder;

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

        MediaLoader.getLoader().loadPhotos(PhotoListActivity.this, new OnPhotoLoaderCallBack() {
            @Override
            public void onResult(PhotoResult result) {
                PhotoListData listData = new PhotoListData();
                listData.setPhotoItems(result.getItems());
                data.setListData(listData);

                PhotoFolderData folderData = new PhotoFolderData();

                PhotoFolder folder = new PhotoFolder();
                folder.setName("所有图片");
                folder.setItems(result.getItems());

                result.getFolders().add(0,folder);

                folderData.setFolders(result.getFolders());
                data.setFolderData(folderData);
                setData(data);
                setPageState(PageState.success());
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        PhotoDataHelper.clear();
    }
}
