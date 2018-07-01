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

public class JSONParse {
    String dataParsed = "";
    String singleParsed = "";
    StringBuffer data = new StringBuffer();
    public void getJSON() {
        try {
            URL url =  new URL("https://api.myjson.com/bins/r1yci");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                data.append(line);
            }
            JSONArray JA = new JSONArray(data.toString());
            for(int i = 0; i < JA.length(); i++) {
                JSONObject JO = (JSONObject)JA.get(i);
                singleParsed = "Name : " + JO.get("name") + "\n" + "Contact : " + JO.get("contact") + "\n";
                dataParsed = dataParsed + singleParsed;
                MainActivity.commonList.add(singleParsed);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
