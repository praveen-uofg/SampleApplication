package com.example.praveen.sampleapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.praveen.sampleapplication.Utils.ListViewItemComparator;
import com.example.praveen.sampleapplication.interfaceutils.JsonResultSetInterface;
import com.example.praveen.sampleapplication.networking.CurrentLocationAsyncTask;
import com.example.praveen.sampleapplication.networking.DownloadJSON;
import com.example.praveen.sampleapplication.networking.NetworkConnectionDetector;

import java.util.ArrayList;
import java.util.Collections;


public class MainActivity extends Activity implements JsonResultSetInterface,LocationListener{

    private final static  String URL ="http://staging.couponapitest.com/task_data.txt";

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60  * 3; // 3 minute

    private String mLocationProvider = null;

    private DownloadJSON downloadJSON = null;
    private LocationManager mLocationManager = null;
    private ArrayList arrayList = null;
    private AlertDialog alertDialog = null;

    private double mCurrentLat;
    private double mCurrentLon;
    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList = new ArrayList();
        mLocationProvider = initializeLocationManager();
        textView =(TextView)findViewById(R.id.current_location);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(MainActivity.class.getName(), "onStart()");
        if (NetworkConnectionDetector.isNetworkConnectionAvailable(getApplicationContext())) {
            if (!mLocationManager.isProviderEnabled(mLocationProvider)) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                showSettingsAlert("GPS settings","GPS is not enabled. Do you want to go to settings menu?",intent);
            } else {
                updateLocation();
                startJsonAsyncTask();
            }
        } else {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            showSettingsAlert("No Internet", "You are not connected to a network.Do you want to go to settings menu", intent);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(MainActivity.class.getName(), "onPause()");
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        Log.d(MainActivity.class.getName(), "onStop()");
        super.onStop();
        if (downloadJSON != null) {
            downloadJSON.cancel(true);
            downloadJSON = null;
        }
    }

    @Override
    protected void onDestroy() {
        Log.d(MainActivity.class.getName(), "onDestroy()");
        super.onDestroy();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(this);
            mLocationManager = null;
        }
    }

    private void setAdapter() {
        Collections.sort(arrayList, new ListViewItemComparator());
        ListAdapter listAdapter = new ListAdapter(this, arrayList);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(listAdapter);
    }

    @Override
    public void processFinished(ArrayList arrayLists) {
        if (arrayLists != null) {
            arrayList = arrayLists;
            setAdapter();
        } else {
            showSettingsAlert("Details", "Unable to Fetch Results, Please Try later", null);
        }
    }

    @Override
    public void processFinished(String s) {
        if (s!=null) {
            textView.setText(s);
        }
    }


    public void showSettingsAlert(String title , String message, final Intent intent){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (intent != null) {
                    startActivity(intent);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
       alertDialog =  builder.show();
    }

    private void startJsonAsyncTask() {
        downloadJSON = new DownloadJSON(this, mCurrentLat, mCurrentLon);
        downloadJSON.jsonResult = this;
        downloadJSON.execute(URL);
    }

    private String initializeLocationManager() {
        mLocationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

        /*}  else if (mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            return  LocationManager.NETWORK_PROVIDER;
        } else {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            showSettingsAlert("GPS settings","GPS is not enabled. Do you want to go to settings menu?",intent);
        }*/
        return LocationManager.GPS_PROVIDER;
    }

    private void updateLocation() {
        if ((mLocationManager != null) && (mLocationProvider != null)) {
            Location mLocation = mLocationManager.getLastKnownLocation(mLocationProvider);
            if (mLocation != null) {
                mCurrentLat = mLocation.getLatitude();
                mCurrentLon = mLocation.getLongitude();
                Log.v(MainActivity.class.getName(), "updateLocation()");

       /*         List<Address> addresses;
                try {
                    Geocoder mGC = new Geocoder(this, Locale.ENGLISH);
                    addresses = mGC.getFromLocation(mLocation.getLatitude(),
                            mLocation.getLongitude(), 1);
                    Log.v(MainActivity.class.getName(), "updateLocation() list addresses = "+addresses);
                    if (addresses != null) {
                        Address currentAddr = addresses.get(0);
                        if (currentAddr.getSubLocality() != null) {
                            Log.v(MainActivity.class.getName(), "updateLocation() setTextView = "+currentAddr.getSubLocality());
                            textView.setText(currentAddr.getSubLocality());
                        } else if (null != currentAddr.getLocality()) {
                            Log.v(MainActivity.class.getName(), "updateLocation() setTextView = "+currentAddr.getLocality());
                            textView.setText(currentAddr.getLocality());
                        } else {
                            Log.v(MainActivity.class.getName(), "updateLocation() setTextView = "+currentAddr.getAdminArea());
                            textView.setText(currentAddr.getAdminArea());
                        }
                        Log.v(MainActivity.class.getName(), "updateLocation() TextView = "+textView.getText());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                CurrentLocationAsyncTask currentLocationAsyncTask = new CurrentLocationAsyncTask(this,mCurrentLat,mCurrentLon);
                currentLocationAsyncTask.execute();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.v(MainActivity.class.getName(), "onProviderEnabled("+provider+")");
        mLocationProvider = provider;
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.v(MainActivity.class.getName(), "onProviderDisabled("+provider+")");
        mLocationProvider = null;
        if (alertDialog != null && !alertDialog.isShowing()) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            showSettingsAlert("GPS settings", "GPS is not enabled. Please Enable GPS for Better Support", intent);
        }

    }
}
