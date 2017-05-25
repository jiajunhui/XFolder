package com.kk.taurus.xfolder.engine;

import android.content.Context;

import com.kk.taurus.filebase.base.FileBase;
import com.kk.taurus.filebase.base.IFileBase;

import java.io.File;

/**
 * Created by Taurus on 2017/5/20.
 */

public class DirectoryDao extends FileBase {

    private final String ROOT_DIR_NAME = ".cache";
    private final String TEMP_DIR = "temp";
    private final String VIDEO_THUMBNAIL_KIND_MINI = "video_thumbnail_kind_mini";
    private final String AUDIO_COVER = "audio_cover";
    private final String APK_ICON = "apk_icon";
    private final String SEARCH_INDEX = "search_directory_index";

    public DirectoryDao(Context context) {
        super(context);
    }

    public File getTemp(){
        return createDir(TEMP_DIR);
    }

    public File getVideoThumbnailMiniKind(){
        return createDir(VIDEO_THUMBNAIL_KIND_MINI);
    }

    public File getAudioCoverDir(){
        return createDir(AUDIO_COVER);
    }

    public File getApkIconDir(){
        return createDir(APK_ICON);
    }

    public File getSearchIndexDir(){
        return createDir(SEARCH_INDEX);
    }

    @Override
    public String getManageRootDirName() {
        return ROOT_DIR_NAME;
    }

    @Override
    public int getRootParentDirType() {
        return IFileBase.MANAGE_PARENT_DIR_APP_EXTERNAL_CACHE_FILES;
    }

    @Override
    public int getRootParentSpareDirType() {
        return IFileBase.MANAGE_PARENT_DIR_APP_CACHE_FILES;
    }

}
