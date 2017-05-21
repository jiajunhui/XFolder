package com.kk.taurus.xfolder.ui.fragment;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.baseframe.ui.fragment.StateFragment;

/**
 * Created by Taurus on 2017/5/21.
 */

public abstract class BasePhotoFragment<T extends HolderData,H extends ContentHolder<T>> extends StateFragment<T,H> {

    public abstract String getPageTitle();

}
