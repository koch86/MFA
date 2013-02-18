package de.weidengraben.mfa.logic;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import de.weidengraben.mfa.R;
import de.weidengraben.mfa.interfaces.IConstants;
import de.weidengraben.mfa.model.Speise;
import de.weidengraben.mfa.model.Tageskarte;
import de.weidengraben.mfa.model.TageskarteMensa;

public class MensaListAdapter extends BaseAdapter {

	
	@SuppressWarnings("rawtypes")
	private List karte = null;
	private String mensa;

	@SuppressWarnings("rawtypes")
	public MensaListAdapter(Context c, List karte, String mensa) {
		this.karte = karte;
		this.mensa = mensa;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (karte instanceof TageskarteMensa) {
			return showTageskarte(position, parent.getContext());
		} else {
			return showWochenkarte(position, parent.getContext());
		}
	}

	private View showWochenkarte(int position, Context c) {
		LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.wocheitem, null, false);
		TextView tag = (TextView) itemLayout.findViewById(R.id.txttag);
		TextView tageskarte = (TextView) itemLayout.findViewById(R.id.txttagesessen);
		tag.setText(IConstants.WOCHE[position]);
		tag.setGravity(Gravity.CENTER);
//		tag.setBackgroundColor(Color.WHITE);
		TageskarteMensa tkarteM = ((Tageskarte) karte.get(position)).get(mensa);
		tageskarte.setText(tkarteM.toString());
		if (position == DateHelper.getTodayIndex()) {
			tag.setTextColor(Color.parseColor("#FF0000"));
		} else {
			tag.setTextColor(Color.parseColor("#FFFFFF"));
		}
		return itemLayout;
	}

	public View showTageskarte(int position, Context c) {
		if (position >= karte.size())
			return null;
		LinearLayout itemLayout = (LinearLayout) LayoutInflater.from(c).inflate(R.layout.tagitem, null, false);
		TextView preis = (TextView) itemLayout.findViewById(R.id.txtpreis);
		TextView name = (TextView) itemLayout.findViewById(R.id.txtessen);
		Speise s = (Speise) karte.get(position);
		name.setText(s.beschreibung);
		if (s.studPreis.replace(" ", "").equals("") && s.bedienstPreis.replace(" ", "").equals("") && s.gastPreis.replace(" ", "").equals("")) {
			// preis.setText("");
			itemLayout.removeView(preis);
		} else {			
			preis.setText("Stud.: " + s.studPreis + "   Bed.: " + s.bedienstPreis + "   Gast: " + s.gastPreis);
		}
		return itemLayout;
	}

	@Override
	public int getCount() {
		return karte.size();
	}

	@Override
	public Object getItem(int arg0) {
		return karte.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return karte.indexOf(arg0);
	}
}
