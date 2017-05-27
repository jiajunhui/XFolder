package com.kk.taurus.xfolder.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.view.Menu;
import android.view.MenuItem;

import com.kk.taurus.animeffect.EffectFactory;
import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.listener.DialogCallBack;
import com.kk.taurus.baseframe.manager.SharedPrefer;
import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.filebase.utils.MIMEUtils;
import com.kk.taurus.threadpool.TaskCallBack;
import com.kk.taurus.xfolder.BuildConfig;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.FolderItem;
import com.kk.taurus.xfolder.bean.StackEntity;
import com.kk.taurus.xfolder.bean.StateRecord;
import com.kk.taurus.xfolder.config.SettingConfig;
import com.kk.taurus.xfolder.engine.FileEditEngine;
import com.kk.taurus.xfolder.engine.StackManager;
import com.kk.taurus.xfolder.holder.ExplorerHolder;
import com.kk.taurus.xfolder.utils.IntentUtils;

import java.io.File;
import java.util.List;

/**
 * Created by Taurus on 2017/5/18.
 */

public class ExplorerActivity extends BaseProjectActivity<StackEntity,ExplorerHolder> implements ExplorerHolder.OnExplorerListener {

    public static final String KEY_STORAGE_DATA = "storage_data";
    public static final String KEY_INTENT_FROM = "intent_from";

    public static final int INTENT_FROM_STORAGE = 0;
    public static final int INTENT_FROM_SEARCH = 1;

    private Storage mStorage;
    private boolean needRefresh;

    @Override
    public ExplorerHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ExplorerHolder(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_explorer,menu);
        MenuItem item = menu.findItem(R.id.dir_or_file_first);
        toggleDirOrFileFirstItemText(item);
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
            Intent scanIntent = IntentUtils.getIntent(this, (FileItem) item);
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

    @Override
    public void onRequestDelete(final List<BaseItem> items, final FileEditEngine.OnDeleteCallBack onDeleteCallBack) {
        showDialog(EffectFactory.ShakeHV.getAnimator(),"确定要删除吗？",new DialogCallBack(){
            @Override
            public void onPositiveClick(DialogInterface dialog) {
                super.onPositiveClick(dialog);
                FileEditEngine.invokeDelete(items, new FileEditEngine.OnDeleteCallBack() {
                    @Override
                    public void onFinish(int code) {
                        if(onDeleteCallBack!=null){
                            onDeleteCallBack.onFinish(code);
                        }
                        showSnackBar("删除完成",null,null);
                        mContentHolder.endEditState();
                        StackEntity entity = StackManager.getInstance().peek(true);
                        setData(entity);
                    }
                });
            }
        });
    }

    @Override
    protected void onToolBarDoubleClick() {
        super.onToolBarDoubleClick();
        mContentHolder.smoothScrollToPosition(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int sortType = SettingConfig.getSortType(this);
        switch (item.getItemId()){
            case R.id.name_asc:
                if(sortType!=SettingConfig.SORT_TYPE_NAME_A_Z){
                    SharedPrefer.getInstance().saveInt(this,SettingConfig.KEY_FILES_SORT_TYPE,SettingConfig.SORT_TYPE_NAME_A_Z);
                    refreshSort();
                }
                break;
            case R.id.name_desc:
                if(sortType!=SettingConfig.SORT_TYPE_NAME_Z_A){
                    SharedPrefer.getInstance().saveInt(this,SettingConfig.KEY_FILES_SORT_TYPE,SettingConfig.SORT_TYPE_NAME_Z_A);
                    refreshSort();
                }
                break;
            case R.id.time_asc:
                if(sortType!=SettingConfig.SORT_TYPE_DATE_ASC){
                    SharedPrefer.getInstance().saveInt(this,SettingConfig.KEY_FILES_SORT_TYPE,SettingConfig.SORT_TYPE_DATE_ASC);
                    refreshSort();
                }
                break;
            case R.id.time_desc:
                if(sortType!=SettingConfig.SORT_TYPE_DATE_DSC){
                    SharedPrefer.getInstance().saveInt(this,SettingConfig.KEY_FILES_SORT_TYPE,SettingConfig.SORT_TYPE_DATE_DSC);
                    refreshSort();
                }
                break;
            case R.id.dir_or_file_first:
                boolean dirFirst = SettingConfig.isDirFirst(this);
                SharedPrefer.getInstance().saveBoolean(this,SettingConfig.KEY_EXPLORER_DIR_FIRST,!dirFirst);
                toggleDirOrFileFirstItemText(item);
                refreshSort();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggleDirOrFileFirstItemText(MenuItem item) {
        boolean dirFirst = SettingConfig.isDirFirst(this);
        item.setTitle(dirFirst?getString(R.string.explorer_menu_item_title_file_first):getString(R.string.explorer_menu_item_title_dir_first));
    }

    private void refreshSort(){
        needRefresh = true;
        StackEntity entity = StackManager.getInstance().peek(true);
        setData(entity);
    }

    @Override
    public void onBackPressed() {
        if(mContentHolder.isEditState()){
            mContentHolder.endEditState();
            return;
        }
        if(StackManager.getInstance().size()<=1){
            super.onBackPressed();
        }else{
            StackManager.getInstance().pop();
            StackEntity entity = StackManager.getInstance().peek(needRefresh);
            mContentHolder.setStateRecords(entity.getStateRecord());
            setData(entity);
        }
    }
}
