package com.wedwiseapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wedwise.calendar.CalendarActivity;
import com.wedwise.dialogs.FacilitiesDialog;
import com.wedwise.dialogs.GeneralDialog;
import com.wedwise.dialogs.PackagesDialog;
import com.wedwise.dialogs.VideoViewerDialog;

public class VendorDetailsActivity extends FragmentActivity implements OnClickListener{

	Button btnBack,btnSchedule;
	TextView tvTitle,tvVenue,tvAddress,tvMobileNumber,
	tvLocation,tvSharedRoom,tvCapacity,tvCapacityCount,tvRate,
	tvType,tvTypeData,tvReadMoreSecond,tvVideoLink,tvRotatingView,tvSPCuisine,tvSPCuisineData,
	tvFaclities,tvReadMoreThird,tvReadMoreFourth,tvPackages,
	tvLocation3,tvSharedRoom3,tvCapacity3,tvCapacityCount3,tvType3,tvTypeData3,
	tvLocation2,tvSharedRoom2,tvCapacity2,tvCapacityCount2,tvType2,tvTypeData2,
	tvDescription;
	ImageView imViewPicture,imViewMap;
	Context mContext;
	LinearLayout llMail,llHome,llSchedule,llMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.vendor_details);
		//		tvReadMore=(TextView) findViewById(R.id.tvReadMore);
		mContext=VendorDetailsActivity.this;
		tvTitle=(TextView) findViewById(R.id.tvTitle);
		tvVenue=(TextView) findViewById(R.id.tvVenue);
		tvAddress=(TextView) findViewById(R.id.tvAddress);
		tvMobileNumber=(TextView) findViewById(R.id.tvMobileNumber);
		llMail=(LinearLayout) findViewById(R.id.llMail);
		llHome=(LinearLayout) findViewById(R.id.llHome);	
		llSchedule=(LinearLayout) findViewById(R.id.llSchedule);
		llMenu=(LinearLayout) findViewById(R.id.llMenu);
		llMail.setOnClickListener(this);
		llHome.setOnClickListener(this);
		llSchedule.setOnClickListener(this);
		llMenu.setOnClickListener(this);
		tvLocation=(TextView) findViewById(R.id.tvLocation);
		tvSharedRoom=(TextView) findViewById(R.id.tvSharedRoom);
		tvCapacity=(TextView) findViewById(R.id.tvCapacity);
		tvCapacityCount=(TextView) findViewById(R.id.tvCapacityCount);
		tvRate=(TextView) findViewById(R.id.tvRate);
		tvType=(TextView) findViewById(R.id.tvType);
		tvTypeData=(TextView) findViewById(R.id.tvTypeData);
		tvReadMoreSecond=(TextView) findViewById(R.id.tvReadMoreSecond);
		tvVideoLink=(TextView) findViewById(R.id.tvVideoLink);
		tvRotatingView=(TextView) findViewById(R.id.tvRotatingView);
		btnSchedule=(Button) findViewById(R.id.btnSchedule);
		tvSPCuisine=(TextView) findViewById(R.id.tvSPCuisine);
		tvSPCuisineData=(TextView) findViewById(R.id.tvSPCuisineData);
		tvFaclities=(TextView) findViewById(R.id.tvFaclities);
		tvReadMoreThird=(TextView)findViewById(R.id.tvReadMoreThird);		
		tvReadMoreFourth=(TextView) findViewById(R.id.tvReadMoreFourth);
		tvPackages=(TextView) findViewById(R.id.tvPackages);

		tvLocation3=(TextView) findViewById(R.id.tvLocation3);
		tvSharedRoom3=(TextView) findViewById(R.id.tvSharedRoom3);
		tvCapacity3=(TextView) findViewById(R.id.tvCapacity3);
		tvCapacityCount3=(TextView) findViewById(R.id.tvCapacityCount3);
		tvType3=(TextView) findViewById(R.id.tvType3);
		tvTypeData3=(TextView) findViewById(R.id.tvTypeData3);
		tvLocation2=(TextView) findViewById(R.id.tvLocation2);
		tvSharedRoom2=(TextView) findViewById(R.id.tvSharedRoom2);
		tvCapacity2=(TextView) findViewById(R.id.tvCapacity2);
		tvCapacityCount2=(TextView) findViewById(R.id.tvCapacityCount2);
		tvType2=(TextView) findViewById(R.id.tvType2);
		tvTypeData2=(TextView) findViewById(R.id.tvTypeData2);
		tvDescription=(TextView)findViewById(R.id.tvDescription);
		btnBack=(Button) findViewById(R.id.btnBack);
		/*ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		LayoutInflater mInflater = LayoutInflater.from(this);

		View mCustomView = mInflater.inflate(R.layout.customactionbarview, null);
		actionBar.setCustomView(mCustomView);
		btnBack=(Button) mCustomView.findViewById(R.id.btnBack);
		actionBar.setDisplayShowCustomEnabled(true);
		 */
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvReadMore,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvVenue,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvAddress,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvMobileNumber,"fonts/GothamRnd-Light.otf");
		//		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvDescription,"fonts/GothamRnd-Light.otf");
		//		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvDecriptionData,"fonts/GothamRnd-Light.otf");
		//		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvListing,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvLocation,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvSharedRoom,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvCapacity,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvCapacityCount,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvType,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvTypeData,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvReadMoreSecond,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvRate,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvVideoLink,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvRotatingView,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvSPCuisine,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvSPCuisineData,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvFaclities,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvReadMoreThird,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvReadMoreFourth,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvPackages,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvLocation3,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvSharedRoom3,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvCapacity3,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvCapacityCount3,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvType3,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvTypeData3,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvLocation2,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvSharedRoom2,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvCapacity2,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvCapacityCount2,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvType2,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvTypeData2,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(VendorDetailsActivity.this,tvGeneral,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfButton(VendorDetailsActivity.this,btnSchedule,"fonts/GothamRnd-Light.otf");

		tvVideoLink.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				VideoViewerDialog dialog=new VideoViewerDialog();
				dialog.newInstance(VendorDetailsActivity.this, null);
				dialog.show(getSupportFragmentManager(), "test");
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		tvReadMoreSecond.setOnClickListener(this);
		tvReadMoreThird.setOnClickListener(this);
		tvReadMoreFourth.setOnClickListener(this);

		imViewPicture=(ImageView) findViewById(R.id.imViewPicture);
		imViewMap=(ImageView) findViewById(R.id.imViewMap);
		imViewMap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent=new Intent(VendorDetailsActivity.this,VendorDetailsPageMapPopup.class);
				startActivity(myIntent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);				
			}
		});
		imViewPicture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent myIntent=new Intent(VendorDetailsActivity.this,VendorDetailsPageMapPopup.class);
				startActivity(myIntent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);*/				
			}
		});

		btnSchedule.setOnClickListener(this);
		idInitialization();
	}

	private void idInitialization() {

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvReadMoreSecond:
			GeneralDialog dialogGeneral=new GeneralDialog();
			dialogGeneral.newInstance(mContext, null);
			dialogGeneral.show(getSupportFragmentManager(), "test");
			break;

		case R.id.tvReadMoreThird:
			FacilitiesDialog dialogFacilities=new FacilitiesDialog();
			dialogFacilities.newInstance(mContext, null);
			dialogFacilities.show(getSupportFragmentManager(), "test");
			break;

		case R.id.tvReadMoreFourth://In case of General
			PackagesDialog dialogPackages=new PackagesDialog();
			dialogPackages.newInstance(mContext, null);
			dialogPackages.show(getSupportFragmentManager(), "test");
			break;
		case R.id.llSchedule:
		case R.id.btnSchedule:
			Intent myIntent=new Intent(VendorDetailsActivity.this,CalendarActivity.class);
			startActivity(myIntent);	
			overridePendingTransition(R.anim.right_in, R.anim.left_out);
			break;
		default:
			break;
		}
	}
}
