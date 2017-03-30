package com.mahmoud.mohammed.capstone_nd;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by siko on 3/30/2017.
 */

public class Helper {
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
