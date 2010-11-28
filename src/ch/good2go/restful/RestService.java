package ch.good2go.restful;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RestService extends Service {

	@Override
	public void onStart(Intent intent, int startid)	{
	
	}
	
	@Override
	public void onDestroy()	{
	
	}

	@Override
	public IBinder onBind(Intent intent) {
		
	     return null;
	}
}
