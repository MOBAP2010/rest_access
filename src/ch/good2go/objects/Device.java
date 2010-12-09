package ch.good2go.objects;

import ch.good2go.restful.DeviceRestContentProvider;
import android.net.Uri;
import android.provider.BaseColumns;


public class Device {
	
	private int id;
	private String name;
	private String location;
	private String device_type;
	private boolean power;
	
	public Device(){
		
	}
	public Device(int id, String name, String location, String device_type, boolean power) {
		this.setId(id);
		this.setName(name);
		this.setLocation(location);
		this.setDevice_type(device_type);
		this.setPower(power);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}

	public String getDevice_type() {
		return device_type;
	}

	public void setPower(boolean power) {
		this.power = power;
	}
	public boolean getPower() {
		return power;
	}

	public static final class Devices implements BaseColumns {
		
		private Devices() {
			
		}

		public static final String DEVICE_ID = "_id";
		public static final String LOCATION = "location";
		public static final String NAME = "name";
		public static final String POWER = "power";
		public static final String DEVICE_TYPE = "device_type";
		public static final String REST_ID = "rest_id";
		public static final String UPDATED_AT = "updated_at";
		public static final String CREATED_AT = "created_at";
		public static final String CONTENT_TYPE = "application/json";
		public static final Uri CONTENT_URI = Uri.parse("content://" + DeviceRestContentProvider.AUTHORITY + "/devices");
	}
}
