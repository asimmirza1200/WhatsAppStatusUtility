package com.wadownloader.whatsappstatussaver.models;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.wadownloader.whatsappstatussaver.R;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import androidx.recyclerview.widget.RecyclerView;


public class VideoViewHolder extends RecyclerView.ViewHolder{

    public final VideoPlayerView mPlayer;
    public final LottieAnimationView mCover;
    public final ImageView share;
    public final Button download;

    public VideoViewHolder(View view) {
        super(view);
        mPlayer = (VideoPlayerView) view.findViewById(R.id.player);
        mCover = (LottieAnimationView) view.findViewById(R.id.cover);
        share = (ImageView) view.findViewById(R.id.share);
        download = (Button) view.findViewById(R.id.save);

    }
}
