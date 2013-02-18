package de.weidengraben.mfa.interfaces;

public interface IConstants {

	public static final String STR_MONTAG = "MONTAG";
	public static final String STR_DIENSTAG = "DIENSTAG";
	public static final String STR_MITTWOCH = "MITTWOCH";
	public static final String STR_DONNERSTAG = "DONNERSTAG";
	public static final String STR_FREITAG = "FREITAG";
	public static final String[] WOCHE = { STR_MONTAG, STR_DIENSTAG, STR_MITTWOCH, STR_DONNERSTAG, STR_FREITAG };

	public static final String DAY_INDEX_BUNDLE_KEY = "dayindex";

	public static final int MENSA_PETRISBERG = 0;
	public static final int MENSA_TARFORST = 1;
	public static final int MENSA_FH_SCHNEIDERSHOF = 2;

	public static final String ID_MENSA_TARFORST = "mensa-1";
	public static final String NAME_MENSA_TARFORST = "[UNI] Tarforst";

	public static final String ID_MENSA_KLEINE_KARTE = "mensa-2";
	public static final String NAME_MENSA_KLEINE_KARTE = "[UNI] Kleine Karte | Forum/Bistro AB";

	public static final String ID_MENSA_ABENDMENSA = "mensa-4";
	public static final String NAME_MENSA_ABENDMENSA = "[UNI] Abendmensa | Bistro A/B";

	public static final String ID_MENSA_SCHNEIDERSHOF_CAFE = "mensa-5";
	public static final String NAME_MENSA_SCHNEIDERSHOF_CAFE = "[FH] Kleine Karte | Cafeteria Schneidershof";

	public static final String ID_MENSA_SCHNEIDERSHOF = "mensa-7";
	public static final String NAME_MENSA_SCHNEIDERSHOF = "[FH] Speisekarte Schneidershof";

	public static final String ID_MENSA_PETRISBERG = "mensa-8";
	public static final String NAME_MENSA_PETRISBERG = "[UNI] Speisekarte Geo-Mensa Petrisberg";

	public static final String[] MENSA_NAMEN = new String[] { 
		NAME_MENSA_PETRISBERG,
		NAME_MENSA_TARFORST,
		NAME_MENSA_KLEINE_KARTE,
		NAME_MENSA_ABENDMENSA,
		NAME_MENSA_SCHNEIDERSHOF_CAFE,
		NAME_MENSA_SCHNEIDERSHOF
	};
	
	public static final String[] MENSA_ID = new String[] { 
		ID_MENSA_PETRISBERG,
		ID_MENSA_TARFORST,
		ID_MENSA_KLEINE_KARTE,
		ID_MENSA_ABENDMENSA,
		ID_MENSA_SCHNEIDERSHOF_CAFE,
		ID_MENSA_SCHNEIDERSHOF
	};
}
