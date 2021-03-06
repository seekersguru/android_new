package com.example.vendordetailpage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.commonsware.cwac.merge.MergeAdapter;
import com.google.gson.Gson;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.common.WidgetsType;
import com.wedwise.gson.Bid;
import com.wedwise.gson.Book;
import com.wedwise.gson.Section;
import com.wedwise.gson.VendorDetail;
import com.wedwise.gsonmodels.KeyValue_Model;
import com.wedwise.gsonmodels.Map_Model;
import com.wedwise.gsonmodels.PackagesModel;
import com.wedwise.gsonmodels.ParaModel;
import com.wedwise.gsonmodels.SectionModel;
import com.wedwise.gsonmodels.TypeModel;
import com.wedwise.tab.BidBookCreateActivity;
import com.wedwiseapp.R;
import com.wedwiseapp.util.IntentHelper;

public class MainActivity extends Activity {

	Context mContext;
	String response = "", url = "";
	private final String TAG = "VendorDetailsActivity2";
	ArrayList<SectionModel> sectionModels = new ArrayList<SectionModel>();
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		mContext = this;
		listView = (ListView) findViewById(R.id.listView);
		findViewById(R.id.btnBack).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();		
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		new HttpAsyncTask().execute();
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {

		ProgressDialog progress;

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
			// Toast.makeText(getBaseContext(), "Data Sent!"+response,
			// Toast.LENGTH_LONG).show();
			
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
				progress = null;
			}

			if (!TextUtils.isEmpty(response)
					&& GlobalCommonMethods.isJSONValid(response)) {
				// Log.d(TAG, "response= "+response);
				Gson gson = new Gson();
				VendorDetail vendorDetail = null;
				try {
					vendorDetail = gson.fromJson(response, VendorDetail.class);
				} catch (Exception e) {
					e.printStackTrace();
					try {
						Toast.makeText(
								mContext,
								" "
										+ new JSONObject(response)
												.getString("result"),
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						finish();
						return;
					}
					finish();
					return;
				}
				Log.d(TAG, "vendorDetail= " + vendorDetail);
				Log.d(TAG, "getVendorEmail= "
						+ vendorDetail.getRequestData().getVendorEmail());
				Log.d(TAG, "vendorDetail= " + vendorDetail);
				try {
					for (Section section : vendorDetail.getJson().getData()
							.getSections()) {
						 Log.d(TAG, "section= "
						 + section.getDataDisplay().get(0).toString());
						boolean hasExtraSection = section.getDataDisplay()
								.size() > 1;
						String section_response = section.getDataDisplay()
								.get(0).toString();
						JSONObject jsonObject = new JSONObject(section_response);
						WidgetsType widgetsType = WidgetsType
								.getWidgetsType(jsonObject);
						TypeModel typeModel = getTypeModel(widgetsType,
								section_response);

						SectionModel sectionModel = new SectionModel();
						sectionModel.setTypeModel(typeModel);
						sectionModel.setHeader(section.getHeading());
						sectionModel.setWidgetsType(widgetsType);

						if (jsonObject.has("read_more")) {
							JSONObject readmore_jsononobj = jsonObject
									.getJSONArray("read_more").getJSONObject(0);// .getJSONObject("data_display");
							String read_more_heading = readmore_jsononobj
									.getString("heading");
							JSONObject read_more_data_display_json = readmore_jsononobj
									.getJSONArray("data_display")
									.getJSONObject(0);
							WidgetsType read_more_widgetsType = WidgetsType
									.getWidgetsType(read_more_data_display_json);
							TypeModel read_more_typeModel = getTypeModel(
									read_more_widgetsType,
									read_more_data_display_json.toString());

							sectionModel.setReadTypeModel(read_more_typeModel);

							sectionModel.setRead_header(read_more_heading);

							sectionModel
									.setRead_widgetsType(read_more_widgetsType);
						}
						//
						if (hasExtraSection) {
							String extra_section_response = section
									.getDataDisplay().get(1).toString();
							JSONObject extra_jsonObject = new JSONObject(
									extra_section_response);
							WidgetsType extra_widgetsType = WidgetsType
									.getWidgetsType(extra_jsonObject);
							TypeModel extra_typeModel = getTypeModel(
									extra_widgetsType, extra_section_response);
							sectionModel.setExtraTypeModel(extra_typeModel);
							sectionModel.setExtraWidgetsType(extra_widgetsType);
						}

						sectionModels.add(sectionModel);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (SectionModel sectionModel : sectionModels) {
//					Log.d(TAG, "getHeader= " + sectionModel.getHeader());
//					Log.d(TAG,
//							"getRead_header= " + sectionModel.getRead_header());
//					Log.d(TAG,
//							"getWidgetsType= " + sectionModel.getWidgetsType());
//					Log.d(TAG,
//							"getRead_widgetsType= "
//									+ sectionModel.getRead_widgetsType());
//					if(sectionModel.getRead_widgetsType() !=null && sectionModel.getRead_widgetsType().equals(WidgetsType.packages)){
//						Log.d(TAG,
//								"getReadTypeModel= "
//										+ sectionModel.getReadTypeModel());
//					}
//					Log.d(TAG,
//							"getExtraWidgetsType= "
//									+ sectionModel.getExtraWidgetsType());
				}
				MergeAdapter mergeAdapter = new MergeAdapter();
				fillVendorDetail(vendorDetail, mergeAdapter);
				SectionManager sectionManager = new SectionManager(
						MainActivity.this);
				sectionManager.initSections(sectionModels, mergeAdapter);
				listView.setAdapter(mergeAdapter);

				
				// Bid & Book Button
				final String vendorEmail = vendorDetail.getRequestData().getVendorEmail();
				
				TextView bid_button = (TextView)findViewById(R.id.bid_button);
				bid_button.setText(vendorDetail.getJson().getData().getBid().getButton());
				final Bid bidDetail = vendorDetail.getJson().getData().getBid();
				final Book bookDetail = vendorDetail.getJson().getData().getBook();
				bid_button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						IntentHelper.addObjectForKey(bidDetail, "bidDetail");
						Intent myIntent=new Intent(getApplicationContext(),BidBookCreateActivity.class);
						myIntent.putExtra("type","bid");
						myIntent.putExtra("vendorEmail",vendorEmail);
						startActivity(myIntent);
						overridePendingTransition(R.anim.right_in, R.anim.left_out);	
					}
				});
				
				
				TextView book_button = (TextView)findViewById(R.id.book_button);
				book_button.setText(vendorDetail.getJson().getData().getBook().getButton());
				book_button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						IntentHelper.addObjectForKey(bookDetail, "bookDetail");
						Intent myIntent=new Intent(getApplicationContext(),BidBookCreateActivity.class);
						myIntent.putExtra("type","book");
						myIntent.putExtra("vendorEmail",vendorEmail);
						startActivity(myIntent);
						overridePendingTransition(R.anim.right_in, R.anim.left_out);	
					}
				});
			}
		}
	}

	// Create GetData Metod
	public void SetData() throws UnsupportedEncodingException {
		// Create data variable for sent values to server
		String data = "";
		String image_type = "";
		int density = getResources().getDisplayMetrics().densityDpi;

		if (density == DisplayMetrics.DENSITY_MEDIUM) {
			image_type = "drawable-hdpi";
		} else if (density == DisplayMetrics.DENSITY_HIGH) {
			image_type = "drawable-xhdpi";
		} else if (density == DisplayMetrics.DENSITY_XHIGH) {
			image_type = "drawable-xhdpi";
		} else if (density == DisplayMetrics.DENSITY_XXHIGH) {
			image_type = "drawable-xxhdpi";
		} else if (density == DisplayMetrics.DENSITY_XXXHIGH) {
			image_type = "drawable-xxxhdpi";
		}

		data = URLEncoder.encode("mode", "UTF-8") + "="
				+ URLEncoder.encode("android", "UTF-8");

		data += "&" + URLEncoder.encode("image_type", "UTF-8") + "="
				+ URLEncoder.encode(image_type, "UTF-8");

		data += "&" + URLEncoder.encode("vendor_email", "UTF-8") + "="
				+ URLEncoder.encode("banquet_novotel@wedwise.in", "UTF-8");

		BufferedReader reader = null;

		// Send data
		try {
			URL _url = null;
			// Defined URL where to send data
			_url = new URL(GlobalCommonValues.CUSTOMER_VENDOR_DETAIL);
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

	public void fillVendorDetail(VendorDetail vendorDetail, MergeAdapter mergeAdapter) {
		View view = getLayoutInflater().inflate(
				R.layout.vendor_detail_header, null);
		mergeAdapter.addView(view);
		
	}

	public TypeModel getTypeModel(WidgetsType widgetsType,
			String section_response) {
		switch (widgetsType) {
		case heading:

			break;
		case key_value:
			KeyValue_Model keyValue_Model = new KeyValue_Model(section_response);
			return keyValue_Model;
		case map:
			Map_Model map_Model = new Map_Model(section_response);
			return map_Model;
		case packages:
			PackagesModel packagesModel = new PackagesModel(section_response);
			return packagesModel;
		case para:
			ParaModel paraModel = new ParaModel(section_response);
			return paraModel;

		default:
			break;
		}
		return null;
	}
	
}
