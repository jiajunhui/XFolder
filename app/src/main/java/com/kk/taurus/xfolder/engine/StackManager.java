package com.kk.taurus.xfolder.engine;

import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FolderItem;
import com.kk.taurus.xfolder.bean.StackEntity;

import java.util.List;
import java.util.Stack;

/**
 * Created by Taurus on 2017/5/6.
 */

public class StackManager {

    private static StackManager instance = new StackManager();
    private StackManager(){
        mStacks = new Stack<>();
    }
    private Stack<StackEntity> mStacks;

    public static StackManager getInstance(){
        return instance;
    }

    public void reset(){
        mStacks.clear();
    }

    public StackEntity push(FolderItem item){
        StackEntity entity = new StackEntity();
        entity.setPath(item.getPath());
        entity.setName(item.getName());
        List<BaseItem> items = StorageVolumeManager.getInstance().getItems(item.getPath());
        entity.setItems(items);
        return mStacks.push(entity);
    }

    public StackEntity pop(){
        return mStacks.pop();
    }

    public StackEntity peek(boolean refresh){
        StackEntity entity = mStacks.peek();
        if(refresh){
            entity.setItems(StorageVolumeManager.getInstance().getItems(entity.getPath()));
            return entity;
        }
        return entity;
    }

    public int size(){
        return mStacks.size();
    }

    public boolean isEmpty(){
        return mStacks.isEmpty();
    }

}
