package com.example.q.cardmail;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Tab2Images extends Fragment {
    private static final String TAG = "Tab2Images";

    GridView gallery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2_images, container, false);
        gallery = (GridView) view.findViewById(R.id.galleryGridView);

        String[] perms = { Manifest.permission.READ_EXTERNAL_STORAGE };

        if (!(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            // Have permissions, do the thing!
            requestPermissions(perms, 1);
        }

        ImageAdapter imageAdapter = new ImageAdapter(getActivity());
        gallery.setAdapter(imageAdapter);


        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), FullImageActivity.class);
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        return view;
    }
}