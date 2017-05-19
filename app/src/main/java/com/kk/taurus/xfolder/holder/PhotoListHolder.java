package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.support.v4.view.ViewPager;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.PhotoListData;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListHolder extends ContentHolder<PhotoListData> {

    private ViewPager mViewPager;

    public PhotoListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_photo_list);
        mViewPager = getViewById(R.id.viewPager);
    }

}
