package com.whatstools.statussaver_directmessage.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.whatstools.statussaver_directmessage.Adapters.SectionsPageAdapter;
import com.whatstools.statussaver_directmessage.Fragments.FragmentWhatsAppImages;
import com.whatstools.statussaver_directmessage.Fragments.FragmentWhatsAppVideos;
import com.whatstools.statussaver_directmessage.R;
import com.whatstools.statussaver_directmessage.Utilities.LoadAdmobAd;

public class ActivityWhatsAppGallery extends AppCompatActivity {

    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whats_app_gallery);

        findViewByIds();
        loadAdmobBannerAds();

        mInterstitialAd = LoadAdmobAd.loadAdmobInterstitialAds(mInterstitialAd, this);

        //setup view pager with section page adapter
        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        setupViewPager(mViewPager);

        tabLayout.setupWithViewPager(mViewPager);
    }

    private void loadAdmobBannerAds() {
        mAdView = findViewById(R.id.adView);
        //mAdView.setAdSize(AdSize.SMART_BANNER);
        //mAdView.setAdUnitId(String.valueOf(R.string.admob_banner_ad_id));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("25E662D1EDF4290CE580D9AA9FA945B9").build();
        mAdView.loadAd(adRequest);
    }

    private void findViewByIds() {
        mViewPager = findViewById(R.id.container);
        tabLayout = findViewById(R.id.tabs);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentWhatsAppImages(), "IMAGES");
        adapter.addFragment(new FragmentWhatsAppVideos(), "VIDEOS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}
