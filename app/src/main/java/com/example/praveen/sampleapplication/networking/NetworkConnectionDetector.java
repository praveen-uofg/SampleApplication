package com.example.praveen.sampleapplication.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by praveen on 9/27/2015.
 */
public class NetworkConnectionDetector {

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (manager != null) {
            NetworkInfo [] info = manager.getAllNetworkInfo();
            if (info != null) {
                for (int i=0;i<info.length;i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
                return  false;
            }
        }
        return  false;
    }
}
