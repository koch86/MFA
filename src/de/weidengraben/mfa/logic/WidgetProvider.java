package de.weidengraben.mfa.logic;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import de.weidengraben.mfa.R;
import de.weidengraben.mfa.WochenActivity;
import de.weidengraben.mfa.interfaces.IConstants;

public class WidgetProvider extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		update(context);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		update(context);
	}

	
	private void update(Context context) {
		Log.d("MFA", "WIDGET_PROVIDER");
		AppWidgetManager man = AppWidgetManager.getInstance(context);
	    int[] ids = man.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
		for (int i = 0; i < ids.length; i++) {
			int appWidgetId = ids[i];
			Intent intent = new Intent(context, WochenActivity.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			views.setOnClickPendingIntent(R.id.widget_dayname, pendingIntent);
			views.setOnClickPendingIntent(R.id.widget_essen, pendingIntent);
			String food = FoodHelper.getFoodForWidget(context);
			Log.d("MFA", "Got String " + food);
			
			if (FoodHelper.loading && (food == null || food.equals(""))) {
				views.setCharSequence(R.id.widget_essen, "setText", "Lade Daten");
			} else {
				views.setCharSequence(R.id.widget_essen, "setText", food);
			}
			views.setCharSequence(R.id.widget_dayname, "setText", IConstants.WOCHE[DateHelper.getTodayIndex()]);
			man.updateAppWidget(appWidgetId, views);
		}
	}
}
