package com.mahmoud.mohammed.capstone_nd.data;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mahmoud.mohammed.capstone_nd.Helper;
import com.mahmoud.mohammed.capstone_nd.remote.ApplicationController;
import com.mahmoud.mohammed.capstone_nd.remote.config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by siko on 3/19/2017.
 */

public class UpdaterService extends IntentService {
    public static final String BROADCAST_ACTION_STATE_CHANGE = "com.mahmoud.mohammed.capstone_nd.intent.action.STATE_CHANGE";
    public static final String EXTRA_REFRESHING = "com.mahmoud.mohammed.capstone_nd.intent.extra.REFRESHING";
    public static final String ACTION_WIDGET_UPDATED = "android.appwidget.action.ACTION_WIDGET_UPDATED";


    public UpdaterService() {
        super("UpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //NetworkInfo info = cm.getActiveNetworkInfo();

        if (!Helper.isNetworkConnected(getApplicationContext())) {
            // Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        sendBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));
        final ArrayList<ContentProviderOperation> cpo = new ArrayList<>();
        //refresh content provider getting data from server
        final Uri dirUri = BookContract.BookItems.buildDirUri();
        cpo.add(ContentProviderOperation.newDelete(dirUri).build());
        String url = config.BASE_URL;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //    Toast.makeText(MainActivity.this,response+"",Toast.LENGTH_SHORT).show();
                try {

                    JSONArray mResultArray = response.getJSONArray("items");
                    for (int i = 0; i < mResultArray.length(); i++) {
                        ContentValues values = new ContentValues();
                        JSONObject mResultObject = mResultArray.getJSONObject(i);
                        values.put(BookContract.BookItems.SERVER_ID, mResultObject.getString("id"));
                        JSONObject volumeInfo = mResultObject.getJSONObject("volumeInfo");
                        values.put(BookContract.BookItems.TITLE, volumeInfo.getString("title"));
                        values.put(BookContract.BookItems.DESCRIPTION, volumeInfo.getString("description"));
                        JSONObject imageinfo = volumeInfo.getJSONObject("imageLinks");
                        values.put(BookContract.BookItems.PHOTO_URL, imageinfo.getString("smallThumbnail"));
                        cpo.add(ContentProviderOperation.newInsert(dirUri).withValues(values).build());
                    }
                    try {
                        getContentResolver().applyBatch(BookContract.CONTENT_AUTHORITY, cpo);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    } catch (OperationApplicationException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        ApplicationController.getInstance().addToRequestQueue(request);
        // sendBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));


    }
}
