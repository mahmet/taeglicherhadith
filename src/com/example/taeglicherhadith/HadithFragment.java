package com.example.taeglicherhadith;

import com.example.taeglicherhadith.R;
import com.example.taeglicherhadith.db.Hadith;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.StrictMode;
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
		
		HadithFetchTask fetchTask = new HadithFetchTask();
		Hadith hadith = fetchTask.doInBackground("http://islamtemplate.com/neu/hadith.php");
		
		hadithTitle.setText(hadith.getTitle());
		hadithText.setText(hadith.getHadith());
		
		return rootView;
	}
	
}
