package com.eventmanagementapp.tab;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.eventmanagementapp.R;

public class BidBookCreateActivity extends FragmentActivity{

	EditText etDate;
	TextView tvTitle;
	Button btnBack,btnBitIt;
//	Spinner spTimeSlot,spPerPlate,spMinPerson;
	ArrayList<String> listAdapter;
	ArrayList<String> listTimings;
//	SpinnerAdapter  adapterSpinner;
	Context mContext;
//	private DatePicker datePicker;
	private Calendar calendar;
	//	private TextView dateView;
	private int year, month, day;
	DatePickerDialog dpDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bid_book_create);
		mContext=BidBookCreateActivity.this;
		tvTitle=(TextView) findViewById(R.id.tvTitle);
		btnBitIt=(Button) findViewById(R.id.btnBitIt);
		etDate=(EditText) findViewById(R.id.etDate);
		btnBack=(Button) findViewById(R.id.btnBack);

//		CustomFonts.setFontOfTextView(mContext,tvTitle,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfButton(mContext,btnBitIt,"fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfEditText(mContext,etDate,"fonts/GothamRnd-Light.otf");

		listAdapter=new ArrayList<String>();
		listAdapter.add("200");
		listAdapter.add("300");
		listAdapter.add("600");
		listTimings=new ArrayList<String>();
		listTimings.add("Morning");
		listTimings.add("Noon");
		listTimings.add("Evening");
		listTimings.add("Night");
		/*adapterSpinner=new SpinnerAdapter(BidBookCreateActivity.this, listAdapter);
		spTimeSlot=(Spinner) findViewById(R.id.spTimeSlot);
		spPerPlate=(Spinner) findViewById(R.id.spPerPlate);
		spMinPerson=(Spinner) findViewById(R.id.spMinPerson);

		spPerPlate.setAdapter(adapterSpinner);
		spMinPerson.setAdapter(adapterSpinner);

		adapterSpinner=new SpinnerAdapter(BidBookCreateActivity.this, listTimings);
		spTimeSlot.setAdapter(adapterSpinner);*/

		if(getIntent().getExtras().getString("type").equals("bid"))
		{
			tvTitle.setText("Create Bid");
			btnBitIt.setText("BID IT");
		}
		else if(getIntent().getExtras().getString("type").equals("book"))
		{
			tvTitle.setText("Create Book");
			btnBitIt.setText("BOOK IT");
		}

		//		etDate.setEnabled(false);
		etDate.setFocusable(false);
		etDate.setFocusableInTouchMode(false);
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		//		showDate(year, month+1, day);
		etDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(999);
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		if (id == 999) {
			dpDialog=new DatePickerDialog(this, myDateListener, year, month, day);
			dpDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//			dpDialog.setTitle("");
			return dpDialog;
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
			// TODO Auto-generated method stub
			// arg1 = year
			// arg2 = month
			// arg3 = day
			showDate(arg1, arg2+1, arg3);
		}
	};

	private void showDate(int year, int month, int day) {
		etDate.setText(new StringBuilder().append(day).append("/")
				.append(month).append("/").append(year));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

}
