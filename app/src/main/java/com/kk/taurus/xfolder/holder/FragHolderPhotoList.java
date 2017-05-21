package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.PhotoListAdapter;
import com.kk.taurus.xfolder.bean.PhotoListData;
import com.kk.taurus.xfolder.callback.OnItemClickListener;
import com.kk.taurus.xfolder.widget.HorizontalDividerItemDecoration;
import com.kk.taurus.xfolder.widget.VerticalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class FragHolderPhotoList extends ContentHolder<PhotoListData> implements OnItemClickListener {

    public final int SPAN_COUNT = 4;
    private RecyclerView mRecycler;
    private PhotoListAdapter mAdapter;
    private List<PhotoItem> mPhotoItems = new ArrayList<>();

    private OnPhotoListListener onPhotoListListener;

    public FragHolderPhotoList(Context context) {
        super(context);
    }

    public void setOnPhotoListListener(OnPhotoListListener onPhotoListListener) {
        this.onPhotoListListener = onPhotoListListener;
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
        HorizontalDividerItemDecoration horizontalDividerItemDecoration =
                new HorizontalDividerItemDecoration.Builder(mContext).margin(0).size(1).color(Color.WHITE).build();

        VerticalDividerItemDecoration verticalDividerItemDecoration =
                new VerticalDividerItemDecoration.Builder(mContext).margin(0).size(1).color(Color.WHITE).build();

        mRecycler.addItemDecoration(horizontalDividerItemDecoration);
        mRecycler.addItemDecoration(verticalDividerItemDecoration);
        mAdapter = new PhotoListAdapter(mContext,mPhotoItems);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void refreshView() {
        super.refreshView();
        mPhotoItems.clear();
        mPhotoItems.addAll(mData.getPhotoItems());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        if(onPhotoListListener!=null){
            onPhotoListListener.onItemClick(mPhotoItems,position);
        }
    }

    public interface OnPhotoListListener{
        void onItemClick(List<PhotoItem> items,int position);
    }
}
