package ch.good2go;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class RestClient {
	
	private static String TAG = "RESTClient";

	private static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public static void getSingleRessource(String url) {

		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i(TAG,response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result= convertStreamToString(instream);
				Log.i(TAG, result);

				// A Simple JSONObject Creation
				JSONObject json=new JSONObject(result);
				Log.i(TAG,"<jsonobject>\n"+json.toString()+"\n</jsonobject>");

				// A Simple JSONObject Parsing
				JSONArray nameArray=json.names();
				JSONArray valArray=json.toJSONArray(nameArray);
				for(int i=0;i<valArray.length();i++)
				{
					Log.i(TAG,"<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
							+"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
				}

				// A Simple JSONObject Value Pushing
				json.put("sample key", "sample value");
				Log.i(TAG,"<jsonobject>\n"+json.toString()+"\n</jsonobject>");

				// Closing the input stream will trigger connection release
				instream.close();
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


	public static void getRessources(String url) {
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i(TAG,response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result= convertStreamToString(instream);
				Log.i(TAG,result);

				// A Simple JSONObject Creation
				JSONArray json = new JSONArray(result);
				for(int i = 0; i < json.length(); i++) {
					JSONObject device = (JSONObject) json.getJSONObject(i).get("device");
					Log.i(TAG, ((Integer)device.getInt("id")).toString());
					Log.i(TAG, device.getString("location"));
				}
				// Closing the input stream will trigger connection release
				instream.close();
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
	
	public static void updateDevices(String url, DevicesDbHelper dbHelper) {
		HttpClient httpclient = new DefaultHttpClient();
		// Prepare a request object
		HttpGet httpget = new HttpGet(url); 
		// Execute the request
		HttpResponse response;
		try {
			response = httpclient.execute(httpget);
			// Examine the response status
			Log.i(TAG,response.getStatusLine().toString());

			// Get hold of the response entity
			HttpEntity entity = response.getEntity();
			// If the response does not enclose an entity, there is no need
			// to worry about connection release

			if (entity != null) {

				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result= convertStreamToString(instream);
				Log.i(TAG,result);
				
				// A Simple JSONObject Creation
				JSONArray json = new JSONArray(result);
				for(int i = 0; i < json.length(); i++) {
					JSONObject device = (JSONObject) json.getJSONObject(i).get("device");
					Log.i(TAG, ((Integer)device.getInt("id")).toString());
					Log.i(TAG, device.getString("name"));
					Log.i(TAG, device.getString("location"));
					Log.i(TAG, device.getString("created_at"));
					Log.i(TAG, device.getString("updated_at"));
					dbHelper.updateDevice(
										  device.getInt("id"), 
										  device.getString("name"), 
										  device.getString("location"), 
										  device.getString("created_at"), 
										  device.getString("updated_at")
										  );
				}
				// Closing the input stream will trigger connection release
				instream.close();
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

}