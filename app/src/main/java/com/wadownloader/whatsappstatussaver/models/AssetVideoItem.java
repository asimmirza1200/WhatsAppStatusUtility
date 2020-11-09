package com.wadownloader.whatsappstatussaver.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.wadownloader.whatsappstatussaver.R;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.Config;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;
import com.volokh.danylo.video_player_manager.utils.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import de.mateware.snacky.Snacky;

public class AssetVideoItem extends BaseVideoItem{
    private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/WhatsApp Statuses/";

    private static final String TAG = AssetVideoItem.class.getSimpleName();
    private static final boolean SHOW_LOGS = Config.SHOW_LOGS;

    private final String mAssetFileDescriptor;
    private final String mTitle;

    private final Picasso mImageLoader;
    private final int mImageResource;

    public AssetVideoItem(String title, String link, VideoPlayerManager<MetaData> videoPlayerManager, Picasso imageLoader, int imageResource) {
        super(videoPlayerManager);
        mTitle = title;
        mAssetFileDescriptor = link;
        mImageLoader = imageLoader;
        mImageResource = imageResource;
    }

    @Override
    public void update(int position, final VideoViewHolder viewHolder, VideoPlayerManager videoPlayerManager, Context context) {
        if(SHOW_LOGS) Logger.v(TAG, "update, position " + position);

        viewHolder.mCover.setVisibility(View.VISIBLE);
        mImageLoader.load(mImageResource).into(viewHolder.mCover);
        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(mAssetFileDescriptor);
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent .setType("video/*");
                intent .putExtra(Intent.EXTRA_STREAM, uri);
                context.startActivity(intent );
            }
        });
        viewHolder.download.setOnClickListener(this.downloadMediaItem(new File(mAssetFileDescriptor),context));

    }


    public View.OnClickListener downloadMediaItem(final File sourceFile,Context context) {

        return new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new Runnable(){

                    @Override
                    public void run() {
                        try {

                            copyFile(sourceFile, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DIRECTORY_TO_SAVE_MEDIA_NOW + sourceFile.getName()), (Activity) context);
                            Snacky.builder().setActivity((Activity) context).
                                    setText(R.string.save_successful_message).
                                    success().
                                    show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );

                            Snacky.builder().
                                    setActivity((Activity) context).
                                    setText(R.string.save_error_message).
                                    success().
                                    show();
                        }
                    }
                }.run();
            }
        };
    }


    public static void copyFile(File sourceFile, File destFile, Activity activity) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();

        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
            MediaScannerConnection.scanFile(activity,
                    new String[] { Environment.getExternalStorageDirectory().getAbsolutePath() + DIRECTORY_TO_SAVE_MEDIA_NOW + sourceFile.getName() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });
        }
    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, mAssetFileDescriptor);
    }

    @Override
    public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }

    @Override
    public String toString() {
        return getClass() + ", mTitle[" + mTitle + "]";
    }
}
