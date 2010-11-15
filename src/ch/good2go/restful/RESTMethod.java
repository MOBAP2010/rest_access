package ch.good2go.restful;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RESTMethod {
	
	private static String TAG = "RestMethod";
	
	public static String GET(String url){
		HttpGet g = new HttpGet(url);
		HttpEntity entity;
		try {
			entity = new DefaultHttpClient().execute(g).getEntity();
			String response = EntityUtils.toString(entity);
			Log.i(TAG, response);
			return response;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return "";
	}
/*	
	private static void getObjects(String url) {
		HttpGet g = new HttpGet(url);
		HttpEntity entity;
		try {
			entity = new DefaultHttpClient().execute(g).getEntity();
			String response = EntityUtils.toString(entity);
			ArrayList<Device> events = parseJSON(response);
			Log.i(TAG, "" + events.size());
			Iterator<Device> it = events.iterator();
			while(it.hasNext()){
				Device d = it.next();
				Log.i(TAG, "device: name => " + d.getName());
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private static ArrayList<Device> parseJSON(String str) throws JSONException {
		ArrayList<Device> devices = new ArrayList<Device>();
		JSONArray json = new JSONArray(str);
		int length = json.length();
		for (int i = 0; i < length; i++) {
			JSONObject jEvent = json.getJSONObject(i).getJSONObject("device");
			Device e = new Device();
			e.setId(jEvent.getInt("id"));
			e.setName(jEvent.getString("name"));
			e.setLocation(jEvent.getString("location"));
			e.setCreated_at(jEvent.getString("created_at"));
			e.setUpdated_at(jEvent.getString("updated_at"));
			devices.add(e);
		}
		return devices;
	}
*/
}
