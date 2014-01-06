package com.example.taeglicherhadith;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HadithDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hadith_detail_activity);
		
		TextView title = (TextView) findViewById(R.id.detail_hadith_title);
		title.setText(getIntent().getExtras().getString("title"));
	}
	
}
