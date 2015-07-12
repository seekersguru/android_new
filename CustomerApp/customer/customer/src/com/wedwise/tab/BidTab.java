package com.wedwise.tab;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.wedwise.adapter.BidAdapter;
import com.wedwise.adapter.MessagesListAdapter;
import com.wedwiseapp.R;

/**
 */
public class BidTab extends Fragment {

	ListView lvBid;
	ArrayList<HashMap<String, String>> listMessages;
	BidAdapter adapterMessageList;
	Button btnCreateBid;

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.bidtab,container,false);
		idInitialization(v);
		return v;
	}

	private void idInitialization(View view)
	{
		lvBid=(ListView) view.findViewById(R.id.lvBid);
		btnCreateBid=(Button) view.findViewById(R.id.btnCreate);
		listMessages=new ArrayList<HashMap<String,String>>();
//		listMessages.add("Andy Lau");
//		listMessages.add("James Moore");
//		listMessages.add("Jorgen Flood");
//		listMessages.add("Claude");
//		listMessages.add("Stefanos Fanidis");
//		listMessages.add("James Moore");
//		listMessages.add("James Moore");
//		listMessages.add("James Moore");
//		listMessages.add("James Moore");
//		listMessages.add("James Moore");
//		CustomFonts.setFontOfButton(getActivity(),btnCreateBid,"fonts/GothamRnd-Light.otf");
		adapterMessageList=new BidAdapter(getActivity(), listMessages);
		lvBid.setAdapter(adapterMessageList);
		lvBid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*Intent myIntent=new Intent(getActivity(),MessageChatActivity.class);
				getActivity().startActivity(myIntent);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);*/
			}
		});

		btnCreateBid.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent myIntent=new Intent(getActivity(),BidBookCreateActivity.class);
				myIntent.putExtra("type","bid");
				startActivity(myIntent);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);		
			}
		});
	}
}