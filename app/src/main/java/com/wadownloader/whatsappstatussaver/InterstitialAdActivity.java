package com.wadownloader.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.wadownloader.whatsappstatussaver.models.Admob;

public class InterstitialAdActivity extends AppCompatActivity {
    public View view;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        //   --- Admob ---
        view=getWindow().getDecorView().getRootView();

        Admob.createLoadInterstitial(getApplicationContext(),null);
        //   --- *** ---
        finish();




    }
}