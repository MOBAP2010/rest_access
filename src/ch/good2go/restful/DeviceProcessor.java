package ch.good2go.restful;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import ch.good2go.objects.Device.Devices;

public class DeviceProcessor {
	
	private static final String DEVICE = "device";
	
	public static ArrayList<ContentValues> parseJSONArray(final String str) {
		try {
			ArrayList<ContentValues> devices = new ArrayList<ContentValues>();
			JSONArray json = new JSONArray(str);
			int length = json.length();
			for (int i = 0; i < length; i++) {
				JSONObject device = json.getJSONObject(i).getJSONObject(DEVICE);
				devices.add(createContentValues(device));
			}
			return devices;
		} catch (JSONException e) {
			return null;
		}
		
	}
	
	private static ContentValues createContentValues(JSONObject json) throws JSONException{
		ContentValues e = new ContentValues();
        e.put(Devices.NAME, json.getString("name"));
        e.put(Devices.LOCATION, json.getString("location"));
        e.put(Devices.DEVICE_TYPE, json.getString("device_type"));
        e.put(Devices.POWER, json.getBoolean("power"));
        e.put(Devices.REST_ID, json.getInt("id"));
        e.put(Devices.CREATED_AT, json.getString("created_at"));
        e.put(Devices.UPDATED_AT, json.getString("updated_at"));
        return e;
	}
	
	public static ContentValues parseJSON(final String str){
		ContentValues values = new ContentValues();
		try {
			JSONObject object = new JSONObject(str);
			values = createContentValues(object.getJSONObject(DEVICE));
		} catch (JSONException e) {
			return values;
		}
		return values;
	}

	public static String toJSON(ContentValues values) {
		JSONObject root = new JSONObject();
		JSONObject device = new JSONObject();
		try {
			device.putOpt(Devices.NAME, values.get(Devices.NAME));
			device.putOpt(Devices.LOCATION, values.get(Devices.LOCATION));
			device.putOpt(Devices.DEVICE_TYPE, values.get(Devices.DEVICE_TYPE));
			device.putOpt(Devices.POWER, values.get(Devices.POWER));
			root.put("device", device);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return root.toString();
	}
}
