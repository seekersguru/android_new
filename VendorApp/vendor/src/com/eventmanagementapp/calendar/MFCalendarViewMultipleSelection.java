package com.eventmanagementapp.calendar;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import com.eventmanagementapp.R;
import com.eventmanagementapp.common.GlobalCommonMethods;
import com.eventmanagementapp.common.GlobalCommonValues;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MFCalendarViewMultipleSelection extends LinearLayout{

	private static final String TODAY = "today";

	private Calendar month;
	private CalendarAdapterMultipleSelection calendaradapter;
	private Handler handler;
	private ExpandableHeightGridView gridview;
	private String currentSelectedDate;
	private String initialDate;
	private View view;
	private Locale locale;
	onMFCalendarViewListener calendarListener;
	//	String response="",url;
	//	ProgressDialog progress;
	//	Context mContext;


	public MFCalendarViewMultipleSelection(Context context) {
		super(context);
		init(context);
		//		mContext=context;
	}


	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public MFCalendarViewMultipleSelection(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		//		mContext=context;
	}

	public MFCalendarViewMultipleSelection(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		//		mContext=context;
	}

	void init(Context context){
		LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = li.inflate(R.layout.mf_calendarview, null, false);
		month = (Calendar) Calendar.getInstance();
		month.setTimeInMillis(Util.dateToLong(getInitialDate()));
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Util.getLocale());
		currentSelectedDate = df.format(month.getTime());
		calendaradapter = new CalendarAdapterMultipleSelection(context, month);
		gridview = (ExpandableHeightGridView) view.findViewById(R.id.gridview);
		gridview.setAdapter(calendaradapter);

		handler = new Handler();
		handler.post(calendarUpdater);

		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
		RelativeLayout previous = (RelativeLayout) view.findViewById(R.id.previous);
		previous.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) view.findViewById(R.id.next);
		next.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();
			}
		});

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				//				((CalendarAdapterMultipleSelection) parent.getAdapter()).setSelected(v,position);
				currentSelectedDate = CalendarAdapterMultipleSelection.dayString.get(position);

				String[] separatedTime = currentSelectedDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 15) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapterMultipleSelection) parent.getAdapter()).setSelected(v,position);

				month.setTimeInMillis(Util.dateToLong(currentSelectedDate));
				calendaradapter.initCalendarAdapter(month, calendarListener);

				if (calendarListener != null) 
					calendarListener.onDateChanged(currentSelectedDate);
			}
		});

		addView(view);
	}

	protected void setNextMonth() {
		if (month.get(Calendar.MONTH) == 
				month.getActualMaximum(Calendar.MONTH)) {

			month.set((month.get(Calendar.YEAR) + 1),
					month.getActualMinimum(Calendar.MONTH), 1);

		} else {
			month.set(Calendar.MONTH,
					month.get(Calendar.MONTH) + 1);
		}
	}

	protected void setPreviousMonth() {
		if (month.get(Calendar.MONTH) == month
				.getActualMinimum(Calendar.MONTH)) {
			month.set((month.get(Calendar.YEAR) - 1),
					month.getActualMaximum(Calendar.MONTH), 1);
		} else {
			month.set(Calendar.MONTH,
					month.get(Calendar.MONTH) - 1);
		}

	}

	protected void showToast(String string) {
		//		Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
	}

	public int getSelectedMonth(){
		return month.get(Calendar.MONTH) + 1;
	}

	public int getSelectedYear(){
		return month.get(Calendar.YEAR);
	}

	public void refreshCalendar() {

		TextView title = (TextView) view.findViewById(R.id.title);

		calendaradapter.refreshDays();
		//calendaradapter.notifyDataSetChanged();
		handler.post(calendarUpdater); 

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		if (calendarListener != null) {
			calendarListener.onDisplayedMonthChanged(
					month.get(Calendar.MONTH) + 1, 
					month.get(Calendar.YEAR), 
					(String) DateFormat.format("MMMM", month));
		}
	}

	public Runnable calendarUpdater = new Runnable() {
		@Override
		public void run() {
			gridview.setExpanded(true);
			Log.d("", "month:"+ (month.get(Calendar.MONTH)+1) + 
					" year:" + month.get(Calendar.YEAR));
			calendaradapter.notifyDataSetChanged();
			//			url=GlobalCommonValues.VENDOR_CALENDAR_HOME;
			//			new HttpAsyncTask().execute(url);
		}
	};

	public void setOnCalendarViewListener(onMFCalendarViewListener c){
		calendarListener = c;
	}

	public String getInitialDate(){
		if (initialDate == null) {
			return Util.getCurrentDate();
		}
		return initialDate;
	}

	/**
	 * @date "yyyy-MM-dd"
	 * */
	public void setDate(String date){
		if (date.equals(MFCalendarViewMultipleSelection.TODAY)) {
			initialDate = Util.getCurrentDate();
		}
		else{
			initialDate = date;
		}

		initialDate = date;
		currentSelectedDate = date;
		month.setTimeInMillis(Util.dateToLong(date));
		calendaradapter.initCalendarAdapter(month, calendarListener);

	}

	public String getSelectedDate(){
		return currentSelectedDate;
	}

	/**
	 * @param date like this format: "2014-01-15"
	 * */
	public void setEvents(ArrayList<String> dates){
		calendaradapter.setItems(dates);
		handler.post(calendarUpdater);
	}

	public void refreshCalendarDates(ArrayList<HashMap<String,String>> listDates)
	{
		calendaradapter.setSelectedDays(listDates);
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
					calendaradapter.setEventCountsList(listDates);


					HashMap<String,String> hashMap=new HashMap<String,String>();
					hashMap.put("","");

					if(mContext instanceof CalendarActivity)
					{
						ErrorDialog dialog=new ErrorDialog();
						dialog.newInstance(mContext, "Test","Test_",null);
						dialog.setCancelable(false);
						dialog.show(((CalendarActivity)mContext).getFragmentManager(), "test");
					}
					String _result = jsonObj.getString("result");
					if(_result.equalsIgnoreCase("error"))
					{
						String errorMessage=jsonObj.getString("message");
						ErrorDialog dialog=new ErrorDialog();
						dialog.newInstance(mContext, _result.toUpperCase(), errorMessage,null);
						dialog.setCancelable(false);
						dialog.show(getFragmentManager(), "test");
					}
					else if(_result.equalsIgnoreCase("success")){
						JSONArray jsonArray = jsonObj.getJSONArray("json");
						for(int i=0;i<jsonArray.length();i++)
						{
							String receiver_name=new JSONObject(jsonArray.getString(i)).getString("receiver_name");
							String message=new JSONObject(jsonArray.getString(i)).getString("message");
							String msg_time=new JSONObject(jsonArray.getString(i)).getString("msg_time");	
							String receiver_email=new JSONObject(jsonArray.getString(i)).getString("receiver_email");
							HashMap<String, String> hashMap=new HashMap<String,String>();
							hashMap.put("receiver_name",receiver_name);
							hashMap.put("message",message);
							hashMap.put("msg_time", msg_time);
							hashMap.put("receiver_email", receiver_email);
							adapterMessageList.listChat.add(hashMap);
							adapterMessageList.notifyDataSetChanged();
						}
					}
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

		data= URLEncoder.encode("availability", "UTF-8") 
				+ "=" + URLEncoder.encode("0", "UTF-8"); 

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
	}*/
}
