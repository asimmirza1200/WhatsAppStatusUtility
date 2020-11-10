package com.wadownloader.whatsappstatussaver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.wadownloader.whatsappstatussaver.models.BaseVideoItem;
import com.wadownloader.whatsappstatussaver.models.VideoViewHolder;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;


import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by danylo.volokh on 9/20/2015.
 */
public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private final VideoPlayerManager mVideoPlayerManager;
    private final List<BaseVideoItem> mList;
    private final Context mContext;
    private final String mtype;


    public VideoRecyclerViewAdapter(VideoPlayerManager videoPlayerManager, Context context, List<BaseVideoItem> list,String type){
        mVideoPlayerManager = videoPlayerManager;
        mContext = context;
        mList = list;
        mtype=type;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        BaseVideoItem videoItem = mList.get(position);
        View resultView = videoItem.createView(viewGroup, mContext.getResources().getDisplayMetrics().widthPixels,position);
        return new VideoViewHolder(resultView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder viewHolder, int position) {
        BaseVideoItem videoItem = mList.get(position);
        if (mtype.equals("History"))
        {
            viewHolder.download.setVisibility(View.GONE);
        }
        videoItem.update(position, viewHolder, mVideoPlayerManager,mContext);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
