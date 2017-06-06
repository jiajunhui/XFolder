package com.kk.taurus.xfolder.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;

import com.kk.taurus.baseframe.listener.DialogCallBack;
import com.kk.taurus.baseframe.ui.activity.ToolsActivity;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.engine.SearchEngine;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by Taurus on 2017/5/18.
 */

public class SplashActivity extends ToolsActivity {

    private final int PERMISSION_STORAGE_REQUEST_CODE = 100;
    private final int MSG_CODE_REQUEST_PERMISSION = 101;

    private final long DELAY_TIME_MS_REQUEST_PERMISSION = 2000;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_CODE_REQUEST_PERMISSION:
                    permissionRequest();
                    break;
            }
        }
    };

    private void permissionRequest() {
        PermissionGen.with(this)
                .addRequestCode(PERMISSION_STORAGE_REQUEST_CODE)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                )
                .request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = PERMISSION_STORAGE_REQUEST_CODE)
    public void onPermissionSuccess(){
        SearchEngine.getInstance().initSearchEngine();
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
        onFinish();
    }

    @PermissionFail(requestCode = PERMISSION_STORAGE_REQUEST_CODE)
    public void onPermissionFail(){
        showDialog(getString(R.string.permission_fail_tips),new DialogCallBack(){
            @Override
            public void onPositiveClick(DialogInterface dialog) {
                super.onPositiveClick(dialog);
                onFinish();
            }
            @Override
            public void onNegativeClick(DialogInterface dialog) {
                super.onNegativeClick(dialog);
                onFinish();
            }
        });
    }

    @Override
    public View getContentView(Bundle savedInstanceState) {
        return View.inflate(this, R.layout.activity_splash,null);
    }

    @Override
    public void initData() {
        super.initData();
        mHandler.sendEmptyMessageDelayed(MSG_CODE_REQUEST_PERMISSION,DELAY_TIME_MS_REQUEST_PERMISSION);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MSG_CODE_REQUEST_PERMISSION);
    }
}
