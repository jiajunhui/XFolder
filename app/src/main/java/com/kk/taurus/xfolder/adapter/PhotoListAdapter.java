package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jiajunhui.xapp.medialoader.bean.PhotoItem;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.PhotoItemHolder>{

    private Context mContext;
    private List<PhotoItem> mPhotoItems;
    private int mItemW;

    public PhotoListAdapter(Context context,List<PhotoItem> items){
        this.mContext = context;
        this.mPhotoItems = items;
        mItemW = getItemWidth();
    }

    @Override
    public PhotoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoItemHolder(View.inflate(mContext,R.layout.item_photo,null));
    }

    @Override
    public void onBindViewHolder(PhotoItemHolder holder, int position) {
        PhotoItem item = mPhotoItems.get(position);
        ViewGroup.LayoutParams layoutParams = holder.photo.getLayoutParams();
        layoutParams.width = mItemW;
        layoutParams.height = mItemW;
        holder.photo.setLayoutParams(layoutParams);
        ImageDisplayEngine.display(mContext,holder.photo,item.getPath(),R.mipmap.icon_image);
    }

    private int getItemWidth(){
        return (getScreenWidth() - 5)/4;
    }

    private int getScreenWidth(){
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    @Override
    public int getItemCount() {
        if(mPhotoItems!=null)
            return mPhotoItems.size();
        return 0;
    }

    public static class PhotoItemHolder extends RecyclerView.ViewHolder{

        ImageView photo;

        public PhotoItemHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.iv_photo);
        }
    }

}
