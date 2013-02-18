package de.weidengraben.mfa.logic;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import de.weidengraben.mfa.model.Wochenkarte;

public class LoadingTask extends AsyncTask<Void, Object, Void> {

	int myProgress;
	Handler handler = null;
	private Context context;
	private boolean dataExists;

	public static final String URL = "http://studiwerk.cms.rdts.de/cgi-bin/cms?_SID=NEW&_bereich=system&_aktion=export_speiseplan&datum=%%%";

	public LoadingTask(Handler handler, Context c, boolean dataExists) {
		this.handler = handler;
		this.context = c;
		this.dataExists = dataExists;
	}

	@Override
	protected void onPostExecute(Void result) {
		Message m = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putBoolean("DONE", true);
		m.setData(bundle);
		handler.sendMessage(m);
	}

	@Override
	protected void onPreExecute() {
		Message m = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putBoolean("DONE", false);
		m.setData(bundle);
		handler.sendMessage(m);

		m = handler.obtainMessage();
		bundle = new Bundle();
		bundle.putInt("PROGRESS", 0);
		m.setData(bundle);
		handler.sendMessage(m);
	}

	@Override
	protected Void doInBackground(Void... params) {
		String weeknum = Integer.toString(DateHelper.getWeekNumber(DateHelper.getWeekToShow()));
		if (dataExists) {
			String data = DBHelper.getData(context, weeknum);
			FoodHelper.wochenkarte = Wochenkarte.deserialize(data);
		} else {
			DBHelper.resetData(context);
			try {
				ResponseHandler<String> responseHandler = new BasicResponseHandler();
				String[] dates = DateHelper.getWeekDaysToShow();
				int progressIntervall = 100 / dates.length;
				int progress = 0;
				for (String date : dates) {
					HttpGet httpGet = new HttpGet(URL.replaceAll("%%%", date));
					HttpParams httpParameters = new BasicHttpParams();
					int timeoutConnection = 20000;
					HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
					int timeoutSocket = 20000;
					HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
					DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
					String response = httpClient.execute(httpGet, responseHandler);
					FoodHelper.parseData(response);
					progress += progressIntervall;
					publishProgress(progress);
				}
				DBHelper.insertData(context, Wochenkarte.serialize(FoodHelper.wochenkarte), weeknum);
			} catch (Exception e) {
				Log.e("MFA", "error", e);
				Message m = handler.obtainMessage();
				Bundle bundle = new Bundle();
				bundle.putInt("ERROR", 0);
				m.setData(bundle);
				handler.sendMessage(m);
				return null;
			}
			Log.d("MFA", "Done with loading");
			Message m = handler.obtainMessage();
			Bundle bundle = new Bundle();
			bundle.putBoolean("DONE", true);
			m.setData(bundle);
			handler.sendMessage(m);
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Object... values) {
		Message m = handler.obtainMessage();
		Bundle bundle = new Bundle();
		bundle.putInt("PROGRESS", Integer.parseInt(values[0].toString()));
		m.setData(bundle);
		handler.sendMessage(m);
	}

}
