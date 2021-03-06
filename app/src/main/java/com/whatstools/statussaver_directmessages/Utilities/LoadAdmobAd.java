package com.whatstools.statussaver_directmessages.Utilities;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.whatstools.statussaver_directmessages.R;


public class LoadAdmobAd {

    public static void loadAdmobBannerAds(AdView mAdView) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("25E662D1EDF4290CE580D9AA9FA945B9").build();
        //AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public static InterstitialAd loadAdmobInterstitialAds(InterstitialAd mInterstitialAd, Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(String.valueOf(R.string.admob_interstitial_ad_id));
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("25E662D1EDF4290CE580D9AA9FA945B9").build());
        return mInterstitialAd;
    }
}
