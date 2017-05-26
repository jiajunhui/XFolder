package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.DirectoryItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.callback.OnItemClickListener;
import com.kk.taurus.xfolder.engine.CacheEngine;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;
import com.kk.taurus.xfolder.utils.ExtensionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/25.
 */

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchItemHolder>{

    private Context mContext;
    private List<BaseItem> mItems = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public SearchResultAdapter(Context mContext, List<BaseItem> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public SearchItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SearchItemHolder(View.inflate(mContext,R.layout.item_search_result,null));
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(final SearchItemHolder holder, final int position) {
        BaseItem item = mItems.get(position);
        holder.name.setText(item.getName());
        holder.arrow.setVisibility((item instanceof DirectoryItem)?View.VISIBLE:View.GONE);
        if(item instanceof FileItem){
            int resId = ExtensionUtils.getImageRes(item);
            if(resId == R.mipmap.icon_image){
                ImageDisplayEngine.display(mContext,holder.icon,item.getPath(),R.mipmap.icon_image);
            }else if(resId == R.mipmap.icon_apk){
                String path = CacheEngine.getInstance().getApkIconPath(mContext,item.getPath());
                ImageDisplayEngine.display(mContext,holder.icon,path,R.mipmap.icon_apk);
            }else{
                holder.icon.setImageResource(resId);
            }
        }else{
            holder.icon.setImageResource(R.mipmap.icon_folder);
        }
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(holder,position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class SearchItemHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        ImageView arrow;
        TextView name;

        public SearchItemHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            arrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
