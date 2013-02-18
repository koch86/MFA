package de.weidengraben.mfa.logic;

import org.xml.sax.SAXException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import de.weidengraben.mfa.model.Wochenkarte;

public class FoodHelper {

	static Wochenkarte wochenkarte = new Wochenkarte();
	static int weeknumber = -1;
	static Handler handler = new FoodHandler();
	static private ProgressDialog pd = null;
	static Context context = null;

	static ListView list = null;
	static ListAdapter adapter = null;
	
	static boolean loading = false;

	static class FoodHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			Bundle b = msg.getData();
			Log.d("MFA", "Handling Message");
			if (b.containsKey("DONE") && b.getBoolean("DONE")) {
				weeknumber = DateHelper.getWeekNumber(DateHelper.getWeekToShow());
				adapter = new MensaListAdapter(context, wochenkarte, DBHelper.getSelectedMensa(context));
				if (list != null) {					
					list.setAdapter(adapter);
				}
				if (pd != null) {
					pd.dismiss();
				}
				Log.d("MFA", "Message: Done");
				loading = false;
			    Intent updateIntent = new Intent();
			    updateIntent.setAction("de.weidengraben.mfa.WIDGET_UPDATE");
			    context.sendBroadcast(updateIntent);
			} else if (b.containsKey("PROGRESS")) {
				if (pd != null) {					
					pd.setProgress(b.getInt("PROGRESS"));
				}
			} else if (b.containsKey("ERROR")) {
				if (pd != null) {
					pd.dismiss();
				}
				error();
			}
		}
	};
	
	public static void error() {
		pd.dismiss();
		Toast.makeText(context, "Die Daten konnten leider nicht geladen werden, Bitte versuch es später nochmal!", Toast.LENGTH_LONG).show();
	}
	
	public static void reset(Context c) {
		context = c;
		weeknumber = -1;
		wochenkarte = new Wochenkarte();
		DBHelper.resetData(c);
	}

	public static void requestWeekAdapter(Context c, ListView list, String mensa) {
		context = c;
		FoodHelper.list = list;
		if (DateHelper.getWeekNumber(DateHelper.getWeekToShow()) != weeknumber) {
			reload(c, true);
		}
		list.setAdapter(new MensaListAdapter(c, wochenkarte, mensa));
	}
	
	public static void requestDayAdapter(Context c, ListView list, String mensa, int dayindex) {
		context = c;
		FoodHelper.list = list;
		list.setAdapter(new MensaListAdapter(c, wochenkarte.get(dayindex).get(mensa), mensa));
	}
	
	public static String getFoodForWidget(Context c) {
		if (loading) return "";
		if (DateHelper.getWeekNumber(DateHelper.getWeekToShow()) != weeknumber) {
			reload(c, false);
		} else {			
			String mensa = DBHelper.getSelectedMensa(c);
			return wochenkarte.get(DateHelper.getTodayIndex()).get(mensa).overView;
		}
		return "";
	}

	public static int indexof(String[] array, String element) {
		for (int i = 0; i < array.length; i++) {
			if (array[i].equals(element)) return i;
		}
		return -1;
	}

	private static void reload(Context c, boolean progress) {
		loading = true;
		context = c;
		weeknumber = DateHelper.getWeekNumber(DateHelper.getWeekToShow());
		Boolean dataExists = checkDataExists(c);
		if (progress) {
			pd = new ProgressDialog(c);
			pd.setProgress(0);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setCancelable(false);
			pd.setCanceledOnTouchOutside(false);
			if (dataExists) {
				pd.setTitle("Lade Speisepläne vom Gerät...");
			} else {
				pd.setTitle("Lade Speisepläne vom Studiwerk...");
				reset(c);
			}
			pd.show();
		} else {
			pd = null;
		}
		Log.d("MFA", "Start Loading Task");
		LoadingTask lt = new LoadingTask(handler, c, dataExists);
		lt.execute();
	}

	public static void parseData(String response) {
		try {
			Xml.parse(response, new MensaContentHandler(wochenkarte));
		} catch (SAXException e) {
			Log.e("MFA", "error", e);
		};
	}

	public static boolean checkDataExists(Context c) {
		context = c;
		String data = DBHelper.getData(c, Integer.toString(DateHelper.getWeekNumber(DateHelper.getWeekToShow())));
		return data != null;
	}
	
}
