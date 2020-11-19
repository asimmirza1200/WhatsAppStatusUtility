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
import android.widget.Toast;

import com.wadownloader.whatsappstatussaver.Adapter.ImagesAdapter;
import com.wadownloader.whatsappstatussaver.R;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ImagesFragment extends Fragment {
    private static final String WHATSAPP_STATUSES_LOCATION = "/WhatsApp/Media/.Statuses";
    GridLayoutManager gridLayoutManager;
    ImagesAdapter simpleAdapter;
    private RecyclerView recyclerView;
    String type;
    private static String DIRECTORY_TO_SAVE_MEDIA_NOW = "/WhatsApp Statuses/";
    private static String DIRECTORY_PATH="";

    public static ImagesFragment newInstance(int number) {
        ImagesFragment fragment = new ImagesFragment();
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
         type=getArguments().getString("type","");
         if (type.equals("Home"))
         {
             DIRECTORY_PATH=WHATSAPP_STATUSES_LOCATION;
         }
         else
         {
             DIRECTORY_PATH=DIRECTORY_TO_SAVE_MEDIA_NOW;
         }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            askForPermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
        return view;
    }



    private void askForPermission(String[] permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getContext(), permission[0])
                != PackageManager.PERMISSION_GRANTED) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission[0])) {
//                Toast.makeText(getContext(), "Please grant the requested permission to get your task done!", Toast.LENGTH_LONG).show();
//            }
            ActivityCompat.requestPermissions(getActivity(), permission, requestCode);
        }else {
            recyclerView.getItemAnimator().setChangeDuration(700);
            simpleAdapter = new ImagesAdapter(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString()+DIRECTORY_PATH)), getActivity(),type);
            recyclerView.setAdapter(simpleAdapter);
            gridLayoutManager = new GridLayoutManager(getContext(), 1);
            recyclerView.setLayoutManager(gridLayoutManager);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Toast.makeText(getContext(), requestCode, Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission with request code 1 granted
                    recyclerView.getItemAnimator().setChangeDuration(700);
                    simpleAdapter = new ImagesAdapter(this.getListFiles(new File(Environment.getExternalStorageDirectory().toString()+DIRECTORY_PATH)), getActivity(),type);
                    recyclerView.setAdapter(simpleAdapter);
                    gridLayoutManager = new GridLayoutManager(getContext(), 1);
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

                if (file.getName().endsWith(".png") ||file.getName().endsWith(".jpg") ||file.getName().endsWith(".jpeg") ) {
                        inFiles.add(file);
                    Log.e("images",file.getAbsolutePath());

                }

            }

        }

        return inFiles;
    }


}
