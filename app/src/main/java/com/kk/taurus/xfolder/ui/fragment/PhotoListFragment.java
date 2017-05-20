package com.kk.taurus.xfolder.ui.fragment;

import android.os.Bundle;

import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.fragment.StateFragment;
import com.kk.taurus.xfolder.bean.EventUpdatePhotoList;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.holder.FragHolderPhotoList;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListFragment extends StateFragment<PhotoListData,FragHolderPhotoList> {

    @Override
    public FragHolderPhotoList getContentViewHolder(Bundle savedInstanceState) {
        return new FragHolderPhotoList(mContext);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
