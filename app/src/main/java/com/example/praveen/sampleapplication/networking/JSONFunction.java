package com.example.praveen.sampleapplication.networking;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by praveen on 9/26/2015.
 */
public class JSONFunction {

    public static JSONObject getJsonFromUrl(String Url) {
        Log.v(JSONFunction.class.getName(), "url = " + Url);
        InputStream inputStream;
        String result ;
        JSONObject jsonObject= null;

        try {
            URL url = new URL(Url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            inputStream = conn.getInputStream();
            Log.v(JSONFunction.class.getName(), "inputstream = " + inputStream);

            result = convertToString(inputStream);
            Log.v(JSONFunction.class.getName(), "result  = " + result);

            jsonObject = new JSONObject(result);
            conn.disconnect();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return  jsonObject;

    }

    private static String convertToString(InputStream inputStream) {
        String resultInString = null;
        try {
            BufferedReader reader =  new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"),8);

            StringBuilder sb =new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                sb.append(line + "\n");
            }
            inputStream.close();

            resultInString = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultInString;
    }

}
