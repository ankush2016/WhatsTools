package com.whatstools.statussaver_directmessages.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.whatstools.statussaver_directmessages.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class SlideshowDialogFragment extends DialogFragment {

    private ArrayList<File> images;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private int selectedPosition = 0;

    private AdView mAdView;

    static SlideshowDialogFragment newInstance() {
        SlideshowDialogFragment f = new SlideshowDialogFragment();
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_slider, container, false);
        viewPager = view.findViewById(R.id.viewpager);
        mAdView = view.findViewById(R.id.adView);

        loadAdmobBannerAds();

        images = (ArrayList<File>) getArguments().getSerializable("images");
        selectedPosition = getArguments().getInt("position");

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerOnPageChangeListener);

        setCurrentItem(selectedPosition);
        return view;
    }

    private void loadAdmobBannerAds() {
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("25E662D1EDF4290CE580D9AA9FA945B9").build();
        mAdView.loadAd(adRequest);
    }

    public void setCurrentItem(int poition) {
        viewPager.setCurrentItem(poition);
    }

    ViewPager.OnPageChangeListener viewPagerOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    public class MyViewPagerAdapter extends PagerAdapter {

        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.image_fullscreen, container, false);
            ImageView imageViewPreview = view.findViewById(R.id.image_preview);
            ImageButton ibDownload = view.findViewById(R.id.bDownload);
            ImageButton ibShare = view.findViewById(R.id.bShare);
            final File file = images.get(position);
            final Uri imageUri = Uri.fromFile(file);

            Glide.with(getContext()).load(imageUri).into(imageViewPreview);
            container.addView(view);

            ibDownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String descPath = Environment.getExternalStorageDirectory().toString() + "/WhatsTools/WhatsTools Images/";
                    File desc = new File(descPath);
                    if (!desc.exists()) {
                        desc.mkdir();
                    }
                    desc = new File(descPath + file.getName());
                    try {
                        FileUtils.copyFile(file, desc);
                        Toast.makeText(getContext(), "Download Successful\n" + desc.getAbsolutePath(), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getContext(), "Opps! Error Occurred. Please try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            ibShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/jpeg");
                    intent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    startActivity(Intent.createChooser(intent, "Share Image"));
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}
