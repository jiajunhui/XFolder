package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.VideoListAdapter;
import com.kk.taurus.xfolder.bean.MVideoItem;
import com.kk.taurus.xfolder.bean.VideoListData;
import com.kk.taurus.xfolder.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;

/**
 * Created by Taurus on 2017/5/19.
 */

public class VideoListHolder extends ContentHolder<VideoListData> implements OnItemClickListener {

    private RecyclerView mRecycler;
    private VideoListAdapter mAdapter;
    private List<MVideoItem> mItems = new ArrayList<>();

    private OnVideoListListener mOnVideoListListener;

    public VideoListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_video_list);
        mRecycler = getViewById(R.id.recycler);
    }

    public void setOnVideoListListener(OnVideoListListener onVideoListListener){
        this.mOnVideoListListener = onVideoListListener;
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

    }

    @Override
    public void refreshView() {
        super.refreshView();
        mItems.clear();
        mItems.addAll(mData.getVideoItems());
        if(mAdapter==null){
            mAdapter = new VideoListAdapter(mContext,mItems);
            SlideInBottomAnimationAdapter animationAdapter = new SlideInBottomAnimationAdapter(mAdapter);
            animationAdapter.setDuration(500);
            mAdapter.setOnItemClickListener(this);
            mRecycler.setAdapter(animationAdapter);
            return;
        }
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        if(mOnVideoListListener!=null){
            MVideoItem item = mItems.get(position);
            mOnVideoListListener.onIntentToPlayer(item,position);
        }
    }

    public interface OnVideoListListener{
        void onIntentToPlayer(MVideoItem item, int position);
    }
}
