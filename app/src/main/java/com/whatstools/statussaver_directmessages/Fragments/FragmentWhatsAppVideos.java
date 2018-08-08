package com.whatstools.statussaver_directmessages.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whatstools.statussaver_directmessages.Activities.ActivityVideoPlayer;
import com.whatstools.statussaver_directmessages.Adapters.GalleryAdapter;
import com.whatstools.statussaver_directmessages.Extras.WhatsToolsConstants;
import com.whatstools.statussaver_directmessages.R;
import com.whatstools.statussaver_directmessages.Utilities.RecyclerTouchListener;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class FragmentWhatsAppVideos extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<File> videos;
    private GalleryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whatsapp_videos, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_videos);
        videos = new ArrayList<>();

        Intent i = getActivity().getIntent();
        String folderName = i.getStringExtra(WhatsToolsConstants.WHATSAPP_FOLDER);

        proceedAfterPermission(folderName);
        mAdapter = new GalleryAdapter(getContext(), videos);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("images", videos);
                bundle.putInt("position", position);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");*/
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.valueOf(videos.get(position))));
                intent.setDataAndType(Uri.parse(String.valueOf(videos.get(position))), "video/mp4");
                startActivity(intent);*/
                Intent intent = new Intent(getContext(), ActivityVideoPlayer.class);
                intent.putExtra("videoPath", videos.get(position).toString());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    private void proceedAfterPermission(String folderName) {
        File folder = new File(Environment.getExternalStorageDirectory().toString() + "/" + folderName + "/Media/.Statuses");
        if (folder.exists()) {
            File[] allFiles = folder.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return (name.endsWith(".mp4"));
                }
            });

            Arrays.sort(allFiles, new Comparator<File>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                public int compare(File f1, File f2) {
                    return Long.compare(f2.lastModified(), f1.lastModified());
                }
            });

            for (int i = 0; i < allFiles.length; i++) {
                videos.add(allFiles[i]);
            }
        }
    }
}
