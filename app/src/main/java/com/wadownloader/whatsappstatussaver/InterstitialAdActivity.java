package com.wadownloader.whatsappstatussaver;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.pitt.library.fresh.FreshDownloadView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.facebook.ads.AdSettings.TestAdType.IMG_16_9_APP_INSTALL;

public class InterstitialAdActivity extends AppCompatActivity {
    private FreshDownloadView freshDownloadView;
    private final int FLAG_SHOW_OK = 10;
    private final int FLAG_SHOW_ERROR = 11;
    com.facebook.ads.NativeAdLayout add;
    private NativeAd nativeAd;
    private InterstitialAd interstitialAd;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int progress = (int) msg.obj;
            freshDownloadView.upDateProgress(progress);
            switch (msg.what) {
                case FLAG_SHOW_OK:
                    break;
                case FLAG_SHOW_ERROR:
                    freshDownloadView.showDownloadError();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);
        freshDownloadView = (FreshDownloadView) findViewById(R.id.pitt);
        add = findViewById(R.id.native_ad_container);
//        AudienceNetworkAds.initialize(this);

        loadInterstitial();

        if (freshDownloadView.using()) return;
        new Thread(() -> {

            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(30);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message = Message.obtain();
                message.obj = i;
              handler.sendMessage(message);

            }
            showInterstitial();
            finish();
        }).start();
        nativeAd = new NativeAd(this, getString(R.string.native_id));//here my id please Replace with your id
        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
            }

            @Override
            public void onAdLoaded(Ad ad) {
                View adView = NativeAdView.render(InterstitialAdActivity.this, nativeAd);
                add.addView(adView, new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };

        // Initiate a request to load an ad.
        nativeAd.loadAd(nativeAd.buildLoadAdConfig()
                .withAdListener(nativeAdListener)
                .withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL)
                .build());
    }

    private void showInterstitial() {
        // Checking If Ad is Loaded or Not
        if (interstitialAd.isAdLoaded()&&interstitialAd!=null) {
            // showing Ad
            interstitialAd.show();
        } else {
            // Loading Ad If Ad is Not Loaded
            interstitialAd.loadAd();
        }
    }

    private void loadInterstitial() {
//        AudienceNetworkAds.initialize(this);
        //  fbInterstitialAd = new InterstitialAd(this,"IMG_16_9_APP_INSTALL#1013674319138195_1013677702471190");
        interstitialAd = new InterstitialAd(this, getString(R.string.interstitial_id));
        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback

                //   Toast.makeText(LOADadd.this, "Add Displayed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback

                //   Toast.makeText(LOADadd.this, "Dismissed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback

                Toast.makeText(InterstitialAdActivity.this, "sorry" + adError.getErrorMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed

                //   Toast.makeText(LOADadd.this, "Interstitial ad is loaded and ready to be displayed!", Toast.LENGTH_SHORT).show();

                // Show the ad
              //  interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback

                //    Toast.makeText(LOADadd.this, "Interstitial ad clicked!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                //  Toast.makeText(LOADadd.this, "Interstitial ad impression logged!", Toast.LENGTH_SHORT).show();
            }
        };

        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());

    }
}