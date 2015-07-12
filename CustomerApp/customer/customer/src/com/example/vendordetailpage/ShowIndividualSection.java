package com.example.vendordetailpage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.wedwise.adapter.ExpandableListAdapter;
import com.wedwise.gsonmodels.KeyValueHeader;
import com.wedwise.gsonmodels.KeyValue_Model;
import com.wedwise.gsonmodels.PackagesModel;
import com.wedwise.gsonmodels.PackagesModel.Package_Values;
import com.wedwise.gsonmodels.PackagesModel.Package_Values.SubSection;
import com.wedwise.gsonmodels.SectionModel;
import com.wedwiseapp.R;
import com.wedwiseapp.views.CTextView;

public class ShowIndividualSection extends Activity {

	Activity activity = this;
	ScrollView scrollView;
	ExpandableListView lvExp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inidividual_section);
		scrollView = (ScrollView) activity.findViewById(R.id.scrollView);
		lvExp = (ExpandableListView) findViewById(R.id.lvExp);
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

			scrollView.setVisibility(View.GONE);
			initPackages(sectionModel);
			
			break;
		case para:
			break;
		default:
			break;
		}
	}

	private void initPackages(SectionModel sectionModel) throws Exception {
		PackagesModel packagesModel = (PackagesModel) sectionModel
				.getReadTypeModel();
		List<String> _listDataHeader = new ArrayList<String>();
		HashMap<String, List<KeyValueHeader>> _listDataChild = new HashMap<String, List<KeyValueHeader>>();
		for (Package_Values package_Values : packagesModel.getPackage_Values()) {
			_listDataHeader.add(package_Values.getPackage_header());
			ArrayList<KeyValueHeader> arrayList = new ArrayList<KeyValueHeader>();
			HashMap<String, String> subsection_values;
			//
			for (SubSection subSection : SubSection.values()) {
				subsection_values = package_Values
							.getSubsection_info().get(subSection);
				KeyValueHeader keyValueHeader  = new KeyValueHeader(subSection.name());
				for (String key : subsection_values.keySet()) {
					String value = subsection_values.get(key);
					keyValueHeader.addKeyValue(key, value);
				}
				arrayList.add(keyValueHeader);
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
			_listDataChild.put(package_Values.getPackage_header(), arrayList);
		}
		ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(activity, _listDataHeader, _listDataChild);
		lvExp.setAdapter(expandableListAdapter);
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
}
