package com.kk.taurus.xfolder;

import android.os.Environment;

import com.bumptech.glide.request.target.ViewTarget;
import com.kk.taurus.baseframe.FrameApplication;
import com.kk.taurus.xfolder.server.ServerEngine;
import com.kk.taurus.xfolder.server.response.AudioResourceResponse;
import com.kk.taurus.xfolder.server.response.DocumentResourceResponse;
import com.kk.taurus.xfolder.server.response.FavIconResponse;
import com.kk.taurus.xfolder.server.response.ImageResourceResponse;
import com.kk.taurus.xfolder.server.response.VideoResourceResponse;
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
        ServerEngine.addResponseType(new VideoResourceResponse());
        ServerEngine.addResponseType(new ImageResourceResponse());
        ServerEngine.addResponseType(new AudioResourceResponse());
        ServerEngine.addResponseType(new DocumentResourceResponse());
        ServerEngine.addResponseType(new FavIconResponse());
    }
}
