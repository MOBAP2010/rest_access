package ch.good2go.restful;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;

public class RESTMethod {
	
	private static String TAG = "RestMethod";
	private static String JSON = "application/json";
	private static String HEADER_ACCEPT = "Accept";
	private static String HEADER_CONTENT_TYPE = "Content-type";
	
	public static String get(String url){
		HttpGet get = new HttpGet(url);
		get.setHeader(HEADER_ACCEPT, JSON);
		get.setHeader(HEADER_CONTENT_TYPE, JSON);
		HttpEntity entity;
		try {
			entity = new DefaultHttpClient().execute(get).getEntity();
			String response = EntityUtils.toString(entity);
			Log.i(TAG, response);
			return response;
		} catch (ClientProtocolException e) {
			Log.i(TAG, "HTTP-Request GET to:" + url + " failed.");
		} catch (IOException e) {
			Log.i(TAG, "HTTP-Request GET to:" + url + " failed.");
		}	
		return "";
	}

	public static String post(String url, String json) {
		String updatedValues = "";
		HttpPost post = new HttpPost(url); 
		post.setHeader(HEADER_ACCEPT, JSON);
		post.setHeader(HEADER_CONTENT_TYPE, JSON);
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			StringEntity se = new StringEntity(json);
			post.setEntity(se);
			HttpResponse response = client.execute(post);
			updatedValues = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			Log.i(TAG, "HTTP-Request POST to:" + url + " failed.");
		} catch (IOException e) {
			Log.i(TAG, "HTTP-Request POST to:" + url + " failed.");
		}
		return updatedValues;
	}
	
	public static String put(String url, String json) {
		String updatedValues = "";
		HttpPut put = new HttpPut(url);
		put.setHeader(HEADER_ACCEPT, JSON);
		put.setHeader(HEADER_CONTENT_TYPE, JSON);
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			StringEntity se = new StringEntity(json);
			put.setEntity(se);
			HttpResponse response = client.execute(put);
			updatedValues = EntityUtils.toString(response.getEntity());
		} catch (ClientProtocolException e) {
			Log.i(TAG, "HTTP-Request PUT to:" + url + " failed.");
		} catch (IOException e) {
			Log.i(TAG, "HTTP-Request PUT to:" + url + " failed.");
		}
		return updatedValues;
	}
	
	public static boolean delete(String url){
		HttpDelete delete = new HttpDelete(url);
		delete.addHeader(HEADER_ACCEPT, JSON);
		delete.addHeader(HEADER_CONTENT_TYPE, JSON);
		DefaultHttpClient client = new DefaultHttpClient();
		try {
			HttpResponse response = client.execute(delete);
			if(response.getStatusLine().getStatusCode()== 200){
				return true;
			}
		} catch (ClientProtocolException e) {
			Log.i(TAG, "HTTP-Request PUT to:" + url + " failed.");
		} catch (IOException e) {
			Log.i(TAG, "HTTP-Request PUT to:" + url + " failed.");
		}
		return false;
	}
}
