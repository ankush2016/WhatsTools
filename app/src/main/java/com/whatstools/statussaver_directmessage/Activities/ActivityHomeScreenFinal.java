package com.whatstools.statussaver_directmessage.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.whatstools.statussaver_directmessage.Extras.WhatsToolsConstants;
import com.whatstools.statussaver_directmessage.R;
import com.whatstools.statussaver_directmessage.Utilities.LoadAdmobAd;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityHomeScreenFinal extends AppCompatActivity {

    @BindView(R.id.bWhatsAppDirect)
    Button bDirectMessage;

    @BindView(R.id.bWhatsAppStatusDownload)
    Button bStatusDownload;

    @BindView(R.id.bWhatsAppBusinessStatusDownload)
    Button bWhatsAppBusinessStatusDownload;

    @BindView(R.id.admob_banner_ad)
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_final);
        ButterKnife.bind(this);

        LoadAdmobAd.loadAdmobBannerAds(mAdView);
    }

    @OnClick({R.id.bWhatsAppDirect, R.id.bWhatsAppStatusDownload, R.id.bWhatsAppBusinessStatusDownload})
    public void onButtonClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.bWhatsAppDirect:
                startActivity(new Intent(ActivityHomeScreenFinal.this, ActivityWhatsAppDirectMsg.class));
                break;

            case R.id.bWhatsAppStatusDownload:
                intent = new Intent(ActivityHomeScreenFinal.this, ActivityWhatsAppGallery.class);
                intent.putExtra(WhatsToolsConstants.WHATSAPP_FOLDER, "WhatsApp");
                startActivity(intent);
                break;

            case R.id.bWhatsAppBusinessStatusDownload:
                intent = new Intent(ActivityHomeScreenFinal.this, ActivityWhatsAppGallery.class);
                intent.putExtra(WhatsToolsConstants.WHATSAPP_FOLDER, "WhatsApp Business");
                startActivity(intent);
                break;
        }
    }


}
