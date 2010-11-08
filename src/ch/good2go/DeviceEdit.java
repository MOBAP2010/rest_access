package ch.good2go;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeviceEdit extends Activity {
	
	private EditText nameText;
	private EditText locationText;
	private Long mRowId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.device_edit);

	    nameText = (EditText) findViewById(R.id.name);
	    locationText = (EditText) findViewById(R.id.location);

	    Button confirmButton = (Button) findViewById(R.id.confirm);

	    mRowId = null;
	    Bundle extras = getIntent().getExtras();
	    if (extras != null) {
	        String name = extras.getString(DevicesDbHelper.KEY_NAME);
	        String location = extras.getString(DevicesDbHelper.KEY_LOCATION);
	        mRowId = extras.getLong(DevicesDbHelper.KEY_ROWID);

	        if (name != null) {
	            nameText.setText(name);
	        }
	        if (location != null) {
	            locationText.setText(location);
	        }
	    }

	    confirmButton.setOnClickListener(new View.OnClickListener() {

	        public void onClick(View view) {
	            Bundle bundle = new Bundle();

	            bundle.putString(DevicesDbHelper.KEY_NAME, nameText.getText().toString());
	            bundle.putString(DevicesDbHelper.KEY_LOCATION, locationText.getText().toString());
	            if (mRowId != null) {
	                bundle.putLong(DevicesDbHelper.KEY_ROWID, mRowId);
	            }

	            Intent mIntent = new Intent();
	            mIntent.putExtras(bundle);
	            setResult(RESULT_OK, mIntent);
	            finish();
	        }
	    });

	}

}
