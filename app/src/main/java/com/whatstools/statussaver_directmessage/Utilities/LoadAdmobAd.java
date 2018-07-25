package com.whatstools.statussaver_directmessage.Utilities;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LoadAdmobAd {

    public static void loadAdmobBannerAds(AdView mAdView) {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("25E662D1EDF4290CE580D9AA9FA945B9").build();
        mAdView.loadAd(adRequest);
    }
}
