package com.mahmoud.mohammed.capstone_nd.data;

/**
 * Created by siko on 3/17/2017.
 */

public class BookContract {
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

}
}
