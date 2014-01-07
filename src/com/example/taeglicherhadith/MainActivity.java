package com.example.taeglicherhadith;

import java.util.List;

import com.example.taeglicherhadith.R;
import com.example.taeglicherhadith.db.Hadith;
import com.example.taeglicherhadith.db.HadithDataSource;
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
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar.TabListener;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

public class MainActivity extends FragmentActivity implements TabListener   {

	public static final String LOGTAG = "DAILY_HADITH";
	
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	
	public static final String TAG = "MainActivity";
	
	//tab titles
	private String[] tabs = { "Hadith", "Favoriten"};
	
	//Database handling
	HadithDataSource datasource;
	
	//Social sharing
	private ShareActionProvider mShareActionProvider;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datasource = new HadithDataSource(this);
        datasource.open();
        
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
        
        MenuItem item = menu.findItem(R.id.action_social);
        
        mShareActionProvider = (ShareActionProvider) item.getActionProvider();

        setShareIntent();
        
        return true;
    }
    
    public void doShare() {
    	HadithFetchTask fetchTask = new HadithFetchTask();
		Hadith hadith = fetchTask.doInBackground("http://islamtemplate.com/neu/hadith.php");
    	
    	Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.setType("text/plain");
    	intent.putExtra(Intent.EXTRA_SUBJECT, hadith.getTitle());
    	intent.putExtra(Intent.EXTRA_TEXT, hadith.getHadith());
    	mShareActionProvider.setShareIntent(intent);
    }
    
    private void setShareIntent() {
    	if(mShareActionProvider != null) {
    		HadithFetchTask fetchTask = new HadithFetchTask();
    		Hadith hadith = fetchTask.doInBackground("http://islamtemplate.com/neu/hadith.php");
    		
            Intent intent = new Intent(Intent.ACTION_SEND);
        	intent.setType("text/plain");
        	intent.putExtra(Intent.EXTRA_SUBJECT, hadith.getTitle());
        	intent.putExtra(Intent.EXTRA_TEXT, hadith.getHadith());
        	mShareActionProvider.setShareIntent(intent);
    	}
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
		case R.id.action_favorite:
			onFavoritesIconClicked();
			return true;
		case R.id.action_social:
			doShare();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		datasource.open();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		datasource.close();
	}
	
	public void onFavoritesIconClicked() {
		
		boolean alreadySaved = false;
		
		HadithFetchTask fetchTask = new HadithFetchTask();
		Hadith hadith = fetchTask.doInBackground("http://islamtemplate.com/neu/hadith.php");
		List<Hadith> ahadith = datasource.findAllHadith();
		
		for (Hadith hadithItem : ahadith) {
			if(hadithItem.getTitle().equals(hadith.getTitle())  && 
			   hadithItem.getHadith().equals(hadith.getHadith())) {
				alreadySaved = true;
			}
		}
		
		if(alreadySaved) {
			Context context = getApplicationContext();
			CharSequence text = "Hadith bereits in den Favoriten vorhanden.";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} else {
			hadith = datasource.create(hadith);
			Log.i(LOGTAG, "Hadith added to database. ID: " + hadith.getId() + " title: " + hadith.getTitle());
			
			Context context = getApplicationContext();
			CharSequence text = "Hadith zu den Favoriten hinzugefügt.";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
			FavoritesFragment favoritesFragment = mAdapter.getFragment();
			favoritesFragment.refreshList();
		}
		
	}


	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

		
	}


	@Override
	public void onTabSelected(Tab arg0, FragmentTransaction arg1) {
		
		viewPager.setCurrentItem(arg0.getPosition());
	}


	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		
		
	}
    
}
