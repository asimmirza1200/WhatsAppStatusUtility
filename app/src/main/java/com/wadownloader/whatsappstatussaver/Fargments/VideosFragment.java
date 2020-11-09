package com.wadownloader.whatsappstatussaver.Fargments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.wadownloader.whatsappstatussaver.Adapter.VideoAdapter;
import com.wadownloader.whatsappstatussaver.R;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class VideosFragment extends Fragment {
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    LinearLayoutManager gridLayoutManager;
    VideoAdapter simpleAdapter;
    private RecyclerView recyclerView;
    private View currentFocusedLayout, oldFocusedLayout;
    public static VideosFragment newInstance(int number) {
        VideosFragment fragment = new VideosFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("TAG_NUMBER", number);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
         recyclerView = (RecyclerView)view. findViewById(R.id.recycler_view);
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            askForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
        return view;
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener =new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView,
                                         int newState) {
            super.onScrollStateChanged(recyclerView, newState);


            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                //get the recyclerview position which is completely visible and first
                int positionView = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.i("VISISBLE", positionView + "");
                if (positionView >= 0) {
                    if (oldFocusedLayout != null) {
                        //Stop the previous video playback after new scroll
                        VideoView vv_dashboard = (VideoView) oldFocusedLayout.findViewById(R.id.video);
                        vv_dashboard.stopPlayback();
                    }


                    currentFocusedLayout = ((LinearLayoutManager) recyclerView.getLayoutManager()).findViewByPosition(positionView);
                    VideoView vv_dashboard = (VideoView) currentFocusedLayout.findViewById(R.id.video);
                    vv_dashboard.start();
                    oldFocusedLayout = currentFocusedLayout;
                }
            }

        }

    };

    private void askForPermission(String[] permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getContext(), permission[0])
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission[0])) {
                Toast.makeText(getContext(), "Please grant the requested permission to get your task done!", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(getActivity(), permission, requestCode);
        }else {
            recyclerView.getItemAnimator().setChangeDuration(700);
            simpleAdapter = new VideoAdapter(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION)), getActivity());
            recyclerView.setAdapter(simpleAdapter);
            gridLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission with request code 1 granted
                    recyclerView.getItemAnimator().setChangeDuration(700);
                    simpleAdapter = new VideoAdapter(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString()+WHATSAPP_STATUSES_LOCATION)), getActivity());
                    recyclerView.setAdapter(simpleAdapter);
                    gridLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(gridLayoutManager);
                    Toast.makeText(getContext(), "Permission Granted" , Toast.LENGTH_LONG).show();
                } else {
                    //permission with request code 1 was not granted
                    Toast.makeText(getContext(), "Permission was not Granted" , Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private ArrayList<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files;
        files = parentDir.listFiles();

        if (files != null) {
            for (File file : files) {

                if (file.getName().endsWith(".mp4") ||file.getName().endsWith(".mkv")  ) {
                        inFiles.add(file);
                    Log.e("video",file.getAbsolutePath());

                }

            }

        }

        return inFiles;
    }

}
