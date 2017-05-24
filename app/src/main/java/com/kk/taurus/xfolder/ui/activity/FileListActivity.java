package com.kk.taurus.xfolder.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.bean.FileType;
import com.jiajunhui.xapp.medialoader.callback.OnFileLoaderCallBack;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.filebase.utils.MimeUtils;
import com.kk.taurus.xfolder.BuildConfig;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.FileListData;
import com.kk.taurus.xfolder.holder.FileListHolder;
import com.kk.taurus.xfolder.utils.IntentUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/24.
 */

public class FileListActivity extends ToolBarActivity<FileListData,FileListHolder> implements FileListHolder.OnFileListListener {

    public static final String KEY_FILE_LIST_DATA = "file_list_data";

    @Override
    public FileListHolder getContentViewHolder(Bundle savedInstanceState) {
        return new FileListHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
        mContentHolder.setOnFileListListener(this);
    }

    @Override
    public void loadState() {
        setPageState(PageState.loading());
        final FileListData data = (FileListData) getIntent().getSerializableExtra(KEY_FILE_LIST_DATA);
        if(data!=null){
            if(!TextUtils.isEmpty(data.getTitle())){
                setToolBarTitle(data.getTitle());
            }
            FileType type = data.getType();
            MediaLoader.getLoader().loadFiles(this, new OnFileLoaderCallBack(type) {
                @Override
                public void onResult(FileResult result) {
                    if(result!=null && result.getItems()!=null){
                        List<com.jiajunhui.xapp.medialoader.bean.FileItem> items = result.getItems();
                        List<FileItem> fileItems = new ArrayList<>();
                        FileItem _item;
                        for(com.jiajunhui.xapp.medialoader.bean.FileItem item : items){
                            _item = new FileItem();
                            _item.setName(new File(item.getPath()).getName());
                            _item.setPath(item.getPath());
                            _item.setSize(item.getSize());
                            fileItems.add(_item);
                        }
                        data.setItems(fileItems);
                        setData(data);
                        setPageState(PageState.success());
                    }
                }
            });
        }
    }

    @Override
    public void onItemClick(List<BaseItem> items, BaseItem item, int position) {
        Intent scanIntent = IntentUtils.getIntent(this, items, (FileItem) item, position);
        if(scanIntent!=null){
            startActivity(scanIntent);
        }else{
            try{
                File file = new File(item.getPath());
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String type = MimeUtils.guessMimeTypeFromExtension(FileEngine.getExtFromFilename(file.getName()));
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
