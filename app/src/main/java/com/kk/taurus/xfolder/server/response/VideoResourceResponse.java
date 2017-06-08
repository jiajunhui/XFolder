package com.kk.taurus.xfolder.server.response;

import android.text.TextUtils;

import com.jaredrummler.android.util.HtmlBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public class VideoResourceResponse extends BaseResourceResponse{

    private final String VIDEO_CONTENT_URI = "/video";

    @Override
    public boolean onDispatchRequest(NanoHTTPD.IHTTPSession session) {
        if(getUri(session).startsWith(getContentUri())){
            return true;
        }
        return false;
    }

    @Override
    public NanoHTTPD.Response response(NanoHTTPD.IHTTPSession session) {
        HtmlBuilder htmlBuilder = new HtmlBuilder();
        htmlBuilder.h1("video").a("http://172.16.216.46:8080/","test_href");
        return responseMessage(htmlBuilder.toString());
//        String path = getResourcePath(session);
//        if(!TextUtils.isEmpty(path)){
//            try {
//                FileInputStream fis = new FileInputStream(path);
//                return NanoHTTPD.newChunkedResponse(NanoHTTPD.Response.Status.OK, "video/mp4", fis);
//            }catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return super.response(session);
    }

    @Override
    public String getContentUri() {
        return VIDEO_CONTENT_URI;
    }
}
