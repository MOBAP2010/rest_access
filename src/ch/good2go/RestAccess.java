package ch.good2go;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;

public class RestAccess extends ListActivity {
	
	//private static String testUrlRessource = "http://192.168.1.33:3000/devices/3.json";
	private static String testUrlRessources = "http://192.168.1.33:3000/devices.json";
	
	private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;
    
    private static final int INSERT_ID = Menu.FIRST;
    private static final int DELETE_ID = Menu.FIRST + 1;
	
	private DevicesDbHelper dbHelper;
	private Cursor deviceCursor;
		
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices);
        dbHelper = new DevicesDbHelper(this);
        dbHelper.open();
        //RestClient.getDevices(testUrlRessources, dbHelper);
        fillData();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID,0, R.string.menu_insert_device);
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
        case INSERT_ID:
            createDevice();
            return true;
        }
        
        return super.onMenuItemSelected(featureId, item);
    }
    
    private void createDevice() {
    	Intent i = new Intent(this, DeviceEdit.class);
    	startActivityForResult(i, ACTIVITY_CREATE);
    }

	private void fillData() {
        // Get all of the rows from the database and create the item list
		deviceCursor = dbHelper.getAllDevices();
        startManagingCursor(deviceCursor);
        
        // Create an array to specify the fields we want to display in the list (only TITLE)
        String[] from = new String[]{DevicesDbHelper.KEY_NAME, DevicesDbHelper.KEY_LOCATION};
        
        // and an array of the fields we want to bind those fields to (in this case just name)
        int[] to = new int[]{R.id.name, R.id.location};
        
        // Now create a simple cursor adapter and set it to display
        SimpleCursorAdapter devices = 
        	    new SimpleCursorAdapter(this, R.layout.device, deviceCursor, from, to);
        setListAdapter(devices);
	}

	public void getRessource(View view){
    	RestClient.updateDevices(testUrlRessources, dbHelper);
    	fillData();
    }
}