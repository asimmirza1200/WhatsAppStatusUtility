package com.wadownloader.whatsappstatussaver.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.wadownloader.whatsappstatussaver.MainActivity;
import com.wadownloader.whatsappstatussaver.R;

import java.util.Objects;

public class VedioView extends AppCompatActivity {
    VideoView videoView;
    ImageView backk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio_view);
        backk=findViewById(R.id.back);
        backk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             onBackPressed();
            }
        });
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString("vediourl");
        videoView = (VideoView) findViewById(R.id.videoview);
        MediaController mediaController = new MediaController(this);
        try  {
            videoView.setVideoPath(url);
            videoView.start();
            videoView.canPause();
            videoView.canSeekBackward();
            videoView.canSeekForward();
            videoView.setMediaController(mediaController);
            mediaController.setAnchorView(videoView);
            mediaController.show();
            mediaController.show(500);
            // implement on completion listener on video view
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}