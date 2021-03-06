package com.example.vendordetailpage;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.commonsware.cwac.merge.MergeAdapter;
import com.wedwise.gsonmodels.KeyValue_Model;
import com.wedwise.gsonmodels.Map_Model;
import com.wedwise.gsonmodels.ParaModel;
import com.wedwise.gsonmodels.SectionModel;
import com.wedwiseapp.R;
import com.wedwiseapp.views.CTextView;

public class SectionManager {

	Activity activity;

	public SectionManager(Activity activity) {
		this.activity = activity;
	}

	public void initSections(ArrayList<SectionModel> sectionModels,
			MergeAdapter mergeAdapter) {
		for (SectionModel sectionModel : sectionModels) {
			switch (sectionModel.getWidgetsType()) {
			case heading:
				break;
			case key_value:
				mergeAdapter.addView(initKeyValue(sectionModel));
				break;
			case map:
				mergeAdapter.addView(initMap(sectionModel));
				break;
			case packages:

				break;
			case para:
				break;
			default:
				break;
			}
		}
	}

	private View initMap(SectionModel sectionModel) {
		View view = activity.getLayoutInflater().inflate(R.layout.maplayout,
				null);
		Map_Model map_Model = (Map_Model) sectionModel.getTypeModel();
		String getMapURL = "http://maps.googleapis.com/maps/api/staticmap?zoom=12&size=560x240&markers=size:mid|color:red|"
				+ map_Model.getPoint().latitude
				+ ","
				+ map_Model.getPoint().longitude + "&sensor=false";
		WebView webView = (WebView) view.findViewById(R.id.webView);
		webView.loadUrl(getMapURL);
		CTextView tvPara = (CTextView) view.findViewById(R.id.tvPara);
		if (sectionModel.getExtraTypeModel() != null) {
			if (sectionModel.getExtraTypeModel() instanceof ParaModel) {
				ParaModel paraModel = (ParaModel) sectionModel
						.getExtraTypeModel();
				tvPara.setText(paraModel.getText());
			} else {
				tvPara.setVisibility(View.GONE);
			}
		} else {
			tvPara.setVisibility(View.GONE);
		}
		return view;
	}

	private View initKeyValue(final SectionModel sectionModel) {
		View view = activity.getLayoutInflater().inflate(
				R.layout.keyvaluelayuot, null);
		LinearLayout linearLayout = (LinearLayout) view
				.findViewById(R.id.key_value_layout);
		CTextView tvDescription = (CTextView) view
				.findViewById(R.id.tvDescription);
		CTextView tvReadMoreSecond = (CTextView) view
				.findViewById(R.id.tvReadMoreSecond);
		if (!TextUtils.isEmpty(sectionModel.getHeader())) {
			tvDescription.setText(sectionModel.getHeader());
		} else {
			tvDescription.setVisibility(View.GONE);
		}
		KeyValue_Model keyValue_Model = (KeyValue_Model) sectionModel
				.getTypeModel();
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
		if(sectionModel.getReadTypeModel()!=null){
			tvReadMoreSecond.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent= new Intent(activity,ShowIndividualSection.class);
					intent.putExtra(SectionModel.intent_key, sectionModel);
					activity.startActivity(intent);
				}
			});
			
		}else{
			tvReadMoreSecond.setVisibility(View.GONE);
		}
		
		return view;
	}
}
