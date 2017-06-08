package com.kk.taurus.xfolder.server.response;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public class NotFoundResponse extends BaseResourceResponse {

    @Override
    public boolean onDispatchRequest(NanoHTTPD.IHTTPSession session) {
        return false;
    }

    @Override
    public String getContentUri() {
        return "Not Found";
    }

}
