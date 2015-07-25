package com.eventmanagementapp.calendar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eventmanagementapp.R;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;
import com.eventmanagementapp.dialogs.DisplayEventDatesDialog;
import com.eventmanagementapp.dialogs.ErrorDialog;
import com.eventmanagementapp.interfaces.IAction;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Shows off the most basic usage
 */
public class CalendarActivityMultipleSelection extends FragmentActivity implements OnClickListener
{
	MFCalendarViewMultipleSelection mf;
	Button btnBack,btnSelecteDate,btnShowBookings,btnFilter,btnCalendar,btnMail,btnLeads,btnMenu;//,btnCalendar,btnMessage,btnBid,btnMenu;
	private Calendar calendar;
	private int year, month, day;
	DatePickerDialog dpDialog;
	Context mContext;
	TextView tvFilterCriteria,tvFilterFirst;//,tvFilterSecond;
	LinearLayout llCalendar,llMail,llLeads,llMenu;
	public static ArrayList<String> listDates = new ArrayList<String>();
	String response,url;
	ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calendar_activity_multiple_selection);
		mContext=CalendarActivityMultipleSelection.this;
		mf = (MFCalendarViewMultipleSelection) findViewById(R.id.mFCalendarView);
		btnBack=(Button) findViewById(R.id.btnBack);
		btnSelecteDate=(Button) findViewById(R.id.btnSelecteDate);
		btnShowBookings=(Button) findViewById(R.id.btnShowBookings);
		btnFilter=(Button) findViewById(R.id.btnFilter);
		btnSelecteDate.setVisibility(View.GONE);
		btnFilter.setVisibility(View.GONE);
		tvFilterFirst=(TextView) findViewById(R.id.tvFilterFirst);
		tvFilterCriteria=(TextView) findViewById(R.id.tvFilterCriteria);
		tvFilterFirst.setVisibility(View.GONE);
		tvFilterCriteria.setVisibility(View.GONE);
		tvFilterCriteria.setVisibility(View.GONE);
		btnBack.setOnClickListener(this);
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
		btnMenu=(Button) findViewById(R.id.btnMenu);
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

		btnShowBookings.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(listDates!=null && !listDates.isEmpty())
				{
					DisplayEventDatesDialog dialog=new DisplayEventDatesDialog();
					dialog.newInstance(mContext, "Event Dates", listDates,iNotifyAction);
					dialog.show(getFragmentManager(), "test");
				}
				else if(listDates!=null && listDates.isEmpty())
				{
					ErrorDialog dialog=new ErrorDialog();
					dialog.newInstance(mContext,"","No event Dates Selected", null);
					dialog.show(getFragmentManager(), "test");
				}
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

	IAction iNotifyAction = new IAction() {
		@Override
		public void setAction(String action) {
			if(action.equals("senddata"))	
			{
				url=GlobalCommonValues.VENDOR_CALENDAR_HOME;
				new HttpAsyncTask().execute(url);
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(CalendarActivityMultipleSelection.listDates!=null)
			CalendarActivityMultipleSelection.listDates.clear();
		CalendarActivityMultipleSelection.listDates=null;
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(progress==null)
			{
				progress=new ProgressDialog(mContext);
				progress.show();		
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// Calling method for setting to be sent to the server
				SetData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(Void result) {
			if(progress!=null && progress.isShowing())
			{
				progress.dismiss();
				progress=null;
			}
			if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
			{
				// In case of fetching message listing
				try {   
					JSONObject jsonObj = new JSONObject(response);
					//new JSONObject(jsonObj.getString("json")).getString("data")
					//new JSONObject(jsonObj.getString("json")).getJSONArray("available_years")
					//{"15":25,"1":5,"16":7}
					String year=new JSONObject(response).getJSONObject("request_data").getString("year");
					String month=new JSONObject(response).getJSONObject("request_data").getString("month");
					JSONArray datesJsonArray=new JSONArray(new JSONObject(jsonObj.getString("json")).getString("data"));
					ArrayList<HashMap<String,String>> listDates = new ArrayList<HashMap<String,String>>();
					HashMap<String,String> hashMap=new HashMap<String,String>();
					@SuppressWarnings("unused")
					String count="",day="";
					for(int i=0;i<datesJsonArray.length();i++)
					{
						JSONObject jobj=new JSONObject(datesJsonArray.get(i).toString());
						day=jobj.getString("day");
						if(day.length()==1)
						{
							day="0"+day;
						}
						hashMap=new HashMap<String,String>();
						hashMap.put("year",year);
						hashMap.put("month",month);
						hashMap.put("count",jobj.getString("count"));
						hashMap.put("day",day);
						listDates.add(hashMap);
					}
					mf.refreshCalendarDates(listDates);
					//					calendaradapter.setEventCountsList(listDates);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
	}

	// Create GetData Method
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server
		//		yyyy-MM-dd
		String date=Util.getCurrentDate();
		String[] arrayDate=date.split("-");
		String data="";
		String year=arrayDate[0];
		String month=arrayDate[1];
		String filter_string=CalendarActivity._filterString;

		//		data= URLEncoder.encode("availability", "UTF-8") 
		//				+ "=" + URLEncoder.encode("0", "UTF-8"); 

		data= URLEncoder.encode("year", "UTF-8") 
				+ "=" + URLEncoder.encode(year, "UTF-8"); 

		data += "&" + URLEncoder.encode("month", "UTF-8") + "="
				+ URLEncoder.encode(month, "UTF-8"); 

		data += "&" + URLEncoder.encode("filter_string", "UTF-8") 
		+ "=" + URLEncoder.encode(filter_string,"UTF-8");

		BufferedReader reader=null;
		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			_url= new URL(GlobalCommonValues.VENDOR_CALENDAR_HOME);
			// Send POST data request

			URLConnection conn = _url.openConnection(); 
			conn.setDoOutput(true); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write( data ); 
			wr.flush(); 

			// Get the server response 
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while((line = reader.readLine()) != null)
			{
				// Append server response in string
				sb.append(line + "\n");
			}
			response = sb.toString();
		}
		catch(Exception ex)
		{
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(Exception ex) {}
		}
	}

	/*private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if(progress==null)
			{
				progress=new ProgressDialog(mContext);
				progress.show();		
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// Calling method for setting to be sent to the server
				SetData();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		// onPostExecute displays the results of the AsyncTask.
		@SuppressLint("DefaultLocale")
		@Override
		protected void onPostExecute(Void result) {
			if(progress!=null && progress.isShowing())
			{
				progress.dismiss();
				progress=null;
			}
			if(!TextUtils.isEmpty(response) && GlobalCommonMethods.isJSONValid(response))
			{
				if(url.equals(GlobalCommonValues.VENDOR_CALENDAR_MULTISELECTIONDATES))
				{
					try {
						JSONObject jsonObj = new JSONObject(response);
					} catch (Exception e) {
						e.getMessage();
					}
				}
			}
		}
	}

	// Create GetData Metod
	public  void  SetData()  throws  UnsupportedEncodingException
	{
		// Create data variable for sent values to server  
		String data="";
		// Send POST data request
		String dates=CalendarActivityMultipleSelection.listDates.toString();
		String _package="";
		String rate="";
		data= URLEncoder.encode("availability", "UTF-8") 
				+ "=" + URLEncoder.encode("1", "UTF-8"); 
		data= URLEncoder.encode("dates", "UTF-8") 
				+ "=" + URLEncoder.encode(dates, "UTF-8"); 

		data += "&" + URLEncoder.encode("package", "UTF-8") + "="
				+ URLEncoder.encode(_package, "UTF-8"); 

		data += "&" + URLEncoder.encode("rate", "UTF-8") + "="
				+ URLEncoder.encode(rate, "UTF-8"); 

		BufferedReader reader=null;
		// Send data 
		try
		{ 
			URL _url=null;
			// Defined URL  where to send data
			_url= new URL(GlobalCommonValues.VENDOR_CALENDAR_MULTISELECTIONDATES);
			URLConnection conn = _url.openConnection(); 
			conn.setDoOutput(true); 
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream()); 
			wr.write( data ); 
			wr.flush(); 

			// Get the server response 
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while((line = reader.readLine()) != null)
			{
				// Append server response in string
				sb.append(line + "\n");
			}
			response = sb.toString();
		}
		catch(Exception ex)
		{
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch(Exception ex) {}
		}
	}*/


	/*INotify iNotify=new INotify() {
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
	};*/

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
		/*else if (v.getId()==R.id.llMail || v.getId()==R.id.btnMail)
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
		}*/
	}
}                                                   
