package ch.good2go.restful;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

public class RestService extends Service {

	@Override
	public ComponentName startService(Intent service) {
		RESTMethod.GET(service.getDataString());
		return super.startService(service);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
