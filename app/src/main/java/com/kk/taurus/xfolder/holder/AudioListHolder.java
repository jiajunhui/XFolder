package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.adapter.AudioListAdapter;
import com.kk.taurus.xfolder.bean.AudioListData;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.callback.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListHolder extends ContentHolder<AudioListData> implements OnItemClickListener {

    private RecyclerView mRecycler;
    private AudioListAdapter mAdapter;
    private List<MAudioItem> audioItems = new ArrayList<>();

    private OnAudioListListener onAudioListListener;

    public AudioListHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_audio_list);
        mRecycler = getViewById(R.id.recycler);
    }

    public void setOnAudioListListener(OnAudioListListener onAudioListListener) {
        this.onAudioListListener = onAudioListListener;
    }

    @Override
    public void onHolderCreated(Bundle savedInstanceState) {
        super.onHolderCreated(savedInstanceState);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        mAdapter = new AudioListAdapter(mContext,audioItems);
        mAdapter.setOnItemClickListener(this);
        mRecycler.setAdapter(mAdapter);
    }

    public void notifyDataChange(){
        if(mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshView() {
        super.refreshView();
        audioItems.clear();
        audioItems.addAll(mData.getAudioItems());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
        if(onAudioListListener!=null){
            onAudioListListener.onItemClick(audioItems.get(position));
        }
    }

    public interface OnAudioListListener{
        void onItemClick(MAudioItem item);
    }
}
