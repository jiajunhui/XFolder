package com.kk.taurus.xfolder.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.jiajunhui.xapp.medialoader.utils.AudioCoverUtil;
import com.jiajunhui.xapp.medialoader.utils.VideoThumbnailUtil;
import com.kk.taurus.baseframe.FrameApplication;
import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.filebase.tools.MD5Utils;
import com.kk.taurus.threadpool.TaskExecutor;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.MVideoItem;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/25.
 */

public class CacheEngine {

    private static CacheEngine instance;
    private DirectoryDao mDirectoryDao;

    private CacheEngine(){
        mDirectoryDao = new DirectoryDao(FrameApplication.getInstance().getApplicationContext());
    }

    public static CacheEngine getInstance(){
        if(null==instance){
            synchronized (CacheEngine.class){
                if(null==instance){
                    instance = new CacheEngine();
                }
            }
        }
        return instance;
    }

    public String getApkIconPath(Context context, String path){
        String md5Name = MD5Utils.md5(path);
        File file = new File(mDirectoryDao.getApkIconDir(),md5Name);
        if(file.exists()){
            return file.getAbsolutePath();
        }else{
            Bitmap bitmap = FileEngine.getApkBitmap(context,path);
            if(bitmap!=null){
                String cachePath = FileEngine.bitmapToFile(bitmap, mDirectoryDao.getApkIconDir(),md5Name, Bitmap.CompressFormat.PNG);
                if(!TextUtils.isEmpty(cachePath)){
                    return cachePath;
                }
            }
        }
        return null;
    }

    public String getVideoThumbnailCachePath(String itemPath){
        File file = new File(mDirectoryDao.getVideoThumbnailMiniKind(),MD5Utils.md5(itemPath));
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return null;
    }

    public String getAudioCoverCachePath(String itemPath){
        File file = new File(mDirectoryDao.getAudioCoverDir(),MD5Utils.md5(itemPath));
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return null;
    }

    /**
     * get audio cover from audio file path.
     * @param path
     * @return
     */
    public String getAudioCover(String path){
        Bitmap bitmap = AudioCoverUtil.createAlbumArt(path);
        if(bitmap!=null) {
            String cache = FileEngine.bitmapToFile(bitmap, mDirectoryDao.getAudioCoverDir(), MD5Utils.md5(path));
            if(bitmap!=null){
                bitmap.recycle();
            }
            return cache;
        }
        return null;
    }

    public AsyncTask generatorAudioCover(List<MAudioItem> audioItems, final OnAudioCoverListener onAudioCoverListener){
        return TaskExecutor.executeConcurrently(new AsyncTask<List<MAudioItem>,Integer,List<MAudioItem>>(){
            @Override
            public List<MAudioItem> doInBackground(List<MAudioItem>... params) {
                try {
                    if(params[0]!=null){
                        for(MAudioItem item : params[0]){
                            if(!TextUtils.isEmpty(getAudioCoverCachePath(item.getPath()))){
                                continue;
                            }
                            String generateCache = getAudioCover(item.getPath());
                            if(generateCache!=null){
                                item.setAudioCover(generateCache);
                                publishProgress(0);
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return params[0];
            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                super.onProgressUpdate(progress);
                if(onAudioCoverListener !=null){
                    onAudioCoverListener.onCoverFinish();
                }
            }

            @Override
            public void onPostExecute(List<MAudioItem> videoItems) {
                super.onPostExecute(videoItems);
                if(onAudioCoverListener !=null){
                    onAudioCoverListener.onCoverFinish();
                }
            }
        },audioItems);
    }

    public AsyncTask generatorThumbnail(List<MVideoItem> videoItems, final OnVideoThumbnailListener onVideoThumbnailListener){
        return TaskExecutor.executeConcurrently(new AsyncTask<List<MVideoItem>, Integer, List<MVideoItem>>(){
            @Override
            public List<MVideoItem> doInBackground(List<MVideoItem>... params) {
                try {
                    if(params[0]!=null){
                        Bitmap bitmap = null;
                        for(MVideoItem item : params[0]){
                            if(!TextUtils.isEmpty(getVideoThumbnailCachePath(item.getPath()))){
                                continue;
                            }
                            bitmap = VideoThumbnailUtil.getVideoThumb(item.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
                            if(bitmap!=null){
                                String path = FileEngine.bitmapToFile(bitmap, mDirectoryDao.getVideoThumbnailMiniKind(),MD5Utils.md5(item.getPath()));
                                if(!TextUtils.isEmpty(path)){
                                    item.setThumbnail(path);
                                    publishProgress(0);
                                }
                            }
                        }
                        if(bitmap!=null){
                            bitmap.recycle();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                return params[0];
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                if(onVideoThumbnailListener !=null){
                    onVideoThumbnailListener.onThumbnailFinish();
                }
            }

            @Override
            public void onPostExecute(List<MVideoItem> videoItems) {
                super.onPostExecute(videoItems);
                if(onVideoThumbnailListener !=null){
                    onVideoThumbnailListener.onThumbnailFinish();
                }
            }
        },videoItems);
    }

    public interface OnVideoThumbnailListener {
        void onThumbnailFinish();
    }

    public interface OnAudioCoverListener {
        void onCoverFinish();
    }


}