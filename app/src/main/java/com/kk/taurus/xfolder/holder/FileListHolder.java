package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.ExplorerAdapter;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileListData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/24.
 */

public class FileListHolder extends ContentHolder<FileListData> implements ExplorerAdapter.OnItemListener {

    private RecyclerView mRecycler;
    private ExplorerAdapter mAdapter;
    private List<BaseItem> mItems = new ArrayList<>();

    private OnFileListListener onFileListListener;

    public FileListHolder(Context context) {
        super(context);
    }

    public void setOnFileListListener(OnFileListListener onFileListListener) {
        this.onFileListListener = onFileListListener;
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_file_list);
        mRecycler = getViewById(R.id.recycler);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAdapter = new ExplorerAdapter(mContext,mItems);
        mAdapter.setOnItemListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void refreshView() {
        super.refreshView();
        mItems.clear();
        mItems.addAll(mData.getItems());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        if(onFileListListener!=null){
            onFileListListener.onItemClick(mItems,mItems.get(position),position);
        }
    }

    @Override
    public boolean onItemLongClick(RecyclerView.ViewHolder holder, int position) {
        return false;
    }

    public interface OnFileListListener{
        void onItemClick(List<BaseItem> items, BaseItem item, int position);
    }
}
