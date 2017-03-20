package com.mahmoud.mohammed.capstone_nd.remote;

import com.mahmoud.mohammed.capstone_nd.BuildConfig;
import com.mahmoud.mohammed.capstone_nd.R;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by siko on 3/17/2017.
 */

public class config {
    public static String API_KEY=BuildConfig.THE_BOOK_API_KEY;
    public static String BASE_URL="https://www.googleapis.com/books/v1/volumes?q=time&printType=magazines&"+ API_KEY;

}
