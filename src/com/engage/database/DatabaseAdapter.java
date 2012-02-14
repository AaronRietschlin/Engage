package com.engage.database;

import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class DatabaseAdapter {
	
	// TAG for debugging
	private final String TAG = "DatabaseHandler";

	// Basic Database information
	private static final String DATABASE_NAME = "engage.db";
	private static final String ATTRACTION_TABLE = "attractionTable";
	private static final String ATTRACTION_TYPE_TABLE = "attractionTypeTable";
	private static final String ATTRACTION_DETAILS_TABLE = "attractionDetailsTable";
	private static final String PHOTOS_TABLE = "attractionPhotosTable";
	private static final String PATH_TABLE = "pathTable";
	private static final int DATABASE_VERSION = 1;

	// Used in all of the tables
	public static final String KEY_ID = "_id";
	
	// Attraction Table KEYS
	public static final String ATTRACTION_NAME = "_name";
	public static final String DETAIL_ID = "detailId";
	public static final String LAT = "_lat";
	public static final String LNG = "_lng";
	public static final String CREATED_AT = "_createdAt";
	public static final String DESCRIPTION = "_description";
	public static final String TAGS = "_tags";
	public static final String PHOTO_ID = "_photoId";
	public static final String TYPE_ID = "_typeId";
	

	
	// Used to identify the columns in other classes.
	public static final int ID_COLUMN_AT = 0;
	public static final int ATTRACTION_NAME_COLUMN_AT = 1;
	public static final int DETAIL_ID_COLUMN = 2;
	public static final int LAT_COLUMN = 3;
	public static final int LNG_COLUMN = 4;
	public static final int CREATED_AT_COLUMN_AT = 5;
	public static final int DESCRIPTION_COLUMN = 6;
	public static final int TAGS_COLUMN = 7;
	public static final int PHOTO_ID_COLUMN = 8;
	public static final int TYPE_ID_COLUMN = 9;
	
	
	// Attraction Type Table KEYS
	//public static final String ATTRACTION_NAME = "_name";
	public static final String ATTRACTION_TYPE = "_attractionType";
	
	// Used to identify the columns in other classes.
	public static final int ID_COLUMN_ATT = 0;
	public static final int ATTRACTION_NAME_COLUMN_ATT = 1;
	public static final int ATTRACTION_TYPE_COLUMN = 2;
	
	// Attraction Details Table KEYS
	public static final String TRAVELER_COMMENT = "_travelerComment";
	public static final String LOCAL_UPSIDE = "_localUpside";
	public static final String ADDRESS = "_address";
	public static final String UP_VOTES = "_upVotes";
	public static final String DOWN_VOTES = "_downVotes";
	public static final String TOTAL_VOTES = "_totalVotes";
	public static final String CONTACT_INFO = "_contactInfo";
	public static final String ATTRACTION_TYPE_DETAILS = "_attractionTypeDetails";
	
	// Used to identify the columns in other classes.
	public static final int ID_COLUMN_ADT = 0;
	public static final int TRAVERLER_COMMENT_COLUMN = 1;
	public static final int LOCAL_UPSIDE_COLUMN = 2;
	public static final int ADDRESS_COLUMN = 3;
	public static final int UP_VOTES_COLUMN = 4;
	public static final int DOWN_VOTES_COLUMN = 5;
	public static final int TOTAL_VOTES_COLUMN = 6;
	public static final int CONTACT_INFO_COLUMN = 7;
	public static final int ATTRACTION_TYPE_DETAILS_COLUMN = 8;
	
	// Photos Tabe KEYS
	//public static final String CREATED_AT = "_createdAt";
	public static final String URL = "_url";
	public static final String FILE_PATH = "_filePath";
	public static final String ATTRACTION_ID = "_attractionId";
	public static final String USER_ID = "_userId";
	
	// Used to identify the columns in other classes.
	public static final int ID_COLUMN_PT = 0;
	public static final int CREATED_AT_COLUMN_PT = 1;
	public static final int URL_COLUMN = 2;
	public static final int FILE_PATH_COLUMN = 3;
	public static final int ATTRACTION_ID_COLUMN = 4;
	public static final int USER_ID_COLUMN_PT = 5;
	

	// Path Table KEYS
	//public static final String CREATED_AT = "_createdAt";
	//public static final String USER_ID = "_userId";
	public static final String ATTRACTIONS = "_attractions";
	public static final String COMMENT = "_comment";
	public static final String SHARE = "_share";
	
	// Used to identify the columns in other classes.
	public static final int CREATED_AT_COLUMN_PATH = 0;
	public static final int USER_ID_COLUMN_PATH = 1;
	public static final int ATTRACTIONS_COLUMN = 2;
	public static final int COMMENT_COLUMN = 3;
	public static final int SHARE_COLUMN = 4;
	
	// SQL Statement to create a new database.
	private static final String DATABASE_CREATE = 
		"create table " + ATTRACTION_TABLE + " (" 
		+ KEY_ID + " integer primary key autoincrement, "
		+ ATTRACTION_NAME + " String, " 
		+ DETAIL_ID + " int, "
		+ LAT + " double, " 
		+ LNG + " double, " 
		+ CREATED_AT + " Date, " 
		+ DESCRIPTION + " String, " 
		+ TAGS + " String, "
		+ PHOTO_ID + " int, " 
		+ TYPE_ID + " int)"
		+ "create table " + ATTRACTION_TYPE_TABLE + " (" 
		+ KEY_ID + " integer primary key autoincrement, "
		+ ATTRACTION_NAME + " String, "
		+ ATTRACTION_TYPE + " int)"
		+ "create table " + ATTRACTION_DETAILS_TABLE + " ("
		+ KEY_ID + " integer primary key autoincrement, "
		+ TRAVELER_COMMENT + " String, "
		+ LOCAL_UPSIDE + " int, " 
		+ ADDRESS_COLUMN + " array, "
		+ UP_VOTES + " int, "
		+ DOWN_VOTES + " int, "
		+ TOTAL_VOTES + " int, "
		+ CONTACT_INFO + " array, "
		+ ATTRACTION_TYPE_DETAILS + " array) " 
		+ "create table " + PHOTOS_TABLE  +  " ("
		+ KEY_ID + " integer primary key autoincrement, "
		+ CREATED_AT + " Date, "
		+ URL + " String, "
		+ FILE_PATH + " String, "
		+ ATTRACTION_ID + " int, "
		+ USER_ID + "int) " 
		+ "create table " + PATH_TABLE + " (" 
		+ KEY_ID + " integer primary key autoincrement, "
		+ CREATED_AT + " Date, "
		+ USER_ID + " int, " 
		+ ATTRACTIONS + " array, "
		+ COMMENT + " String, "
		+ SHARE + " int);";
	
	// Variables to hold the database instance
	private SQLiteDatabase db;
	// Context of the appliction using the database
	private final Context context;
	// Database open/upgrade helper
	private myDbHelper dbHelper;

	public DatabaseAdapter(Context _context) {
		context = _context;
		dbHelper = new myDbHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
	}

	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}

	public void close() {
		Log.d(TAG, "Closing: " + DATABASE_NAME);
		db.close();
	}
	
	public boolean removeEntry(long _rowIndex, String table) {
		Log.d(TAG, "Removed entry: " + _rowIndex);
		return db.delete(table, KEY_ID + "=" + _rowIndex, null) > 0;
	}

	public int updateEntry(ContentValues _rowIndex, String table, String whereClause, String whereArgs[]) {
		// TODO: Create a new ContentValues based on the new object
		// and use it to update a row e database.
		return db.update(table, _rowIndex, whereClause, whereArgs);
	}
	
	public long insertAttractionItem(AttractionItem item) {
		ContentValues newAttractionValues = new ContentValues();
		newAttractionValues.put(ATTRACTION_NAME, item.getAttractionName());
		newAttractionValues.put(DETAIL_ID, item.getDetailId());
		newAttractionValues.put(LAT, item.getLat());
		newAttractionValues.put(LNG, item.getLng());
		newAttractionValues.put(CREATED_AT, item.getCreatedAt());
		newAttractionValues.put(DESCRIPTION, item.getDescription());
		newAttractionValues.put(TAGS, item.getTags());
		newAttractionValues.put(PHOTO_ID, item.getPhotoId());
		newAttractionValues.put(TYPE_ID, item.getTypeId());
		Log.d(TAG, "Values: " + newAttractionValues.toString()
				+ " have been put in to: " + DATABASE_NAME);
		return db.insert(ATTRACTION_TABLE, null, newAttractionValues);
		
	}
	
	public long insertAttractionDetailsItem(AttractionDetailsItem item) {
		ContentValues newAttractionDetailsValues = new ContentValues();
		newAttractionDetailsValues.put(TRAVELER_COMMENT, item.getTravelerComment());
		newAttractionDetailsValues.put(LOCAL_UPSIDE, item.getLocalUpside());
		newAttractionDetailsValues.put(ADDRESS, item.getAddress());
		newAttractionDetailsValues.put(UP_VOTES, item.getUpVotes());
		newAttractionDetailsValues.put(DOWN_VOTES, item.getDownVotes());
		newAttractionDetailsValues.put(TOTAL_VOTES, item.getTotalVotes());
		newAttractionDetailsValues.put(CONTACT_INFO, item.getContactInfo());
		newAttractionDetailsValues.put(ATTRACTION_TYPE_DETAILS, item.getAttractionTypeDetails());
		Log.d(TAG, "Values: " + newAttractionDetailsValues.toString()
				+ " have been put in to: " + DATABASE_NAME);
		return db.insert(ATTRACTION_TABLE, null, newAttractionDetailsValues);
		
	}
	
	public long insertAttractionTypeItem(AttractionTypeItem item) {
		ContentValues newAttractionTypeValues = new ContentValues();
		newAttractionTypeValues.put(ATTRACTION_NAME, item.getAttractionName());
		newAttractionTypeValues.put(ATTRACTION_TYPE, item.getAttractionType());
		Log.d(TAG, "Values: " + newAttractionTypeValues.toString()
				+ " have been put in to: " + DATABASE_NAME);
		return db.insert(ATTRACTION_TABLE, null, newAttractionTypeValues);
		
	}

	public long insertPhotoItem(PhotoItem item) {
		ContentValues newPhotoValues = new ContentValues();
		newPhotoValues.put(CREATED_AT, item.getCreatedAt());
		newPhotoValues.put(URL, item.getUrl());
		newPhotoValues.put(FILE_PATH, item.getFilePath());
		newPhotoValues.put(ATTRACTION_ID, item.getAttractionId());
		newPhotoValues.put(USER_ID, item.getUserId());
		Log.d(TAG, "Values: " + newPhotoValues.toString()
				+ " have been put in to: " + DATABASE_NAME);
		return db.insert(ATTRACTION_TABLE, null, newPhotoValues);
		
	}
	
	public long insertPathItem(PathItem item) {
		ContentValues newPathValues = new ContentValues();
		newPathValues.put(CREATED_AT, item.getCreatedAt());
		newPathValues.put(USER_ID, item.getUserId());
		newPathValues.put(ATTRACTIONS, item.getAttractions());
		newPathValues.put(COMMENT, item.getComment());
		newPathValues.put(SHARE, item.getShare());
		Log.d(TAG, "Values: " + newPathValues.toString()
				+ " have been put in to: " + DATABASE_NAME);
		return db.insert(ATTRACTION_TABLE, null, newPathValues);
		
	}
	
	/*
	 * Returns the cursor for the given table ATTRACTION_TABLE, ATTRACTION_TYPE_TABLE, 
	 * ATTRACTION_DETAILS_TABLE, PHOTOS_TABLE, or PATH_TABLE if
	 * not those returns null
	 */
	public Cursor getAllEntries(String table) {
		Log.d(TAG, "Getting all entries from: " + table);
		if (table == ATTRACTION_TABLE) {
			return db.query(table, new String[] { KEY_ID, DETAIL_ID,
					ATTRACTION_NAME, DETAIL_ID, LAT, LNG, CREATED_AT,
					DESCRIPTION, TAGS, PHOTO_ID, TYPE_ID },
					null, null, null, null, null);
		}
		if (table == ATTRACTION_TYPE_TABLE) {
			return db.query(table, new String[] { KEY_ID, ATTRACTION_NAME,
					ATTRACTION_TYPE }, null, null, null, null, null);
		
		}
		
		if (table == ATTRACTION_DETAILS_TABLE) {
			return db.query(table, new String[] { KEY_ID, TRAVELER_COMMENT,
					LOCAL_UPSIDE, ADDRESS, UP_VOTES, DOWN_VOTES, TOTAL_VOTES,
					CONTACT_INFO, ATTRACTION_TYPE_DETAILS },
					null, null, null, null, null);

		}
		
		if (table == PHOTOS_TABLE) {
			return db.query(table, new String[] { KEY_ID, CREATED_AT,
					URL, FILE_PATH, ATTRACTION_ID, USER_ID },
					null, null, null, null, null);
			
		}
		
		if (table == PATH_TABLE) {
			return db.query(table, new String[] { KEY_ID, CREATED_AT,
					USER_ID, ATTRACTIONS, COMMENT, SHARE },
					null, null, null, null, null);
			
		}
		
		Log.e(TAG, table + " is not vaild");
		return null;
	}
	
	private static class myDbHelper extends SQLiteOpenHelper {
		private final String TAG = "myDbHelper";

		public myDbHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		// Called when no database exists in disk and the helper class needs
		// to create a new one.
		@Override
		public void onCreate(SQLiteDatabase _db) {
			try {
				Log.d(TAG, "Creation: Trying to create SMS table");
				Log.d(TAG, "Creation: " + DATABASE_CREATE);
				_db.execSQL(DATABASE_CREATE);
				Map<String, String> map = _db.getSyncedTables();
				if (map.size() == 0) {
					Log.d(TAG, "Creation: Empty.");
				}
				for (Map.Entry<String, String> entry : map.entrySet()) {
					Log.d(TAG,
							"Creation: " + entry.getKey() + "/"
									+ entry.getValue());
				}
			} catch (SQLException e) {
				Log.d(TAG, "Creation: Failed trying to create SMS table");
			}
		}

		/*
		 * Called when there is a database wersion mismatch meaning that the
		 * version of the database on disk needs to be upgraded to the current
		 * version.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			// Log the version upgrade.
			Log.w(TAG, "Upgrade from version " + _oldVersion
					+ " to " + _newVersion
					+ ", which will destroy all old data");

			/*
			 * Upgrade the existing database to conform to the new version.
			 * Multiple previous version can be handled by comparing _oldVersion
			 * and _newVersion values.
			 */

			// The simplest case is to drop the old table and create a new one.
			_db.execSQL("DROP TABLE IF EXISTS " + ATTRACTION_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + ATTRACTION_TYPE_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + ATTRACTION_DETAILS_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + PHOTOS_TABLE);
			_db.execSQL("DROP TABLE IF EXISTS " + PATH_TABLE);
			// Create a new one.
			onCreate(_db);
		}
	}

}
