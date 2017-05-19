package com.kk.taurus.xfolder.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kk.taurus.xfolder.callback.OnStorageChangeListener;

/**
 * Created by Taurus on 2017/5/9.
 */

public class StorageChangeReceiver extends BroadcastReceiver{

    private final String TAG = "Storage_Receiver";
    private OnStorageChangeListener onStorageChangeListener;

    public StorageChangeReceiver(OnStorageChangeListener onStorageChangeListener){
        this.onStorageChangeListener = onStorageChangeListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d(TAG,"action = " + action);
        if(onStorageChangeListener!=null){
            onStorageChangeListener.onStorageChange();
        }
    }
}
