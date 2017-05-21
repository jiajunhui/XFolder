package com.kk.taurus.xfolder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.kk.taurus.xfolder.ui.fragment.BasePhotoFragment;

import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoPagerAdapter extends FragmentStatePagerAdapter {

    private List<BasePhotoFragment> mFragments;
    private String[] titles = new String[]{"所有图片","相册"};

    public PhotoPagerAdapter(FragmentManager fm, List<BasePhotoFragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if(mFragments!=null && position < mFragments.size())
            return mFragments.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if(mFragments!=null)
            return mFragments.size();
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
