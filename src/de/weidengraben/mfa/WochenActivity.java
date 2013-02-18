package de.weidengraben.mfa;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import de.weidengraben.mfa.interfaces.IConstants;
import de.weidengraben.mfa.logic.DBHelper;
import de.weidengraben.mfa.logic.FoodHelper;

public class WochenActivity extends Activity implements OnItemSelectedListener, OnItemClickListener {

	ProgressDialog progressDialog;
	public boolean spinnerInit = false;
	private ListView listView;
	private String mensa = null;

	@Override
	public void onStart() {
		Log.d("MFA", "onStart()");
		spinnerInit = false;
		super.onStart();
		setContentView(R.layout.woche);
		Spinner s = (Spinner) findViewById(R.id.mensaauswahl);
		mensa = DBHelper.getSelectedMensa(this);
		s.setSelection(FoodHelper.indexof(IConstants.MENSA_ID, mensa));
		s.setOnItemSelectedListener(this);
		listView = (ListView) findViewById(R.id.essensliste);
		listView.setOnItemClickListener(this);
		FoodHelper.requestWeekAdapter(this, listView, mensa);
	}

	private void forceUpdate() {
		FoodHelper.reset(this);
		String mensa = DBHelper.getSelectedMensa(this);
		FoodHelper.requestWeekAdapter(this, listView, mensa);
	}

	@Override
	public void onItemSelected(AdapterView<?> a, View v, int position, long id) {
		if (!spinnerInit) { // To Avoid click if nothing was clicked .... :-(
			spinnerInit = !spinnerInit;
			return;
		}
		DBHelper.setSelectedMensa(this, IConstants.MENSA_ID[position]);
		FoodHelper.requestWeekAdapter(this, listView, IConstants.MENSA_ID[position]);
		
		Intent updateIntent = new Intent();
	    updateIntent.setAction("de.weidengraben.mfa.WIDGET_UPDATE");
	    getApplicationContext().sendBroadcast(updateIntent);
	}

	public void error() {
		Log.d("MFA", "MensaFoodApp.error()");
		progressDialog.dismiss();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("SORRY. Das Studiwerk ist wohl aktuell nicht verfügbar... Bitte versuch es später nochmal!")
				.setCancelable(false).setPositiveButton("ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						WochenActivity.this.finish();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings_update:
			askForUpdate();
			return true;
		case R.id.settings_update_widget:
			Intent updateIntent = new Intent();
		    updateIntent.setAction("de.weidengraben.mfa.WIDGET_UPDATE");
		    getApplicationContext().sendBroadcast(updateIntent);
			return true;
		}
		return false;
	}

	public void askForUpdate() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Alle geladenen Datensätze werden gelöscht! Sicher?").setCancelable(false)
				.setPositiveButton("Ja klar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						forceUpdate();
					}
				}).setNegativeButton("Nein, doch nicht", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent i = new Intent(WochenActivity.this, TagesActivity.class);
		i.putExtra("DATE", arg2);
		startActivity(i);
	}

}
