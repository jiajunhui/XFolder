package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.jiajunhui.xapp.medialoader.bean.PhotoFolder;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.callback.OnItemClickListener;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class PhotoFolderAdapter extends RecyclerView.Adapter<PhotoFolderAdapter.PhotoFolderItemHolder>{

    private Context mContext;
    private List<PhotoFolder> mFolders = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public PhotoFolderAdapter(Context context, List<PhotoFolder> folders){
        this.mContext = context;
        this.mFolders = folders;
    }

    @Override
    public PhotoFolderItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoFolderItemHolder(View.inflate(mContext,R.layout.item_photo_folder,null));
    }

    @Override
    public void onBindViewHolder(final PhotoFolderItemHolder holder, final int position) {
        PhotoFolder folder = mFolders.get(position);
        holder.info.setText(getFolderInfo(folder));
        int size = folder.getItems().size();
        holder.cover1.setVisibility(size>=1?View.VISIBLE:View.GONE);
        holder.cover2.setVisibility(size>=2?View.VISIBLE:View.GONE);
        holder.cover3.setVisibility(size>=3?View.VISIBLE:View.GONE);
        holder.cover4.setVisibility(size>=4?View.VISIBLE:View.GONE);
        holder.cover5.setVisibility(size>=5?View.VISIBLE:View.GONE);
        holder.cover6.setVisibility(size>=6?View.VISIBLE:View.GONE);
        if(size >= 1){
            ImageDisplayEngine.displayAsBitmap(mContext,holder.cover1,folder.getItems().get(0).getPath(),R.mipmap.icon_image);
        }
        if(size >= 2){
            ImageDisplayEngine.displayAsBitmap(mContext,holder.cover2,folder.getItems().get(1).getPath(),R.mipmap.icon_image);
        }
        if(size >= 3){
            ImageDisplayEngine.displayAsBitmap(mContext,holder.cover3,folder.getItems().get(2).getPath(),R.mipmap.icon_image);
        }
        if(size >= 4){
            ImageDisplayEngine.displayAsBitmap(mContext,holder.cover4,folder.getItems().get(3).getPath(),R.mipmap.icon_image);
        }
        if(size >= 5){
            ImageDisplayEngine.displayAsBitmap(mContext,holder.cover5,folder.getItems().get(4).getPath(),R.mipmap.icon_image);
        }
        if(size >= 6){
            ImageDisplayEngine.displayAsBitmap(mContext,holder.cover6,folder.getItems().get(5).getPath(),R.mipmap.icon_image);
        }
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder,position);
                }
            });
        }
    }

    private String getFolderInfo(PhotoFolder folder){
        return String.format(mContext.getString(R.string.photo_folder_info),folder.getName(),folder.getItems().size());
    }

    @Override
    public int getItemCount() {
        return mFolders.size();
    }

    public static class PhotoFolderItemHolder extends RecyclerView.ViewHolder{

        CircularImageView cover1;
        CircularImageView cover2;
        CircularImageView cover3;
        CircularImageView cover4;
        CircularImageView cover5;
        CircularImageView cover6;
        TextView info;

        public PhotoFolderItemHolder(View itemView) {
            super(itemView);
            cover1 = (CircularImageView) itemView.findViewById(R.id.iv_cover1);
            cover2 = (CircularImageView) itemView.findViewById(R.id.iv_cover2);
            cover3 = (CircularImageView) itemView.findViewById(R.id.iv_cover3);
            cover4 = (CircularImageView) itemView.findViewById(R.id.iv_cover4);
            cover5 = (CircularImageView) itemView.findViewById(R.id.iv_cover5);
            cover6 = (CircularImageView) itemView.findViewById(R.id.iv_cover6);
            info = (TextView) itemView.findViewById(R.id.tv_folder_info);
        }
    }

}
