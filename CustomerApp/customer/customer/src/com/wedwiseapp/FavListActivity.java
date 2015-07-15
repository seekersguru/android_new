package com.wedwiseapp;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.vendordetailpage.MainActivity;
import com.wedwise.adapter.FavAdapter;
import com.wedwise.adapter.SpinnerAdapter;
import com.wedwise.fragments.VendorCategoryHomeFragment;

@SuppressLint("InflateParams")
public class FavListActivity extends FragmentActivity {
	ListView favList;
	FavAdapter adapterSubList;
	Context mContext;
	Button btnBack,btnMenu,btnSearch;//,btnSpinnerOpen;
	ArrayList<FavData> data;
	SearchView searchView;
	//	Spinner spSwitchCategory;
	ArrayList<String> listCategory;
	SpinnerAdapter  adapterSpinner;
	//	ImageView imViewCategoryType;
	TextView tvCategoryType;
	View viewTopbar;
	String category="";
	Button btnCategory;
	Spinner spSwitchCategory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = FavListActivity.this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.favorite);
		idInitialization();
	}

	private void idInitialization() {
		//		spSwitchCategory=(Spinner) findViewById(R.id.spSwitchCategory);
		favList = (ListView) findViewById(R.id.favList);
		listCategory=new ArrayList<String>();
		data=new ArrayList<FavData>();
		adapterSubList = new FavAdapter(mContext,data);
		favList.setAdapter(adapterSubList);
		btnBack=(Button) findViewById(R.id.btnBack);
		viewTopbar=findViewById(R.id.viewTopbar);
		viewTopbar.setVisibility(View.GONE);
		searchView=(SearchView) findViewById(R.id.searchView);
		btnSearch=(Button) findViewById(R.id.btnSeacrh);
		tvCategoryType=(TextView) findViewById(R.id.tvCategoryType);
		spSwitchCategory=(Spinner) findViewById(R.id.spSwitchCategory);
		btnCategory=(Button) findViewById(R.id.btnCategory);
		btnCategory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				spSwitchCategory.performClick();
			}
		});

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, VendorCategoryHomeFragment.listItemsCategory);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spSwitchCategory.setAdapter(dataAdapter);

		spSwitchCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				category=String.valueOf(spSwitchCategory.getSelectedItem());
				tvCategoryType.setText(category);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		//		imViewCategoryType=(ImageView) findViewById(R.id.imViewCategoryType);
		//		btnSpinnerOpen=(Button) findViewById(R.id.btnSpinnerOpen);
		btnSearch.setVisibility(View.VISIBLE);
		//		imViewCategoryType.setVisibility(View.VISIBLE);
		tvCategoryType.setVisibility(View.VISIBLE);

		if(getIntent()!=null && getIntent().getExtras()!=null)
		{
			category=getIntent().getExtras().getString("category_type");
			tvCategoryType.setText(category);
		}
		else{
			tvCategoryType.setText("");
		}
		//		actionBar.setDisplayShowCustomEnabled(true);

		listCategory.add("BANQUITE");
		listCategory.add("PHOTOGRAPHY");
		listCategory.add("CATERERS");
		listCategory.add("DECORATORS");
		listCategory.add("OTHERS");
		adapterSpinner=new SpinnerAdapter(FavListActivity.this, listCategory);
		//		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		//				android.R.layout.simple_spinner_item, listCategory);
		//		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		//		spSwitchCategory.setAdapter(adapterSpinner);
		/*btnSpinnerOpen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				spSwitchCategory.performClick();				
			}
		});*/
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				btnSearch.setVisibility(View.INVISIBLE);
				searchView.setVisibility(View.VISIBLE);
				tvCategoryType.setText(category.substring(0,1)+"...");
				/*InputFilter[] filterArray = new InputFilter[1];
				filterArray[0] = new InputFilter.LengthFilter(20);
				tvCategoryType.setFilters(filterArray);*/
				searchView.setIconified(false);
				searchView.setBackgroundColor(Color.TRANSPARENT);
				searchView.requestFocus();
				int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
				//				searchView.findViewById(searchPlateId).setBackgroundColor(Color.parseColor("#00000000"));
				View searchPlateView=searchView.findViewById(searchPlateId);
				if(searchPlateView!=null)
				{
					searchPlateView.setBackgroundColor(Color.WHITE);
				}
				try {
					int id=searchView.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
					TextView tv=(TextView) searchView.findViewById(id);
					EditText et=(EditText) searchView.findViewById(id);
					et.setHint("Search Here");
					tv.setTextColor(Color.parseColor("#F05543"));
				} catch (Exception e) {
					e.getMessage();
				}
				searchView.performClick();
			}
		});

		//		spSwitchCategory.setOnItemSelectedListener(new OnItemSelectedListener() {
		//
		//			@Override
		//			public void onItemSelected(AdapterView<?> parent, View view,
		//					int position, long id) {
		//
		//			}
		//
		//			@Override
		//			public void onNothingSelected(AdapterView<?> parent) {
		//			}
		//		});
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {

				btnSearch.setVisibility(View.VISIBLE);
				searchView.setVisibility(View.INVISIBLE);
				tvCategoryType.setText(category);
				/*InputFilter[] filterArray = new InputFilter[1];
				filterArray[0] = new InputFilter.LengthFilter(1);
				tvCategoryType.setFilters(filterArray);*/
				//				searchView.setIconified(true);
				return false;
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});

		favList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View viewChild,
					int position, long arg3) {

				Intent myIntent = new Intent(FavListActivity.this,
						MainActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		/*btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(FavListActivity.this,
						LoginSignUpActivity.class);
				startActivity(myIntent);

				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});*/
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

}
