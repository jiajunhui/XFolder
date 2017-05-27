package com.kk.taurus.xfolder.ui.activity;

import android.os.Bundle;

import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.ScanTextData;
import com.kk.taurus.xfolder.holder.ScanTextHolder;

/**
 * Created by Taurus on 2017/5/8.
 */

public class ScanTextActivity extends BaseProjectActivity<ScanTextData,ScanTextHolder> {

    public static final String KEY_SCAN_TEXT_DATA = "text_data";

    @Override
    public ScanTextHolder getContentViewHolder(Bundle savedInstanceState) {
        return new ScanTextHolder(this);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void loadState() {
        super.loadState();
        ScanTextData scanTextData = new ScanTextData();
        FileItem item = (FileItem) getIntent().getSerializableExtra(KEY_SCAN_TEXT_DATA);
        setToolBarTitle(item.getName());
        scanTextData.setItem(item);
        setData(scanTextData);
    }
}
