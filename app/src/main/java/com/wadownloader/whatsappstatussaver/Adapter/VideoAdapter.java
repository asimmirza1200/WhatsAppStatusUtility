package com.wadownloader.whatsappstatussaver.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import com.wadownloader.whatsappstatussaver.R;
import com.wadownloader.whatsappstatussaver.Utils.ImagePopup;
import com.potyvideo.library.AndExoPlayerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.mateware.snacky.Snacky;


/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 06-May-17
 * 8:24 AM
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.FileHolder>    {

	private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/WhatsApp Statuses/";
	private ArrayList<File> filesList;
	private Activity activity;

	public VideoAdapter(ArrayList<File> filesList, Activity activity) {
		this.filesList = filesList;
		this.activity = activity;
		//setHasStableIds(true);
	}
//	@Override
//	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//		super.onAttachedToRecyclerView(recyclerView);
//		RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//		if(manager instanceof LinearLayoutManager && getItemCount() > 0) {
//			final LinearLayoutManager llm = (LinearLayoutManager) manager;
//			recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//				@Override
//				public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//					super.onScrollStateChanged(recyclerView, newState);
//				}
//
//				@Override
//				public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//					super.onScrolled(recyclerView, dx, dy);
//					int visiblePosition = llm.findFirstCompletelyVisibleItemPosition();
//					if(visiblePosition > -1) {
//						View v = llm.findViewByPosition(visiblePosition);
//						//do something
//						FileHolder firstViewHolder = (FileHolder) recyclerView.findViewHolderForLayoutPosition(visiblePosition);
//						View itemView = firstViewHolder.itemView;
//						firstViewHolder.videoViewVideoMedia.start();
//						v.setBackgroundColor(Color.parseColor("#777777"));
//					}
//				}
//			});
//		}
//	}
	@Override
	public VideoAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
		return new FileHolder(inflatedView);
	}

	@Override
	public void onBindViewHolder(final VideoAdapter.FileHolder holder, int position) {
		final File currentFile = filesList.get(position);

		holder.buttonVideoDownload.setOnClickListener(this.downloadMediaItem(currentFile));
		//holder.buttonVideoDownload.setOnClickListener(this.downloadMediaItem(currentFile));

//		if (currentFile.getAbsolutePath().endsWith(".mp4")) {
////			holder.cardViewImageMedia.setVisibility(View.GONE);
////			holder.cardViewVideoMedia.setVisibility(View.VISIBLE);
////			Uri video = Uri.parse(currentFile.getAbsolutePath());
////			holder.videoViewVideoMedia.setVideoURI(video);
////			holder.videoViewVideoMedia.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
////				@Override
////				public void onPrepared(MediaPlayer mp) {
////					mp.setLooping(true);
////					holder.videoViewVideoMedia.start();
////				}
////			});
//		} else {

		holder.share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Uri uri = Uri.parse(currentFile.getPath());
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent .setType("video/*");
				intent .putExtra(Intent.EXTRA_STREAM, uri);
				activity.startActivity(intent );
			}
		});

		initiatePopupWithPicasso(currentFile.getAbsolutePath(),holder);
		Bitmap myBitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
		holder.videoViewVideoMedia.setSource(currentFile.getAbsolutePath());
//			holder.videoViewVideoMedia.start();
//		}

	}
	private void initiatePopupWithPicasso(String url,FileHolder holder) {






		holder.videoViewVideoMedia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

			}
		});

	}

	@Override
	public int getItemCount() {
		return filesList.size();
	}

	public View.OnClickListener downloadMediaItem(final File sourceFile) {

		return new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Runnable(){

					@Override
					public void run() {
						try {

							copyFile(sourceFile, new File(Environment.getExternalStorageDirectory().getAbsolutePath() + DIRECTORY_TO_SAVE_MEDIA_NOW + sourceFile.getName()),activity);
							Snacky.builder().setActivity(activity).
									setText(R.string.save_successful_message).
									success().
									show();
						} catch (Exception e) {
							e.printStackTrace();
							Log.e("RecyclerV", "onClick: Error:"+e.getMessage() );

							Snacky.builder().
									setActivity(activity).
									setText(R.string.save_error_message).
									success().
									show();
						}
					}
				}.run();
			}
		};
	}


	public static void copyFile(File sourceFile, File destFile,Activity activity) throws IOException {
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


	public static class FileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		ImageView share;
		AndExoPlayerView videoViewVideoMedia;

		Button buttonVideoDownload;

		public FileHolder(View itemView) {
			super(itemView);
			videoViewVideoMedia = (AndExoPlayerView) itemView.findViewById(R.id.video);
			buttonVideoDownload = (Button) itemView.findViewById(R.id.save);
			share = (ImageView) itemView.findViewById(R.id.share);


			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}

	}

}