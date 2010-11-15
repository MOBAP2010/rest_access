package ch.good2go.restful;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import ch.good2go.objects.Device.Devices;

public class DeviceRESTProvider extends ContentProvider {
	
    private static final String TAG = "DeviceRESTProvider";

    private static final String DATABASE_NAME = "devices";

    private static final int DATABASE_VERSION = 2;

    private static final String DEVICES_TABLE_NAME = "devices";

    public static final String AUTHORITY = "ch.good2go.restful.DeviceRESTProvider";

    private static final UriMatcher sUriMatcher;

    private static final int DEVICES = 1;

    private static HashMap<String, String> devicesProjectionMap;

    private static class DatabaseHelper extends SQLiteOpenHelper {
    	
    	private static final String DATABASE_CREATE =
            "create table "+ DATABASE_NAME +" (_id integer primary key autoincrement, "
            + Devices.NAME + " text not null, "+ Devices.LOCATION +" text not null);";

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                    + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
            onCreate(db);
        }
    }

    private DatabaseHelper dbHelper;

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case DEVICES:
                count = db.delete(DEVICES_TABLE_NAME, where, whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case DEVICES:
                return Devices.CONTENT_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != DEVICES) { throw new IllegalArgumentException("Unknown URI " + uri); }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(DEVICES_TABLE_NAME, Devices.LOCATION, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(Devices.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)) {
            case DEVICES:
                qb.setTables(DEVICES_TABLE_NAME);
                qb.setProjectionMap(devicesProjectionMap);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.query(DEVICES_TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder); //qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case DEVICES:
                count = db.update(DEVICES_TABLE_NAME, values, where, whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, DATABASE_NAME, DEVICES);

        devicesProjectionMap = new HashMap<String, String>();
        devicesProjectionMap.put(Devices._ID, Devices._ID);
        devicesProjectionMap.put(Devices.NAME, Devices.NAME);
        devicesProjectionMap.put(Devices.LOCATION, Devices.LOCATION);

    }


}
