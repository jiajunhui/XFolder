package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.PhotoPagerAdapter;
import com.kk.taurus.xfolder.bean.EventUpdatePhotoList;
import com.kk.taurus.xfolder.bean.PhotoData;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.ui.fragment.PhotoFolderListFragment;
import com.kk.taurus.xfolder.ui.fragment.PhotoListFragment;
import com.kk.taurus.xfolder.widget.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListHolder extends ContentHolder<PhotoData> {

    private NoScrollViewPager mViewPager;
    private PhotoPagerAdapter mAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    public PhotoListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_photo_list);
        mViewPager = getViewById(R.id.viewPager);
        mViewPager.setPagingEnabled(false);
    }

    @Subscribe
    public void onEventUpdate(EventUpdatePhotoList event){
        togglePage();
    }

    public boolean togglePage(){
        if(mViewPager.getCurrentItem()==0){
            mViewPager.setCurrentItem(1);
            return false;
        }else{
            mViewPager.setCurrentItem(0);
            return true;
        }
    }

    @Override
    public void refreshView() {
        super.refreshView();
        PhotoDataHelper.putPhotoFolderData(mData.getFolderData());
        PhotoDataHelper.putPhotoListData(mData.getListData());
        mFragments.clear();
        mFragments.add(PhotoListFragment.getInstance());
        mFragments.add(PhotoFolderListFragment.getInstance());
        mAdapter = new PhotoPagerAdapter(((FragmentActivity)mContext).getSupportFragmentManager(),mFragments);
        mViewPager.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
