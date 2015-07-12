/*package com.wedwiseapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wedwise.server.api.Server;

public class CategoryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		setContentView(R.layout.activity_category);
		
		ListView v = (ListView) findViewById(R.id.categoryList);
		CategoryAdapter ca = new CategoryAdapter(this, Server.getInstance().getCategories());
		v.setAdapter(ca);
		
		v.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View viewChild, int position, long arg3) {
				startActivity(new Intent(CategoryActivity.this, SubListCategoryActivity.class));
			}
		});
	}
}
*/