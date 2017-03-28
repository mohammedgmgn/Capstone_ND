package com.mahmoud.mohammed.capstone_nd.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.mahmoud.mohammed.capstone_nd.R;
import com.mahmoud.mohammed.capstone_nd.data.UpdaterService;
import com.mahmoud.mohammed.capstone_nd.ui.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class CollectionWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.app_name);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.collection_widget);
        views.setTextViewText(R.id.app_widget_text, widgetText);
        views.setTextColor(R.id.app_widget_text, Color.CYAN);
        views.setRemoteAdapter(R.id.widget_list,new Intent(context,WidgetListService.class));
        Intent launchIntent=new Intent(context, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(context,0,launchIntent,0);
        views.setOnClickPendingIntent(R.id.widget,pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if(UpdaterService.ACTION_WIDGET_UPDATED.equals(intent.getAction())) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetService.class)), R.id.widget_list);

        }    }
}

