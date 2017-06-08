package com.kk.taurus.xfolder.server;

import android.util.Log;

import com.kk.taurus.xfolder.server.response.IResourceResponse;
import com.kk.taurus.xfolder.server.response.NotFoundResponse;

import java.util.ArrayList;
import java.util.List;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Taurus on 2017/6/8.
 */

public class ServerEngine {

    private static final String TAG = "ServerEngine";

    private static List<IResourceResponse> mResponseList = new ArrayList<>();

    public static boolean addResponseType(IResourceResponse resourceResponse){
        if(mResponseList.contains(resourceResponse)){
            return false;
        }
        return mResponseList.add(resourceResponse);
    }

    public static void clearAllResponse(){
        mResponseList.clear();
    }

    public static NanoHTTPD.Response handleRequest(NanoHTTPD.IHTTPSession session){
        Log.d(TAG,"Visitor : " + session.getRemoteIpAddress());
        Log.d(TAG,"Uri : " + session.getUri());
        Log.d(TAG,"QueryParameter : " + session.getQueryParameterString());
        IResourceResponse target = new NotFoundResponse();
        for(IResourceResponse resourceResponse : mResponseList){
            if(resourceResponse.onDispatchRequest(session)){
                target = resourceResponse;
                break;
            }
        }
        return target.response(session);
    }

}
