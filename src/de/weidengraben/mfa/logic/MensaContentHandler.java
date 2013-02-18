package de.weidengraben.mfa.logic;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.weidengraben.mfa.model.Speise;
import de.weidengraben.mfa.model.Tageskarte;
import de.weidengraben.mfa.model.TageskarteMensa;

public class MensaContentHandler extends DefaultHandler {

	String mensaId = "";

	Tageskarte tageskarte = new Tageskarte();
	TageskarteMensa tageskarteMensa = new TageskarteMensa();
	ArrayList<Tageskarte> wochenkarte = null;
	Speise speise = new Speise();

	public MensaContentHandler(ArrayList<Tageskarte> woche) {
		this.wochenkarte = woche;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (localName.startsWith("mensa")) {
			mensaId = localName;
		}
	}

	@Override
	public void endDocument() throws SAXException {
		wochenkarte.add(tageskarte);
	}

	String ort = "";
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (localName.startsWith("mensa")) {
			tageskarteMensa.ort = mensaId;
			tageskarteMensa.initOverview();
			tageskarte.put(mensaId, tageskarteMensa);
			tageskarteMensa = new TageskarteMensa();
		} else if (localName.startsWith("ausgabe")) {
			ort = sb.toString();
		} else if (localName.startsWith("preis-1")) {
			speise.setStudPreis(sb.toString());
		} else if (localName.startsWith("preis-2")) {
			speise.setBedienstPreis(sb.toString());
		} else if (localName.startsWith("preis-3")) {
			speise.setGastPreis(sb.toString());
		} else if (localName.startsWith("text")) {
			speise.setBeschreibung(sb.toString());
		} else if (localName.startsWith("zeile")) {
			speise.setOrt(ort);
			if (!speise.isEmpty()) {
				tageskarteMensa.add(speise);
			}
			speise = new Speise();
		}
		sb = new StringBuffer();
	}

	StringBuffer sb = new StringBuffer();
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		sb.append(ch, start, start+length);
	}
}
