package com.example.praveen.sampleapplication.networking;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;

import com.example.praveen.sampleapplication.ListViewItems;
import com.example.praveen.sampleapplication.interfaceutils.JsonResultSetInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 9/26/2015.
 */
public class DownloadJSON extends AsyncTask<String,Void,ArrayList> {


    public JsonResultSetInterface jsonResult = null;
    ProgressDialog mProgressDialog;
    private Context mContext = null;
    private ArrayList<ListViewItems> arrayList = null;

    private JSONObject jsonObject;
    private JSONArray jsonArray;

    private JSONObject data;
    private JSONObject jsonId;
    private JSONObject jsonCategories;

    private double current_lat;
    private double current_lon;

    private String rest_lat;
    private String rest_lon;



    public DownloadJSON(Context context,double lat,double lon) {
        mContext = context;
        current_lat = lat;
        current_lon = lon;
        arrayList = new ArrayList<ListViewItems>();
    }

    @Override
    protected ArrayList doInBackground(String... params) {
        jsonObject = JSONFunction.getJsonFromUrl(params[0]);

        if (jsonObject != null) {
            try {
                data = jsonObject.getJSONObject("data");
                for (int i = 0; i < 30; i++) {
                    if (data.has(String.valueOf(i))) {
                        jsonId = data.getJSONObject(String.valueOf(i));
                        ListViewItems items = new ListViewItems();
                        float[] results = new float[1];

                        items.setRest_name(jsonId.getString("OutletName"));
                        items.setOffers(jsonId.getString("NumCoupons"));
                        items.setImageResUrl(jsonId.getString("LogoURL"));
                        items.setNeighbourHoodName(jsonId.getString("NeighbourhoodName"));

                        rest_lat = jsonId.getString("Latitude");
                        rest_lon = jsonId.getString("Longitude");

                        Location.distanceBetween(current_lat, current_lon, Double.valueOf(rest_lat),
                                Double.valueOf(rest_lon), results);

                        items.setDistance(Math.abs(results[0]));


                        List<String> allCat = new ArrayList<>();
                        jsonArray = jsonId.getJSONArray("Categories");
                        for (int j = 0; j < jsonArray.length(); j++) {
                            jsonCategories = jsonArray.getJSONObject(j);
                            if (jsonCategories.getString("CategoryType").contentEquals("Cuisine")) {
                                String catg = jsonCategories.getString("Name");
                                allCat.add(catg);
                                Log.v(DownloadJSON.class.getName(), "catg = " + catg);
                            }

                        }
                        items.setCategories(allCat);

                        arrayList.add(items);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return arrayList;
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog =  new ProgressDialog(mContext);
        mProgressDialog.setTitle("Coupon Dunia");
        mProgressDialog.setMessage("Loading");
        mProgressDialog.setIndeterminate(false);

        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        super.onPostExecute(arrayList);
        mProgressDialog.dismiss();
        jsonResult.processFinished(arrayList);
    }
}
