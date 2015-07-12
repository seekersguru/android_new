package com.wedwiseapp;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.wedwise.fragments.VendorCategoryHomeFragment;
import com.wedwise.navigationdrawer.NavigationDrawerCallbacks;
import com.wedwise.navigationdrawer.NavigationDrawerFragment;

@SuppressWarnings("deprecation")
public class NavigationDrawerHomeActivity extends ActionBarActivity implements
NavigationDrawerCallbacks {

	private Toolbar mToolbar;
	private NavigationDrawerFragment mNavigationDrawerFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_topdrawer);
		mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
		mNavigationDrawerFragment.setup(R.id.fragment_drawer,(DrawerLayout) findViewById(R.id.drawer), mToolbar);
		if (mNavigationDrawerFragment!=null && mNavigationDrawerFragment.isDrawerOpen())
			mNavigationDrawerFragment.closeDrawer();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return false;
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		Fragment fragment=null;
		if(position==0 || position==2)
		{
			fragment=new VendorCategoryHomeFragment();
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
		}
		if (mNavigationDrawerFragment!=null && mNavigationDrawerFragment.isDrawerOpen())
			mNavigationDrawerFragment.closeDrawer();
	}

	@Override
	public void onBackPressed() {
		if (mNavigationDrawerFragment.isDrawerOpen())
			mNavigationDrawerFragment.closeDrawer();
		else
			super.onBackPressed();
	}
}

/*import java.util.ArrayList;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.wedwise.adapter.NavigationDrawerAdapter;
import com.wedwise.model.ObjectDrawerItem;

public class NavigationDrawerHomeActivity extends FragmentActivity{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mNavigationListTitles;
	Toolbar toolbar;
	//	TextView toolbartitle;
	ArrayList<ObjectDrawerItem> listItems;
	TextView tvHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.navigationdrawerlayout);
		mTitle = mDrawerTitle = getTitle();
		mNavigationListTitles = getResources().getStringArray(R.array.navigationlistarray);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		tvHeader=(TextView) findViewById(R.id.tvHeader);
		// set a custom shadow that overlays the main content when the drawer opens
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, Gravity.START);

		tvHeader.setShadowLayer(1, 2, 2, Color.BLACK);
		listItems=new ArrayList<ObjectDrawerItem>(); 
		ObjectDrawerItem item1=new
				ObjectDrawerItem(R.drawable.like, "Banquite");
		listItems.add(item1); ObjectDrawerItem item2=new
				ObjectDrawerItem(R.drawable.like, "Decorators");
		listItems.add(item2); ObjectDrawerItem item3=new
				ObjectDrawerItem(R.drawable.like, "Caterers");
		listItems.add(item3); ObjectDrawerItem item4=new
				ObjectDrawerItem(R.drawable.like, "Photography");
		listItems.add(item4); ObjectDrawerItem item5=new
				ObjectDrawerItem(R.drawable.like, "Others");
		listItems.add(item5); 
		mDrawerList.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mDrawerLayout.isDrawerOpen(Gravity.START))
				{
					mDrawerLayout.closeDrawer(mDrawerList);
				}
				else{
					mDrawerLayout.openDrawer(Gravity.START);
				}				
			}
		});
		//		mDrawerLayout.setOnTouchListener(new OnTouchListener() {
		//
		//			@Override
		//			public boolean onTouch(View v, MotionEvent event) {
		//				if(mDrawerLayout.isDrawerOpen(GravityCompat.START))
		//				{
		//					mDrawerLayout.closeDrawer(mDrawerList);
		//				}
		//				else{
		//					mDrawerLayout.openDrawer(GravityCompat.START);
		//				}
		//				return false;
		//			}
		//		});


		// set up the drawer's list view with items and click listener
		mDrawerList.setAdapter(new NavigationDrawerAdapter(NavigationDrawerHomeActivity.this, listItems));
		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.drawer_list_item, mNavigationListTitles));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
		mDrawerToggle = new ActionBarDrawerToggle(
				this,                   host Activity 
				mDrawerLayout,          DrawerLayout object 
				toolbar,   nav drawer image to replace 'Up' caret 
				R.string.drawer_open,   "open drawer" description for accessibility 
				R.string.drawer_close   "close drawer" description for accessibility 
				) {
			public void onDrawerClosed(View view) {
				//getSupportActionBar().setTitle(mTitle);
				//				toolbartitle.setText(mTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}

			public void onDrawerOpened(View drawerView) {
				//getSupportActionBar().setTitle(mDrawerTitle);
				//				toolbartitle.setText(mDrawerTitle);
				invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();

		if (savedInstanceState == null) {
			//			selectItem(2);
		}
	}

	 The click listner for ListView in the navigation drawer 
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		Fragment fragment=null;
		if(position == 3){
			//			fragment = new DirectoryFragment();
		}else{
			//			fragment = new HomeFragment();
		}
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		setTitle(mNavigationListTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		//getSupportActionBar().setTitle(mTitle);
		//		toolbartitle.setText(mTitle);
	}

 *//**
 * When using the ActionBarDrawerToggle, you must call it during
 * onPostCreate() and onConfigurationChanged()...
 *//*

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

  *//**
  * Fragment that appears in the "content_frame", shows a planet
  *//*

}
   */