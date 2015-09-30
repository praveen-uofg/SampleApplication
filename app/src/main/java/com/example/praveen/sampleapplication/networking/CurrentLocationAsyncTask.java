package com.example.praveen.sampleapplication.networking;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import com.example.praveen.sampleapplication.interfaceutils.JsonResultSetInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by praveen on 10/1/2015.
 */
public class CurrentLocationAsyncTask extends AsyncTask<Void,Void,String> {

    public JsonResultSetInterface jsonResult = null;

    private Context mContext;
    private double current_lat;
    private double current_lon;

    public CurrentLocationAsyncTask (Context context,double lat, double lon) {
        mContext = context;
        current_lat = lat;
        current_lon = lon;
    }

    @Override
    protected String doInBackground(Void... params) {
        List<Address> addresses;
        String result = "Can't get Current Location.";;
        try {
            Geocoder mGC = new Geocoder(mContext, Locale.ENGLISH);
            addresses = mGC.getFromLocation(current_lat,
                    current_lon, 1);
            if (addresses != null) {
                Address currentAddr = addresses.get(0);
                if (currentAddr.getSubLocality() != null) {
                    result = currentAddr.getSubLocality();

                } else if (null != currentAddr.getLocality()) {
                    result = currentAddr.getLocality();
                } else if (null != currentAddr.getAdminArea()){
                    result = currentAddr.getAdminArea();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(s!= null)
        jsonResult.processFinished(s);
    }
}
