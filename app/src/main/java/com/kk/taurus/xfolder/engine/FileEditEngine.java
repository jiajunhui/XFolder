package com.kk.taurus.xfolder.engine;

import android.os.AsyncTask;

import com.kk.taurus.filebase.engine.FileEngine;
import com.kk.taurus.threadpool.TaskExecutor;
import com.kk.taurus.xfolder.bean.BaseItem;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/26.
 */

public class FileEditEngine {

    public static AsyncTask invokeDelete(List<BaseItem> items, final OnDeleteCallBack onDeleteCallBack){
        List<File> files = new ArrayList<>();
        File file;
        for(BaseItem item:items){
            file = new File(item.getPath());
            files.add(file);
        }
        return TaskExecutor.executeConcurrently(new AsyncTask<List<File>, Integer, Integer>() {
            @Override
            protected Integer doInBackground(List<File>... params) {
                return FileEngine.deleteFiles(params[0], new FileEngine.OnDeleteListener() {
                    @Override
                    public void onDeleteProgress(int progress, int max) {
                        publishProgress(progress,max);
                    }
                    @Override
                    public void onDeleteFinish(int failNumber) {

                    }
                });
            }
            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                if(onDeleteCallBack!=null){
                    onDeleteCallBack.onProgress(values[0],values[1]);
                }
            }
            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if(onDeleteCallBack!=null){
                    onDeleteCallBack.onFinish(result);
                }
            }
        },files);
    }

    public static abstract class OnDeleteCallBack implements OnEditListener{
        public void onProgress(int progress, int max){

        }
    }

    public interface OnEditListener{
        void onFinish(int code);
    }

}
