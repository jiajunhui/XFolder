package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.FileItem;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.MainHolderData;
import com.kk.taurus.xfolder.bean.MediaEntity;
import com.kk.taurus.xfolder.engine.AnimationEngine;
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
    private View mApk;
    private View mZip;
    private View mDoc;

    private TextView mTvImageNum;
    private TextView mTvVideoNum;
    private TextView mTvMusicNum;
    private TextView mTvApkNum;
    private TextView mTvZipNum;
    private TextView mTvDocNum;

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
        mApk = getViewById(R.id.ll_apk);
        mZip = getViewById(R.id.ll_zip);
        mDoc = getViewById(R.id.ll_doc);

        mTvImageNum = getViewById(R.id.tv_image_num);
        mTvVideoNum = getViewById(R.id.tv_video_num);
        mTvMusicNum = getViewById(R.id.tv_music_num);
        mTvApkNum = getViewById(R.id.tv_apk_num);
        mTvZipNum = getViewById(R.id.tv_zip_num);
        mTvDocNum = getViewById(R.id.tv_doc_num);

        mStorageContainer = getViewById(R.id.ll_storage);

        mImage.setOnClickListener(this);
        mVideo.setOnClickListener(this);
        mMusic.setOnClickListener(this);
        mApk.setOnClickListener(this);
        mZip.setOnClickListener(this);
        mDoc.setOnClickListener(this);
    }

    @Override
    public void refreshView() {
        super.refreshView();

        mImage.post(new Runnable() {
            @Override
            public void run() {
                int width = mImage.getMeasuredWidth();
                int height = mImage.getMeasuredHeight();
//                mImage.startAnimation(AnimationEngine.getMediaTypeAnimation(width,height));
//                mVideo.startAnimation(AnimationEngine.getMediaTypeAnimation(width,height));
//                mMusic.startAnimation(AnimationEngine.getMediaTypeAnimation(width,height));
//                mApk.startAnimation(AnimationEngine.getMediaTypeAnimation(width,height));
//                mZip.startAnimation(AnimationEngine.getMediaTypeAnimation(width,height));
//                mDoc.startAnimation(AnimationEngine.getMediaTypeAnimation(width,height));

                AnimationEngine.animator(mImage,width/2,height/2);
                AnimationEngine.animator(mVideo,width/2,height/2);
                AnimationEngine.animator(mMusic,width/2,height/2);
                AnimationEngine.animator(mApk,width/2,height/2);
                AnimationEngine.animator(mZip,width/2,height/2);
                AnimationEngine.animator(mDoc,width/2,height/2);
            }
        });

        MediaEntity entity = mData.getMediaEntity();
        if(entity!=null){
            if(entity.getPhotoResult()!=null && entity.getPhotoResult().getItems()!=null){
                mTvImageNum.setText(getNumberText(entity.getPhotoResult().getItems().size()));
            }
            if(entity.getVideoResult()!=null && entity.getVideoResult().getItems()!=null){
                mTvVideoNum.setText(getNumberText(entity.getVideoResult().getItems().size()));
            }
            if(entity.getAudioResult()!=null && entity.getAudioResult().getItems()!=null){
                mTvMusicNum.setText(getNumberText(entity.getAudioResult().getItems().size()));
            }
            if(entity.getApkResult()!=null && entity.getApkResult().getItems()!=null){
                mTvApkNum.setText(getNumberText(entity.getApkResult().getItems().size()));
            }
            if(entity.getZipResult()!=null && entity.getZipResult().getItems()!=null){
                mTvZipNum.setText(getNumberText(entity.getZipResult().getItems().size()));
            }
            if(entity.getDocResult()!=null && entity.getDocResult().getItems()!=null){
                mTvDocNum.setText(getNumberText(entity.getDocResult().getItems().size()));
            }
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
                if(mOnMainListener!=null && mData.getMediaEntity().getPhotoResult()!=null){
                    mOnMainListener.intentToPhotoList(mData.getMediaEntity().getPhotoResult().getItems());
                }
                break;
            case R.id.ll_video:
                if(mOnMainListener!=null && mData.getMediaEntity().getVideoResult()!=null){
                    mOnMainListener.intentToVideoList(mData.getMediaEntity().getVideoResult().getItems());
                }
                break;
            case R.id.ll_music:
                if(mOnMainListener!=null && mData.getMediaEntity().getAudioResult()!=null){
                    mOnMainListener.intentToAudioList(mData.getMediaEntity().getAudioResult().getItems());
                }
                break;
            case R.id.ll_apk:
                if(mOnMainListener!=null && mData.getMediaEntity().getApkResult()!=null){
                    mOnMainListener.intentToApkList(mData.getMediaEntity().getApkResult().getItems());
                }
                break;
            case R.id.ll_zip:
                if(mOnMainListener!=null && mData.getMediaEntity().getZipResult()!=null){
                    mOnMainListener.intentToZipList(mData.getMediaEntity().getZipResult().getItems());
                }
                break;
            case R.id.ll_doc:
                if(mOnMainListener!=null && mData.getMediaEntity().getDocResult()!=null){
                    mOnMainListener.intentToDocList(mData.getMediaEntity().getDocResult().getItems());
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
        void intentToApkList(List<FileItem> items);
        void intentToZipList(List<FileItem> items);
        void intentToDocList(List<FileItem> items);
    }
}
