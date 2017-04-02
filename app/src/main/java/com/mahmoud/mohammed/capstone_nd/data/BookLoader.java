package com.mahmoud.mohammed.capstone_nd.data;

import android.content.Context;
import android.content.CursorLoader;
import android.net.Uri;

/**
 * Created by siko on 3/21/2017.
 */

public class BookLoader extends CursorLoader {
    //for all items
    public static BookLoader newAllArticlesInstance(Context context) {
        return new BookLoader(context, BookContract.BookItems.buildDirUri());
    }

    //for specific item

    public static BookLoader newInstanceForItemId(Context context, long itemId) {
        BookLoader loader = new BookLoader(context, BookContract.BookItems.buildItemUri(itemId));
        return loader;
    }

    private BookLoader(Context context, Uri uri) {
        super(context, uri, Query.PROJECTION, null, null, BookContract.BookItems.DEFAULT_SORT);
    }

    public interface Query {
        String[] PROJECTION = {
                BookContract.BookItems._ID,
                BookContract.BookItems.SERVER_ID,
                BookContract.BookItems.TITLE,
                BookContract.BookItems.DESCRIPTION,
                BookContract.BookItems.PHOTO_URL,
        };
        int _ID = 0;
        int SERVER_ID = 1;
        int TITLE = 2;
        int DESCRIPTION = 3;
        int PHOTO_URL = 4;
    }


}
