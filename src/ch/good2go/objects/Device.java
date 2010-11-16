package ch.good2go.objects;

import ch.good2go.restful.DeviceRestContentProvider;
import android.net.Uri;
import android.provider.BaseColumns;


	public class Device {

		public Device() {
			
		}

		public static final class Devices implements BaseColumns {
			
			private Devices() {
				
			}

			public static final String DEVICE_ID = "_id";
			public static final String LOCATION = "location";
			public static final String NAME = "name";
			public static final String CONTENT_TYPE = "application/json";
			public static final Uri CONTENT_URI = Uri.parse("content://" + DeviceRestContentProvider.AUTHORITY + "/devices");
		}

	}
