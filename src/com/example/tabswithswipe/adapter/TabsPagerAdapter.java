package com.example.tabswithswipe.adapter;

import com.example.taeglicherhadith.FavoritesFragment;
import com.example.taeglicherhadith.HadithFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		
		switch (arg0) {
        case 0:
            // Top Rated fragment activity
            return new HadithFragment();
        case 1:
            // Games fragment activity
            return new FavoritesFragment();
        }
 
        return null;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
