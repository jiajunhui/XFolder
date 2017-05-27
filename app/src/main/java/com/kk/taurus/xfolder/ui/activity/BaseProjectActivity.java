package com.kk.taurus.xfolder.ui.activity;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.R;

/**
 * Created by Taurus on 2017/5/27.
 */

public abstract class BaseProjectActivity<T extends HolderData,H extends ContentHolder<T>> extends ToolBarActivity<T,H> {

    @Override
    public void initData() {
        super.initData();
        getWindow().setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
    }
}
