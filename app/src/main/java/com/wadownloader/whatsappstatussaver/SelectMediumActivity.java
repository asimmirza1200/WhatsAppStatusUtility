package com.wadownloader.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SelectMediumActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_medium);
    }
   public void onWhatsAppClick(View view){
        Intent mainIntent = new Intent(SelectMediumActivity.this,MainActivity.class);
        SelectMediumActivity.this.startActivity(mainIntent);
    }
    public void onInstagramClick(View view){
        Intent mainIntent = new Intent(SelectMediumActivity.this,MainActivity.class);
        SelectMediumActivity.this.startActivity(mainIntent);
    }
}