package ch.good2go;


import ch.good2go.objects.Device.Devices;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class DeviceEdit extends Activity {
	
	private EditText nameText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.device_edit);

	    nameText = (EditText) findViewById(R.id.device_name);
	    //typeText = (EditText) findViewById(R.id.type);
	    
	    Button confirmButton = (Button) findViewById(R.id.confirm);
	    

	    confirmButton.setOnClickListener(new View.OnClickListener() {

	        public void onClick(View view) {
	        	String name = nameText.getText().toString();
	        	if(name==null || name.length()<=0)
	        	{
	        		Toast.makeText(getApplicationContext(), "Namen ausfüllen", 0).show();
	        		return;
	        	}
	        	insertRecord(name, ((Spinner)findViewById(R.id.location)).getSelectedItem().toString(), 
	        				((Spinner)findViewById(R.id.type)).getSelectedItem().toString());
	            Intent mIntent = new Intent();
	            setResult(RESULT_OK, mIntent);
	            finish();
	        }
	        
	    });

	}
	
	private void insertRecord(String name, String location, String type) {
        ContentValues values = new ContentValues();
        values.put(Devices.NAME, name);
        values.put(Devices.LOCATION, location);
        values.put(Devices.DEVICE_TYPE, type);
        getContentResolver().insert(Devices.CONTENT_URI, values);
	}

}
