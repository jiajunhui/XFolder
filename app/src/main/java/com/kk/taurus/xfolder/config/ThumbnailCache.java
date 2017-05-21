package com.kk.taurus.xfolder.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.kk.taurus.filebase.base.FileBase;
import com.kk.taurus.filebase.base.IFileBase;
import com.kk.taurus.filebase.tools.MD5Utils;
import com.kk.taurus.threadpool.TaskCallBack;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.bean.MVideoItem;
import com.kk.taurus.xfolder.utils.AudioCoverUtils;
import com.kk.taurus.xfolder.utils.VideoThumbnailUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/20.
 */

public class ThumbnailCache extends FileBase {

    private final String ROOT_DIR_NAME = ".bitmap_cache";
    private final String VIDEO_THUMBNAIL_KIND_MINI = "video_thumbnail_kind_mini";
    private final String AUDIO_COVER = "audio_cover";

    public ThumbnailCache(Context context) {
        super(context);
    }

    public File getVideoThumbnailMiniKind(){
        return createDir(VIDEO_THUMBNAIL_KIND_MINI);
    }

    public File getAudioCoverDir(){
        return createDir(AUDIO_COVER);
    }

    @Override
    public String getManageRootDirName() {
        return ROOT_DIR_NAME;
    }

    @Override
    public int getRootParentDirType() {
        return IFileBase.MANAGE_PARENT_DIR_APP_CACHE_FILES;
    }

    @Override
    public int getRootParentSpareDirType() {
        return IFileBase.MANAGE_PARENT_DIR_APP_EXTERNAL_CACHE_FILES;
    }

    public String getVideoThumbnailCachePath(String itemPath){
        File file = new File(getVideoThumbnailMiniKind(),MD5Utils.md5(itemPath));
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return null;
    }

    public String getAudioCoverCachePath(String itemPath){
        File file = new File(getAudioCoverDir(),MD5Utils.md5(itemPath));
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return null;
    }

    public void generatorAudioCover(List<MAudioItem> audioItems, final OnAudioCoverListener onAudioCoverListener){
        new TaskCallBack<List<MAudioItem>,Integer,List<MAudioItem>>(){
            @Override
            public List<MAudioItem> doInBackground(List<MAudioItem>... params) {
                try {
                    if(params[0]!=null){
                        Bitmap bitmap = null;
                        for(MAudioItem item : params[0]){
                            if(!TextUtils.isEmpty(getAudioCoverCachePath(item.getPath()))){
                                continue;
                            }
                            bitmap = AudioCoverUtils.createAlbumArt(item.getPath());
                            if(bitmap!=null){
                                String path = VideoThumbnailUtil.bitmap2File(bitmap,getAudioCoverDir(),MD5Utils.md5(item.getPath()));
                                item.setAudioCover(path);
                                publishProgress(0);
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
            public void onProgressUpdate(Integer progress) {
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
        }.execute(audioItems);
    }

    public void generatorThumbnail(List<MVideoItem> videoItems, final OnVideoThumbnailListener onVideoThumbnailListener){
        new TaskCallBack<List<MVideoItem>,Integer,List<MVideoItem>>(){
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
                                String path = VideoThumbnailUtil.bitmap2File(bitmap, getVideoThumbnailMiniKind(),MD5Utils.md5(item.getPath()));
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
            public void onProgressUpdate(Integer progress) {
                super.onProgressUpdate(progress);
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
        }.execute(videoItems);
    }

    public interface OnVideoThumbnailListener {
        void onThumbnailFinish();
    }

    public interface OnAudioCoverListener {
        void onCoverFinish();
    }
}
