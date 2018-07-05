package com.example.q.sujin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private static final int RC_LOCATION_CONTACTS_PERM = 123;

    public static final int IMAGE_GALLERY_REQUEST = 20;
    public Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        //Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "CONTACTS");
        adapter.addFragment(new Tab2Fragment(), "IMAGES");
        adapter.addFragment(new Tab3Fragment(), "CARD");
        viewPager.setAdapter(adapter);
    }

    /* FOLLOWING ARE FUNCTIONS NEEDED FOR TAB 3 CARDS */

    //Function invoked when "CREATE" button is pressed.
    public void createCard(View view) {
        //Send inputs to CardActivity.
        EditText senderInput = findViewById(R.id.sender);
        String sender = senderInput.getText().toString();
        EditText messageInput = findViewById(R.id.message);
        String message = messageInput.getText().toString();
        EditText receiverInput = findViewById(R.id.receiver);
        String receiver = receiverInput.getText().toString();
        Intent i = new Intent(this, CardActivity.class);
        i.putExtra("sender", sender);
        i.putExtra("message", message);
        i.putExtra("receiver", receiver);
        if (imageUri != null)
            i.putExtra("selectedPicture", imageUri.toString());
        startActivity(i);
    }

    //Function invoked when "SELECT PICTURE" button is pressed.
    public void selectPicture(View view) {

        //Display gallery and get picture input.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent,IMAGE_GALLERY_REQUEST);

    }

    //Function invoked when intents call startActivityForResult.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                imageUri = data.getData();
                InputStream inputStream;

                try {
                    //Get selected image.
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedPicture = BitmapFactory.decodeStream(inputStream);

                    //Update picture on tab3.
                    ImageView imageHolder = (ImageView) findViewById(R.id.pictureHolder);
                    imageHolder.setImageBitmap(selectedPicture);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed to load image", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}