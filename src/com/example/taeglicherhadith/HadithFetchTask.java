package com.example.taeglicherhadith;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.taeglicherhadith.db.Hadith;

import android.os.AsyncTask;
import android.util.Log;

public class HadithFetchTask extends AsyncTask<String, Integer, Hadith> {

	@Override
	protected Hadith doInBackground(String... arg0) {
		
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://islamtemplate.com/neu/hadith.php");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fetchedHadith = builder.toString();
		JSONObject hadithObject;
		try {
			hadithObject = new JSONObject(fetchedHadith);
			Log.i("HADITH", hadithObject.getString("hadith"));
			Hadith hadith = new Hadith(hadithObject.getString("title"), hadithObject.getString("hadith"));
			return hadith;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
