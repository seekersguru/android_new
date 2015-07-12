package com.eventmanagementapp.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.eventmanagementapp.R;
import com.eventmanagementapp.util.CustomFonts;

public class BidBookDetailsScreenActivity extends FragmentActivity{

	Button btnBack;
	Context mContext;
	TextView tvCoupleName,tvAddress,tvMobileNumber,tvEventDate,tvTimeSlot,tvPackage,tvQuotdPrice,tvBidPrice,tvAccept,tvReject,tvRebid,
	tvPending;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bid_book_details_screen);
		btnBack=(Button) findViewById(R.id.btnBack);
		mContext=BidBookDetailsScreenActivity.this;
		tvCoupleName=(TextView) findViewById(R.id.tvCoupleName);
		tvAddress=(TextView) findViewById(R.id.tvAddress);
		tvMobileNumber=(TextView) findViewById(R.id.tvMobileNumber);
		tvEventDate=(TextView) findViewById(R.id.tvEventDate);
		tvTimeSlot=(TextView) findViewById(R.id.tvTimeSlot);
		tvPackage=(TextView) findViewById(R.id.tvPackage);
		tvQuotdPrice=(TextView) findViewById(R.id.tvQuotdPrice);
		tvBidPrice=(TextView) findViewById(R.id.tvBidPrice);
		tvAccept=(TextView) findViewById(R.id.tvAccept);
		tvReject=(TextView) findViewById(R.id.tvReject);
		tvRebid=(TextView) findViewById(R.id.tvRebid);
		tvPending=(TextView) findViewById(R.id.tvPending);
//		CustomFonts.setFontOfTextView(mContext,tvCoupleName,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvAddress,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvMobileNumber,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvEventDate,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvTimeSlot,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvPackage,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvQuotdPrice,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvBidPrice,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvAccept,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvReject,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvRebid,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext,tvPending,"fonts/GothamRnd-Light.otf");
//		
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
