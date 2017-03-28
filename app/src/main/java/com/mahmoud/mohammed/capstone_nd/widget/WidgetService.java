package com.mahmoud.mohammed.capstone_nd.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.BookContract;
import com.mahmoud.mohammed.capstone_nd.data.UpdaterService;
import com.mahmoud.mohammed.capstone_nd.ui.MainActivity;

/**
 * Created by siko on 3/25/2017.
 */
//the adapter for list view :)
public class WidgetService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
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

    public WidgetService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CollectionWidgetProvider.class));

        Uri bookUri = BookContract.BookItems.buildDirUri();

        Cursor data = getContentResolver().query(bookUri, BOOK_COLUMNS, null, null, null);

        if(data == null){
            return;
        }

        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        String title = data.getString(POSITION_TITLE);


        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.collection_widget);
            String titletext = data.getString(POSITION_TITLE);
            views.setTextViewText(R.id.app_widget_text, titletext);

            Intent launchIntent=new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,0,launchIntent,0);
            views.setOnClickPendingIntent(R.id.widget,pendingIntent);
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }
}
