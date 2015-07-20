package com.wedwise.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wedwiseapp.R;
import com.wedwiseapp.util.CustomFonts;

public class CalendarAdapter extends BaseAdapter {
	private Context mContext;

	private java.util.Calendar month;
	public Calendar pmonth; // calendar instance for previous month
	/**
	 * calendar instance for previous month for getting complete view
	 */
	public Calendar pmonthmaxset;
	private Calendar selectedDate;
	private int firstDay;
	private int maxWeeknumber;
	private int maxP;
	private int calMaxP;
	int lastWeekDay;
	int leftDays;
	private int mnthlength;
	private String itemvalue, curentDateString;
	DateFormat df;

	private ArrayList<String> items;
	public static List<String> dayString;
	private View previousView;

	Calendar a;
	TextView dayView,tvCount;
	Button btnDisableOverlay;
	RelativeLayout rlContainer;
	LinearLayout llSelectedDateBorder;

	public CalendarAdapter(Context c, Calendar monthCalendar) {

		mContext = c;
		initCalendarAdapter(monthCalendar, null);

	}

	public void initCalendarAdapter(Calendar monthCalendar,
			onMFCalendarViewListener calendarListener){
		CalendarAdapter.dayString = new ArrayList<String>();
		month = monthCalendar;

		selectedDate = (Calendar) monthCalendar.clone();

		month.set(Calendar.DAY_OF_MONTH, 1);
		this.items = new ArrayList<String>();

		adaptersetDate(selectedDate, calendarListener);
		refreshDays();
	}

	public void setItems(ArrayList<String> items) {

		if (items == null) 
			return;

		for (int i = 0; i != items.size(); i++) {
			if (items.get(i).length() == 1) {
				items.set(i, "0" + items.get(i));
			}
		}
		this.items = items;
	}



	public int getCount() {
		return dayString.size();
	}

	public Object getItem(int position) {
		return dayString.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	// create a new view for each item referenced by the Adapter
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;

		if (convertView == null) { // if it's not recycled, initialize some
			// attributes
			LayoutInflater vi = (LayoutInflater) mContext
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.cell_item_custom, null);
		}
		dayView = (TextView) v.findViewById(R.id.tvDate);
		tvCount=(TextView) v.findViewById(R.id.tvCount);
		btnDisableOverlay=(Button) v.findViewById(R.id.btnDisableOverlay);
		rlContainer=(RelativeLayout) v.findViewById(R.id.rlContainer);
		llSelectedDateBorder=(LinearLayout) v.findViewById(R.id.llSelectedDateBorder);
		btnDisableOverlay.setVisibility(View.GONE);
		llSelectedDateBorder.setVisibility(View.GONE);
		rlContainer.setBackgroundColor(Color.parseColor("#ffffff"));

//		CustomFonts.setFontOfTextView(mContext, dayView, "fonts/GothamRnd-Light.otf");
//		CustomFonts.setFontOfTextView(mContext, tvCount, "fonts/GothamRnd-Light.otf");

		tvCount.setText("19");
		//		tvCount.setTextColor(Color.parseColor("#ffffff"));
		//		tvCount.setBackground(mContext.getResources().getDrawable(R.drawable.notification));

		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("-");
		// taking last part of date. ie; 2 from 2012-12-02
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		// checking whether the day is in current month or not.
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			// setting offdays to white color.
			//			dayView.setTextColor(Color.WHITE);
			//			dayView.setClickable(false);
			//			dayView.setFocusable(false);
			//			v.setClickable(false);
			//			v.setFocusable(false);
			//			rlContainer.setBackgroundColor(Color.parseColor("#ffffff"));//setBackground(mContext.getResources().getDrawable(R.drawable.calendar_cell_white));
			btnDisableOverlay.setVisibility(View.VISIBLE);
			llSelectedDateBorder.setVisibility(View.GONE);
			/*try {
				v.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_item_background_disable));
			} catch (Exception e) {
				e.getMessage();
			}*/
		} else if ((Integer.parseInt(gridvalue) < 15) && (position > 28)) {
			//			dayView.setTextColor(Color.WHITE);
			//			dayView.setClickable(false);
			//			dayView.setFocusable(false);
			//			v.setClickable(false);
			//			v.setFocusable(false);
			//			rlContainer.setBackgroundColor(Color.parseColor("#ffffff"));//setBackground(mContext.getResources().getDrawable(R.drawable.calendar_cell_white));
			btnDisableOverlay.setVisibility(View.VISIBLE);
			llSelectedDateBorder.setVisibility(View.GONE);
			//			v.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_item_background_disable));//setBackgroundResource(R.drawable.list_item_background_disable);
		} else{
			//			rlContainer.setBackgroundColor(Color.parseColor("#ECECEC"));
			//			dayView.setClickable(false);
			//			dayView.setFocusable(false);
			//			v.setClickable(false);
			//			v.setFocusable(false);             //#EEECED
			//			rlContainer.setBackgroundColor(Color.parseColor("#ECECEC"));//setBackground(mContext.getResources().getDrawable(R.drawable.calendar_cell));
			// setting curent month's days in blue color.
			//			dayView.setTextColor(v.getResources().getColor(R.color.blue));

			//			v.setBackgroundResource(R.drawable.list_item_background);
			//			v.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_item_background));		
		}

		/*if (dayString.get(position).equals(curentDateString)) {
			setSelected(v);
			previousView = v;
		} else {
			rlContainer.setBackgroundColor(Color.parseColor("#EEECED"));
			//			v.setBackgroundResource(R.drawable.list_item_background);
		}*/
		dayView.setText(gridvalue);

		// create date string for comparison
		String date = dayString.get(position);

		if (date.length() == 1) {
			date = "0" + date;
		}
		String monthStr = "" + (month.get(Calendar.MONTH) + 1);
		if (monthStr.length() == 1) {
			monthStr = "0" + monthStr;
		}

		// show icon if date is not empty and it exists in the items array
		//		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		//		if (date.length() > 0 && items != null && items.contains(date)) {
		//			iw.setVisibility(View.VISIBLE);
		//		} else {
		//			iw.setVisibility(View.INVISIBLE);
		//		}
		return v;
	}

	public View setSelected(View view) {
		RelativeLayout rlTemp;
		//		LinearLayout llTemp;
		//		llTemp=(LinearLayout) view.findViewById(R.id.rlContainer).findViewById(R.id.llSelectedDateBorder);
		rlTemp=(RelativeLayout) view.findViewById(R.id.rlContainer);
		/*if (previousView != null) {
			previousView.findViewById(R.id.rlContainer).findViewById(R.id.llSelectedDateBorder).setVisibility(View.GONE);
			previousView.findViewById(R.id.rlContainer).setBackgroundColor(Color.parseColor("#EEECED"));
			//Make OuterBoundary Visibility Gone


			//			view.setBackgroundColor(Color.parseColor("#cc00cc"));
			//setBackground(mContext.getResources().getDrawable(R.drawable.calendar_cell));
			//			previousView.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.list_item_background));
		}
		previousView = rlTemp;
		rlTemp.setBackgroundColor(Color.parseColor("#ffffff"));
		//setBackground(mContext.getResources().getDrawable(R.drawable.calendar_cel_selectl));//setBackgroundColor(Color.parseColor("#cc00cc"));
		//Make OuterBoundary Visible
		llTemp.setVisibility(View.VISIBLE);*/

		//MultiSelection
		rlTemp.setBackgroundColor(Color.parseColor("#F05543"));
		TextView tv=(TextView)view.findViewById(R.id.rlContainer).findViewById(R.id.tvDate);
//		tv.setTextColor(Color.parseColor("#ffffff"));

		//		llTemp.setVisibility(View.VISIBLE);

		//		view.setBackgroundColor(Color.parseColor("#EEECED"));
		//setBackground(mContext.getResources().getDrawable(R.drawable.calendar_cel_selectl));
		//		view.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.calendar_cel_selectl));
		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		dayString.clear();

		pmonth = (Calendar) month.clone();
		// month start day. ie; sun, mon, etc
		firstDay = month.get(Calendar.DAY_OF_WEEK);
		// finding number of weeks in current month.
		maxWeeknumber = month.getActualMaximum(Calendar.WEEK_OF_MONTH);
		// allocating maximum row number for the gridview.
		mnthlength = maxWeeknumber * 7;
		maxP = getMaxP(); // previous month maximum day 31,30....
		calMaxP = maxP - (firstDay - 1);// calendar offday starting 24,25 ...
		/**
		 * Calendar instance for getting a complete gridview including the three
		 * month's (previous,current,next) dates.
		 */
		pmonthmaxset = (Calendar) pmonth.clone();
		/**
		 * setting the start date as previous month's required date.
		 */
		pmonthmaxset.set(Calendar.DAY_OF_MONTH, calMaxP + 1);

		/**
		 * filling calendar gridview.
		 */
		for (int n = 0; n < mnthlength; n++) {

			itemvalue = df.format(pmonthmaxset.getTime());
			pmonthmaxset.add(Calendar.DATE, 1);
			dayString.add(itemvalue);

		}
	}

	private int getMaxP() {
		int maxP;
		if (month.get(Calendar.MONTH) == month
				.getActualMinimum(Calendar.MONTH)) {
			pmonth.set((month.get(Calendar.YEAR) - 1),
					month.getActualMaximum(Calendar.MONTH), 1);
		} else {
			pmonth.set(Calendar.MONTH,
					month.get(Calendar.MONTH) - 1);
		}
		maxP = pmonth.getActualMaximum(Calendar.DAY_OF_MONTH);

		return maxP;
	}

	public void adaptersetDate(Calendar monthCalendar, onMFCalendarViewListener c){

		df = new SimpleDateFormat("yyyy-MM-dd", Util.getLocale());
		selectedDate = monthCalendar;
		curentDateString = df.format(selectedDate.getTime());

		//Log.d("","CalendarAdapter selectedDate:" + curentDateString);

		/*	if (c != null) 
			c.onDateChanged(curentDateString);
		 */	
	}

	public String getSelectedDate(){
		return curentDateString;
	}


}