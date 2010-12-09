package ch.good2go;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.SimpleCursorAdapter.ViewBinder;
import ch.good2go.objects.Device.Devices;

public class RestAccess extends ListActivity {
	
	private static final String TAG = "RestAccess";
    private static final int DELETE_ID = Menu.FIRST;
	// localhost on pc => 10.0.2.2

    String mlocation="";
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.devices);
        Bundle extras = getIntent().getExtras();
        mlocation = extras.getString("location");
        displayRecords(mlocation);
        
        registerForContextMenu(getListView());
    }
   
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		CheckBox cb = ((CheckBox)v.findViewById(R.id.power));
		cb.setChecked(!cb.isChecked());
		updateDevice(id, cb.isChecked());
	}

	private void displayRecords(String where) {
        // An array specifying which columns to return.
        Cursor cur = managedQuery(Devices.CONTENT_URI, 
        		new String[] { Devices._ID, Devices.NAME, Devices.LOCATION, Devices.DEVICE_TYPE, Devices.POWER }, // Which columns to return
                Devices.LOCATION + "= ?", // WHERE clause; which rows to return(all rows)
                new String[]{where}, // WHERE clause selection arguments (none)
                null // Order-by clause (ascending by name)
        );
        startManagingCursor(cur);
        Log.i(TAG, "Prepare devices to display.");
        SimpleCursorAdapter devices = new SimpleCursorAdapter(	this, 
        														R.layout.device, 
        														cur, 
        														new String[] { Devices.NAME, Devices.DEVICE_TYPE, Devices.POWER}, 
        														new int[] { R.id.name, R.id.type_icon,  R.id.power});

        devices.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if(columnIndex==3)
				{
					String type = cursor.getString(columnIndex);
					String[]types = getResources().getStringArray(R.array.device_type_array);
					if(type.equals(types[0]))
					{
						((ImageView)view).setImageResource(R.drawable.ic_flash);
					}
					else if(type.equals(types[1]))
					{
						((ImageView)view).setImageResource(R.drawable.ic_multimedia);
					}
					else if(type.equals(types[2]))
					{
						((ImageView)view).setImageResource(R.drawable.ic_light);
					}
					else 
						((ImageView)view).setImageResource(R.drawable.ic_computer);

					return true;
				}
				else if (columnIndex==4){
					int value = cursor.getInt(columnIndex);
					boolean power = (value==0)?false:true;
					((CheckBox)view).setChecked(power);
					return true;
				}
				else
					return false;
			}
		});
        
        setListAdapter(devices);
	}

	private void updateDevice(long id, boolean power) {
        ContentValues values = new ContentValues();
        values.put(Devices.POWER, power);
        //getContentResolver().update(Devices.CONTENT_URI, values, "id="+id, null);
	}
	
	private void deleteDevice(long id) {
        //getContentResolver().delete(Devices.CONTENT_URI, "id="+id, null);
	}
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) 
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                
                deleteDevice(info.id);
                displayRecords(mlocation);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}