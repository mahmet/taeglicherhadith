package com.example.taeglicherhadith;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.taeglicherhadith.R;
import com.example.tabswithswipe.adapter.TabsPagerAdapter;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.app.ActionBar.TabListener;
import android.content.res.Resources;
import android.graphics.Color;

public class MainActivity extends FragmentActivity implements TabListener   {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	public static final String TAG = "MainActivity";
	
	//tab titles
	private String[] tabs = { "Hadith", "Favoriten"};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //Initialization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        TextView actionBarTitle = (TextView) findViewById(titleId);
        actionBarTitle.setTextColor(Color.WHITE);
        
        //Adding tabs
        for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
		}
        
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				actionBar.setSelectedNavigationItem(arg0);
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.action_favorite:
			try {
				onFavoritesIconClicked();
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			return true;
		case R.id.action_social:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }


	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		
		viewPager.setCurrentItem(tab.getPosition());
		
	}


	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	public void onFavoritesIconClicked() throws JSONException, IOException {
		
		String hadithTitleToSave = "title";
		String hadithTextToSave = "hadith";
		
		String fetchedHadith = fetchHadith();
		JSONObject hadithObject;
		try {
			hadithObject = new JSONObject(fetchedHadith);
			Log.i("HADITH", hadithObject.getString("hadith"));
			hadithTitleToSave = hadithObject.getString("title");
			hadithTextToSave = hadithObject.getString("hadith");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONArray hadithArray = new JSONArray();
		JSONObject hadith = new JSONObject();
		hadith.put("title", hadithTitleToSave);
		hadith.put("hadith", hadithTextToSave);
		hadithArray.put(hadith);
		
		File fileToSave = new File("favorites");
		
		String text = hadithArray.toString();
		
		if(fileToSave.exists()) {
			FileInputStream fis = openFileInput("favorites");
			BufferedInputStream bis = new BufferedInputStream(fis);
			StringBuffer b = new StringBuffer();
			while (bis.available() != 0) {
				char c = (char) bis.read();
				b.append(c);
			}
			bis.close();
			fis.close();
			
			JSONArray oldHadithArray = new JSONArray(b.toString());
			oldHadithArray.put(hadith);
			
			String newText = oldHadithArray.toString();
			
			FileOutputStream fos = openFileOutput("favorites", MODE_PRIVATE);
			fos.write(newText.getBytes());
			fos.close();
			
		} else {
			FileOutputStream fos = openFileOutput("favorites", MODE_PRIVATE);
			fos.write(text.getBytes());
			fos.close();
		}
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

public String fetchHadithText() {
	String fetchedHadith = fetchHadith();
	JSONObject hadithObject;
	try {
		hadithObject = new JSONObject(fetchedHadith);
		Log.i("HADITH", hadithObject.getString("hadith"));
		return hadithObject.getString("hadith");
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
    
}
