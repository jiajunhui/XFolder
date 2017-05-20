package com.kk.taurus.xfolder.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.kk.taurus.filebase.base.FileBase;
import com.kk.taurus.filebase.base.IFileBase;
import com.kk.taurus.filebase.tools.MD5Utils;
import com.kk.taurus.threadpool.TaskCallBack;
import com.kk.taurus.xfolder.bean.MVideoItem;
import com.kk.taurus.xfolder.utils.VideoThumbnailUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/20.
 */

public class ThumbnailCache extends FileBase {

    private final String ROOT_DIR_NAME = ".video_thumbnail_cache";
    private final String THUMBNAIL_KIND_MINI = "kind_mini";

    public ThumbnailCache(Context context) {
        super(context);
    }

    public File getMiniKindThumbnail(){
        return createDir(THUMBNAIL_KIND_MINI);
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

    public String getThumbnail(String path){
        File file = new File(getMiniKindThumbnail(),MD5Utils.md5(path));
        if(file.exists()){
            return file.getAbsolutePath();
        }
        return null;
    }

    public void generatorThumbnail(List<MVideoItem> videoItems, final OnThumbnailListener onThumbnailListener){
        new TaskCallBack<List<MVideoItem>,Integer,List<MVideoItem>>(){
            @Override
            public List<MVideoItem> doInBackground(List<MVideoItem>... params) {
                try {
                    if(params[0]!=null){
                        Bitmap bitmap = null;
                        for(MVideoItem item : params[0]){
                            if(!TextUtils.isEmpty(getThumbnail(item.getPath()))){
                                continue;
                            }
                            bitmap = VideoThumbnailUtil.getVideoThumb(item.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
                            if(bitmap!=null){
                                String path = VideoThumbnailUtil.bitmap2File(bitmap,getMiniKindThumbnail(),MD5Utils.md5(item.getPath()));
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
                if(onThumbnailListener!=null){
                    onThumbnailListener.onThumbnailFinish();
                }
            }

            @Override
            public void onPostExecute(List<MVideoItem> videoItems) {
                super.onPostExecute(videoItems);
                if(onThumbnailListener!=null){
                    onThumbnailListener.onThumbnailFinish();
                }
            }
        }.execute(videoItems);
    }

    public interface OnThumbnailListener{
        void onThumbnailFinish();
    }
}
