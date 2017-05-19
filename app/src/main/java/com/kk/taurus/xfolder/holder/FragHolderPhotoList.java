package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.PhotoListData;

/**
 * Created by Taurus on 2017/5/19.
 */

public class FragHolderPhotoList extends ContentHolder<PhotoListData> {

    public final int SPAN_COUNT = 4;
    private RecyclerView mRecycler;

    public FragHolderPhotoList(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.fragment_photo_list);
        mRecycler = getViewById(R.id.recycler);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new GridLayoutManager(mContext,SPAN_COUNT, LinearLayoutManager.VERTICAL,false));
    }
}
