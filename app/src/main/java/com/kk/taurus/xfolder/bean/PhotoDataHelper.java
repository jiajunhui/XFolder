package com.kk.taurus.xfolder.bean;

/**
 * Created by Taurus on 2017/5/20.
 */

public class PhotoDataHelper {

    private static PhotoListData photoListData;
    private static PhotoFolderData photoFolderData;
    private static ScanImageData scanImageData;

    public static void putPhotoListData(PhotoListData data){
        photoListData = data;
    }

    public static void putPhotoFolderData(PhotoFolderData data){
        photoFolderData = data;
    }

    public static PhotoListData getPhotoListData(){
        return photoListData;
    }

    public static PhotoFolderData getPhotoFolderData(){
        return photoFolderData;
    }

    public static void putScanImageData(ScanImageData data){
        scanImageData = data;
    }

    public static ScanImageData getScanImageData(){
        return scanImageData;
    }

    public static void clear(){
        if(photoListData!=null && photoListData.getPhotoItems()!=null){
            photoListData.getPhotoItems().clear();
        }
        if(photoFolderData!=null && photoFolderData.getFolders()!=null){
            photoFolderData.getFolders().clear();
        }
    }

    public static void clearScanImageData(){
        if(scanImageData!=null && scanImageData.getPhotoItems()!=null){
            scanImageData.getPhotoItems().clear();
        }
    }
}
