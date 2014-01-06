package com.example.taeglicherhadith;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HadithDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hadith_detail_activity);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		TextView title = (TextView) findViewById(R.id.detail_hadith_title);
		TextView hadith = (TextView) findViewById(R.id.detail_hadith_text);
		title.setText(getIntent().getExtras().getString("title"));
		hadith.setText(getIntent().getExtras().getString("hadith"));
		
	}
	
}
