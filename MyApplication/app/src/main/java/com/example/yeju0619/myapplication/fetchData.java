/*package com.example.yeju0619.myapplication;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

public class fetchData extends AsyncTask<Void, Void, Void>{
    String data = "";
    String dataParsed = "";
    String singleParsed = "";
    ArrayList<String> arrayList = new ArrayList<String>();
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url =  new URL("https://api.myjson.com/bins/r1yci");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
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

        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.contact = new String[MainActivity.commonList.size()];
        int size = 0;
        for(String temp : MainActivity.commonList) {
            MainActivity.contact[size++] = temp;
        }

    //    MainActivity.textView.setText(MainActivity.commonList.get(1));
    }
}*/
