package com.eventmanagementapp;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.eventmanagementapp.tab.BidBookCreateActivity;
import com.eventmanagementapp.tab.SlidingTabLayout;
import com.eventmanagementapp.tab.TabAdapter;

/**
 * Created by Edwin on 15/02/2015.
 */
public class MessageTabActivity extends FragmentActivity {

	// Declaring Your View and Variables

	Toolbar toolbar;
	ViewPager pager;
	TabAdapter adapter;
	SlidingTabLayout tabs;
	CharSequence Titles[]={"Enquiry","Book"};
	int Numboftabs =2;
	Button btnBack;
	Button btnAddBidBook;

	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.messagetabactivity);
		// Creating The Toolbar and setting it as the Toolbar for the activity

		toolbar = (Toolbar) findViewById(R.id.tool_bar);
		toolbar.setVisibility(View.GONE);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.customactionbarview, null);
		actionBar.setCustomView(mCustomView);
		btnBack=(Button) mCustomView.findViewById(R.id.btnBack);
		actionBar.setDisplayShowCustomEnabled(true);
		btnAddBidBook=(Button) mCustomView.findViewById(R.id.btnAddBidBook);
		btnAddBidBook.setVisibility(View.VISIBLE);
		btnAddBidBook.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent=new Intent(MessageTabActivity.this,BidBookCreateActivity.class);
			if(pager.getCurrentItem()==0)
				myIntent.putExtra("type","bid");
			else
				myIntent.putExtra("type","book");
			
			startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);	
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		// Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
		adapter =  new TabAdapter(getSupportFragmentManager(),Titles,Numboftabs);

		// Assigning ViewPager View and setting the adapter
		pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		// Assiging the Sliding Tab Layout View
		tabs = (SlidingTabLayout) findViewById(R.id.tabs);
		tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

		// Setting Custom Color for the Scroll bar indicator of the Tab View
		tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.tabsScrollColor);
			}
		});

		// Setting the ViewPager For the SlidingTabsLayout
		tabs.setViewPager(pager);
		pager.setCurrentItem(2);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item= menu.findItem(R.id.action_settings);
		item.setVisible(false);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}
}