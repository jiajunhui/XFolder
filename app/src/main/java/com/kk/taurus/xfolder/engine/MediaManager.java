package com.kk.taurus.xfolder.engine;

import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.jiajunhui.xapp.medialoader.bean.AudioItem;
import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.jiajunhui.xapp.medialoader.callback.OnAudioLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnPhotoLoaderCallBack;
import com.jiajunhui.xapp.medialoader.callback.OnVideoLoaderCallBack;
import com.kk.taurus.xfolder.bean.MediaEntity;
import com.kk.taurus.xfolder.callback.OnMediaLoadListener;

import java.util.List;

/**
 * Created by Taurus on 2017/5/9.
 */

public class MediaManager {

    private static final String TAG = "MediaManager";

    public static void loadMedia(final FragmentActivity activity, final OnMediaLoadListener onMediaLoadListener){
        final MediaEntity mediaEntity = new MediaEntity();
//        MediaLoader.loadVideos(activity, new OnVideoLoaderCallBack() {
//            @Override
//            public void onResultList(List<VideoItem> items) {
//                Log.d(TAG,"videos : " + items.size());
//                mediaEntity.setVideoItems(items);
//                MediaLoader.loadPhotos(activity, new OnPhotoLoaderCallBack() {
//                    @Override
//                    public void onResultList(List<PhotoItem> items) {
//                        Log.d(TAG,"photos : " + items.size());
//                        mediaEntity.setPhotoItems(items);
//                        MediaLoader.loadAudios(activity, new OnAudioLoaderCallBack() {
//                            @Override
//                            public void onResultList(List<AudioItem> items) {
//                                Log.d(TAG,"audios : " + items.size());
//                                mediaEntity.setAudioItems(items);
//                                if(onMediaLoadListener!=null){
//                                    onMediaLoadListener.onLoadFinish(mediaEntity);
//                                }
//                            }
//                        });
//                    }
//                });
//            }
//        });
    }

}
