package com.kk.taurus.xfolder.server;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/7.
 */

public class BaseServer extends NanoHTTPD {

    public BaseServer(int port) {
        super(port);
    }

    public BaseServer(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
//        StringBuilder builder = new StringBuilder();
//        builder.append("<!DOCTYPE html><html><body>");
//        builder.append("hello,guys");
//        builder.append("</body></html>\n");
//        return newFixedLengthResponse(builder.toString());
        return ServerEngine.handleRequest(session);
    }

}
