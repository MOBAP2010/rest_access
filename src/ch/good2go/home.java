package ch.good2go;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

public class home extends TabActivity{
    private static final int INSERT_ID = Menu.FIRST;
    private static final int ACTIVITY_CREATE=0;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.location);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	    Intent intent;  // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, RestAccess.class);

	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabHost.newTabSpec("Wohnzimmer").setIndicator("Wohnzimmer",
	                      res.getDrawable(R.drawable.home_white))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    // Do the same for the other tabs
	    intent = new Intent().setClass(this, RestAccess.class);
	    spec = tabHost.newTabSpec("Schlafzimmer").setIndicator("Schlafzimmer",
	                      res.getDrawable(R.drawable.home_white))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    intent = new Intent().setClass(this, RestAccess.class);
	    spec = tabHost.newTabSpec("Küche").setIndicator("Küche",
	                      res.getDrawable(R.drawable.home_white))
	                  .setContent(intent);
	    tabHost.addTab(spec);

	    tabHost.setCurrentTab(2);
	}
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        super.onCreateOptionsMenu(menu);
	        menu.add(0, INSERT_ID, 0, R.string.menu_insert);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // TODO Auto-generated method stub
	    	switch (item.getItemId()) 
	    	{
			case INSERT_ID:
				createDevice();
				return true;
			}
	        return super.onOptionsItemSelected(item);
	    }
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
		
	    @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	        super.onActivityResult(requestCode, resultCode, intent);
	        //fillData();
	    }
}
