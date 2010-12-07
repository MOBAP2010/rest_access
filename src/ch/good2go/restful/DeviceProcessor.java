package ch.good2go.restful;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import ch.good2go.objects.Device.Devices;

public class DeviceProcessor {
	public static ArrayList<ContentValues> parseJSON(final String str) {
		try {
			ArrayList<ContentValues> devices = new ArrayList<ContentValues>();
			JSONArray json = new JSONArray(str);
			int length = json.length();
			for (int i = 0; i < length; i++) {
				JSONObject jEvent = json.getJSONObject(i).getJSONObject("device");
				ContentValues e = new ContentValues();
		        e.put(Devices.NAME, jEvent.getString("name"));
		        e.put(Devices.LOCATION, jEvent.getString("location"));
		        e.put(Devices.DEVICE_TYPE, jEvent.getString("device_type"));
		        e.put(Devices.REST_ID, jEvent.getInt("id"));
				devices.add(e);
			}
			return devices;
		} catch (JSONException e) {
			return null;
		}
		
	}

	public static String toJSON(ContentValues values) {
		JSONObject root = new JSONObject();
		JSONObject device = new JSONObject();
		try {
			device.putOpt(Devices.NAME, values.get(Devices.NAME));
			device.putOpt(Devices.LOCATION, values.get(Devices.LOCATION));
			device.putOpt(Devices.DEVICE_TYPE, values.get(Devices.DEVICE_TYPE));
			root.put("device", device);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return root.toString();
	}
}
