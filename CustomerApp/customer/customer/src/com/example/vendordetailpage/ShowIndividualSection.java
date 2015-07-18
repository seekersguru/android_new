package com.example.vendordetailpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.wedwise.adapter.Acodianadapter;
import com.wedwise.gsonmodels.Acodianmodal;
import com.wedwise.gsonmodels.KeyValueHeader;
import com.wedwise.gsonmodels.KeyValueHeader.KeyValue;
import com.wedwise.gsonmodels.KeyValue_Model;
import com.wedwise.gsonmodels.PackagesModel;
import com.wedwise.gsonmodels.SectionModel;
import com.wedwise.json.PackagesModelNew;
import com.wedwise.json.TopPackege;
import com.wedwiseapp.R;
import com.wedwiseapp.views.CTextView;

public class ShowIndividualSection extends Activity{

	Activity activity = this;
	ScrollView scrollView;
//	ExpandableListView lvExp;
	Button btnShowIndBack;
	ListView acodianlistview;
	Acodianadapter adapter;
	List<Acodianmodal> _listDataHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.inidividual_section);
		scrollView = (ScrollView) activity.findViewById(R.id.scrollView);
//		lvExp = (ExpandableListView) findViewById(R.id.lvExp);
		btnShowIndBack = (Button) findViewById(R.id.btnShowIndBack);
		acodianlistview = (ListView) findViewById(R.id.acodianlistview);
		btnShowIndBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				overridePendingTransition(R.anim.right_in, R.anim.right_out);
			}
		});
		acodianlistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Acodianmodal temp = _listDataHeader.get(arg2);
				for (int i = 0; i < _listDataHeader.size(); i++) {
					
					if (_listDataHeader.get(i).getName().equals(temp.getName())) {
						if(temp.isOpen()){
							temp.setOpen(false);
						}else{
							temp.setOpen(true);
						}
						
					} else {
						_listDataHeader.get(i).setOpen(false);
					}
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		SectionModel sectionModel = (SectionModel) getIntent()
				.getSerializableExtra(SectionModel.intent_key);
		try {
			initSection(sectionModel);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initSection(SectionModel sectionModel)  throws Exception{
		switch (sectionModel.getRead_widgetsType()) {
		case heading:
			break;
		case key_value:
			//
			initKeyValue(sectionModel);
			break;
		case map:
			break;
		case packages:
			//

//			scrollView.setVisibility(View.GONE);
			initPackages(sectionModel);
			
			break;
		case para:
			break;
		default:
			break;
		}
	}

	@SuppressLint("InflateParams")
	private void initPackages(SectionModel sectionModel) throws Exception {
		PackagesModel packagesModel = (PackagesModel) sectionModel.getReadTypeModel();
		_listDataHeader = new ArrayList<Acodianmodal>();
		HashMap<String, List<KeyValueHeader>> _listDataChild = new HashMap<String, List<KeyValueHeader>>();
		ArrayList<ArrayList<KeyValue>> arrayList1 = new ArrayList<ArrayList<KeyValue>>();
		CTextView tvDescription = (CTextView) activity
				.findViewById(R.id.tvDescription);
		if (!TextUtils.isEmpty(sectionModel.getHeader())) {
			tvDescription.setText(sectionModel.getRead_header());
		} else {
			tvDescription.setVisibility(View.GONE);
		}
		for (TopPackege package_Values : packagesModel.getPackage_Values()) {
		
//			Acodianmodal modal = new Acodianmodal();
//			modal.setName(package_Values.getPackage_header());
//			_listDataHeader.add(modal);
			ArrayList<KeyValueHeader> arrayList = new ArrayList<KeyValueHeader>();
//			ArrayList<ArrayList<KeyValue>> arrayList1 = new ArrayList<ArrayList<KeyValue>>();
			HashMap<String, String> subsection_values;
			
			//
			for (PackagesModelNew subSection : package_Values.getPackegeItems()) {
//				subsection_values = package_Values
//							.getSubsection_info().get(subSection);
				KeyValueHeader keyValueHeader  = new KeyValueHeader(subSection.getLabel());
				subSection.getOptions().add(0, subSection.getMinimum());
				subSection.getOptions().add(1, subSection.getQuoted());
//				for (String key : subsection_values.keySet()) {
//					String value = subsection_values.get(key);
//					keyValueHeader.addKeyValue(key, value);
//					if(key.equals("label")){
						Acodianmodal modal = new Acodianmodal();
						modal.setName(package_Values.getPackegeType() + " ("+subSection.getLabel()+")");
						_listDataHeader.add(modal);
//					}else if(key.equals("options")){
//						arrayList1.add(keyValueHeader);
//					}
//					
//				}
				arrayList1.add(subSection.getOptions());
//				arrayList.add(keyValueHeader);
			}
			
//			for (String key : package_Values
//					.getSubsection_info()
//					.get(SubSection.getSubSection(package_Values
//							.getPackage_header())).keySet()) {
//				KeyValue keyValue = new KeyValue(key, package_Values
//						.getSubsection_info()
//						.get(SubSection.getSubSection(package_Values
//								.getPackage_header())).get(key));
//				arrayList.add(keyValue);
//			}
//			_listDataChild.put(package_Values.getPackegeType(), arrayList1);
		}
		
		adapter = new Acodianadapter(activity, _listDataHeader, arrayList1);
		acodianlistview.setAdapter(adapter);
//		ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(activity, _listDataHeader, _listDataChild);
//		lvExp.setAdapter(expandableListAdapter);
		
	}
	

	private void initKeyValue(SectionModel sectionModel) {
		LinearLayout linearLayout = (LinearLayout) activity
				.findViewById(R.id.key_value_layout);
		CTextView tvDescription = (CTextView) activity
				.findViewById(R.id.tvDescription);
		if (!TextUtils.isEmpty(sectionModel.getHeader())) {
			tvDescription.setText(sectionModel.getRead_header());
		} else {
			tvDescription.setVisibility(View.GONE);
		}
		KeyValue_Model keyValue_Model = (KeyValue_Model) sectionModel
				.getReadTypeModel();
		View blank_line_view = null;
		for (String key : keyValue_Model.getPairs().keySet()) {
			View child = activity.getLayoutInflater().inflate(
					R.layout.keyvaluerow, null);
			CTextView key_layout = (CTextView) child
					.findViewById(R.id.key_layout);
			CTextView value_layout = (CTextView) child
					.findViewById(R.id.value_layout);
			key_layout.setText(key);
			value_layout.setText(keyValue_Model.getPairs().get(key));
			linearLayout.addView(child);
			blank_line_view = activity.getLayoutInflater().inflate(
					R.layout.blank_line, null);
			linearLayout.addView(blank_line_view);
		}
		// this is the last balnk view
		if (blank_line_view != null)
			blank_line_view.setVisibility(View.GONE);

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_in, R.anim.right_out);
	}

}
