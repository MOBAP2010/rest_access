package ch.good2go.restful;

import android.app.Activity;
import android.content.Intent;

public class RestServiceHelper extends Activity {
		
	@Override
	public void startActivity(Intent intent) {
		RestService service = new RestService();
		service.startService(intent);
		super.startActivity(intent);
	}
}
