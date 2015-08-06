package com.wedwiseapp;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.wedwise.adapter.ViewPagerAdapter;

public class VendorDetailViewPagerScreen  extends FragmentActivity{
	
	Button btnBack;
	
	Context mContext;
	ViewPager mypager;
	private ArrayList<String> imageArra;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vendor_view_pager_full_screen);
		imageArra = getIntent().getStringArrayListExtra("imageArray");
		mContext=VendorDetailViewPagerScreen.this;
		btnBack=(Button) findViewById(R.id.btnBack);
		mypager = (ViewPager) findViewById(R.id.mypager);
		
		ViewPagerAdapter adapterview = new ViewPagerAdapter(VendorDetailViewPagerScreen.this, imageArra);
		mypager.setAdapter(adapterview);
		mypager.setCurrentItem(0);

		
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				VendorDetailViewPagerScreen.this.finish();		
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

}
