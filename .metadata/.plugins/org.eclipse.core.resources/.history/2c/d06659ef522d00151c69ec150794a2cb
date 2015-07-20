package com.eventmanagementapp.calendar;

import java.util.ArrayList;
import java.util.Calendar;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.eventmanagementapp.MessageTabActivity;
import com.eventmanagementapp.R;
import com.eventmanagementapp.Activities.MessageListActivity;
import com.eventmanagementapp.dialogs.FilterDialog;
import com.eventmanagementapp.interfaces.INotify;

/**
 * Shows off the most basic usage
 */
public class CalendarActivityMultipleSelection extends FragmentActivity implements OnClickListener
{

	MFCalendarView mf;
	Button btnBack,btnSelecteDate,btnFilter,btnCalendar,btnMail,btnLeads,
	btnChange,btnClearAll;//,btnCalendar,btnMessage,btnBid,btnMenu;
	private Calendar calendar;
	private int year, month, day;
	DatePickerDialog dpDialog;
	Context mContext;
	TextView tvFilterCriteria,tvFilterFirst;//,tvFilterSecond;
	LinearLayout llCalendar,llMail,llLeads,llMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calendar_activity);
		mContext=CalendarActivityMultipleSelection.this;
		mf = (MFCalendarView) findViewById(R.id.mFCalendarView);

		btnBack=(Button) findViewById(R.id.btnBack);
		btnSelecteDate=(Button) findViewById(R.id.btnSelecteDate);
		btnFilter=(Button) findViewById(R.id.btnFilter);
		btnChange=(Button) findViewById(R.id.btnChange);
		btnClearAll=(Button) findViewById(R.id.btnClearAll);
		tvFilterFirst=(TextView) findViewById(R.id.tvFilterFirst);
		//		tvFilterSecond=(TextView) findViewById(R.id.tvFilterSecond);
		tvFilterCriteria=(TextView) findViewById(R.id.tvFilterCriteria);
		tvFilterFirst.setVisibility(View.GONE);
		//		tvFilterSecond.setVisibility(View.GONE);
		btnChange.setVisibility(View.GONE);
		btnClearAll.setVisibility(View.GONE);
		tvFilterCriteria.setVisibility(View.GONE);
		/*btnCalendar=(Button) findViewById(R.id.btnCalendar);
		btnMessage=(Button) findViewById(R.id.btnMessage);
		btnBid=(Button) findViewById(R.id.btnBid);
		btnMenu=(Button) findViewById(R.id.btnMenu);*/
		btnBack.setOnClickListener(this);
		/*btnCalendar.setOnClickListener(this);
		btnMessage.setOnClickListener(this);
		btnBid.setOnClickListener(this);
		btnMenu.setOnClickListener(this);*/
		//		btnCalendar.performClick();
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		btnLeads=(Button) findViewById(R.id.btnLeads);
		btnCalendar=(Button) findViewById(R.id.btnCalendar);
		btnMail=(Button)findViewById(R.id.btnMail);
		llCalendar=(LinearLayout) findViewById(R.id.llCalendar);
		llMail=(LinearLayout) findViewById(R.id.llMail);
		llLeads=(LinearLayout) findViewById(R.id.llLeads);
		llMenu=(LinearLayout) findViewById(R.id.llMenu);
		llCalendar.setOnClickListener(this);
		llMail.setOnClickListener(this);
		llLeads.setOnClickListener(this);
		llMenu.setOnClickListener(this);
		btnMail.setOnClickListener(this);
		btnLeads.setOnClickListener(this);
		/*btnMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent=new Intent(CalendarActivityMultipleSelection.this,MessageListActivity.class);
				startActivity(myIntent);	
				overridePendingTransition(R.anim.right_in, R.anim.left_out);	
			}
		});
		btnLeads.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent=new Intent(mContext,MessageTabActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);		
			}
		});
*/
		btnSelecteDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(999);
			}
		});

		btnChange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterDialog dialog = new FilterDialog();
				dialog.newInstance(CalendarActivityMultipleSelection.this, iNotify);
				dialog.show(getSupportFragmentManager(),"Test");
			}
		});

		btnClearAll.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btnChange.setVisibility(View.GONE);
				btnClearAll.setVisibility(View.GONE);
				tvFilterFirst.setVisibility(View.GONE);
				tvFilterCriteria.setVisibility(View.GONE);
			}
		});
		btnFilter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FilterDialog dialog = new FilterDialog();
				//				dialog.getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
				//				dialog.getActivity().setTheme(android.R.style.Theme_Translucent);
				dialog.newInstance(CalendarActivityMultipleSelection.this, iNotify);
				dialog.show(getSupportFragmentManager(),"Test");
			}
		});

		mf.setOnCalendarViewListener(new onMFCalendarViewListener() {
			@Override
			public void onDisplayedMonthChanged(int month, int year, String monthStr) {

				StringBuffer bf = new StringBuffer()
				.append(" month:")
				.append(month)
				.append(" year:")
				.append(year)
				.append(" monthStr: ")
				.append(monthStr);
			}

			@Override
			public void onDateChanged(String date) {
			}
		});

		/**
		 * you can set calendar date anytime
		 * */
		//mf.setDate("2014-02-19");


		/**
		 * calendar events samples 
		 * */
		ArrayList<String> eventDays = new ArrayList<String>();
		eventDays.add("2014-02-25");
		eventDays.add(Util.getTomorrow());
		eventDays.add(Util.getCurrentDate());


		mf.setEvents(eventDays);

		Log.e("","locale:" + Util.getLocale());
	}

	INotify iNotify=new INotify() {
		@Override
		public void yes() {
			tvFilterFirst.setVisibility(View.VISIBLE);
			//			tvFilterSecond.setVisibility(View.VISIBLE);
			tvFilterCriteria.setVisibility(View.VISIBLE);
			btnChange.setVisibility(View.VISIBLE);
			btnClearAll.setVisibility(View.VISIBLE);
		}

		@Override
		public void no() {
		}
	};

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
		public void onDateSet(DatePicker arg0, int year, int month, int day) {
			showDate(year, month+1, day);
		}
	};

	private void showDate(int year, int month, int day) {
		StringBuilder dateString=new StringBuilder().append(year).append("-")
				.append(month).append("-").append(day);
		mf.setDate(dateString.toString());
		//		Toast.makeText(getApplicationContext(), dateString.toString(),1000).show();
		//		etDate.setText(new StringBuilder().append(day).append("/")
		//				.append(month).append("/").append(year));
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.btnBack)
		{
			finish();	
			overridePendingTransition(R.anim.right_in, R.anim.right_out);
		}
		else if (v.getId()==R.id.llMail || v.getId()==R.id.btnMail)
		{
			Intent myIntent=new Intent(CalendarActivityMultipleSelection.this,MessageListActivity.class);
			startActivity(myIntent);	
			overridePendingTransition(R.anim.right_in, R.anim.left_out);	
		}
		else if (v.getId()==R.id.llLeads || v.getId()==R.id.btnLeads)
		{
			Intent myIntent=new Intent(CalendarActivityMultipleSelection.this,MessageTabActivity.class);
			startActivity(myIntent);	
			overridePendingTransition(R.anim.right_in, R.anim.left_out);	
		}
	}
}                                                   
