package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.ExplorerAdapter;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FolderItem;
import com.kk.taurus.xfolder.bean.StackEntity;
import com.kk.taurus.xfolder.bean.StateRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/18.
 */

public class ExplorerHolder extends ContentHolder<StackEntity> implements ExplorerAdapter.OnItemListener {

    private TextView mTvPath;
    private TextView mTvNullTip;
    private RecyclerView mRecycler;
    private ExplorerAdapter mAdapter;
    private List<BaseItem> mItems = new ArrayList<>();
    private List<BaseItem> mEditItems = new ArrayList<>();

    private boolean mEditState,mMoveState,mCopyState;

    private Storage storage;
    private StateRecord mCurrRecord;
    private OnExplorerListener onExplorerListener;

    public ExplorerHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_explorer);

        mTvPath = getViewById(R.id.tv_curr_path);
        mTvNullTip = getViewById(R.id.tv_null_tip);
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
        mTvPath.setText(mData.getPath());
        mItems.clear();
        mItems.addAll(mData.getItems());
        mAdapter.notifyDataSetChanged();

        mTvNullTip.setVisibility(mItems.size()>0?View.GONE:View.VISIBLE);

        mRecycler.post(new Runnable() {
            @Override
            public void run() {
                int scrollPosition = 0;
                int offSet = 0;
                if(mCurrRecord!=null && mItems.size()>0){
                    scrollPosition = mCurrRecord.getScrollToPosition();
                    offSet = mCurrRecord.getOffset();
                }
                scrollToPositionWithOffset(scrollPosition,offSet);
            }
        });
    }

    private void scrollToPositionWithOffset(int position, int offset){
        Log.d("ExplorerHolder","scrollWithOffset : position = " + position + " offset = " + offset);
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
        layoutManager.scrollToPositionWithOffset(position, offset);
    }

    public void smoothScrollToPosition(int position){
        mRecycler.smoothScrollToPosition(position);
    }

    public void setStorage(Storage storage){
        this.storage = storage;
    }

    public void setStateRecords(StateRecord record){
        this.mCurrRecord = record;
        mRecycler.stopScroll();
    }

    public void setOnExplorerListener(OnExplorerListener onExplorerListener) {
        this.onExplorerListener = onExplorerListener;
    }

    private int getFirstVisiblePosition(){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
        return layoutManager.findFirstVisibleItemPosition();
    }

    private int getOffset(int position){
        LinearLayoutManager layoutManager = (LinearLayoutManager) mRecycler.getLayoutManager();
        View view = layoutManager.findViewByPosition(position);
        if(view!=null){
            return view.getTop();
        }
        return 0;
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        BaseItem item = mItems.get(position);
        if(mEditState){
            item.setChecked(!item.isChecked());
            if(item.isChecked() && !mEditItems.contains(item)){
                mEditItems.add(item);
            }else{
                mEditItems.remove(item);
            }
            mAdapter.notifyDataSetChanged();
        }else{
            StateRecord record = null;
            if(item instanceof FolderItem){
                record = new StateRecord();
                record.setFocusPosition(position);
                int firstVisiblePosition = getFirstVisiblePosition();
                record.setScrollToPosition(firstVisiblePosition);
                record.setOffset(getOffset(firstVisiblePosition));
            }
            if(onExplorerListener!=null){
                onExplorerListener.onItemClick(mItems,item,record,position);
            }
        }
    }

    public boolean isEditState(){
        return mEditState;
    }

    public void endEditState(){
        setEditState(false);
    }

    private void setEditState(boolean edit){
        mEditState = edit;
        mAdapter.setEditState(edit);
    }

    @Override
    public boolean onItemLongClick(RecyclerView.ViewHolder holder, int position) {
        if(!mEditState && !mMoveState && !mCopyState){
            setEditState(true);
            return true;
        }
        return false;
    }

    public interface OnExplorerListener{
        void onItemClick(List<BaseItem> items, BaseItem item, StateRecord record, int position);
    }
}
