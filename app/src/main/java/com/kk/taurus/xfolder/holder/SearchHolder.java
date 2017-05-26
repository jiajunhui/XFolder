package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.SearchResultAdapter;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.DirectoryItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/25.
 */

public class SearchHolder extends ContentHolder<HolderData> implements OnItemClickListener {

    private RecyclerView mRecycler;
    private TextView mResultInfo;
    private TextView mTimeInfo;
    private View mNullTip;
    private View mTopInfo;
    private SearchResultAdapter mAdapter;
    private List<BaseItem> mItems = new ArrayList<>();

    private long mSearchStart;

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
        mResultInfo = getViewById(R.id.tv_result_info);
        mTimeInfo = getViewById(R.id.tv_consume_time);
        mNullTip = getViewById(R.id.null_tip);
        mTopInfo = getViewById(R.id.ll_top_info);
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAdapter = new SearchResultAdapter(mContext,mItems);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    private void onListDataChange(boolean clean){
        mAdapter.notifyDataSetChanged();
        if(clean){
            mTopInfo.setVisibility(View.GONE);
        }else{
            mTopInfo.setVisibility(View.VISIBLE);
            mResultInfo.setText(String.format(getString(R.string.search_result_info),mItems.size()));
        }
    }

    public void onSearchFinish(){
        if(mItems.size()<=0){
            mNullTip.setVisibility(View.VISIBLE);
            return;
        }
        mNullTip.setVisibility(View.GONE);
        mTimeInfo.setText(String.format(getString(R.string.search_time_info),(System.currentTimeMillis() - mSearchStart)));
    }

    public void cleanResult(){
        mItems.clear();
        onListDataChange(true);
        mSearchStart = System.currentTimeMillis();
    }

    public void onResult(List<FileItem> fileItems){
        mItems.addAll(fileItems);
        onListDataChange(false);
    }

    public void onResultDirectory(List<DirectoryItem> directoryItems){
        mItems.addAll(directoryItems);
        onListDataChange(false);
    }

    public void onResultFile(List<FileItem> fileItems){
        mItems.addAll(fileItems);
        onListDataChange(false);
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
