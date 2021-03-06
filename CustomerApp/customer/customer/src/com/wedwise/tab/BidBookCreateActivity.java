package com.wedwise.tab;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wedwise.adapter.SpinnerAdapter;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.dialogs.ErrorDialog;
import com.wedwise.gson.Bid;
import com.wedwise.gson.Book;
import com.wedwiseapp.R;
import com.wedwiseapp.util.IntentHelper;
import com.wedwiseapp.util.PreferenceUtil;

public class BidBookCreateActivity extends FragmentActivity{

	EditText etDate;
	TextView tvTitle;
	Button btnBack,btnBitIt;
	Spinner spTimeSlot,spPerPlate,spMinPerson;
	ArrayList<String> listAdapter;
	ArrayList<String> listTimings;
	SpinnerAdapter  adapterSpinner;
	Context mContext;
	//	private DatePicker datePicker;
	private Calendar calendar;
	//	private TextView dateView;
	private int year, month, day;
	DatePickerDialog dpDialog;
	String deviceId;
	String response = "", url = "";
	String selectedTimeSlot;

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

		deviceId = Secure.getString(this.getContentResolver(),
				Secure.ANDROID_ID);

		final Object obj = (Object) IntentHelper.getObjectForKey("bidDetail");

		//		CustomFonts.setFontOfTextView(mContext,tvTitle,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfButton(mContext,btnBitIt,"fonts/GothamRnd-Light.otf");
		//		CustomFonts.setFontOfEditText(mContext,etDate,"fonts/GothamRnd-Light.otf");

		listAdapter=new ArrayList<String>();
		listAdapter.add("200");
		listAdapter.add("300");
		listAdapter.add("600");



		/*adapterSpinner=new SpinnerAdapter(BidBookCreateActivity.this, listAdapter);
		spTimeSlot=(Spinner) findViewById(R.id.spTimeSlot);
		spPerPlate=(Spinner) findViewById(R.id.spPerPlate);
		spMinPerson=(Spinner) findViewById(R.id.spMinPerson);

		spPerPlate.setAdapter(adapterSpinner);
		spMinPerson.setAdapter(adapterSpinner);

		 */

		if(getIntent().getExtras().getString("type").equals("bid"))
		{
			tvTitle.setText("Create Bid");
			btnBitIt.setText("BID IT");

			// Time Slot Data
			TextView timeSlotLabel = (TextView)findViewById(R.id.time_slot_label);
			timeSlotLabel.setText(((Bid)obj).getTimeSlot().getName());
			listTimings=new ArrayList<String>();
			listTimings.add(((Bid)obj).getTimeSlot().getValue().get(0).get(1));
			listTimings.add(((Bid)obj).getTimeSlot().getValue().get(1).get(1));

			adapterSpinner=new SpinnerAdapter(BidBookCreateActivity.this, listTimings);
			final Spinner spTimeSlot = (Spinner)findViewById(R.id.spTimeSlot);
			spTimeSlot.setAdapter(adapterSpinner);

			spTimeSlot.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
					selectedTimeSlot =listTimings.get(position).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});

			// package Detail:
			TextView packageLabel = (TextView)findViewById(R.id.package_label);
			packageLabel.setText(((Bid)obj).getPackage().getName());

			TextView packageValue = (TextView)findViewById(R.id.package_value);
			packageValue.setText(((Bid)obj).getPackage().getValue());

			// Quoted Price Detail:
			TextView quatedPriceLabel = (TextView)findViewById(R.id.quoted_price_label);
			quatedPriceLabel.setText(((Bid)obj).getQuoted().getName());

			TextView quotedPriceValue = (TextView)findViewById(R.id.quoted_price_value);
			quotedPriceValue.setText(((Bid)obj).getQuoted().getValue());

			// Bidding Detail
			TextView BidPriceLabel = (TextView)findViewById(R.id.bid_price_label);
			BidPriceLabel.setText(((Bid)obj).getBidOptions().getName());

			TextView perPlateLabel = (TextView)findViewById(R.id.tvPerPlate);
			perPlateLabel.setText(((Bid)obj).getBidOptions().getItem().getLabel());

			TextView minPersonLabel = (TextView)findViewById(R.id.tvMinPerson);
			minPersonLabel.setText(((Bid)obj).getBidOptions().getQuantity().getLabel());

			final EditText per_plate_value = (EditText)findViewById(R.id.per_plate_value);
			//per_plate_value.setText(""+((Bid)obj).getBidOptions().getItem().getMin());


			final EditText min_person_value = (EditText)findViewById(R.id.min_person_value);
			//min_person_value.setText(((Bid)obj).getBidOptions().getQuantity().getMin().getValue());

			btnBitIt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (Integer.parseInt(per_plate_value.getText().toString()
							.trim()) < ((Bid) obj).getBidOptions().getItem()
							.getMin()
							|| Integer.parseInt(per_plate_value.getText()
									.toString().trim()) > ((Bid) obj)
									.getBidOptions().getItem().getMax()) {
						per_plate_value.setError("Value must be between "+((Bid)obj).getBidOptions().getItem().getMin()+" to "+((Bid)obj).getBidOptions().getItem().getMax());
					}else if(Integer.parseInt(min_person_value.getText().toString().trim())<((Bid)obj).getBidOptions().getQuantity().getMin().getValue()){
						min_person_value.setError(((Bid)obj).getBidOptions().getQuantity().getMin().getMessage());
					}else if(Integer.parseInt(min_person_value.getText().toString().trim())>((Bid)obj).getBidOptions().getQuantity().getMax()){
						min_person_value.setError("Max people can be"+((Bid)obj).getBidOptions().getQuantity().getMax());
					}else{
						// call API here.
						Gson gson = new Gson();
						new HttpAsyncTask("Bidding", "Bid by customer", "bid",
								gson.toJson(((Bid) obj).getBidOptions()), etDate.getText().toString().trim(),
								selectedTimeSlot,
								per_plate_value.getText().toString().trim(), "12")
						.execute("");
					}
				}
			});
		}


		else if(getIntent().getExtras().getString("type").equals("book"))
		{
			tvTitle.setText("Create Book");
			btnBitIt.setText("BOOK IT");

			final Object objBook = (Object) IntentHelper.getObjectForKey("bookDetail");

			LinearLayout price_layout = (LinearLayout)findViewById(R.id.price_layout);
			price_layout.setVisibility(View.INVISIBLE);
			LinearLayout bottom_label_layout = (LinearLayout)findViewById(R.id.bottom_label_layout);
			bottom_label_layout.setVisibility(View.GONE);
			LinearLayout price_value_layout = (LinearLayout)findViewById(R.id.price_value_layout);
			price_value_layout.setVisibility(View.GONE);

			TextView timeSlotLabel = (TextView)findViewById(R.id.time_slot_label);
			timeSlotLabel.setText(((Book)objBook).getTimeSlot().getName());

			// package Detail:
			TextView packageLabel = (TextView)findViewById(R.id.package_label);
			packageLabel.setText(((Book)objBook).getPackage().getName());

			TextView packageValue = (TextView)findViewById(R.id.package_value);
			packageValue.setText(((Book)objBook).getPackage().getValue());

			// Time Slot Data
			timeSlotLabel.setText(((Book)objBook).getTimeSlot().getName());
			listTimings=new ArrayList<String>();
			listTimings.add(((Book)objBook).getTimeSlot().getValue().get(0).get(1));
			listTimings.add(((Book)objBook).getTimeSlot().getValue().get(1).get(1));

			adapterSpinner=new SpinnerAdapter(BidBookCreateActivity.this, listTimings);
			final Spinner spTimeSlot = (Spinner)findViewById(R.id.spTimeSlot);
			spTimeSlot.setAdapter(adapterSpinner);

			spTimeSlot.setOnItemSelectedListener(new OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
					selectedTimeSlot =listTimings.get(position).toString();
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub

				}
			});


			btnBitIt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Gson gson = new Gson();
					new HttpAsyncTask("Booking", "New Booking by customer", "book",
							gson.toJson(((Book) obj)), etDate.getText().toString().trim(),
							selectedTimeSlot,
							"", "")
					.execute("");

				}
			});
		}

		//		etDate.setEnabled(false);
		etDate.setFocusable(false);
		etDate.setFocusableInTouchMode(false);
		calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
		month = calendar.get(Calendar.MONTH);
		day = calendar.get(Calendar.DAY_OF_MONTH);
		etDate.setText(year+"-"+(month+1)+"-"+day);
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
		etDate.setText(new StringBuilder().append(year).append("-")
				.append(month).append("-").append(day));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {

		ProgressDialog progress;
		String pushData;
		String message;
		String msgType;
		String bidJSON;
		String eventDate;
		String timeSlot;
		String bidPrice;
		String bidQuantity;

		public HttpAsyncTask(String pushData,String message,String msgType,String bidJSON,String eventDate,String timeSlot,String bidPrice,String bidQuantity){
			this.pushData = pushData;
			this.message = message;
			this.msgType = msgType;
			this.bidJSON = bidJSON;
			this.eventDate = eventDate;
			this.timeSlot = timeSlot;
			this.bidPrice = bidPrice;
			this.bidQuantity = bidQuantity;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (progress == null) {
				progress = new ProgressDialog(mContext);
				progress.show();
			}
		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				// Calling method for setting to be sent to the server
				SetData(pushData,message,msgType,bidJSON,eventDate,timeSlot,bidPrice,bidQuantity);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// onPostExecute displays the results of the AsyncTask.
		@SuppressLint("DefaultLocale")
		@Override
		protected void onPostExecute(Void result) {
			// Toast.makeText(getBaseContext(), "Data Sent!"+response,
			// Toast.LENGTH_LONG).show();
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
				progress = null;
			}

			if (!TextUtils.isEmpty(response)
					&& GlobalCommonMethods.isJSONValid(response)) {
				try {
					JSONObject jobject = new JSONObject(response);
					String message = jobject.getString("result");
					ErrorDialog dialog=new ErrorDialog();
					dialog.newInstance(mContext, "STATUS", message, null);
					dialog.setCancelable(false);
					dialog.show(getFragmentManager(), "test");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// Create GetData Metod
	public void SetData(String pushData,String message,String msgType,String bidJSON,String eventDate,String timeSlot,String bidPrice,String bidQuantity) throws UnsupportedEncodingException {
		// Create data variable for sent values to server
		String data = "";
		String image_type = "";

		data = URLEncoder.encode("mode", "UTF-8") + "="
				+ URLEncoder.encode("android", "UTF-8");

		data += "&" + URLEncoder.encode("device_id", "UTF-8") + "="
				+ URLEncoder.encode(deviceId, "UTF-8");

		data += "&" + URLEncoder.encode("push_data", "UTF-8") + "="
				+ URLEncoder.encode(pushData, "UTF-8");

		data += "&" + URLEncoder.encode("identifier", "UTF-8") + "="
				+ URLEncoder.encode(PreferenceUtil.getInstance().getIdentifier(), "UTF-8");

		data += "&" + URLEncoder.encode("receiver_email", "UTF-8") + "="
				+ URLEncoder.encode(getIntent().getStringExtra("vendorEmail"), "UTF-8");

		data += "&" + URLEncoder.encode("message", "UTF-8") + "="
				+ URLEncoder.encode(message, "UTF-8");

		data += "&" + URLEncoder.encode("from_to", "UTF-8") + "="
				+ URLEncoder.encode("c2v", "UTF-8");

		data += "&" + URLEncoder.encode("msg_type", "UTF-8") + "="
				+ URLEncoder.encode(msgType, "UTF-8");

		if(msgType.equalsIgnoreCase("bid")){
			data += "&" + URLEncoder.encode("bid_json", "UTF-8") + "="
					+ URLEncoder.encode(bidJSON, "UTF-8");

			data += "&" + URLEncoder.encode("book_json", "UTF-8") + "="
					+ URLEncoder.encode("", "UTF-8");

		}else{
			data += "&" + URLEncoder.encode("bid_json", "UTF-8") + "="
					+ URLEncoder.encode("", "UTF-8");

			data += "&" + URLEncoder.encode("book_json", "UTF-8") + "="
					+ URLEncoder.encode(bidJSON, "UTF-8");
		}


		data += "&" + URLEncoder.encode("event_date", "UTF-8") + "="
				+ URLEncoder.encode(eventDate, "UTF-8");

		data += "&" + URLEncoder.encode("time_slot", "UTF-8") + "="
				+ URLEncoder.encode(timeSlot, "UTF-8");

		data += "&" + URLEncoder.encode("bid_price", "UTF-8") + "="
				+ URLEncoder.encode(bidPrice, "UTF-8");

		data += "&" + URLEncoder.encode("bid_quantity", "UTF-8") + "="
				+ URLEncoder.encode(bidQuantity, "UTF-8");

		System.out.println("Data is:"+data.toString());
		BufferedReader reader = null;

		// Send data
		try {
			URL _url = null;
			// Defined URL where to send data
			_url = new URL(GlobalCommonValues.CUSTOMER_VENDOR_MESSAGE_CREATE);
			// Send POST data request
			URLConnection conn = _url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(
					conn.getOutputStream());
			wr.write(data);
			wr.flush();

			// Get the server response
			reader = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;

			// Read Server Response
			while ((line = reader.readLine()) != null) {
				// Append server response in string
				sb.append(line + "\n");
			}
			response = sb.toString();
		} catch (Exception ex) {
		} finally {
			try {
				reader.close();
			} catch (Exception ex) {
			}
		}
	}

}
