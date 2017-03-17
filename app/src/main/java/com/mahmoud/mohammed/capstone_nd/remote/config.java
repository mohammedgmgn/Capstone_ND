package com.mahmoud.mohammed.capstone_nd.remote;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by siko on 3/17/2017.
 */

public class config {
    public static URL BASE_URL;
    static {
   URL url=null;
        try {
            url=new URL("https://www.googleapis.com/books/v1/volumes?q=search+terms");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        BASE_URL=url;
    }
}
