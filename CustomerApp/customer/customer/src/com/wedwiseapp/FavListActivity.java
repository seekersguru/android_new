package com.wedwiseapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vendordetailpage.MainActivity;
import com.wedwise.Activities.MenuListActivity;
import com.wedwise.Activities.MessageListActivity;
import com.wedwise.adapter.FavAdapter;
import com.wedwise.adapter.SpinnerAdapter;
import com.wedwise.common.GlobalCommonMethods;
import com.wedwise.common.GlobalCommonValues;
import com.wedwise.dialogs.ErrorDialog;
import com.wedwise.tab.MessageTabActivity;
import com.wedwiseapp.util.CustomFonts;
import com.wedwiseapp.util.PreferenceUtil;

@SuppressLint("InflateParams")
public class FavListActivity extends FragmentActivity {
	ListView favList;
	FavAdapter adapterSubList;
	Context mContext;
	Button btnBack, btnSearch;// ,btnSpinnerOpen;
	Button btnMail,btnHome,btnLeads,btnMenu;
	LinearLayout llMail, llHome, llLeads, llMenu;
	ArrayList<FavData> data;
	SearchView searchView;
	// Spinner spSwitchCategory;
	ArrayList<String> listCategory;
	SpinnerAdapter adapterSpinner;
	// ImageView imViewCategoryType;
	TextView tvCategoryType, tvresult;
	View viewTopbar;
	String category = "";
	// Button btnCategory;
	// Spinner spSwitchCategory;
	String response, url;
	ProgressDialog progress;
	String vendor_type;
	
	
	ArrayList<ArrayList<HashMap<String,String>>> listImages1 = new ArrayList<ArrayList<HashMap<String,String>>>();
	
	boolean isSearch = false;
	EditText et;
	String search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = FavListActivity.this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.favorite);
		idInitialization();
	}

	private void idInitialization() {
		// spSwitchCategory=(Spinner) findViewById(R.id.spSwitchCategory);
		favList = (ListView) findViewById(R.id.favList);
		listCategory = new ArrayList<String>();
		data = new ArrayList<FavData>();
		adapterSubList = new FavAdapter(mContext, listData, listImages1);
		favList.setAdapter(adapterSubList);
		btnBack = (Button) findViewById(R.id.btnBack);
		viewTopbar = findViewById(R.id.viewTopbar);
		viewTopbar.setVisibility(View.GONE);
		searchView = (SearchView) findViewById(R.id.searchView);
		btnSearch = (Button) findViewById(R.id.btnSeacrh);
		tvCategoryType = (TextView) findViewById(R.id.tvCategoryType);
		tvresult = (TextView) findViewById(R.id.tvresult);
		CustomFonts.setFontOfTextView(FavListActivity.this, tvCategoryType, "fonts/GothamRoundedBook.ttf");
		CustomFonts.setFontOfTextView(FavListActivity.this, tvresult, "fonts/GothamRoundedBook.ttf");

		llMail = (LinearLayout) findViewById(R.id.llMail);
		llHome = (LinearLayout) findViewById(R.id.llHome);
		llLeads = (LinearLayout) findViewById(R.id.llLeads);
		llMenu = (LinearLayout) findViewById(R.id.llMenu);
		btnMail = (Button) findViewById(R.id.btnMail);
		btnHome=(Button) findViewById(R.id.btnHome);
		btnLeads=(Button) findViewById(R.id.btnLeads);
		btnMenu = (Button) findViewById(R.id.btnMenu);
		// spSwitchCategory=(Spinner) findViewById(R.id.spSwitchCategory);
		// btnCategory=(Button) findViewById(R.id.btnCategory);
		// btnCategory.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// spSwitchCategory.performClick();
		// }
		// });

		if (getIntent() != null && getIntent().getExtras() != null) {
			vendor_type = getIntent().getExtras().getString("category_type");
		}

		llHome.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.left_in, R.anim.right_out);
			}
		});

		llLeads.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(FavListActivity.this,
						MessageTabActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
			}
		});

		llMail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (PreferenceUtil.getInstance().getBrideName() == null || PreferenceUtil.getInstance().getBrideName().equals("") ) {
					ErrorDialog dialog = new ErrorDialog();
					dialog.newInstance(mContext, "Alert!",
							"Please register on Wedwise to use this feature",
							null);
					dialog.setCancelable(false);
					dialog.show(getFragmentManager(), "test");
				} else if (PreferenceUtil.getInstance().isRegistered()) {
					Intent myIntent = new Intent(FavListActivity.this,
							MessageListActivity.class);
					startActivity(myIntent);
					overridePendingTransition(R.anim.right_in,
							R.anim.left_out);
				}
			}
		});

		llMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(FavListActivity.this,
						MenuListActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
				// boolean isLogin = PreferenceUtil.getInstance().isLogin();
				// if(isLogin)
				// {
				// LogoutConfirmationDialog dialogLogout=new
				// LogoutConfirmationDialog();
				// dialogLogout.setCancelable(false);
				// dialogLogout.newInstance(getActivity(), "",
				// "You are logged in.Do you want to logout?", iNotifyLogout);
				// dialogLogout.show(getFragmentManager(), "test");
				// }
				// else if (!isLogin)
				// {
				// Intent myIntent = new Intent(getActivity(),
				// LoginSignUpActivity.class);
				//  startActivity(myIntent);
				//  overridePendingTransition(R.anim.right_in,
				// R.anim.left_out);
				// }
			}
		});
		
		btnMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(FavListActivity.this,
						MenuListActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);
			}
		});

		
		btnMail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (PreferenceUtil.getInstance().getBrideName() == null || PreferenceUtil.getInstance().getBrideName().equals("") ) {
					ErrorDialog dialog = new ErrorDialog();
					dialog.newInstance(mContext, "Alert!",
							"Please register on Wedwise to use this feature",
							null);
					dialog.setCancelable(false);
					dialog.show(getFragmentManager(), "test");
				} else if (PreferenceUtil.getInstance().isRegistered()) {
					Intent myIntent = new Intent(FavListActivity.this,
							MessageListActivity.class);
					startActivity(myIntent);
					overridePendingTransition(R.anim.right_in,
							R.anim.left_out);
				}
			}
		});
		
		btnLeads.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent = new Intent(FavListActivity.this,
						MessageTabActivity.class);
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in,
						R.anim.left_out);					
			}
		});


		// ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
		// android.R.layout.simple_spinner_item,
		// VendorCategoryHomeFragment.listItemsCategory);
		// dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// spSwitchCategory.setAdapter(dataAdapter);

		// spSwitchCategory.setOnItemSelectedListener(new
		// OnItemSelectedListener() {
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int position, long id) {
		// category=String.valueOf(spSwitchCategory.getSelectedItem());
		// tvCategoryType.setText(category);
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		// }
		// });
		// btnSpinnerOpen=(Button) findViewById(R.id.btnSpinnerOpen);
		btnSearch.setVisibility(View.VISIBLE);
		// imViewCategoryType.setVisibility(View.VISIBLE);
		tvCategoryType.setVisibility(View.VISIBLE);

		if (getIntent() != null && getIntent().getExtras() != null) {
			category = getIntent().getExtras().getString("category_type");
			tvCategoryType.setText(category);
		} else {
			tvCategoryType.setText("");
		}
		// actionBar.setDisplayShowCustomEnabled(true);

		listCategory.add("BANQUITE");
		listCategory.add("PHOTOGRAPHY");
		listCategory.add("CATERERS");
		listCategory.add("DECORATORS");
		listCategory.add("OTHERS");
		adapterSpinner = new SpinnerAdapter(FavListActivity.this, listCategory);
		btnSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//				btnSearch.setVisibility(View.INVISIBLE);
				searchView.setIconified(false);
				searchView.setBackgroundColor(Color.TRANSPARENT);
				searchView.requestFocus();
				int searchPlateId = searchView.getContext().getResources()
						.getIdentifier("android:id/search_plate", null, null);
				// searchView.findViewById(searchPlateId).setBackgroundColor(Color.parseColor("#00000000"));
				View searchPlateView = searchView.findViewById(searchPlateId);
				if (searchPlateView != null) {
					searchPlateView.setBackgroundColor(Color.WHITE);
				}
				try {
					int id = searchView
							.getContext()
							.getResources()
							.getIdentifier("android:id/search_src_text", null,
									null);
					TextView tv = (TextView) searchView.findViewById(id);
					et = (EditText) searchView.findViewById(id);
					et.setHint("Search Here");
					tv.setTextColor(Color.parseColor("#F05543"));
				} catch (Exception e) {
					e.getMessage();
				}
				searchView.performClick();

				if(searchView.getVisibility() == View.VISIBLE && !et.getHint().equals("Search Here")){
					searchView.setVisibility(View.GONE);
					tvCategoryType.setText(category);
					isSearch = true;
					search = et.getText().toString();
					new HttpAsyncTask().execute(url);
				}else if(searchView.getVisibility() == View.VISIBLE && et.getHint().equals("Search Here")){
					Toast.makeText(FavListActivity.this, "Please enter some text", Toast.LENGTH_SHORT).show();
				}else{
					isSearch = false;
					searchView.setVisibility(View.VISIBLE);
					tvCategoryType.setText(category.substring(0, 1) + "...");
					/*
					 * InputFilter[] filterArray = new InputFilter[1];
					 * filterArray[0] = new InputFilter.LengthFilter(20);
					 * tvCategoryType.setFilters(filterArray);
					 */

				}
			}
		});

		// spSwitchCategory.setOnItemSelectedListener(new
		// OnItemSelectedListener() {
		//
		// @Override
		// public void onItemSelected(AdapterView<?> parent, View view,
		// int position, long id) {
		//
		// }
		//
		// @Override
		// public void onNothingSelected(AdapterView<?> parent) {
		// }
		// });
		searchView.setOnCloseListener(new OnCloseListener() {

			@Override
			public boolean onClose() {

				btnSearch.setVisibility(View.VISIBLE);
				searchView.setVisibility(View.INVISIBLE);
				tvCategoryType.setText(category);
				/*
				 * InputFilter[] filterArray = new InputFilter[1];
				 * filterArray[0] = new InputFilter.LengthFilter(1);
				 * tvCategoryType.setFilters(filterArray);
				 */
				// searchView.setIconified(true);
				return false;
			}
		});
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.right_out, R.anim.left_in);
			}
		});

		favList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View viewChild,
					int position, long arg3) {

				Intent myIntent = new Intent(FavListActivity.this,
						MainActivity.class);
				myIntent.putExtra("_receiveremail",
						listData.get(position).get("email"));
				startActivity(myIntent);
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		url = GlobalCommonValues.CUSTOMER_VENDOR_LIST_AND_SEARCH;
		new HttpAsyncTask().execute(url);
	}

	ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();

	private class HttpAsyncTask extends AsyncTask<String, Void, Void> {
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
			if (progress != null && progress.isShowing()) {
				progress.dismiss();
				progress = null;
			}
			if (!TextUtils.isEmpty(response)
					&& GlobalCommonMethods.isJSONValid(response)) {
				try {
					JSONArray jsonArray = new JSONObject(response)
					.getJSONObject("json").getJSONArray("vendor_list");
					HashMap<String, String> hashMap;
					if(jsonArray.length() != 0){
						tvresult.setVisibility(View.GONE);
					for (int i = 0; i < jsonArray.length(); i++) {
						String email = new JSONObject(String.valueOf(jsonArray
								.get(i))).get("vendor_email").toString();
						String name = new JSONObject(String.valueOf(jsonArray
								.get(i))).get("name").toString();
						String image = new JSONObject(String.valueOf(jsonArray
								.get(i))).get("image").toString();
						String price = new JSONObject(String.valueOf(jsonArray
								.get(i))).get("starting_price").toString();


						ArrayList<HashMap<String,String>> listImagesicon = new ArrayList<HashMap<String,String>>();

//						item_icons = new JSONObject(String.valueOf(jsonArray
//								.get(i))).getJSONArray("icons_line2");

						String[] arricon1=new JSONObject(String.valueOf(jsonArray
								.get(i))).getJSONArray("icons_line1").toString().split(",");


						

						for(int j=0;j<arricon1.length;j++)
						{                              //[[{"\/media\/icons\/2x\/icon3.png", "DJ"}]
							String[] arr2=arricon1[j].split(":");//[0].substring(arr1[0].split(":")[0].indexOf("\"")+1,arr1[0].split(":")[0].lastIndexOf("\"")).replaceAll("\", " ");
							String _image=arr2[0].substring(arr2[0].split(":")[0].indexOf("\"")+1,arricon1[0].split(":")[0].lastIndexOf("\"")).replace("\\","");
							String _name=arr2[1].substring(arr2[1].indexOf("\"")+1,arr2[1].lastIndexOf("\""));
							HashMap<String, String> hashMapImages=new HashMap<String, String>();
							hashMapImages.put("image1"+j,_image);
							hashMapImages.put("name1"+j,_name);
							listImagesicon.add(hashMapImages);
						}
						
						
						
						
						String[] arricon2=new JSONObject(String.valueOf(jsonArray
								.get(i))).getJSONArray("icons_line2").toString().split(",");


//						ArrayList<HashMap<String,String>> listImages2 = new ArrayList<HashMap<String,String>>();

						for(int j=0;j<arricon2.length;j++)
						{                              //[[{"\/media\/icons\/2x\/icon3.png", "DJ"}]
							String[] arr2=arricon2[j].split(":");//[0].substring(arr1[0].split(":")[0].indexOf("\"")+1,arr1[0].split(":")[0].lastIndexOf("\"")).replaceAll("\", " ");
							String _image=arr2[0].substring(arr2[0].split(":")[0].indexOf("\"")+1,arricon2[0].split(":")[0].lastIndexOf("\"")).replace("\\","");
							String _name=arr2[1].substring(arr2[1].indexOf("\"")+1,arr2[1].lastIndexOf("\""));
							HashMap<String, String> hashMapImages=new HashMap<String, String>();
							hashMapImages.put("image2"+j,_image);
							hashMapImages.put("name2"+j,_name);
							listImagesicon.add(hashMapImages);
						}
//						System.out.println(listImages);//[{name=DJ, image=/media/icons/2x/icon3.png}, {name=MM, image=/media/icons/2x/icon4.png"}]
						hashMap = new HashMap<String, String>();
						hashMap.put("name", name);
						hashMap.put("email", email);
						hashMap.put("image", image);
						hashMap.put("price", price);
						listData.add(hashMap);
						listImages1.add(listImagesicon);
					}
					adapterSubList.listData = listData;
					adapterSubList.listImages = listImages1;
					adapterSubList.notifyDataSetChanged();
					}else{
						tvresult.setVisibility(View.VISIBLE);
					}
					
				} catch (Exception e) {
				}
			}
		}
	}

	// Create GetData Method
	public void SetData() throws UnsupportedEncodingException {
		// Create data variable for sent values to server
		String data = "";
		String page_no = "1";

		int density = getResources().getDisplayMetrics().densityDpi;
		String image_type = "", search_string = "";
		if(isSearch){
			search_string = search;
		}else{
			search_string = "";
		}

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

		data += "&" + URLEncoder.encode("vendor_type", "UTF-8") + "="
				+ URLEncoder.encode(vendor_type, "UTF-8");

		data += "&" + URLEncoder.encode("page_no", "UTF-8") + "="
				+ URLEncoder.encode(page_no, "UTF-8");

		data += "&" + URLEncoder.encode("search_string", "UTF-8") + "="
				+ URLEncoder.encode(search_string, "UTF-8");

		BufferedReader reader = null;
		// Send data
		try {
			URL _url = null;
			// Defined URL where to send data
			_url = new URL(GlobalCommonValues.CUSTOMER_VENDOR_LIST_AND_SEARCH);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
