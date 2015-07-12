package com.wedwiseapp;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.wedwise.adapter.AlbumAdapter;
import com.wedwise.tab.MessageTabActivity;
import com.wedwiseapp.login.LoginSignUpActivity;

public class VendorCategoryHome extends Activity implements
OnMenuItemClickListener,OnClickListener {

	// Button btnBack;
	// RecyclerView listRecyclerView;
	ListView lvAlbums;
	AlbumAdapter adapterAlbum;
	ArrayList<String> listItems;
	Button btnMail, btnMenu,btnLeads;
	LinearLayout llMail,llHome,llLeads,llMenu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vendorcategoryhome);
		lvAlbums = (ListView) findViewById(R.id.lvAlbums);
		btnMail = (Button) findViewById(R.id.btnMail);
		btnMenu = (Button) findViewById(R.id.btnMenu);
		btnLeads=(Button) findViewById(R.id.btnLeads);
		llMail=(LinearLayout) findViewById(R.id.llMail);
		llHome=(LinearLayout) findViewById(R.id.llHome);	
		llLeads=(LinearLayout) findViewById(R.id.llLeads);
		llMenu=(LinearLayout) findViewById(R.id.llMenu);

		listItems = new ArrayList<String>();
		listItems.add("BANQUITE");
		listItems.add("PHOTOGRAPHY");
		listItems.add("CATERERS");
		listItems.add("DECORATORS");
		listItems.add("OTHERS");
		llMail.setOnClickListener(this);
		llHome.setOnClickListener(this);
		llLeads.setOnClickListener(this);
		llMenu.setOnClickListener(this);
		btnMail.setOnClickListener(this);
		btnMenu.setOnClickListener(this);
		btnLeads.setOnClickListener(this);

//		adapterAlbum = new AlbumAdapter(VendorCategoryHome.this, listItems);
//		lvAlbums.setAdapter(adapterAlbum);

		lvAlbums.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent myIntent = new Intent(VendorCategoryHome.this,
						FavListActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.login:

			Intent myIntent = new Intent(VendorCategoryHome.this,
					LoginSignUpActivity.class);
			startActivity(myIntent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);

			break;
		}
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnMail:
		case R.id.llMail:
			Intent myIntent = new Intent(VendorCategoryHome.this,
					MessageTabActivity.class);
			startActivity(myIntent);
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;

		case R.id.btnMenu:
		case R.id.llMenu:
			Intent myIntentLoginActivity = new Intent(VendorCategoryHome.this,
					LoginSignUpActivity.class);
			startActivity(myIntentLoginActivity);
			overridePendingTransition(R.anim.right_out, R.anim.left_out);
			break;

		default:
			break;
		}		
	}
}
