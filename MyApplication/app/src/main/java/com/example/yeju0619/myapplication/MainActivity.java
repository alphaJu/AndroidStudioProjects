package com.example.yeju0619.myapplication;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button click;
    ListView listView;
    static ArrayList<String> commonList = new ArrayList<String>();
    static String[] contact;
    TextView textView;


    static int x = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.TV);

        final JSONParse JP = new JSONParse();
        new Thread() {
            public void run() {
                JP.getJSON();
            }
        }.start();

        int i = commonList.size();
        contact = new String[commonList.size()];
        int size = 0;
        for(String temp : commonList) {
            contact[size++] = temp;
        }
/*
        listView = (ListView) findViewById(R.id.LV);
        ListAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contact);
        listView.setAdapter(myAdapter);*/

        textView.setText(String.valueOf(i));


/*
       click = (Button) findViewById(R.id.button);
        data = (TextView) findViewById(R.id.fetcheddata);

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchData process = new fetchData();
                process.execute();
            }
        });*/

    //    fetchData process = new fetchData();
    //    process.execute();



    }




}
