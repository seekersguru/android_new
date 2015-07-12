package com.wedwiseapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wedwiseapp.util.CustomFonts;

public class VendorDetailsPageMapPopup extends FragmentActivity{

	Button btnBack;
	TextView tvPlace,tvAddress,tvPlaceBottom,tvAddressBottom;
	
	Context mContext;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vendor_detail_page_map_popup_page);
		mContext=VendorDetailsPageMapPopup.this;
		btnBack=(Button) findViewById(R.id.btnBack);
		tvPlace=(TextView) findViewById(R.id.tvPlace);		
		tvAddress=(TextView) findViewById(R.id.tvAddress);	
		tvPlaceBottom=(TextView) findViewById(R.id.tvPlaceBottom);
		tvAddressBottom=(TextView) findViewById(R.id.tvAddressBottom);
//		CustomFonts.setFontOfTextView(mContext,tvPlace,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvAddress,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvPlaceBottom,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvAddressBottom,"fonts/GothamRnd-Light.otf");
		
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}
}
