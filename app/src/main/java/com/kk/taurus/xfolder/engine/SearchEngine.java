package com.kk.taurus.xfolder.engine;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.jiajunhui.xapp.medialoader.MediaLoader;
import com.jiajunhui.xapp.medialoader.bean.FileItem;
import com.jiajunhui.xapp.medialoader.bean.FileResult;
import com.jiajunhui.xapp.medialoader.callback.OnFileLoaderCallBack;
import com.jiajunhui.xapp.medialoader.inter.OnRecursionListener;
import com.jiajunhui.xapp.medialoader.utils.TraversalSearchLoader;
import com.kk.taurus.baseframe.FrameApplication;
import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.filebase.engine.StorageEngine;
import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.threadpool.TaskExecutor;
import com.kk.taurus.xfolder.bean.DirectoryItem;
import com.kk.taurus.xfolder.filter.DirectoryFilter;
import com.kk.taurus.xfolder.filter.NonDirectoryFilter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/25.
 */

public class SearchEngine {

    private final String TAG = "SearchEngine";
    private final String SEARCH_DIRECTORY_INDEX_FILE_NAME = ".search_directory_index_record.txt";
    private final String SEARCH_FILES_INDEX_FILE_NAME = ".search_files_index_record.txt";
    private static SearchEngine instance;

    private boolean mSearchDirectoryEnable;
    private boolean mSearchFilesEnable;

    private final DirectoryDao mDirectoryDao;

    private OnDirectoryInitListener onDirectoryInitListener;
    private OnFileInitListener onFileInitListener;
    private AsyncTask mInitFileIndexTask;
    private AsyncTask mInitDirectoryTask;

    private SearchEngine(){
        mDirectoryDao = new DirectoryDao(FrameApplication.getInstance().getApplicationContext());
    }

    public static SearchEngine getInstance(){
        if(null==instance){
            synchronized (SearchEngine.class){
                if(null==instance){
                    instance = new SearchEngine();
                }
            }
        }
        return instance;
    }

    private void setOnDirectoryInitListener(OnDirectoryInitListener onDirectoryInitListener){
        this.onDirectoryInitListener = onDirectoryInitListener;
    }

    private void setOnFileInitListener(OnFileInitListener onFileInitListener) {
        this.onFileInitListener = onFileInitListener;
    }

    public void initSearchEngine(){
        List<Storage> storageList = StorageEngine.getStorageList(FrameApplication.getInstance().getApplicationContext());
        if(storageList!=null){
            mSearchDirectoryEnable = existDirectoryIndex()!=null;
            mSearchFilesEnable = existFilesIndex()!=null;
            initDirectoryIndex(storageList);
            initFileIndex(storageList);
        }
    }

    private void initDirectoryIndex(List<Storage> storageList) {
        //init directory index
        mInitDirectoryTask = TaskExecutor.executeConcurrently(new AsyncTask<List<Storage>, Integer, Integer>() {
            @Override
            protected Integer doInBackground(List<Storage>... params) {
                for(Storage storage : params[0]){
                    TraversalSearchLoader.LoadParams loadParams = new TraversalSearchLoader.LoadParams();
                    loadParams.root = new File(storage.getPath());
                    loadParams.fileFilter = new DirectoryFilter();
                    TraversalSearchLoader.loadSync(loadParams, new OnRecursionListener() {
                        @Override
                        public void onStart() {

                        }
                        @Override
                        public void onItemAdd(File file, int counter) {
                            Log.d(TAG,"directory : " + file.getAbsolutePath());
                            String recordText = file.getAbsolutePath();
                            if(!TextUtils.isEmpty(recordText)){
                                recordSearchIndex(mDirectoryDao.getTemp(),SEARCH_DIRECTORY_INDEX_FILE_NAME,recordText);
                            }
                        }
                        @Override
                        public void onFinish(List<File> files) {

                        }
                    });
                }
                copyTempDirectoryIndex();
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

            }
        },storageList);
    }

    private void copyTempDirectoryIndex() {
        mSearchDirectoryEnable = false;
        File sourceFile = new File(mDirectoryDao.getTemp(),SEARCH_DIRECTORY_INDEX_FILE_NAME);
        if(sourceFile.exists()){
            boolean success = FileEngine.copy(sourceFile,mDirectoryDao.getSearchIndexDir());
            Log.d(TAG, "dirIndex_copy_success : " + success);
            sourceFile.delete();
        }
        mSearchDirectoryEnable = true;
        if(onDirectoryInitListener !=null){
            onDirectoryInitListener.onInitSuccess();
        }
    }

    private void initFileIndex(List<Storage> storageList) {
        //init file index
        mInitFileIndexTask = TaskExecutor.executeConcurrently(new AsyncTask<List<Storage>, Integer, Integer>() {
            @Override
            protected Integer doInBackground(List<Storage>... params) {
                for(Storage storage : params[0]){
                    TraversalSearchLoader.LoadParams loadParams = new TraversalSearchLoader.LoadParams();
                    loadParams.root = new File(storage.getPath());
                    loadParams.fileFilter = new NonDirectoryFilter();
                    TraversalSearchLoader.loadSync(loadParams, new OnRecursionListener() {
                        @Override
                        public void onStart() {

                        }
                        @Override
                        public void onItemAdd(File file, int counter) {
                            Log.d(TAG,"file : " + file.getAbsolutePath());
                            String recordText = file.getAbsolutePath();
                            if(!TextUtils.isEmpty(recordText)){
                                recordSearchIndex(mDirectoryDao.getTemp(),SEARCH_FILES_INDEX_FILE_NAME,recordText);
                            }
                        }
                        @Override
                        public void onFinish(List<File> files) {

                        }
                    });
                }
                copyTempFilesIndex();
                return null;
            }
            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

            }
        },storageList);
    }

    private void copyTempFilesIndex() {
        mSearchFilesEnable = false;
        File sourceFile = new File(mDirectoryDao.getTemp(),SEARCH_FILES_INDEX_FILE_NAME);
        if(sourceFile.exists()){
            boolean success = FileEngine.copy(sourceFile,mDirectoryDao.getSearchIndexDir());
            Log.d(TAG, "fileIndex_copy_success : " + success);
            sourceFile.delete();
        }
        mSearchFilesEnable = true;
        if(onFileInitListener !=null){
            onFileInitListener.onInitSuccess();
        }
    }

    private void recordSearchIndex(File dir, String fileName, String text){
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(new File(dir,fileName),true));
            writer.write(text,0,text.length());
            writer.newLine();
            writer.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File existDirectoryIndex(){
        File recordFile = new File(mDirectoryDao.getSearchIndexDir(),SEARCH_DIRECTORY_INDEX_FILE_NAME);
        if(recordFile.exists()){
            return recordFile;
        }
        return null;
    }

    private File existFilesIndex(){
        File recordFile = new File(mDirectoryDao.getSearchIndexDir(),SEARCH_FILES_INDEX_FILE_NAME);
        if(recordFile.exists()){
            return recordFile;
        }
        return null;
    }

    private List<DirectoryItem> readSearchDirectoryRecord(String key){
        File recordFile = new File(mDirectoryDao.getSearchIndexDir(),SEARCH_DIRECTORY_INDEX_FILE_NAME);
        if(recordFile.exists()){
            BufferedReader reader = null;
            try {
                List<DirectoryItem> result = new ArrayList<>();
                reader = new BufferedReader(new FileReader(recordFile));
                String line;
                DirectoryItem item;
                while ((line=reader.readLine())!=null){
                    String dirName = getNameByPath(line);
                    if(dirName!=null && dirName.contains(key)){
                        item = new DirectoryItem();
                        item.setPath(line);
                        item.setDisplayName(dirName);
                        result.add(item);
                    }
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private List<FileItem> readSearchFileRecord(String key){
        File recordFile = new File(mDirectoryDao.getSearchIndexDir(),SEARCH_FILES_INDEX_FILE_NAME);
        if(recordFile.exists()){
            BufferedReader reader = null;
            try {
                List<FileItem> result = new ArrayList<>();
                reader = new BufferedReader(new FileReader(recordFile));
                String line;
                FileItem item;
                while ((line=reader.readLine())!=null){
                    String fileName = getNameByPath(line);
                    if(fileName!=null && fileName.contains(key)){
                        item = new FileItem();
                        item.setPath(line);
                        item.setDisplayName(fileName);
                        result.add(item);
                    }
                }
                return result;
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(reader!=null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private String getNameByPath(String path){
        if(TextUtils.isEmpty(path))
            return null;
        int index = path.lastIndexOf("/");
        if(index!=-1){
            return path.substring(index+1,path.length());
        }
        return null;
    }

    public void search(FragmentActivity activity, final String key, final OnSearchListener onSearchListener){
        MediaLoader.getLoader().loadFiles(activity, new OnFileLoaderCallBack() {
            @Override
            public void onResult(FileResult result) {
                if(onSearchListener!=null){
                    onSearchListener.onResult(result);
                }
            }
            @Override
            public String getSelections() {
                return MediaStore.Files.FileColumns.DISPLAY_NAME + " like ? ";
            }
            @Override
            public String[] getSelectionsArgs() {
                return new String[]{"%"+key+"%"};
            }
        });
        if(mSearchDirectoryEnable){
            invokeSearchDirectory(key, onSearchListener);
        }else{
            setOnDirectoryInitListener(new OnDirectoryInitListener() {
                @Override
                public void onInitSuccess() {
                    invokeSearchDirectory(key, onSearchListener);
                }
            });
        }
        if(mSearchFilesEnable){
            invokeSearchFile(key, onSearchListener);
        }else{
            setOnFileInitListener(new OnFileInitListener() {
                @Override
                public void onInitSuccess() {
                    invokeSearchFile(key, onSearchListener);
                }
            });
        }
    }

    private void invokeSearchDirectory(final String key, final OnSearchListener onSearchListener) {
        TaskExecutor.executeConcurrently(new AsyncTask<String, Integer, List<DirectoryItem>>() {
            @Override
            protected List<DirectoryItem> doInBackground(String... params) {
                return readSearchDirectoryRecord(key);
            }
            @Override
            protected void onPostExecute(List<DirectoryItem> directoryItems) {
                super.onPostExecute(directoryItems);
                if(onSearchListener!=null){
                    onSearchListener.onDirectoryResult(directoryItems);
                }
            }
        },key);
    }

    private void invokeSearchFile(final String key, final OnSearchListener onSearchListener) {
        TaskExecutor.executeConcurrently(new AsyncTask<String, Integer, List<FileItem>>() {
            @Override
            protected List<FileItem> doInBackground(String... params) {
                return readSearchFileRecord(key);
            }

            @Override
            protected void onPostExecute(List<FileItem> fileItems) {
                super.onPostExecute(fileItems);
                if(onSearchListener!=null){
                    onSearchListener.onFileResult(fileItems);
                }
            }
        },key);
    }

    public void cancelTask(){
        if(mInitDirectoryTask!=null){
            mInitDirectoryTask.cancel(true);
        }
        if(mInitFileIndexTask!=null){
            mInitFileIndexTask.cancel(true);
        }
    }

    public interface OnSearchListener{
        void onResult(FileResult result);
        void onDirectoryResult(List<DirectoryItem> directoryItems);
        void onFileResult(List<FileItem> fileItems);
    }

    private interface OnDirectoryInitListener {
        void onInitSuccess();
    }

    private interface OnFileInitListener{
        void onInitSuccess();
    }

}
