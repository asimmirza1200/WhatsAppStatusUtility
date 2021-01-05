package com.wadownloader.whatsappstatussaver.models;

import android.app.Activity;

import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;

import it.sephiroth.android.library.picasso.Picasso;

public class ItemFactory {

    public static AssetVideoItem createItemFromAsset(String assetName, int imageResource, Activity activity, VideoPlayerManager<MetaData> videoPlayerManager)  {
        return new AssetVideoItem(assetName, assetName, videoPlayerManager, Picasso.with(activity), imageResource);
    }
}
