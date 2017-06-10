package com.kk.taurus.xfolder.server.response;

import com.kk.taurus.xfolder.server.BaseUri;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/10.
 */

public class ExplorerResourceResponse extends BaseResourceResponse {

    @Override
    public NanoHTTPD.Response response(NanoHTTPD.IHTTPSession session) {
        return super.response(session);
    }

    @Override
    public String getContentUri() {
        return BaseUri.EXPLORER;
    }
}
