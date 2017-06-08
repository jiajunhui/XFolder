package com.kk.taurus.xfolder.server.response;

import com.kk.taurus.baseframe.FrameApplication;
import com.kk.taurus.filebase.engine.AssetsEngine;
import com.kk.taurus.xfolder.server.BaseUri;

import java.io.InputStream;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public class FavIconResponse extends BaseResourceResponse {

    @Override
    public NanoHTTPD.Response response(NanoHTTPD.IHTTPSession session) {
        try {
            InputStream isBm = AssetsEngine.getAssets(FrameApplication.getInstance().getApplicationContext(),"favicon.ico");
            return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, "image/x-icon", isBm);
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.response(session);
    }

    @Override
    public String getContentUri() {
        return BaseUri.FAVICON_ICO;
    }
}
