package com.wadownloader.whatsappstatussaver.models;

import android.app.MediaRouteButton;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.ads.NativeAdLayout;
import com.wadownloader.whatsappstatussaver.R;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class VideoViewHolder extends RecyclerView.ViewHolder{
    public final VideoPlayerView mPlayer;
    public final LottieAnimationView mCover;
    public final ImageView share;
    public final Button download;
    public final ImageButton paly;
    RelativeLayout parent;
    CardView  root;
    public com.facebook.ads.NativeAdLayout add;

    public VideoViewHolder(View view) {
        super(view);
        mPlayer = (VideoPlayerView) view.findViewById(R.id.player);
        mCover = (LottieAnimationView) view.findViewById(R.id.cover);
        share = (ImageView) view.findViewById(R.id.share);
        download = (Button) view.findViewById(R.id.save);
        paly=(ImageButton) view.findViewById(R.id.play);
        add= (NativeAdLayout) view.findViewById(R.id.tt);
        parent=(RelativeLayout) view.findViewById(R.id.parent);
        root=(CardView) view.findViewById(R.id.card_view);
    }
}
