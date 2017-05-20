package com.kk.taurus.xfolder.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.Menu;

import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.activity.ToolBarActivity;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.threadpool.TaskCallBack;
import com.kk.taurus.xfolder.BuildConfig;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.FolderItem;
import com.kk.taurus.xfolder.bean.StackEntity;
import com.kk.taurus.xfolder.bean.StateRecord;
import com.kk.taurus.xfolder.engine.StackManager;
import com.kk.taurus.xfolder.holder.ExplorerHolder;
import com.kk.taurus.xfolder.utils.ExtensionUtils;
import com.kk.taurus.xfolder.utils.IntentUtils;
import com.kk.taurus.xfolder.utils.MIMEUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/18.
 */

public class ExplorerActivity extends ToolBarActivity<StackEntity,ExplorerHolder> implements ExplorerHolder.OnExplorerListener {

    public static final String KEY_STORAGE_DATA = "storage_data";

    private Storage mStorage;

    @Override
    public ExplorerHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ExplorerHolder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_explorer,menu);
        return true;
    }

    @Override
    public void parseIntent() {
        super.parseIntent();
        mStorage = (Storage) getIntent().getSerializableExtra(KEY_STORAGE_DATA);
    }

    @Override
    public void initData() {
        super.initData();
        StackManager.getInstance().reset();
        mContentHolder.setOnExplorerListener(this);
        mContentHolder.setStorage(mStorage);
    }

    @Override
    public void loadState() {
        FolderItem item = new FolderItem();
        item.setPath(mStorage.getPath());
        item.setName(mStorage.getDescription());
        openFolder(item);
    }

    @Override
    public void setData(StackEntity data) {
        super.setData(data);
        setToolBarTitle(data.getName());
    }

    private void openFolder(final FolderItem item){
        new TaskCallBack<FolderItem,Void,StackEntity>(){
            @Override
            public void onPreExecute() {
                super.onPreExecute();
                setToolBarTitle(item.getName());
                setPageState(PageState.loading());
            }
            @Override
            public StackEntity doInBackground(FolderItem... params) {
                return StackManager.getInstance().push(params[0]);
            }
            @Override
            public void onPostExecute(StackEntity stackEntity) {
                super.onPostExecute(stackEntity);
                setData(stackEntity);
                setPageState(PageState.success());
            }
        }.execute(item);
    }

    @Override
    public void onItemClick(List<BaseItem> items, BaseItem item, StateRecord record, int position) {
        if(item instanceof FolderItem){
            StackManager.getInstance().peek(false).setStateRecord(record);
            mContentHolder.setStateRecords(null);
            openFolder((FolderItem) item);
        }else{
            Intent scanIntent = IntentUtils.getIntent(this, items, (FileItem) item, position);
            if(scanIntent!=null){
                startActivity(scanIntent);
            }else{
                try{
                    File file = new File(item.getPath());
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                        intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    }else{
                        intent.addCategory(Intent.CATEGORY_DEFAULT);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Uri uri = Uri.fromFile(file);
                        intent.setDataAndType(uri, MIMEUtils.getMIMEType(ExtensionUtils.getExtension(item.getName())));
                    }
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onToolBarDoubleClick() {
        super.onToolBarDoubleClick();
        mContentHolder.smoothScrollToPosition(0);
    }

    @Override
    public void onBackPressed() {
//        if(mContentHolder.isEdit()){
//            mContentHolder.endEdit();
//            return;
//        }
        if(StackManager.getInstance().size()<=1){
            super.onBackPressed();
        }else{
            StackManager.getInstance().pop();
            StackEntity entity = StackManager.getInstance().peek(false);
            mContentHolder.setStateRecords(entity.getStateRecord());
            setData(entity);
        }
    }
}
