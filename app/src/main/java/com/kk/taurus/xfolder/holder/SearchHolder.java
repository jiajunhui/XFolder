package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jiajunhui.xapp.medialoader.bean.BaseItem;
import com.jiajunhui.xapp.medialoader.bean.FileItem;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.SearchResultAdapter;
import com.kk.taurus.xfolder.bean.DirectoryItem;
import com.kk.taurus.xfolder.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/25.
 */

public class SearchHolder extends ContentHolder<HolderData> implements OnItemClickListener {

    private RecyclerView mRecycler;
    private SearchResultAdapter mAdapter;
    private List<BaseItem> mItems = new ArrayList<>();

    private OnSearchListener onSearchListener;

    public SearchHolder(Context context) {
        super(context);
    }

    public void setOnSearchListener(OnSearchListener onSearchListener) {
        this.onSearchListener = onSearchListener;
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_search);
        mRecycler = getViewById(R.id.recycler);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAdapter = new SearchResultAdapter(mContext,mItems);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    public void cleanResult(){
        mItems.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void onResult(List<FileItem> fileItems){
        mItems.addAll(fileItems);
        mAdapter.notifyDataSetChanged();
    }

    public void onResultDirectory(List<DirectoryItem> directoryItems){
        mItems.addAll(directoryItems);
        mAdapter.notifyDataSetChanged();
    }

    public void onResultFile(List<FileItem> fileItems){
        mItems.addAll(fileItems);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        if(onSearchListener!=null){
            onSearchListener.onItemClick(mItems.get(position),position);
        }
    }

    public interface OnSearchListener{
        void onItemClick(BaseItem item, int position);
    }

}
