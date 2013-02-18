package de.weidengraben.mfa.model;

import java.util.ArrayList;


public class TageskarteMensa extends ArrayList<Speise>{
	private static final long serialVersionUID = -9189491581430976927L;
	public String ort = "";
	public String overView = "";
	public static final String SPEISE_TRENNER = "---4---";
	
	public void initOverview() {
		StringBuffer sb = new StringBuffer();
		if (size() > 1) {
			if (get(1).beschreibung.length() > 100) {				
				sb.append(get(1).beschreibung, 0, 100);
			} else {				
				sb.append(get(1).beschreibung);
			}
			sb.append("...");
		}
		if (size() > 2) {
			sb.append("\n\n");
			if (get(2).beschreibung.length() > 100) {				
				sb.append(get(2).beschreibung, 0, 100);
			} else {
				sb.append(get(2).beschreibung);				
			}
			sb.append("...");
		}
		overView = sb.toString();
	}
	
	@Override
	public String toString() {
		return overView;
	}

	public static String serialize(TageskarteMensa input) {
		StringBuffer buffer = new StringBuffer();
		for (Speise s : input) {
			buffer.append(Speise.serialize(s));
			buffer.append(SPEISE_TRENNER);
		}
		return buffer.toString();
	}
	
	public static TageskarteMensa deserialize(String input) {
		try {			
			TageskarteMensa tkm = new TageskarteMensa();
			String[] speisen = input.split(SPEISE_TRENNER);
			for (String speise : speisen) {
				tkm.add(Speise.deserialize(speise));
			}
			tkm.initOverview();
			return tkm;
		} catch (Exception e) {
			return null;
		}
	}
}
