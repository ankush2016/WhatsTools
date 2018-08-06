package com.whatstools.statussaver_directmessage.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.hbb20.CountryCodePicker;
import com.whatstools.statussaver_directmessage.R;
import com.whatstools.statussaver_directmessage.Utilities.LoadAdmobAd;

public class ActivityWhatsAppDirectMsg extends AppCompatActivity {

    CountryCodePicker ccp;
    Button bSend, bSendWhatsAppBusiness;
    EditText etNumber;

    AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewByIds();

        LoadAdmobAd.loadAdmobBannerAds(mAdView);
        mInterstitialAd = LoadAdmobAd.loadAdmobInterstitialAds(mInterstitialAd, this);

        ccp.registerCarrierNumberEditText(etNumber);

        bSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPackageInstalled("com.whatsapp")) {
                    if (!TextUtils.isEmpty(etNumber.getText().toString().trim())) {
                        String number = ccp.getSelectedCountryCode() + etNumber.getText().toString().trim();
                        //Toast.makeText(ActivityWhatsAppDirectMsg.this, number, Toast.LENGTH_SHORT).show();
                        String url = "https://api.whatsapp.com/send?phone=" + number;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.whatsapp");
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityWhatsAppDirectMsg.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityWhatsAppDirectMsg.this, "WhatsApp Not Installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bSendWhatsAppBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPackageInstalled("com.whatsapp.w4b")) {
                    if (!TextUtils.isEmpty(etNumber.getText().toString().trim())) {
                        String number = ccp.getSelectedCountryCode() + etNumber.getText().toString().trim();
                        //Toast.makeText(ActivityWhatsAppDirectMsg.this, number, Toast.LENGTH_SHORT).show();
                        String url = "https://api.whatsapp.com/send?phone=" + number;
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setPackage("com.whatsapp.w4b");
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } else {
                        Toast.makeText(ActivityWhatsAppDirectMsg.this, "Enter valid number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ActivityWhatsAppDirectMsg.this, "WhatsApp Business not installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findViewByIds() {
        ccp = findViewById(R.id.ccp);
        bSend = findViewById(R.id.bSend);
        bSendWhatsAppBusiness = findViewById(R.id.b_send_whatsapp_business);
        etNumber = findViewById(R.id.etNumber);
        mAdView = findViewById(R.id.admob_banner_ad);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private boolean isPackageInstalled(String packagename) {
        try {
            PackageManager packageManager = getPackageManager();
            packageManager.getPackageInfo(packagename, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
