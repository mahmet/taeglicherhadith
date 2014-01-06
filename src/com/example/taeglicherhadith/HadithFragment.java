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

import com.example.taeglicherhadith.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class HadithFragment extends Fragment {
	
	public final static String TAG = "hadith_fragment";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		View rootView = inflater.inflate(R.layout.fragment_hadith, container, false);
		
		TextView hadithTitle = (TextView) rootView.findViewById(R.id.hadithTitle);
		TextView hadithText = (TextView) rootView.findViewById(R.id.hadithText);
		
		String fetchedHadith = fetchHadith();
		JSONObject hadithObject;
		try {
			hadithObject = new JSONObject(fetchedHadith);
			Log.i("HADITH", hadithObject.getString("hadith"));
			hadithTitle.setText(hadithObject.getString("title"));
			hadithText.setText(hadithObject.getString("hadith"));
			hadithText.setKeyListener(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return rootView;
	}
	
	public String fetchHadith() {
		
		
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
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
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
		
		return builder.toString();
	}
	
}
