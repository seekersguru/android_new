package com.wedwise.dialogs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;
import com.wedwise.interfaces.INotify;
import com.wedwiseapp.R;


public class VideoViewerDialog extends DialogFragment{

	Context mContext;
	INotify iNotify;
	VideoView videoViewer;
	ProgressDialog pDialog;

	public void newInstance(Context mContext,INotify iNotify)
	{
		this.mContext=mContext;
		this.iNotify=iNotify;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(STYLE_NO_TITLE, getTheme());
	}

	@Override
	public void onStart() {
		super.onStart();
		Window window=getDialog().getWindow();
		WindowManager.LayoutParams params=window.getAttributes();
		params.dimAmount=0.6f;
		window.setAttributes(params);
		window.setBackgroundDrawableResource(android.R.color.transparent);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.videoviewerdialog,container);
		videoViewer=(VideoView) view.findViewById(R.id.videoViewer);
		// Create a progressbar
		pDialog = new ProgressDialog(mContext);
		// Set progressbar title
//		pDialog.setTitle("Android Video Streaming Tutorial");
		// Set progressbar message
		pDialog.setMessage("Buffering...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		// Show progressbar
		pDialog.show();

		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(mContext);
			mediacontroller.setAnchorView(videoViewer);
			// Get the URL from String VideoURL
			Uri video = Uri.parse("http://www.androidbegin.com/tutorial/AndroidCommercial.3gp");
			videoViewer.setMediaController(mediacontroller);
			videoViewer.setVideoURI(video);
 
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
 
		videoViewer.requestFocus();
		videoViewer.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
				pDialog.dismiss();
				videoViewer.start();
			}
		});
		return view;
	}
	
	@Override
	public void onDismiss(DialogInterface dialog) {
		super.onDismiss(dialog);
		if(pDialog!=null && pDialog.isShowing())
			pDialog.dismiss();
	}
}
