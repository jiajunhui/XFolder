package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.kk.taurus.filebase.tools.BytesTool;
import com.kk.taurus.playerbase.utils.TimeUtil;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.MAudioItem;
import com.kk.taurus.xfolder.callback.OnItemClickListener;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.AudioItemHolder>{

    private Context mContext;
    private List<MAudioItem> audioItems = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    public AudioListAdapter(Context context, List<MAudioItem> items){
        this.mContext = context;
        this.audioItems = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public AudioItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AudioItemHolder(View.inflate(mContext,R.layout.item_audio,null));
    }

    @Override
    public void onBindViewHolder(final AudioItemHolder holder, final int position) {
        MAudioItem item = audioItems.get(position);
        ImageDisplayEngine.displayAsBitmap(mContext,holder.icon,item.getAudioCover(),R.mipmap.icon_audio_item_default);
        holder.name.setText(item.getDisplayName());
        holder.info.setText(getAudioInfo(item));
        if(onItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(holder,position);
                }
            });
        }
    }

    private String getAudioInfo(MAudioItem item){
        return String.format(mContext.getString(R.string.audio_item_info), BytesTool.formatBytes(item.getSize()), TimeUtil.getTime(item.getDuration()));
    }

    @Override
    public int getItemCount() {
        return audioItems.size();
    }

    public static class AudioItemHolder extends RecyclerView.ViewHolder{

        CircularImageView icon;
        TextView name;
        TextView info;

        public AudioItemHolder(View itemView) {
            super(itemView);
            icon = (CircularImageView) itemView.findViewById(R.id.circle_icon);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            info = (TextView) itemView.findViewById(R.id.tv_info);
        }
    }

}
