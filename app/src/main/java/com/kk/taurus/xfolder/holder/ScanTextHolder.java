package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.ScanTextData;
import com.kk.taurus.xfolder.widget.UIWebView;

import java.io.File;

/**
 * Created by Taurus on 2017/5/8.
 */

public class ScanTextHolder extends ContentHolder<ScanTextData> {

    private UIWebView mWebView;
    private ProgressBar mProgressBar;

    public ScanTextHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_scan_text);
        mWebView = getViewById(R.id.web_view);
        mProgressBar = getViewById(R.id.progressBar);
        initWebView();
    }

    private void initWebView(){
        mWebView.setDefaultSettings();
        mWebView.setOnLoadProgressListener(new UIWebView.OnLoadProgressListener() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if(newProgress>=100){
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void refreshView() {
        super.refreshView();
        FileItem item = mData.getItem();
        String path = item.getPath();
        File file = new File(path);
        if(!TextUtils.isEmpty(path)){
            Uri uri = Uri.fromFile(file);
            String url = uri.toString();
            mWebView.loadUrl(url);
        }
    }
}
