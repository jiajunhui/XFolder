package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.PhotoPagerAdapter;
import com.kk.taurus.xfolder.bean.EventUpdatePhotoList;
import com.kk.taurus.xfolder.bean.PhotoData;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.ui.fragment.BasePhotoFragment;
import com.kk.taurus.xfolder.ui.fragment.PhotoFolderListFragment;
import com.kk.taurus.xfolder.ui.fragment.PhotoListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListHolder extends ContentHolder<PhotoData> {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PhotoPagerAdapter mAdapter;
    private List<BasePhotoFragment> mFragments = new ArrayList<>();

    public PhotoListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_photo_list);
        mViewPager = getViewById(R.id.viewPager);
        mTabLayout = getViewById(R.id.tabLayout);
    }

    @Subscribe
    public void onEventUpdate(EventUpdatePhotoList event){
        togglePage();
        updatePhotoListTitle(event);
    }

    private void updatePhotoListTitle(EventUpdatePhotoList event) {
        if(mViewPager.getCurrentItem()==0){
            updateListTitle(event.getFolderName() + "(" + event.getItems().size() + ")");
        }
    }

    private void updateListTitle(String title){
        TabLayout.Tab tab = mTabLayout.getTabAt(0);
        tab.setText(title);
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
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        PhotoListData listData = PhotoDataHelper.getPhotoListData();
        EventUpdatePhotoList event  = new EventUpdatePhotoList();
        event.setFolderName(getString(R.string.photo_list_default_page_title));
        event.setItems(listData.getPhotoItems());
        updatePhotoListTitle(event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
