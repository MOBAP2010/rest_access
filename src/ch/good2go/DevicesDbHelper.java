package ch.good2go;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DevicesDbHelper {
	
    public static final String KEY_NAME = "name";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_CREATED_AT = "created_at";
    public static final String KEY_UPDATED_AT = "updated_at";
    public static final String KEY_ID = "id";
    public static final String KEY_ROWID = "_id";

    private static final String TAG = "Device";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    /**
     * Database creation sql statement
     */
    private static final String DATABASE_CREATE =
        "create table devices (_id integer primary key autoincrement, "
        + "name text not null, location text not null, created_at datetime not null, updated_at datetime not null, id integer not null);";

    private static final String DATABASE_NAME = "rest_access";
    private static final String DATABASE_TABLE = "devices";
    private static final int DATABASE_VERSION = 2;

    private final Context mCtx;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param ctx the Context within which to work
     */
    public DevicesDbHelper(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Open the notes database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public DevicesDbHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
    }


    /**
     * Create a new note using the title and body provided. If the note is
     * successfully created return the new rowId for that note, otherwise return
     * a -1 to indicate failure.
     * 
     * @param name the title of the note
     * @param location the body of the note
     * @return rowId or -1 if failed
     */
    private long createDevice(Integer id, String name, String location, String created_at, String updated_at) {
    	Log.i(TAG, "create: " + name + " with id:"+id.toString());
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID, id);
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_LOCATION, location);
        initialValues.put(KEY_CREATED_AT, created_at);
        initialValues.put(KEY_UPDATED_AT, updated_at);
        return mDb.insert(DATABASE_TABLE, null, initialValues);
    }
    
    public long updateDevice(Integer id, String name, String location, String created_at, String updated_at) {
    	if(mDb.query(DATABASE_TABLE, null, KEY_ID + "=" + id, null, null, null, null).getCount()>0){
    		if(updateDeviceInDB(id, name, location, created_at, updated_at)){
    			return 0;
    		}else{
    			return -1;
    		}
    	}else{
    		return createDevice(id, name, location, created_at, updated_at);
    	}
	}

    /**
     * Delete the note with the given rowId
     * 
     * @param rowId id of note to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteNote(long rowId) {

        return mDb.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return a Cursor over the list of all notes in the database
     * 
     * @return Cursor over all notes
     */
    public Cursor getAllDevices() {

        return mDb.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_LOCATION}, null, null, null, null, null);
    }

    /**
     * Return a Cursor positioned at the note that matches the given rowId
     * 
     * @param rowId id of note to retrieve
     * @return Cursor positioned to matching note, if found
     * @throws SQLException if note could not be found/retrieved
     */
    public Cursor fetchNote(long rowId) throws SQLException {

        Cursor mCursor =

            mDb.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                    KEY_NAME, KEY_LOCATION}, KEY_ROWID + "=" + rowId, null,
                    null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    /**
     * Update the note using the details provided. The note to be updated is
     * specified using the rowId, and it is altered to use the title and body
     * values passed in
     * 
     * @param rowId id of note to update
     * @param title value to set note title to
     * @param body value to set note body to
     * @return true if the note was successfully updated, false otherwise
     */
    private boolean updateDeviceInDB(Integer id, String name, String location, String created_at, String updated_at) {
    	Log.i(TAG, "update: " + name + " with id:"+id.toString());
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_LOCATION, location);
        args.put(KEY_CREATED_AT, created_at);
        args.put(KEY_UPDATED_AT, updated_at);
        return mDb.update(DATABASE_TABLE, args, KEY_ID + "=" + id, null) > 0;
    }
}
