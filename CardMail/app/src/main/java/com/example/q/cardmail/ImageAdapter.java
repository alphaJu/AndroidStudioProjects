package com.example.q.cardmail;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Activity context;

    public static ArrayList<String> images = new ArrayList<String>();

    public ImageAdapter(Activity localContext) {
        context = localContext;
        images = getAllShownImagesPath(context);
    }

    public int getCount() {
        return images.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ImageView picturesView;

        if (convertView == null) {
            picturesView = new ImageView(context);
        } else {
            picturesView = (ImageView) convertView;
            picturesView.setAdjustViewBounds(true);

        }

        picturesView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        picturesView.setLayoutParams(new GridView.LayoutParams(320, 320));

        Glide.with(context).load(images.get(position)).into(picturesView);

        return picturesView;
    }

    public ArrayList<String> getAllShownImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;

        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);
        }
        return listOfAllImages;
    }

}
