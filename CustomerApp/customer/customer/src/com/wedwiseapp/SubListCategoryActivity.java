package com.wedwiseapp;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wedwise.adapter.VendorListByCategory;

public class SubListCategoryActivity extends FragmentActivity {
	ListView lvSubCategory;
	VendorListByCategory adapterSubList;
	ArrayList<String> listItems;
	Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mContext=SubListCategoryActivity.this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sublistfragment);
		idInitialization();
	}

	private void idInitialization() {
		lvSubCategory=(ListView) findViewById(R.id.category_list);
		listItems=new ArrayList<String>();
		listItems.add("Wedding Venues");
		listItems.add("Bridal Fashion");
		listItems.add("Photographers");
		listItems.add("Makeup");
		listItems.add("Caterers");
		listItems.add("Flowers");
		listItems.add("Disc jockeys");
		listItems.add("Invitations");

		adapterSubList=new VendorListByCategory(mContext, listItems);
		lvSubCategory.setAdapter(adapterSubList);
		lvSubCategory.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View viewChild, int position,
					long arg3) {
//				startActivity(new Intent(SubListCategoryActivity.this,  SlidingImagesActivity .class));
			}
		});
	}
}
