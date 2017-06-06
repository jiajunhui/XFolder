package com.kk.taurus.xfolder.widget;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kk.taurus.filebase.entity.Storage;
import com.kk.taurus.filebase.tools.BytesTool;
import com.kk.taurus.xfolder.R;

/**
 * Created by Taurus on 2017/5/6.
 */

public class StorageItem {

    private View mItemView;

    public StorageItem(Context context){
        this.mItemView = initItemLayout(context);
    }

    public View build(Storage storage, View.OnClickListener onClickListener){
        TextView name = (TextView) mItemView.findViewById(R.id.tv_name);
        TextView info = (TextView) mItemView.findViewById(R.id.tv_info);
        name.setText(storage.getDescription());
        StringBuilder sb = new StringBuilder();
        sb.append("总空间: ").append(BytesTool.formatBytes(storage.getTotalSize())).append("  ")
                .append("可用空间: ").append(BytesTool.formatBytes(storage.getAvailableSize()));
        info.setText(sb.toString());
//        ProgressBar progressBar = (ProgressBar) mItemView.findViewById(R.id.progressBar);
//        progressBar.setMax((int) storage.getTotalSize());
//        progressBar.setProgress((int) storage.getAvailableSize());
        mItemView.findViewById(R.id.card_view).setOnClickListener(onClickListener);
        return mItemView;
    }

    private View initItemLayout(Context context){
        return View.inflate(context, R.layout.item_storage,null);
    }

}
