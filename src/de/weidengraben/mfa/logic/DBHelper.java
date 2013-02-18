package de.weidengraben.mfa.logic;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import de.weidengraben.mfa.interfaces.IConstants;

public class DBHelper {

	private static SQLiteDatabase mfaDB = null;
	public static final String DBNAME = "mfa";

	public static final String CREATE_PREFERENCES = "CREATE TABLE IF NOT EXISTS PREFERENCES "
			+ "(id integer primary key autoincrement, " + "name  varchar(100)," + "value varchar(100));";

	public static final String CREATE_DATA = "CREATE TABLE IF NOT EXISTS FOOD " + "(id integer primary key autoincrement, "
			+ "date  varchar(100)," + "data varchar(10000));";

	public static void initDB(Context c) {
		DBHelper.mfaDB = c.openOrCreateDatabase(DBNAME, Context.MODE_PRIVATE, null);
		mfaDB.execSQL(CREATE_PREFERENCES);
		mfaDB.execSQL(CREATE_DATA);
	}

	public static void setSelectedMensa(Context c, String mensa) {
		Cursor cur = null;
		try {
			initDB(c);
			cur = mfaDB.rawQuery("SELECT count(id) as count FROM PREFERENCES where name = 'selected';", new String[] {});
			cur.moveToFirst();
			int count = (int) cur.getLong(cur.getColumnIndex("count"));
			if (count == 0) {
				mfaDB.execSQL("INSERT INTO PREFERENCES (name, value) values ('selected', ?);", new String[] { mensa });
			} else {
				mfaDB.execSQL("UPDATE PREFERENCES SET value=? WHERE name='selected';", new String[] { mensa });
			}
		} finally {
			cur.close();
			mfaDB.close();
		}
	}

	public static String getSelectedMensa(Context c) {
		Cursor cur = null;
		try {
			initDB(c);
			cur = mfaDB.rawQuery("SELECT value FROM PREFERENCES where name = 'selected';", new String[] {});
			cur.moveToFirst();
			String name = cur.getString(cur.getColumnIndex("value"));
			cur.close();
			return name;
		} catch (Exception e) {
			Log.d("MFA", "Could not load last update datetime: " + e.getLocalizedMessage());
			setSelectedMensa(c, IConstants.ID_MENSA_PETRISBERG);
		} finally {
			cur.close();
			mfaDB.close();
		}
		return IConstants.ID_MENSA_PETRISBERG;
	}

	public static void resetData(Context c) {
		initDB(c);
		mfaDB.execSQL("DELETE FROM FOOD;", new String[] {});
		mfaDB.close();
	}

	public static void insertData(Context c, String data, String date) {
		try {			
			initDB(c);
			mfaDB.execSQL("INSERT INTO FOOD (date, data) values (?, ?);", new String[] { date, data });
		} finally {
			mfaDB.close();
		}
	}

	public static String getData(Context c, String date) {
		Cursor cur = null;
		try {
			initDB(c);
			cur = mfaDB.rawQuery("SELECT data FROM FOOD where date = ?;", new String[] { date });
			cur.moveToFirst();
			String name = cur.getString(cur.getColumnIndex("data"));
			return name;
		} catch (Exception e) {
			Log.d("MFA", "Could not load last update datetime: " + e);
		} finally {
			cur.close();
			mfaDB.close();
		}
		return null;
	}

}
