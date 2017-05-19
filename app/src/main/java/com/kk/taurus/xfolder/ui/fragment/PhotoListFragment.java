package com.kk.taurus.xfolder.ui.fragment;

import android.os.Bundle;

import com.kk.taurus.baseframe.ui.fragment.StateFragment;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.holder.FragHolderPhotoList;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListFragment extends StateFragment<PhotoListData,FragHolderPhotoList> {
    @Override
    public FragHolderPhotoList getContentViewHolder(Bundle savedInstanceState) {
        return new FragHolderPhotoList(mContext);
    }
}
