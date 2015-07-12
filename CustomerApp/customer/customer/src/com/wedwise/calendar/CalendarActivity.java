package com.wedwise.calendar;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wedwiseapp.R;

/**
 * Shows off the most basic usage
 */
public class CalendarActivity extends FragmentActivity implements OnClickListener
{

	MFCalendarView mf;
	Button btnBack,btnSchedule;
	TextView tvTitle,tvScheduledDate;
	TimePicker tpScheduleVisit;
	Resources system;
	int hour_numberpicker_id,minute_numberpicker_id,ampm_numberpicker_id;
	NumberPicker hour_numberpicker,minute_numberpicker,ampm_numberpicker;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calendar_activity);
		mf = (MFCalendarView) findViewById(R.id.mFCalendarView);
		tvTitle=(TextView) findViewById(R.id.tvTitle);
		btnBack=(Button) findViewById(R.id.btnBack);
		btnSchedule=(Button) findViewById(R.id.btnSchedule);
		tvScheduledDate=(TextView) findViewById(R.id.tvScheduledDate);
		tpScheduleVisit=(TimePicker) findViewById(R.id.tpScheduleVisit);
		tvScheduledDate.setText("Visit scheduled at 2/05/2104 09:40 am");
		btnBack.setOnClickListener(this);
		btnSchedule.setOnClickListener(this);

		//		CustomFonts.setFontOfTextView(CalendarActivity.this,tvTitle,"fonts/GothamRnd-Book_0.otf");
		//		CustomFonts.setFontOfButton(CalendarActivity.this,btnSchedule,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfTextView(CalendarActivity.this,tvScheduledDate,"fonts/GothamRnd-Light.otf");
		//		
		try {
			set_timepicker_text_colour();
		} catch (Exception e) {
			e.getMessage();
		}
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
				//				
				//				Toast.makeText(CalendarActivity.this,  bf.toString(),
				//						Toast.LENGTH_SHORT).show();
				//				
			}

			@Override
			public void onDateChanged(String date) {

				//				Toast.makeText(CalendarActivity.this, "onDateChanged:" + date, 
				//						Toast.LENGTH_SHORT).show();
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

	private void set_timepicker_text_colour(){

		try {
			system = Resources.getSystem();
			hour_numberpicker_id = system.getIdentifier("hour", "id", "android");
			minute_numberpicker_id = system.getIdentifier("minute", "id", "android");
			ampm_numberpicker_id = system.getIdentifier("amPm", "id", "android");
		} catch (Exception e) {
			e.getMessage();
		}

		try {
			hour_numberpicker = (NumberPicker) tpScheduleVisit.findViewById(hour_numberpicker_id);
			minute_numberpicker = (NumberPicker) tpScheduleVisit.findViewById(minute_numberpicker_id);
			ampm_numberpicker = (NumberPicker) tpScheduleVisit.findViewById(ampm_numberpicker_id);
		} catch (Exception e) {
			e.getMessage();
		}

		try {
			set_numberpicker_text_colour(hour_numberpicker);
			set_numberpicker_text_colour(minute_numberpicker);
			set_numberpicker_text_colour(ampm_numberpicker);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private void set_numberpicker_text_colour(NumberPicker number_picker){
		try {
			final int count = number_picker.getChildCount();
			final int color = getResources().getColor(R.color.OrangeColorTheme);
			for(int i = 0; i < count; i++){
				View child = number_picker.getChildAt(i);
				try{
					Field wheelpaint_field = number_picker.getClass().getDeclaredField("mSelectorWheelPaint");
					wheelpaint_field.setAccessible(true);

					((Paint)wheelpaint_field.get(number_picker)).setColor(color);
					((EditText)child).setTextColor(color);

					Field field2=number_picker.getClass().getDeclaredField("mSelectionDivider");

					if (field2.getName().equals("mSelectionDivider")) {
						field2.setAccessible(true);
						try {
							field2.set(number_picker, getResources().getDrawable(R.drawable.divider_orange));

						}catch (Exception e) {
							Toast.makeText(getApplicationContext(), e.getMessage(),1000).show();
						}
					}
					number_picker.invalidate();
				}
				catch(NoSuchFieldException e){
					Log.w("setNumberPickerTextColor", e);
				}
				catch(IllegalAccessException e){
					Log.w("setNumberPickerTextColor", e);
				}
				catch(IllegalArgumentException e){
					Log.w("setNumberPickerTextColor", e);
				}
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*private void setDividerColor(TimePicker picker) {
        Field[] numberPickerFields = DatePicker.class.getDeclaredFields();
        for (Field field : numberPickerFields) {
            if (field.getName().equals("mSelectionDivider")) {
                field.setAccessible(true);
                try {
                    field.set(picker, getResources().getDrawable(R.drawable.divider));
                } catch (IllegalArgumentException e) {
                    Log.v("CalendarActivity", "Illegal Argument Exception");
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    Log.v("CalendarActivity", "Resources NotFound");
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    Log.v("CalendarActivity", "Illegal Access Exception");
                    e.printStackTrace();
                }
                break;
            }
        }
    }*/

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
		else if(v.getId()==R.id.btnSchedule)
		{
			tvScheduledDate.setText("Visit Scheduled at 2/5/2015 9:40 AM");
		}
		/*else if(v.getId()==R.id.btnCalendar)
		{
			btnMessage.setSelected(false);
			btnBid.setSelected(false);
			btnMenu.setSelected(false);
			btnCalendar.setSelected(true);
		}
		else if(v.getId()==R.id.btnMessage)
		{
			btnCalendar.setSelected(false);
			btnBid.setSelected(false);
			btnMenu.setSelected(false);
			btnMessage.setSelected(true);
		}
		else if(v.getId()==R.id.btnBid)
		{
			btnCalendar.setSelected(false);
			btnMessage.setSelected(false);
			btnMenu.setSelected(false);
			btnBid.setSelected(true);
		}
		else if(v.getId()==R.id.btnMenu)
		{
			btnCalendar.setSelected(false);
			btnMessage.setSelected(false);
			btnBid.setSelected(false);
			btnMenu.setSelected(true);
		}*/
	}
}                                                   
/*public class CalendarActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTheme(android.R.style.Theme_Holo_Light_NoActionBar_TranslucentDecor);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.calendar_activity);

	}*/
//}