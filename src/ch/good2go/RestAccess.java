package ch.good2go;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import ch.good2go.objects.Device.Devices;

public class RestAccess extends ListActivity {
	
	private static final String TAG = "RestAccess";
	// localhost on pc => 10.0.2.2
			
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices);
        insertRecord("New Device454","cham, pfad");
        displayRecords();
    }
    
    private void displayRecords() {
        // An array specifying which columns to return.
        Cursor cur = managedQuery(Devices.CONTENT_URI, 
        		new String[] { Devices._ID, Devices.NAME, Devices.LOCATION }, // Which columns to return
                null, // WHERE clause; which rows to return(all rows)
                null, // WHERE clause selection arguments (none)
                null // Order-by clause (ascending by name)
        );
        startManagingCursor(cur);
        Log.i(TAG, "Prepare devices to display.");
        SimpleCursorAdapter devices = new SimpleCursorAdapter(	this, 
        														R.layout.device, 
        														cur, 
        														new String[] { Devices.NAME }, 
        														new int[] { R.id.name });
        setListAdapter(devices);
	}

	private void insertRecord(String name, String location) {
        ContentValues values = new ContentValues();
        values.put(Devices.NAME, name);
        values.put(Devices.LOCATION, location);
        getContentResolver().insert(Devices.CONTENT_URI, values);
	}

	public void getRessource(View view){
		//RESTMethod.GET(testUrlRessources);
    }
}