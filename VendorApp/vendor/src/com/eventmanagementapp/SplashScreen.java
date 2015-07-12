package com.eventmanagementapp;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.MediaController;
import android.widget.VideoView;

public class SplashScreen extends Activity{

	int	SPLASH_TIME_OUT=5000;
	//	private String path ="http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
	VideoView videoview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		try {
			setContentView(R.layout.splash);
			videoview = (VideoView) findViewById(R.id.videoViewer);
			try {
				// Start the MediaController
				MediaController mediacontroller = new MediaController(
						SplashScreen.this);
				mediacontroller.setAnchorView(videoview);
				// Get the URL from String VideoURL
//				String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
//				videoview.setMediaController(mediacontroller);
//				videoview.setVideoURI(Uri.parse(path));
				videoview.start();
				//				Uri video = Uri.parse("");
				//				videoview.setMediaController(mediacontroller);
				//				videoview.setVideoURI(video);

			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

			videoview.requestFocus();
			videoview.setOnPreparedListener(new OnPreparedListener() {
				// Close the progress bar and play the video
				public void onPrepared(MediaPlayer mp) {
					videoview.start();
				}
			});


			new Handler().postDelayed(new Runnable() {
				/*
				 * Showing splash screen with a timer. This will be useful when you
				 * want to show case your app logo / company
				 */

				@Override
				public void run() {
					// This method will be executed once the timer is over
					// Start your app main activity
					if(videoview.isPlaying())
						videoview.stopPlayback();
					Intent i = new Intent(SplashScreen.this, HomeActivity.class);
					startActivity(i);

					// close this activity
					finish();
				}
			}, SPLASH_TIME_OUT);
		} catch (Exception e) {
			e.getMessage();
		}
	}
}
