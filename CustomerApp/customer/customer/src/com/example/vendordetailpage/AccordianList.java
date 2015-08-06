package com.example.vendordetailpage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wedwise.gsonmodels.KeyValueHeader.KeyValue;
import com.wedwise.gsonmodels.PackagesModel;
import com.wedwise.json.PackagesModelNew;
import com.wedwise.json.TopPackege;
import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;
/**
 * 
 * @author manish
 *
 */
public class AccordianList extends Activity {
	
	private PackagesModel packegeModel;
	
	private LinearLayout mLinearListView;
	boolean isFirstViewClick=false;
	boolean isSecondViewClick=false;
	private Button btnShowIndBack;
	TextView tvTitle;
	String header;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.accordianlist);
		mLinearListView = (LinearLayout) findViewById(R.id.linear_listview);
		btnShowIndBack = (Button) findViewById(R.id.btnShowIndBack);
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		
		CustomFonts.setFontOfTextView(AccordianList.this, tvTitle, "fonts/GothamRoundedBook.ttf");
		
		btnShowIndBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				setResult(RESULT_OK);
				overridePendingTransition(R.anim.right_out, R.anim.left_in);
			}
		});
		
		packegeModel=(PackagesModel)getIntent().getExtras().get("Datalist");
		header=getIntent().getStringExtra("header");
		tvTitle.setText(header);
		
		for (int i = 0; i < packegeModel.getPackage_Values().size(); i++) {
			
			TopPackege tppackege=packegeModel.getPackage_Values().get(i);
			
			LayoutInflater inflater = null;
			inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View mLinearView = inflater.inflate(R.layout.row_first, null);
			
			final TextView mProductName = (TextView) mLinearView.findViewById(R.id.textViewName);
			final RelativeLayout mLinearFirstArrow=(RelativeLayout)mLinearView.findViewById(R.id.linearFirst);
			final ImageView mImageArrowFirst=(ImageView)mLinearView.findViewById(R.id.imageFirstArrow);
			final LinearLayout mLinearScrollSecond=(LinearLayout)mLinearView.findViewById(R.id.linear_scroll);
			
			CustomFonts.setFontOfTextView(AccordianList.this, mProductName, "fonts/GothamRoundedBook.ttf");
			
			if(isFirstViewClick==false){
			mLinearScrollSecond.setVisibility(View.GONE);
			mImageArrowFirst.setBackgroundResource(R.drawable.arw_lt);
			}
			else{
				mLinearScrollSecond.setVisibility(View.VISIBLE);
				mImageArrowFirst.setBackgroundResource(R.drawable.arw_down);
			}
			
			mLinearFirstArrow.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					
					if(isFirstViewClick==false){
						isFirstViewClick=true;
						mImageArrowFirst.setBackgroundResource(R.drawable.arw_down);
						mLinearScrollSecond.setVisibility(View.VISIBLE);
						
					}else{
						isFirstViewClick=false;
						mImageArrowFirst.setBackgroundResource(R.drawable.arw_lt);
						mLinearScrollSecond.setVisibility(View.GONE);	
					}
					return false;
				} 
			});
			
			
			final String name = tppackege.getPackegeType();
			mProductName.setText(name);
		    
			/**
			 * 
			 */
		    for (int j = 0; j < tppackege.getPackegeItems().size(); j++) {
				PackagesModelNew packmodelnew=tppackege.getPackegeItems().get(j);
		    	
				LayoutInflater inflater2 = null;
				inflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View mLinearView2 = inflater2.inflate(R.layout.row_second, null);
		    
				TextView mSubItemName = (TextView) mLinearView2.findViewById(R.id.textViewTitle);
				final RelativeLayout mLinearSecondArrow=(RelativeLayout)mLinearView2.findViewById(R.id.linearSecond);
				final ImageView mImageArrowSecond=(ImageView)mLinearView2.findViewById(R.id.imageSecondArrow);
				final LinearLayout mLinearScrollThird=(LinearLayout)mLinearView2.findViewById(R.id.linear_scroll_third);
				
				CustomFonts.setFontOfTextView(AccordianList.this, mSubItemName, "fonts/GothamRoundedBook.ttf");
				
				if(isSecondViewClick==false){
					mLinearScrollThird.setVisibility(View.GONE);
					mImageArrowSecond.setBackgroundResource(R.drawable.arw_lt);
					}
					else{
						mLinearScrollThird.setVisibility(View.VISIBLE);
						mImageArrowSecond.setBackgroundResource(R.drawable.arw_down);
					}
					
				mLinearSecondArrow.setOnTouchListener(new OnTouchListener() {
						
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							
							if(isSecondViewClick==false){
								isSecondViewClick=true;
								mImageArrowSecond.setBackgroundResource(R.drawable.arw_down);
								mLinearScrollThird.setVisibility(View.VISIBLE);
								
							}else{
								isSecondViewClick=false;
								mImageArrowSecond.setBackgroundResource(R.drawable.arw_lt);
								mLinearScrollThird.setVisibility(View.GONE);	
							}
							return false;
						} 
					});
				
				
				final String catName =packmodelnew.getLabel();
				mSubItemName.setText(catName);
				
				//Need to set data here extra 
				mLinearScrollThird.addView(getLastView(packmodelnew.getMinimum()));
				mLinearScrollThird.addView(getLastView(packmodelnew.getQuoted()));
				  for (int k = 0; k <packmodelnew.getOptions().size(); k++) {
						
					  KeyValue keyvalue=packmodelnew.getOptions().get(k);
					  mLinearScrollThird.addView(getLastView(keyvalue));
				  }
				
				mLinearScrollSecond.addView(mLinearView2);
		    
		    }
		    
		    mLinearListView.addView(mLinearView);
		}		
	}
private View getLastView(KeyValue keyValue){
	LayoutInflater inflater3 = null;
	inflater3 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	View mLinearView3 = inflater3.inflate(R.layout.row_third, null);

	TextView mItemName = (TextView) mLinearView3.findViewById(R.id.textViewItemName);
	TextView mItemPrice = (TextView) mLinearView3.findViewById(R.id.textViewItemPrice);
	final String itemName = keyValue.getKey();
	final String itemPrice = keyValue.getValue();
	CustomFonts.setFontOfTextView(AccordianList.this, mItemName, "fonts/GothamRoundedBook.ttf");
	CustomFonts.setFontOfTextView(AccordianList.this, mItemPrice, "fonts/GothamRoundedBook.ttf");
	mItemName.setText(itemName);
	mItemPrice.setText(itemPrice);
	
	return mLinearView3;
}

@Override
public void onBackPressed() {
	super.onBackPressed();
	setResult(RESULT_OK);
	overridePendingTransition(R.anim.right_out, R.anim.left_in);
	
}
}
