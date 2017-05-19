package com.kk.taurus.xfolder.holder;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kk.taurus.baseframe.base.ContentHolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.ScanTextData;

import java.io.File;

/**
 * Created by Taurus on 2017/5/8.
 */

public class ScanTextHolder extends ContentHolder<ScanTextData> {

    private WebView mWebView;

    public ScanTextHolder(Context context) {
        super(context);
    }

    @Override
    public void onCreate() {
        setContentView(R.layout.activity_scan_text);
        mWebView = getViewById(R.id.web_view);
        initWebView();
    }

    private void initWebView(){
        WebSettings settings = mWebView.getSettings();
        settings.setDefaultTextEncodingName("utf-8");
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
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
