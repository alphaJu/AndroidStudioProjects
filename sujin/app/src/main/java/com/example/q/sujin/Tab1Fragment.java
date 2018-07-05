package com.example.q.sujin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    public final int EDIT_CONTACT_ACTIVITY_CODE = 1;

    private RecyclerView recyclerView;
    ContactListViewAdapter adapter;
    private static final int RC_LOCATION_CONTACTS_PERM = 123;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.contactlistView);
        recyclerView.setLayoutManager(new LinearLayoutManager(Tab1Fragment.this.getContext().getApplicationContext()));

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AddContactActivity.isUpdate = false;
                Intent goToAddContact = new Intent(getContext(),AddContactActivity.class);
                startActivity(goToAddContact);


            }
        });

        return view;
    }

    private class GetContact extends AsyncTask<String, String, List<ContactList>> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Fetching Contacts...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected List<ContactList> doInBackground(String... params) {

            List<ContactList> data = new ArrayList<ContactList>();
            String phoneNumber = "";
            String email = null;
            String displayName = "";
            Bitmap contactImage;
            Bitmap default_photo = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.ic_launcher);
            Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
            String _ID = ContactsContract.Contacts._ID;
            String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
            String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
            Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
            String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
            String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
            Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
            String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
            String DATA = ContactsContract.CommonDataKinds.Email.DATA;
            ContentResolver contentResolver = getContext().getContentResolver();
            Cursor cursor = contentResolver.query(CONTENT_URI, null,null, null, null);

            if (cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                    Uri my_contact_Uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, contact_id);
                    InputStream photo_stream = ContactsContract.Contacts.openContactPhotoInputStream(getContext().getContentResolver(), my_contact_Uri);

                    if(photo_stream != null){

                        BufferedInputStream buf = new BufferedInputStream(photo_stream);
                        Bitmap my_btmp = BitmapFactory.decodeStream(buf);
                        contactImage = my_btmp;

                    }

                    else{

                        contactImage = default_photo;
                    }

                    displayName = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                    int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

                    if (hasPhoneNumber > 0) {
                        // Query and loop for every phone number of the contact
                        Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

                        while (phoneCursor.moveToNext()) {

                            phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER)).trim() + (phoneCursor.isLast() ? "" : "\n");

                            if(phoneNumber.trim().isEmpty()){

                                continue;
                            }
                        }
                        phoneCursor.close();

                        Cursor emailCursor = contentResolver.query(EmailCONTENT_URI,null, EmailCONTACT_ID+ " = ?", new String[] { contact_id }, null);
                        while (emailCursor.moveToNext()) {

                            email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                        }

                        emailCursor.close();
                    }

                    else{

                        continue;
                    }
                    data.add(new ContactList(contact_id,contactImage, displayName,phoneNumber,email));
                    phoneNumber = "";
                    email = null;
                }
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<ContactList> result) {
            super.onPostExecute(result);

            Collections.sort(result,new CustomComparator());

            if (result != null) {
                adapter = new ContactListViewAdapter(getContext(),R.layout.list_item,result);
                recyclerView.setAdapter(adapter);
            }

            if (pDialog.isShowing())

                pDialog.dismiss();
        }
    }

    public class CustomComparator implements Comparator<ContactList> {

        @Override
        public int compare(ContactList o1, ContactList o2) {

            return o1.getContactName().toLowerCase().compareTo(o2.getContactName().toLowerCase());

        }

    }

    public void locationAndContactsTask() {

        String[] perms = {  Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE };

        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            // Have permissions, do the thing!
            new GetContact().execute();
        }
        else {
            // Ask for both permissions
            requestPermissions(perms, 7);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        locationAndContactsTask();

    }

}
