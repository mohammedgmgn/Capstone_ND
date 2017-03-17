package com.mahmoud.mohammed.capstone_nd.remote;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mahmoud.mohammed.capstone_nd.ui.MainActivity;

import org.json.JSONObject;

/**
 * Created by siko on 3/17/2017.
 */

public class RemoteEndpointUtil {
    /*
    RequestQueue requestQueue;
    requestQueue = Volley.newRequestQueue(this);
    sendJsonRequest(url);
    public  void sendJsonRequest(String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, (String) null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Toast.makeText(MainActivity.this,jsonObject.toString(),Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        requestQueue.add(request);


    }
    */

}
