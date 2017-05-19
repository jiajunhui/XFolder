package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.holder.PhotoListHolder;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListActivity extends ToolBarActivity<PhotoListData,PhotoListHolder> {

    public static final String KEY_PHOTO_LIST_DATA = "photo_list_data";

    @Override
    public PhotoListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new PhotoListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        setCenterTitle("图片");
    }
}
