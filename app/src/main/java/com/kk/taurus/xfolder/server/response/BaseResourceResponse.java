package com.kk.taurus.xfolder.server.response;

import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public abstract class BaseResourceResponse implements IResourceResponse {

    @Override
    public boolean onDispatchRequest(NanoHTTPD.IHTTPSession session) {
        return shouldHandle(session);
    }

    protected String getResourcePath(NanoHTTPD.IHTTPSession session){
        Map<String,List<String>> params = session.getParameters();
        if(params!=null){
            List<String> values = params.get(KEY_RESOURCE_PATH_URL_PARAM);
            if(values!=null){
                return values.get(0);
            }
        }
        return null;
    }

    protected boolean shouldHandle(NanoHTTPD.IHTTPSession session){
        return getUri(session).equalsIgnoreCase(getContentUri());
    }

    protected String getUri(NanoHTTPD.IHTTPSession session){
        String uri = session.getUri();
        if(uri==null){
            uri = "";
        }
        return uri;
    }

    @Override
    public NanoHTTPD.Response response(NanoHTTPD.IHTTPSession session) {
        return responseMessage(notFoundMessage());
    }

    protected NanoHTTPD.Response responseMessage(String message){
        return NanoHTTPD.newFixedLengthResponse(message);
    }

    protected String notFoundMessage(){
        return "Not Found";
    }

}
