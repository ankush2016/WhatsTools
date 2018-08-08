package com.whatstools.statussaver_directmessages.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whatstools.statussaver_directmessages.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class ActivityVideoPlayer extends AppCompatActivity {

    VideoView videoView;
    MediaController mediaController;

    ImageButton ibDownload, ibShare;

    Uri videoUri;
    String videoPath;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        findViewByIds();

        loadAdmobBannerAds();

        videoPlayer();

        ibShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("video/*");
                intent.putExtra(Intent.EXTRA_STREAM, videoUri);
                startActivity(Intent.createChooser(intent, "Share Video"));
            }
        });

        ibDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String descPath = Environment.getExternalStorageDirectory().toString() + "/WhatsTools/WhatsTools Videos/";
                File srcFile = new File(videoPath);
                File desc = new File(descPath);
                if (!desc.exists()) {
                    desc.mkdir();
                }
                desc = new File(descPath + srcFile.getName());
                try {
                    FileUtils.copyFile(srcFile, desc);
                    Toast.makeText(ActivityVideoPlayer.this, "Download Successful\n" + desc.getAbsolutePath(), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(ActivityVideoPlayer.this, "Opps! Error Occurred. Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadAdmobBannerAds() {
        mAdView = findViewById(R.id.adView);
        //mAdView.setAdSize(AdSize.SMART_BANNER);
        //mAdView.setAdUnitId(String.valueOf(R.string.admob_banner_ad_id));
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("25E662D1EDF4290CE580D9AA9FA945B9").build();
        mAdView.loadAd(adRequest);
    }

    private void findViewByIds() {
        videoView = findViewById(R.id.videoView);
        ibDownload = findViewById(R.id.ibDownload);
        ibShare = findViewById(R.id.ibShare);
    }

    private void videoPlayer() {
        mediaController = new MediaController(this);
        Intent intent = getIntent();
        videoPath = intent.getStringExtra("videoPath");
        videoUri = Uri.parse(videoPath);
        videoView.setVideoURI(videoUri);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
        mediaController.requestFocus();
        videoView.start();
    }
}
