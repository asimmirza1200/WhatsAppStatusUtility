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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import de.mateware.snacky.Snacky;


/**
 * Created by Ace Programmer Rbk<rodney@swiftpot.com> on 06-May-17
 * 8:24 AM
 */
public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.FileHolder> {

	private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/WhatsApp Statuses/";
	private ArrayList<File> filesList;
	private Activity activity;
	private String mtype;

	public ImagesAdapter(ArrayList<File> filesList, Activity activity,String type) {
		this.filesList = filesList;
		this.activity = activity;
		this.mtype=type;
		//setHasStableIds(true);
	}

	@Override
	public ImagesAdapter.FileHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View inflatedView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_card, parent, false);
		return new FileHolder(inflatedView);
	}

	@Override
	public void onBindViewHolder(final ImagesAdapter.FileHolder holder, int position) {
		final File currentFile = filesList.get(position);
		if (mtype.equals("History"))
		{
			holder.buttonImageDownload.setVisibility(View.GONE);
		}

		holder.buttonImageDownload.setOnClickListener(this.downloadMediaItem(currentFile));
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
				intent .setType("image/*");
				intent .putExtra(Intent.EXTRA_STREAM, uri);
				activity.startActivity(intent );
			}
		});

		   initiatePopupWithPicasso(currentFile.getAbsolutePath(),holder);
			Bitmap myBitmap = BitmapFactory.decodeFile(currentFile.getAbsolutePath());
			holder.imageViewImageMedia.setImageBitmap(myBitmap);
//		}

	}
	private void initiatePopupWithPicasso(String url,FileHolder holder) {


		Log.e("Width",""+ Resources.getSystem().getDisplayMetrics().widthPixels);
		final ImagePopup imagePopup = new ImagePopup(activity);
		imagePopup.setBackgroundColor(Color.WHITE);
		imagePopup.setFullScreen(true);
		imagePopup.setHideCloseIcon(false);
		imagePopup.setImageOnClickClose(false);



		imagePopup.initiatePopupWithPicasso(url);

		holder.imageViewImageMedia.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				imagePopup.viewPopup();
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

		ImageView imageViewImageMedia,share;
		VideoView videoViewVideoMedia;
		CardView cardViewVideoMedia;
		CardView cardViewImageMedia;
		Button buttonVideoDownload;
		Button buttonImageDownload;

		public FileHolder(View itemView) {
			super(itemView);
			imageViewImageMedia = (ImageView) itemView.findViewById(R.id.image);
			buttonImageDownload = (Button) itemView.findViewById(R.id.save);
			share = (ImageView) itemView.findViewById(R.id.share);


			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {

		}

	}

}