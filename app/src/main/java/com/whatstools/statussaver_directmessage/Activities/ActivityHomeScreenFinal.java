package com.whatstools.statussaver_directmessage.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.whatstools.statussaver_directmessage.Extras.WhatsToolsConstants;
import com.whatstools.statussaver_directmessage.R;
import com.whatstools.statussaver_directmessage.Utilities.LoadAdmobAd;

import butterknife.BindView;
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
        switch (view.getId()) {
            case R.id.bWhatsAppDirect:
                startActivity(new Intent(ActivityHomeScreenFinal.this, ActivityWhatsAppDirectMsg.class));
                break;

            case R.id.bWhatsAppStatusDownload:
                requestPremission("WhatsApp");
                break;

            case R.id.bWhatsAppBusinessStatusDownload:
                requestPremission("WhatsApp Business");
                break;
        }
    }

    private void requestPremission(final String activityName) {
        Dexter.withActivity(ActivityHomeScreenFinal.this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(ActivityHomeScreenFinal.this, ActivityWhatsAppGallery.class);
                        intent.putExtra(WhatsToolsConstants.WHATSAPP_FOLDER, activityName);
                        startActivity(intent);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        showSettingsDialog();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityHomeScreenFinal.this);
        builder.setTitle("Need Permission");
        builder.setMessage("This app needs this permission to use this feature. You can grand this from app settings");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
