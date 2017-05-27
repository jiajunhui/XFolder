package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.taurus.filebase.tools.BytesTool;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.BaseItem;
import com.kk.taurus.xfolder.bean.FileItem;
import com.kk.taurus.xfolder.bean.FolderItem;
import com.kk.taurus.xfolder.engine.CacheEngine;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;
import com.kk.taurus.xfolder.utils.ExtensionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/18.
 */

public class ExplorerAdapter extends RecyclerView.Adapter<ExplorerAdapter.ItemHolder> {

    private Context mContext;
    private List<BaseItem> mItems = new ArrayList<>();
    private OnItemListener onItemListener;
    private boolean mEditState;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public void setEditState(boolean edit) {
        this.mEditState = edit;
        notifyDataSetChanged();
    }

    public ExplorerAdapter(Context context, List<BaseItem> items){
        this.mContext = context;
        this.mItems = items;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position + 1;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(View.inflate(mContext,R.layout.item_file,null));
    }

    @Override
    public void onBindViewHolder(final ItemHolder holder, final int position) {
        BaseItem item = mItems.get(position);
        holder.name.setText(item.getName());
        int resId = ExtensionUtils.getImageRes(item);
        if(resId == R.mipmap.icon_image){
            ImageDisplayEngine.display(mContext,holder.icon,item.getPath(),R.mipmap.icon_image);
        }else if(resId == R.mipmap.icon_apk){
            String path = CacheEngine.getInstance().getApkIconPath(mContext,item.getPath());
            ImageDisplayEngine.display(mContext,holder.icon,path,R.mipmap.icon_apk);
        }else{
            holder.icon.setImageResource(resId);
        }
        if(mEditState){
            holder.arrow.setVisibility(View.VISIBLE);
            setItemChecked(holder,item.isChecked());
        }else{
            item.setChecked(false);
            holder.arrow.setVisibility((item instanceof FolderItem)?View.VISIBLE:View.GONE);
            if(item instanceof FolderItem){
                holder.arrow.setImageResource(R.mipmap.icon_arrow);
                holder.info.setText(getFolderInfo((FolderItem) item));
            }else{
                holder.info.setText(getFileInfo((FileItem) item));
            }
        }
        if(onItemListener !=null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemListener.onItemClick(holder, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return onItemListener.onItemLongClick(holder,position);
                }
            });
        }
    }

    public void setItemChecked(ItemHolder holder, boolean checked){
        holder.arrow.setImageResource(checked?R.mipmap.icon_edit_checked:R.mipmap.icon_edit_unchecked);
    }

    private String getFolderInfo(FolderItem item){
        return String.format(mContext.getString(R.string.folder_info),item.getDirNumber(),item.getFileNumber());
    }

    private String getFileInfo(FileItem item){
        return BytesTool.formatBytes(item.getSize());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder{

        public ImageView icon;
        public ImageView arrow;
        public TextView name;
        public TextView info;

        public ItemHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            arrow = (ImageView) itemView.findViewById(R.id.iv_arrow);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            info = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }

    public interface OnItemListener {
        void onItemClick(RecyclerView.ViewHolder holder, int position);
        boolean onItemLongClick(RecyclerView.ViewHolder holder, int position);
    }

}
