package com.kk.taurus.xfolder.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.jiajunhui.xapp.medialoader.bean.BaseItem;
import com.jiajunhui.xapp.medialoader.bean.FileItem;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.kk.taurus.baseframe.base.HolderData;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.filebase.utils.MIMEUtils;
import com.kk.taurus.xfolder.BuildConfig;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.DirectoryItem;
import com.kk.taurus.xfolder.engine.SearchEngine;
import com.kk.taurus.xfolder.holder.SearchHolder;
import com.kk.taurus.xfolder.utils.IntentUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/22.
 */

public class SearchActivity extends ToolBarActivity<HolderData,SearchHolder> implements SearchView.OnQueryTextListener, SearchHolder.OnSearchListener {

    private SearchView mSearchView;

    @Override
    public SearchHolder getContentViewHolder(Bundle savedInstanceState) {
        return new SearchHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        mContentHolder.setOnSearchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item = menu.findItem(R.id.search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(item);
        mSearchView.onActionViewExpanded();
        mSearchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if(TextUtils.isEmpty(query))
            return true;
        mContentHolder.cleanResult();
        SearchEngine.getInstance().search(this, query, new SearchEngine.OnSearchListener() {
            @Override
            public void onResult(FileResult result) {
                closeKeyCode(SearchActivity.this);
                if(result!=null){
                    List<FileItem> fileItems = result.getItems();
                    mContentHolder.onResult(fileItems);
                }
            }
            @Override
            public void onDirectoryResult(List<DirectoryItem> directoryItems) {
                closeKeyCode(SearchActivity.this);
                if(directoryItems!=null){
                    mContentHolder.onResultDirectory(directoryItems);
                }
            }
            @Override
            public void onFileResult(List<FileItem> fileItems) {
                closeKeyCode(SearchActivity.this);
                if(fileItems!=null){
                    mContentHolder.onResultFile(fileItems);
                }
            }
        });
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(BaseItem item, int position) {
        if(item instanceof DirectoryItem){
            Storage storage = new Storage();
            storage.setPath(item.getPath());
            storage.setDescription(item.getDisplayName());
            Bundle bundle = new Bundle();
            bundle.putSerializable(ExplorerActivity.KEY_STORAGE_DATA,storage);
            intentTo(ExplorerActivity.class,bundle);
        }else{
            Intent scanIntent = IntentUtils.getIntent(this, (FileItem)item);
            if(scanIntent!=null){
                startActivity(scanIntent);
            }else{
                try{
                    File file = new File(item.getPath());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    String type = MIMEUtils.guessMimeTypeFromExtension(FileEngine.getExtFromFilename(file.getName()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                        intent.setDataAndType(contentUri, type);
                    }else{
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(android.content.Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file), type);
                    }
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
