package com.mahmoud.mohammed.capstone_nd;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.provider.SyncStateContract;

import com.google.gson.Gson;
import com.mahmoud.mohammed.capstone_nd.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by siko on 3/30/2017.
 */

public class Helper {
    public static final String SharedPreferencesFileName = "favorit";

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void addToFavorite(Context context, String BookId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName() + SharedPreferencesFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(BookId, BookId);
        editor.apply();
    }

    public static void removeFromFavorite(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName() + SharedPreferencesFileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(id);
        editor.apply();    // Or commit()
    }

    public static boolean ifExist(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName() + SharedPreferencesFileName, Context.MODE_PRIVATE);
        return sharedPreferences.contains(id);
    }

    public static List<String> getAllFavIds(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName() + SharedPreferencesFileName, Context.MODE_PRIVATE);
        ArrayList<String> ids = new ArrayList<String>();
        Map<String, ?> keys = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            ids.add(entry.getValue().toString());
        }
        return ids;
    }

    public static Book getSpecificId(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getPackageName() + SharedPreferencesFileName, Context.MODE_PRIVATE);
        Gson gson = new Gson();

        Book obj = gson.fromJson(sharedPreferences.getString(id, ""), Book.class);
        return obj;
    }

}
