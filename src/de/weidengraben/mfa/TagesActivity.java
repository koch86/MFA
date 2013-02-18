package de.weidengraben.mfa;

import android.R.anim;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import de.weidengraben.mfa.interfaces.IConstants;
import de.weidengraben.mfa.logic.DBHelper;
import de.weidengraben.mfa.logic.DateHelper;
import de.weidengraben.mfa.logic.FoodHelper;

public class TagesActivity extends Activity implements OnItemSelectedListener {

	public boolean spinnerInit = false;

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(anim.fade_in, anim.fade_out);
	}

	/** Called when the activity is first created. */
	ListView listView;
	int date = 0;

	@Override
	public void onStart() {
		overridePendingTransition(anim.fade_in, anim.fade_out);
		super.onStart();
		spinnerInit = false;
		setContentView(R.layout.tag);
		Log.d("MFA", "onStart()");
		Bundle b = this.getIntent().getExtras();
		if (b != null && b.containsKey("DATE")) {
			date = b.getInt("DATE");
		} else {
			date = DateHelper.getTodayIndex();
		}
		TextView tv = (TextView) findViewById(R.id.dayname);
		tv.setText(IConstants.WOCHE[date]);
		Spinner s = (Spinner) findViewById(R.id.spinner1);
		String mensa = DBHelper.getSelectedMensa(this);
		s.setSelection(FoodHelper.indexof(IConstants.MENSA_ID, mensa));
		s.setOnItemSelectedListener(this);
		listView = (ListView) findViewById(R.id.essenslistedetail);
		FoodHelper.requestDayAdapter(this, listView, mensa, date);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (!spinnerInit) { // To Avoid click if nothing was clicked .... :-(
			spinnerInit = !spinnerInit;
			return;
		}
		if (arg0 instanceof Spinner) {
			Spinner s = (Spinner) arg0;
			DBHelper.setSelectedMensa(this, IConstants.MENSA_ID[s.getSelectedItemPosition()]);
			FoodHelper.requestDayAdapter(this, listView, IConstants.MENSA_ID[s.getSelectedItemPosition()], date);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

}
