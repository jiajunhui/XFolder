package com.kk.taurus.xfolder.ui.fragment;

import android.os.Bundle;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.EventUpdatePhotoList;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.bean.ScanImageData;
import com.kk.taurus.xfolder.holder.FragHolderPhotoList;
import com.kk.taurus.xfolder.ui.activity.ScanImagesActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListFragment extends BasePhotoFragment<PhotoListData,FragHolderPhotoList> implements FragHolderPhotoList.OnPhotoListListener {

    @Override
    public FragHolderPhotoList getContentViewHolder(Bundle savedInstanceState) {
        return new FragHolderPhotoList(mContext);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mContentHolder.setOnPhotoListListener(this);
        EventBus.getDefault().register(this);
    }

    public static PhotoListFragment getInstance(){
        PhotoListFragment f = new PhotoListFragment();
        return f;
    }

    @Subscribe
    public void onEventUpdate(EventUpdatePhotoList event){
        if(event!=null){
            PhotoListData data = new PhotoListData();
            data.setPhotoItems(event.getItems());
            setData(data);
        }
    }

    @Override
    public void loadState() {
        PhotoListData data = PhotoDataHelper.getPhotoListData();
        if(data!=null){
            setData(data);
            setPageState(PageState.success());
        }
    }

    @Override
    public String getPageTitle() {
        return getString(R.string.photo_list_default_page_title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(List<PhotoItem> items, int position) {
        ScanImageData data = new ScanImageData();
        List<PhotoItem> photoItems = new ArrayList<>();
        photoItems.addAll(items);
        data.setPhotoItems(photoItems);
        data.setCurrPosition(position);
        PhotoDataHelper.putScanImageData(data);
        intentTo(ScanImagesActivity.class);
    }
}
