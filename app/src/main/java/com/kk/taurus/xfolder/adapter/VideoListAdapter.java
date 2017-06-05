package com.kk.taurus.xfolder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.jiajunhui.xapp.medialoader.bean.VideoItem;
import com.kk.taurus.filebase.tools.BytesTool;
import com.kk.taurus.imagedisplay.ImageDisplay;
import com.kk.taurus.imagedisplay.entity.ThumbnailType;
import com.kk.taurus.playerbase.utils.TimeUtil;
import com.kk.taurus.xfolder.R;
import com.kk.taurus.xfolder.bean.MVideoItem;
import com.kk.taurus.xfolder.callback.OnItemClickListener;
import com.kk.taurus.xfolder.engine.ImageDisplayEngine;

import java.util.List;

/**
 * Created by Taurus on 2017/5/19.
 */

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoItemHolder>{

    private Context mContext;
    private List<MVideoItem> videoItems;
    private OnItemClickListener mOnItemClickListener;

    public VideoListAdapter(Context context, List<MVideoItem> items){
        this.mContext = context;
        this.videoItems = items;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public VideoItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoItemHolder(View.inflate(mContext,R.layout.item_video_list,null));
    }

    @Override
    public void onBindViewHolder(final VideoItemHolder holder, final int position) {
        MVideoItem item = videoItems.get(position);
        holder.name.setText(item.getDisplayName());
        holder.info.setText(getVideoInfo(item));
        ImageDisplay.disPlayThumbnail(mContext,holder.icon,item.getPath(),R.mipmap.icon_video, ThumbnailType.VIDEO_MINI_KIND);
        if(mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder,position);
                }
            });
        }
    }

    private String getVideoInfo(VideoItem item){
        return String.format(mContext.getString(R.string.video_item_info)
                , BytesTool.formatBytes(item.getSize())
                , TimeUtil.getTime(item.getDuration()));
    }

    @Override
    public int getItemCount() {
        if(videoItems!=null)
            return videoItems.size();
        return 0;
    }

    public static class VideoItemHolder extends RecyclerView.ViewHolder{

//        ImageView icon;
        PorterShapeImageView icon;
        TextView name;
        TextView info;

        public VideoItemHolder(View itemView) {
            super(itemView);
            icon = (PorterShapeImageView) itemView.findViewById(R.id.iv_thumbnail);
            name = (TextView) itemView.findViewById(R.id.tv_video_name);
            info = (TextView) itemView.findViewById(R.id.tv_video_info);
        }
    }

}
