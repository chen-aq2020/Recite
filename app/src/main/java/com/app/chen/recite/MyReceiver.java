package com.app.chen.recite;

import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity mainActivity = new MainActivity();
        CharSequence charSequence = mainActivity.random(true).get("questions") + "\n" + mainActivity.random(false).get("solution");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.app_widget);
        //remoteViews.setTextViewText(R.id.appwidget_text,charSequence);
        throw new UnsupportedOperationException("Not yet implemented");
    }
}