package com.example.android.gmsbigblackbox;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.RemoteViews;

import com.example.android.gmsbigblackbox.database.AppDatabase;
import com.example.android.gmsbigblackbox.database.NpcCardEntry;

import java.util.concurrent.Executor;

/**
 * Implementation of App Widget functionality.
 */
public class NpcWidget extends AppWidgetProvider {
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_npc);
        views.setTextViewText(R.id.widget_default_tv, widgetText);
        views.setViewVisibility(R.id.widget_default_tv, View.VISIBLE);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void setNpcWidgetText(Context context, int position, String name, String game, String role) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, NpcWidget.class));
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_npc);
        remoteViews.setViewVisibility(R.id.widget_default_tv, View.GONE);
        remoteViews.setTextViewText(R.id.widget_tv_name, name);
        remoteViews.setTextViewText(R.id.widget_tv_game, game);
        remoteViews.setTextViewText(R.id.widget_tv_role, role);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_tv_name);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_tv_game);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_tv_role);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}

