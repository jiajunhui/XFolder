package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/21.
 */

public class ScanImagePagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<PhotoItem> mItems = new ArrayList<>();

    public ScanImagePagerAdapter(Context context, List<PhotoItem> photoItems){
        this.mContext = context;
        this.mItems = photoItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PhotoItem item = mItems.get(position);
        View itemView = View.inflate(mContext, R.layout.item_scan_image,null);
        ImageView image = (ImageView) itemView.findViewById(R.id.iv_image);
        ImageDisplayEngine.display(mContext,image,item.getPath(),R.mipmap.icon_image);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
