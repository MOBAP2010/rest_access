package ch.good2go;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class RestAccess extends Activity {
	
	private static String testUrlRessource = "http://192.168.1.33:3000/devices/3.json";
	private static String testUrlRessources = "http://192.168.1.33:3000/devices.json";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void getRessource(View view){
    	RestClient.getRessources(testUrlRessources);
    	//RestClient.getSingleRessource(testUrlRessource);
    }
}