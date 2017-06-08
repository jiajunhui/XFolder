package com.kk.taurus.xfolder.server.response;

import android.text.TextUtils;

import com.kk.taurus.xfolder.server.BaseUri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public class VideoResourceResponse extends BaseResourceResponse{

    @Override
    public NanoHTTPD.Response response(NanoHTTPD.IHTTPSession session) {
        String path = getResourcePath(session);
        if(!TextUtils.isEmpty(path)){
            try {
                File file = new File(path);
                if(!file.exists()){
                    return super.response(session);
                }
                FileInputStream fis = new FileInputStream(file);
                return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, "video/mp4", fis);
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return super.response(session);
    }

    @Override
    public String getContentUri() {
        return BaseUri.VIDEO;
    }
}
