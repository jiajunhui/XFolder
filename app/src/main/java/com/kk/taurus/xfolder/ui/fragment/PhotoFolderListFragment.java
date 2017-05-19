package com.kk.taurus.xfolder.ui.fragment;

import android.os.Bundle;

import com.kk.taurus.baseframe.ui.fragment.StateFragment;
import com.kk.taurus.xfolder.bean.PhotoFolderData;
import com.kk.taurus.xfolder.holder.FragHolderPhotoFolderList;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoFolderListFragment extends StateFragment<PhotoFolderData,FragHolderPhotoFolderList> {
    @Override
    public FragHolderPhotoFolderList getContentViewHolder(Bundle savedInstanceState) {
        return new FragHolderPhotoFolderList(mContext);
    }
}
