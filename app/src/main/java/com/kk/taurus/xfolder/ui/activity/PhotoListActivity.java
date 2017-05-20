package com.kk.taurus.xfolder.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoFolderLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.loader.MediaLoader;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.EventUpdatePhotoList;
import com.kk.taurus.xfolder.bean.PhotoData;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoFolderData;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.holder.PhotoListHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

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
        EventBus.getDefault().register(this);
        setCenterTitle("图片");
        setArrowDown(true);
        getCenterTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean down = mContentHolder.togglePage();
                setArrowDown(down);
            }
        });
    }

    private void setArrowDown(boolean down){
        int resId = down ? R.mipmap.icon_arrow_down:R.mipmap.icon_arrow_up;
        Drawable nav_up = getResources().getDrawable(resId);
        nav_up.setBounds(0, 0, 48, 48);
        getCenterTitle().setCompoundDrawablePadding(10);
        getCenterTitle().setCompoundDrawables(null, null, nav_up, null);
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
    public void setData(PhotoData data) {
        super.setData(data);
        PhotoFolderData folderData = data.getFolderData();
        if(folderData.getFolders().size()>0){
            setCenterTitle(folderData.getFolders().get(0).getName());
        }
    }

    @Subscribe
    public void onEventUpdate(EventUpdatePhotoList event){
        if(event!=null){
            setCenterTitle(event.getFolderName());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        PhotoDataHelper.clear();
    }
}
