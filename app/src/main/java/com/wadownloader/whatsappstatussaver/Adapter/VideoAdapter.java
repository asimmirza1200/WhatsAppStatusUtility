package com.wadownloader.whatsappstatussaver.Adapter;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.NativeAd;
import com.wadownloader.whatsappstatussaver.R;
import com.potyvideo.library.AndExoPlayerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

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
	private NativeAd nativeAd;

	public VideoAdapter(ArrayList<File> filesList, Activity activity) {
		this.filesList = filesList;
		this.activity = activity;
	}
	@Override
	public VideoAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
		return new FileHolder(inflatedView);
	}

	@Override
	public void onBindViewHolder(final VideoAdapter.FileHolder holder, int position) {

		final File currentFile = filesList.get(position);

		holder.buttonVideoDownload.setOnClickListener(this.downloadMediaItem(currentFile));

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
					(path, uri) -> {
						Log.i("ExternalStorage", "Scanned " + path + ":");
						Log.i("ExternalStorage", "-> uri=" + uri);
					});
		}
	}


	public static class FileHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		ImageView share;
		AndExoPlayerView videoViewVideoMedia;
		Button buttonVideoDownload;
		LinearLayout linearLayout;
		public FileHolder(View itemView) {
			super(itemView);
			videoViewVideoMedia = (AndExoPlayerView) itemView.findViewById(R.id.video);
			buttonVideoDownload = (Button) itemView.findViewById(R.id.save);
			share = (ImageView) itemView.findViewById(R.id.share);
			linearLayout=itemView.findViewById(R.id.tt);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}

	}

}