package com.mahmoud.mohammed.capstone_nd.data;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mahmoud.mohammed.capstone_nd.adapter.MyAdapter;
import com.mahmoud.mohammed.capstone_nd.model.Book;
import com.mahmoud.mohammed.capstone_nd.remote.ApplicationController;
import com.mahmoud.mohammed.capstone_nd.remote.config;
import com.mahmoud.mohammed.capstone_nd.ui.DetailActivity;
import com.mahmoud.mohammed.capstone_nd.ui.MainActivity;

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

    public UpdaterService() {
        super("UpdaterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (!cm.getActiveNetworkInfo().isConnected() || cm.getActiveNetworkInfo() == null) {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
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
                         /*
                        book.setPublishedDate(volumeInfo.getString("publishedDate"));
                        String img=imageinfo.getString("smallThumbnail");
                        book.setImageUrl(img); */
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

        sendBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, false));


    }
}
