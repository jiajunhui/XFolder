package com.kk.taurus.xfolder;

import android.os.Environment;

import com.bumptech.glide.request.target.ViewTarget;
import com.kk.taurus.baseframe.FrameApplication;
import com.xapp.jjh.logtools.config.XLogConfig;
import com.xapp.jjh.logtools.tools.XLog;

/**
 * Created by Taurus on 2017/5/18.
 */

public class XFolderApplication extends FrameApplication {
    @Override
    public void onCreateInAppMainProcess() {
        super.onCreateInAppMainProcess();
        XLog.init(getApplicationContext(),new XLogConfig().setLogDir(Environment.getExternalStorageDirectory()));
        ViewTarget.setTagId(R.id.glide_tag);
    }
}
