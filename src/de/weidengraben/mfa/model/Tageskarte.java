package de.weidengraben.mfa.model;

import java.util.HashMap;


public class Tageskarte extends HashMap<String, TageskarteMensa>{
	private static final long serialVersionUID = 3581132733608951383L;
	public static final String TAGESKARTE_MENSA_TRENNER = "---2---";
	public static final String TAGESKARTE_MENSA_NAME_TRENNER = "---3---";

	public static String serialize(Tageskarte input) {
		StringBuffer buffer = new StringBuffer();
		for (String mensaname : input.keySet()) {
			buffer.append(mensaname);
			buffer.append(TAGESKARTE_MENSA_NAME_TRENNER);
			buffer.append(TageskarteMensa.serialize(input.get(mensaname)));
			buffer.append(TAGESKARTE_MENSA_TRENNER);
		}
		return buffer.toString();
	}
	
	public static Tageskarte deserialize(String input) {
		Tageskarte t = new Tageskarte();
		String[] mensen = input.split(TAGESKARTE_MENSA_TRENNER);
		for (String tag : mensen) {
			String[] tmp = tag.split(TAGESKARTE_MENSA_NAME_TRENNER);
			if (tmp.length >= 2) {				
				t.put(tmp[0], TageskarteMensa.deserialize(tmp[1]));
			} else {
				t.put(tmp[0], new TageskarteMensa());
			}
		}
		return t;
	}
}
