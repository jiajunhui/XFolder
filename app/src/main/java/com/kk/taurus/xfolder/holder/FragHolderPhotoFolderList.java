package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.PhotoFolderAdapter;
import com.kk.taurus.xfolder.bean.EventUpdatePhotoList;
import com.kk.taurus.xfolder.bean.PhotoFolderData;
import com.kk.taurus.xfolder.callback.OnItemClickListener;
import com.kk.taurus.xfolder.widget.HorizontalDividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class FragHolderPhotoFolderList extends ContentHolder<PhotoFolderData> implements OnItemClickListener {

    private RecyclerView mRecycler;
    private PhotoFolderAdapter mAdapter;
    private List<PhotoFolder> mFolders = new ArrayList<>();

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
        HorizontalDividerItemDecoration itemDecoration =
                new HorizontalDividerItemDecoration.Builder(mContext).margin(0).size(1).color(Color.parseColor("#dddddd")).build();
        mRecycler.addItemDecoration(itemDecoration);
        mAdapter = new PhotoFolderAdapter(mContext,mFolders);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void refreshView() {
        super.refreshView();
        mFolders.clear();
        mFolders.addAll(mData.getFolders());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        EventUpdatePhotoList event = new EventUpdatePhotoList();
        PhotoFolder folder = mFolders.get(position);
        event.setItems(folder.getItems());
        event.setFolderName(folder.getName());
        EventBus.getDefault().post(event);
    }
}
