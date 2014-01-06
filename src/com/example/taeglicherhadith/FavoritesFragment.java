package com.example.taeglicherhadith;

import java.util.List;

import com.example.taeglicherhadith.R;
import com.example.taeglicherhadith.db.Hadith;
import com.example.taeglicherhadith.db.HadithDataSource;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FavoritesFragment extends Fragment {

	ListView listView;
	List<Hadith> ahadith;
	
	HadithDataSource datasource;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		final View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
		
		
		
		datasource = new HadithDataSource(rootView.getContext());
		
		listView = (ListView) rootView.findViewById(R.id.favoritesListView);
		
		ahadith = datasource.findAllHadith();
		
		ArrayAdapter<Hadith> adapter = new ArrayAdapter<Hadith>(rootView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, ahadith);
		
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Hadith hadith = (Hadith) listView.getItemAtPosition(arg2);
				
				Intent intent = new Intent(rootView.getContext(), HadithDetailActivity.class);
        		intent.putExtra("title", hadith.getTitle());
        		intent.putExtra("hadith", hadith.getHadith());
        		startActivity(intent);
			}	 
		}); 
		
		return rootView;
	}
	
}
