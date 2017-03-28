package com.mahmoud.mohammed.capstone_nd.widget;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.mahmoud.mohammed.capstone_nd.data.BookContract;

/**
 * Created by siko on 3/25/2017.
 */

public class WidgetListService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            public static final int POSITION_ID = 0;
            public static final int POSITION_SERVER_ID = 1;
            public static final int POSITION_TITLE = 2;
            public static final int POSITION_DESCRIPTION = 3;
            public static final int POSITION_PHOTO_URL = 4;

            private final String[] BOOK_COLUMNS = {
                    BookContract.BookItems._ID,
                    BookContract.BookItems.SERVER_ID,
                    BookContract.BookItems.TITLE,
                    BookContract.BookItems.DESCRIPTION,
                    BookContract.BookItems.PHOTO_URL,

            };

            @Override
            public void onCreate() {
                data = getContentResolver().query(BookContract.BookItems.buildDirUri(), BOOK_COLUMNS, null, null, null);

            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }
                data = getContentResolver().query(BookContract.BookItems.buildDirUri(), BOOK_COLUMNS, null, null, null);

            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                }
            }

            @Override
            public int getCount() {
                if (data != null) {
                    return data.getCount();
                }
                return 0;
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION || data == null) {
                    return null;
                }
                data.moveToPosition(position);

                RemoteViews remoteViews = new RemoteViews(getPackageName(), android.R.layout.simple_list_item_1);
                String title = data.getString(POSITION_TITLE);
                String desc = data.getString(POSITION_DESCRIPTION);
                remoteViews.setTextViewText(android.R.id.text1, title);
                remoteViews.setTextColor(android.R.id.text1, Color.CYAN);

                return remoteViews;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public long getItemId(int position) {
                data.moveToPosition(position);
                return data.getInt(POSITION_ID);
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }


        };
    }
}