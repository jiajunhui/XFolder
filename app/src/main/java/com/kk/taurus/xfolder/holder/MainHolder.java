package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.MainHolderData;
import com.kk.taurus.xfolder.bean.MediaEntity;
import com.kk.taurus.xfolder.widget.StorageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/18.
 */

public class MainHolder extends ContentHolder<MainHolderData> {

    private View mImage;
    private View mVideo;
    private View mMusic;

    private TextView mTvImageNum;
    private TextView mTvVideoNum;
    private TextView mTvMusicNum;

    private LinearLayout mStorageContainer;

    private List<StorageItem> storageItems = new ArrayList<>();

    private OnMainListener mOnMainListener;

    public MainHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_main);

        mImage = getViewById(R.id.ll_image);
        mVideo = getViewById(R.id.ll_video);
        mMusic = getViewById(R.id.ll_music);

        mTvImageNum = getViewById(R.id.tv_image_num);
        mTvVideoNum = getViewById(R.id.tv_video_num);
        mTvMusicNum = getViewById(R.id.tv_music_num);

        mStorageContainer = getViewById(R.id.ll_storage);

        mImage.setOnClickListener(this);
        mVideo.setOnClickListener(this);
        mMusic.setOnClickListener(this);
    }

    @Override
    public void refreshView() {
        super.refreshView();
        MediaEntity entity = mData.getMediaEntity();
        if(entity!=null){
            mTvImageNum.setText(getNumberText(entity.getPhotoItems().size()));
            mTvVideoNum.setText(getNumberText(entity.getVideoItems().size()));
            mTvMusicNum.setText(getNumberText(entity.getAudioItems().size()));
        }
        List<Storage> storageList = mData.getStorageList();
        mStorageContainer.removeAllViews();
        storageItems.clear();
        StorageItem item;
        for(final Storage storage : storageList){
            item = new StorageItem(mContext);
            storageItems.add(item);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 5;
            mStorageContainer.addView(item.build(storage, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnMainListener!=null){
                        mOnMainListener.intentToExplorer(storage);
                    }
                }
            }),params);
        }
    }

    private String getNumberText(int number){
        return "(" + number + ")";
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.ll_image:
                if(mOnMainListener!=null){
                    mOnMainListener.intentToPhotoList(mData.getMediaEntity().getPhotoItems());
                }
                break;
            case R.id.ll_video:
                if(mOnMainListener!=null){
                    mOnMainListener.intentToVideoList(mData.getMediaEntity().getVideoItems());
                }
                break;
            case R.id.ll_music:
                if(mOnMainListener!=null){
                    mOnMainListener.intentToAudioList(mData.getMediaEntity().getAudioItems());
                }
                break;
        }
    }

    public void setOnMainListener(OnMainListener onMainListener){
        this.mOnMainListener = onMainListener;
    }

    public interface OnMainListener{
        void intentToExplorer(Storage storage);
        void intentToVideoList(List<VideoItem> videoItems);
        void intentToAudioList(List<AudioItem> audioItems);
        void intentToPhotoList(List<PhotoItem> photoItems);
    }
}
