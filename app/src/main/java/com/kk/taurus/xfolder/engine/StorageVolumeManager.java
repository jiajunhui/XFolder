package com.kk.taurus.xfolder.engine;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;

import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.FolderItem;
import com.kk.taurus.xfolder.callback.OnStorageChangeListener;
import com.kk.taurus.xfolder.config.SettingConfig;
import com.kk.taurus.xfolder.receiver.StorageChangeReceiver;
import com.kk.taurus.xfolder.utils.ExtensionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/6.
 */

public class StorageVolumeManager {

    private static StorageVolumeManager instance = new StorageVolumeManager();
    private BroadcastReceiver mReceiver;

    private StorageVolumeManager(){}

    public static StorageVolumeManager getInstance(){
        return instance;
    }

    public List<BaseItem> getItems(String path){
        List<File> files = listFiles(new File(path));
        List<BaseItem> items = new ArrayList<>();
        BaseItem item;
        for(File file : files){
            if(file.isFile()){
                item = new FileItem();
                item.setName(file.getName());
                item.setPath(file.getAbsolutePath());
                ((FileItem)item).setSize(file.length());
                ((FileItem)item).setExtensionName(ExtensionUtils.getExtension(file.getName()));
            }else if(file.isDirectory()){
                item = new FolderItem();
                item.setName(file.getName());
                item.setPath(file.getAbsolutePath());
                int[] numbers = getNumbers(item.getPath());
                ((FolderItem) item).setDirNumber(numbers[0]);
                ((FolderItem) item).setFileNumber(numbers[1]);
            }else{
                item = new BaseItem();
            }
            items.add(item);
        }
        return items;
    }

    public List<BaseItem> fillInfo(List<BaseItem> items){
        if(items==null)
            return items;
        for(BaseItem item : items){
            if(item instanceof FolderItem){
                int[] numbers = getNumbers(item.getPath());
                ((FolderItem) item).setDirNumber(numbers[0]);
                ((FolderItem) item).setFileNumber(numbers[1]);
            }
        }
        return items;
    }

    public int[] getNumbers(String dirPath){
        List<File> files = listFilesNoSort(new File(dirPath));
        int[] numbers = new int[2];
        for(File file : files){
            if(file.isDirectory()){
                numbers[0]++;
            }else if(file.isFile()){
                numbers[1]++;
            }
        }
        return numbers;
    }

    public void registerStorageChangeListener(Context context, OnStorageChangeListener onStorageChangeListener){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
        intentFilter.addAction(Intent.ACTION_MEDIA_CHECKING);
        intentFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_ATTACHED);
        intentFilter.addAction(UsbManager.ACTION_USB_ACCESSORY_DETACHED);
        intentFilter.addDataScheme("file");
        context.registerReceiver(mReceiver = new StorageChangeReceiver(onStorageChangeListener),intentFilter);
    }

    public void unregisterStorageChangeListener(Context context){
        if(mReceiver!=null){
            try {
                context.unregisterReceiver(mReceiver);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private List<File> listFilesNoSort(File dir){
        return FileEngine.listFiles(dir, null, null);
    }

    private List<File> listFiles(File dir){
        return FileEngine.listFiles(dir, SettingConfig.getFileFilter(),SettingConfig.getComparators());
    }
}
