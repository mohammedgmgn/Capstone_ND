package com.mahmoud.mohammed.capstone_nd.remote;

import com.mahmoud.mohammed.capstone_nd.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by siko on 3/17/2017.
 */

public class config {
    public static URL BASE_URL;
    public static String API_KEY;


    static {
   URL url=null;
        API_KEY=null;
        try {
            API_KEY=BuildConfig.THE_BOOK_API_KEY;
            url=new URL("https://www.googleapis.com/books/v1/volumes?q=search+terms");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BASE_URL=url;
    }
}
