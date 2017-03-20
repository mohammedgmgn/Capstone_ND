package com.mahmoud.mohammed.capstone_nd.data;

import android.net.Uri;

/**
 * Created by siko on 3/17/2017.
 */

public class BookContract {
    public static final String CONTENT_AUTHORITY = "com.mahmoud.mohammed.capstone_nd";
    public static final Uri BASE_URI = Uri.parse("content://com.mahmoud.mohammed.capstone_nd");
    interface BookColumns{
        /** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
        String _ID = "_id";
        /** Type: TEXT */
        String SERVER_ID = "server_id";
        /** Type: TEXT NOT NULL */
        String TITLE = "title";
        /** Type: TEXT NOT NULL */
        String DESCRIPTION = "desc";
        /** Type: TEXT NOT NULL */
        String PHOTO_URL = "photo_url";
    }
public static class BookItems implements BookColumns{
    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.mahmoud.mohammed.capstone_nd.items";
    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.mahmoud.mohammed.capstone_nd.items";
    /** Matches: /items/ */
    public static Uri buildDirUri() {
        return BASE_URI.buildUpon().appendPath("items").build();
    }

    /** Matches: /items/[_id]/ */
    public static Uri buildItemUri(long _id) {
        return BASE_URI.buildUpon().appendPath("items").appendPath(Long.toString(_id)).build();
    }

    /** Read item ID item detail URI. */
    public static long getItemId(Uri itemUri) {
        return Long.parseLong(itemUri.getPathSegments().get(1));
    }

}
}
