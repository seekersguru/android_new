package com.eventmanagementapp.calendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import com.eventmanagementapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarAdapterMultipleSelection extends BaseAdapter {
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
	DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	private ArrayList<String> items;
	public static List<String> dayString = new ArrayList<String>();
	private View previousView;
	Calendar a;
	TextView dayView,tvCount,tvPeekDate;
	Button btnDisableOverlay;
	ImageView imViewBGIndicator;
	RelativeLayout rlContainer;
	LinearLayout llSelectedDateBorder;
	ArrayList<HashMap<String,String>> listSelectedDates = new ArrayList<HashMap<String,String>>();

	public CalendarAdapterMultipleSelection(Context c, Calendar monthCalendar) {
		mContext = c;
		initCalendarAdapter(monthCalendar, null);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	public void initCalendarAdapter(Calendar monthCalendar,
			onMFCalendarViewListener calendarListener){
		CalendarAdapterMultipleSelection.dayString = new ArrayList<String>();
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

	public void setSelectedDays(ArrayList<HashMap<String,String>> listSelectedDates)
	{
		this.listSelectedDates = listSelectedDates;
		notifyDataSetInvalidated();
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
			v = vi.inflate(R.layout.cell_item_custom_multiple_selection, null);
		}
		dayView = (TextView) v.findViewById(R.id.tvDate);
		tvCount=(TextView) v.findViewById(R.id.tvCount);
		tvPeekDate=(TextView) v.findViewById(R.id.tvPeekDate);
		btnDisableOverlay=(Button) v.findViewById(R.id.btnDisableOverlay);
		rlContainer=(RelativeLayout) v.findViewById(R.id.rlContainer);
		imViewBGIndicator=(ImageView)v.findViewById(R.id.imViewBGIndicator);
		llSelectedDateBorder=(LinearLayout) v.findViewById(R.id.llSelectedDateBorder);
		btnDisableOverlay.setVisibility(View.GONE);
		llSelectedDateBorder.setVisibility(View.GONE);
		rlContainer.setBackgroundColor(Color.parseColor("#ffffff"));

		//		CustomFonts.setFontOfTextView(mContext, dayView, "fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(mContext, tvCount, "fonts/GothamRnd-Light.otf");
		dayView.setTextColor(Color.BLACK);
		//		tvCount.setText("19");
		tvCount.setVisibility(View.GONE);
		tvPeekDate.setVisibility(View.GONE);
		//		tvCount.setTextColor(Color.parseColor("#ffffff"));
		//		tvCount.setBackground(mContext.getResources().getDrawable(R.drawable.notification));

		// separates daystring into parts.
		String[] separatedTime = dayString.get(position).split("-");
		// taking last part of date. ie; 2 from 2012-12-02
		String gridvalue = separatedTime[2].replaceFirst("^0*", "");
		// checking whether the day is in current month or not.
		if ((Integer.parseInt(gridvalue) > 1) && (position < firstDay)) {
			btnDisableOverlay.setVisibility(View.VISIBLE);
			llSelectedDateBorder.setVisibility(View.GONE);
		} else if ((Integer.parseInt(gridvalue) < 15) && (position > 28)) {
			btnDisableOverlay.setVisibility(View.VISIBLE);
			llSelectedDateBorder.setVisibility(View.GONE);
		} else{
		}
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

		if(listSelectedDates!=null && !listSelectedDates.isEmpty())
		{
			String[] arrayDate=date.split("-");
			String year=arrayDate[0];
			String month=arrayDate[1];
			String day=arrayDate[2];
			tagloop:for(int i=0;i<listSelectedDates.size();i++)
			{
				if(year.equals(listSelectedDates.get(i).get("year")) && month.equals(listSelectedDates.get(i).get("month")))
				{
					if(day.equals(listSelectedDates.get(i).get("day")))
					{
						imViewBGIndicator.setVisibility(View.VISIBLE);
						imViewBGIndicator.setAlpha(.5f);
						rlContainer.setBackgroundColor(Color.parseColor("#ffffff"));
						String imagePath=listSelectedDates.get(i).get("image_path");
						ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext)
								.defaultDisplayImageOptions(options)
								.build();
						ImageLoader.getInstance().init(config);
						try {
							ImageLoader.getInstance().displayImage(imagePath, imViewBGIndicator, options, animateFirstListener);
						} catch (Exception e) {
							e.getMessage();
						}
						break tagloop;
					}
				}
			}
		}
		else{
			rlContainer.setBackgroundColor(Color.parseColor("#ffffff"));
			imViewBGIndicator.setVisibility(View.GONE);
		}

		/*if(listSelectedDates!=null && !listSelectedDates.isEmpty())
		{
			String[] arrayDate=date.split("-");
			String year=arrayDate[0];
			String month=arrayDate[1];
			String day=arrayDate[2];
			tagloop:for(int i=0;i<listSelectedDates.size();i++)
			{
				if(date.equals(listSelectedDates.get(i)))
				{
					rlContainer.setBackgroundColor(Color.parseColor("#009688"));
					break tagloop;
				}
			}
		}*/
		/*if(date.equals("2015-06-25") || date.equals("2015-06-28") || date.equals("2015-06-30") || date.equals("2015-07-01") || date.equals("2015-07-05"))
		{
			rlContainer.setBackgroundColor(Color.parseColor("#BDBDBD"));//#00796B
			tvCount.setVisibility(View.VISIBLE);
			tvPeekDate.setVisibility(View.VISIBLE);
		}*/

		// show icon if date is not empty and it exists in the items array
		//		ImageView iw = (ImageView) v.findViewById(R.id.date_icon);
		//		if (date.length() > 0 && items != null && items.contains(date)) {
		//			iw.setVisibility(View.VISIBLE);
		//		} else {
		//			iw.setVisibility(View.INVISIBLE);
		//		}
		return v;
	}

	View prev=null;

	public View setSelected(View view,int position) {
		RelativeLayout rlTemp;
		rlTemp=(RelativeLayout) view.findViewById(R.id.rlContainer);
		/*if(prev!=null)
		{
			prev.setBackgroundColor(Color.parseColor("#ffffff"));
			TextView tv=(TextView)prev.findViewById(R.id.rlContainer).findViewById(R.id.tvDate);
			tv.setTextColor(Color.parseColor("#000000"));
		}*/
		//To Display Selection
		rlTemp.setBackgroundColor(Color.parseColor("#F05543"));
		TextView tv=(TextView)view.findViewById(R.id.rlContainer).findViewById(R.id.tvDate);
		tv.setTextColor(Color.parseColor("#ffffff"));

		String strDate=dayString.get(position);//tv.getText().toString();
		if(CalendarActivityMultipleSelection.listDates.contains(strDate))
		{
			CalendarActivityMultipleSelection.listDates.remove(strDate);
			if(prev!=null)
			{
				prev.setBackgroundColor(Color.parseColor("#ffffff"));
				TextView tv_Date=(TextView)prev.findViewById(R.id.rlContainer).findViewById(R.id.tvDate);
				tv_Date.setTextColor(Color.parseColor("#000000"));
			}
		}
		else{
			CalendarActivityMultipleSelection.listDates.add(strDate);
		}

		prev=rlTemp;
		return view;
	}

	public void refreshDays() {
		// clear items
		items.clear();
		if(dayString!=null)
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

	public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
		public static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
		@Override
		public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}