package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.PhotoFolderData;

/**
 * Created by Taurus on 2017/5/19.
 */

public class FragHolderPhotoFolderList extends ContentHolder<PhotoFolderData> {

    private RecyclerView mRecycler;

    public FragHolderPhotoFolderList(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.fragment_photo_folder_list);
        mRecycler = getViewById(R.id.recycler);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
    }
}
