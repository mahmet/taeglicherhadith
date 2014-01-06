package com.example.taeglicherhadith;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;

import com.example.taeglicherhadith.R;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoritesFragment extends Fragment {

	ListView listView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
		
		listView = (ListView) rootView.findViewById(R.id.favoritesListView);
		String[] values = new String[] {};
		
		try {
			JSONArray hadithArray = getJsonArray();
			for(int i=0; i<hadithArray.length(); i++) {
				values[i] = hadithArray.getJSONObject(i).getString("title");
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
			
			listView.setAdapter(adapter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Intent intent = new Intent(rootView.getContext(), HadithDetailActivity.class);
        		intent.putExtra("title", listView.getItemAtPosition(arg2).toString());
        		startActivity(intent);
			}	 
		}); 
		
		return rootView;
	}
	
	private JSONArray getJsonArray() throws IOException, JSONException {
		File file = new File("favorites");
		if(file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			StringBuffer b = new StringBuffer();
			while (bis.available() != 0) {
				char c = (char) bis.read();
				b.append(c);
			}
			bis.close();
			fis.close();
			
			JSONArray hadithArray = new JSONArray(b.toString());
			return hadithArray;
		} else {
			Log.e("FILENOTFOUND", "File could not be loaded: Not existent");
		}
		return null;
	}
	
}
