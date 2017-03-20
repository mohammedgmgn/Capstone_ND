package com.mahmoud.mohammed.capstone_nd.data;

import android.app.IntentService;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

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
if(!cm.getActiveNetworkInfo().isConnected()||cm.getActiveNetworkInfo()==null){
    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
    return;
}
        sendBroadcast(new Intent(BROADCAST_ACTION_STATE_CHANGE).putExtra(EXTRA_REFRESHING, true));
        ArrayList<ContentProviderOperation>cpo=new ArrayList<>();
        //refresh content provider getting data from server

    }
}
