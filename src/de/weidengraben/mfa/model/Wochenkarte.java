package de.weidengraben.mfa.model;

import java.util.ArrayList;

public class Wochenkarte extends ArrayList<Tageskarte> {

	private static final long serialVersionUID = 1L;
	
	public static final String TAGESKARTE_TRENNER = "---1---";

	
	public static String serialize(Wochenkarte input) {
		StringBuffer buffer = new StringBuffer();
		
		for (Tageskarte tk : input) {
			buffer.append(Tageskarte.serialize(tk));
			buffer.append(TAGESKARTE_TRENNER);
		}
		return buffer.toString();
	}
	
	public static Wochenkarte deserialize(String input) {
		Wochenkarte w = new Wochenkarte();
		String[] tage = input.split(TAGESKARTE_TRENNER);
		for (String tag : tage) {
			w.add(Tageskarte.deserialize(tag));
		}
		return w;
	}
}
