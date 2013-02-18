package de.weidengraben.mfa.model;

public class Speise {
	
	public static final String SPEISE_TRENNER = "---5---";
	
	public Speise(String text, String preisStud, String preisBed, String preisGast, String ort) {
		this.beschreibung = text;
		this.studPreis = preisStud;
		this.bedienstPreis = preisBed;
		this.gastPreis = preisGast;
		this.ort = ort;
	}
	
	@Override
	public String toString() {
		return beschreibung;
	}
	
	public Speise() {
	}
	
	public String studPreis = "";
	public String bedienstPreis = "";
	public String gastPreis = "";
	
	public String beschreibung = "";
	public String ort = "";
	public void setBeschreibung(String data) {
		this.beschreibung = data.replaceAll("(<[^>]*>)|(\\([^\\)]*\\))|([\n])|(\\[[^\\]]*\\])", "").replaceAll("[ ]{2,}", " ");
	}

	public void setStudPreis(String data) {
		this.studPreis = data.replaceAll("(<[^>]*>)|(\\([^\\)]*\\))|([\n])|(\\[[^\\]]*\\])", "").replaceAll("[ ]{2,}", " ");
	}

	public void setBedienstPreis(String data) {
		this.bedienstPreis = data.replaceAll("(<[^>]*>)|(\\([^\\)]*\\))|([\n])|(\\[[^\\]]*\\])", "").replaceAll("[ ]{2,}", " ");		
	}

	public void setGastPreis(String data) {
		this.gastPreis = data.replaceAll("(<[^>]*>)|(\\([^\\)]*\\))|([\n])|(\\[[^\\]]*\\])", "").replaceAll("[ ]{2,}", " ");		
	}

	public void setOrt(String data) {
		this.ort = data.replaceAll("(<[^>]*>)|(\\([^\\)]*\\))|([\n])|(\\[[^\\]]*\\])", "").replaceAll("[ ]{2,}", " ");
	}
	
	public boolean isEmpty() {
		return (beschreibung == null || beschreibung.equals("") || beschreibung.equals(" "));
	}

	public static String serialize(Speise s) {
		StringBuffer sb = new StringBuffer();
		sb.append(s.beschreibung);
		sb.append(SPEISE_TRENNER);
		sb.append(s.studPreis);
		sb.append(SPEISE_TRENNER);
		sb.append(s.bedienstPreis);
		sb.append(SPEISE_TRENNER);
		sb.append(s.gastPreis);
		sb.append(SPEISE_TRENNER);
		sb.append(s.ort);
		return sb.toString();
	}

	public static Speise deserialize(String input) {
		String[] speisetmp = input.split(SPEISE_TRENNER);
		Speise s = new Speise();
		s.beschreibung = speisetmp[0];
		s.studPreis = speisetmp[1];
		s.bedienstPreis = speisetmp[2];
		s.gastPreis = speisetmp[3];
		s.ort = speisetmp[4];
		return s;
	}
}