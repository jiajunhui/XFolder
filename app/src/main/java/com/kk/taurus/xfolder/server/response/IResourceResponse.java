package com.kk.taurus.xfolder.server.response;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public interface IResourceResponse {

    String KEY_RESOURCE_PATH_URL_PARAM = "path";

    boolean onDispatchRequest(NanoHTTPD.IHTTPSession session);

    NanoHTTPD.Response response(NanoHTTPD.IHTTPSession session);

    String getContentUri();

}
