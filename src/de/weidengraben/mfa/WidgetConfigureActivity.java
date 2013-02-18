package de.weidengraben.mfa;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class WidgetConfigureActivity extends Activity {

	@Override
	protected void onStart() {
		super.onStart();

		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int mAppWidgetId = -1;
		if (extras != null) {
			mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}
		if (mAppWidgetId >= 0) {
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
			RemoteViews views = new RemoteViews(getApplicationContext().getPackageName(), R.layout.widget);
			appWidgetManager.updateAppWidget(mAppWidgetId, views);
			Intent resultValue = new Intent();
			resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
			setResult(RESULT_OK, resultValue);
			Intent i = new Intent("de.weidengraben.mfa.APPWIDGET_MANUAL_UPDATE");
			getApplicationContext().sendBroadcast(i);
			finish();
		}
	}
}
