package com.kk.taurus.xfolder.ui.fragment;

import android.os.Bundle;

import com.kk.taurus.baseframe.bean.PageState;
import com.kk.taurus.baseframe.ui.fragment.StateFragment;
import com.kk.taurus.threadpool.TaskCallBack;
import com.kk.taurus.xfolder.bean.PhotoDataHelper;
import com.kk.taurus.xfolder.bean.PhotoFolderData;
import com.kk.taurus.xfolder.comparator.PhotoFolderComparator;
import com.kk.taurus.xfolder.holder.FragHolderPhotoFolderList;

import java.util.Collections;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoFolderListFragment extends StateFragment<PhotoFolderData,FragHolderPhotoFolderList> {

    @Override
    public FragHolderPhotoFolderList getContentViewHolder(Bundle savedInstanceState) {
        return new FragHolderPhotoFolderList(mContext);
    }

    public static PhotoFolderListFragment getInstance(){
        PhotoFolderListFragment f = new PhotoFolderListFragment();
        return f;
    }

    @Override
    public void loadState() {
        PhotoFolderData data = PhotoDataHelper.getPhotoFolderData();
        if(data!=null){
            setPageState(PageState.loading());
            new TaskCallBack<PhotoFolderData,Void,PhotoFolderData>(){
                @Override
                public PhotoFolderData doInBackground(PhotoFolderData... params) {
                    Collections.sort(params[0].getFolders(),new PhotoFolderComparator());
                    return params[0];
                }
                @Override
                public void onPostExecute(PhotoFolderData photoFolderData) {
                    super.onPostExecute(photoFolderData);
                    setData(photoFolderData);
                    setPageState(PageState.success());
                }
            }.execute(data);

        }
    }
}
