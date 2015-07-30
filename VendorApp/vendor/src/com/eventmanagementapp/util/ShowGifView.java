package com.eventmanagementapp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;

public class ShowGifView extends View {

	// Set true to use decodeStream
	// Set false to use decodeByteArray
	private static final boolean DECODE_STREAM = true;

	private InputStream gifInputStream;
	private Movie gifMovie;
	private int movieWidth, movieHeight;
	private long movieDuration;
	private long mMovieStart;
	private Context context;

	static String gifURL;

	public ShowGifView(Context context, String a) {
		super(context);
		this.context = context;
		gifURL = a;
		init(context);
	}

	public ShowGifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public ShowGifView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(final Context context) {
		setFocusable(true);

		gifMovie = null;
		movieWidth = 0;
		movieHeight = 0;
		movieDuration = 0;
		Loder task = new Loder();
		task.execute(new String[] { gifURL });

	}

	private static byte[] streamToBytes(InputStream is) {
		ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
		byte[] buffer = new byte[1024];
		int len;
		try {
			while ((len = is.read(buffer)) >= 0) {
				os.write(buffer, 0, len);
			}
		} catch (java.io.IOException e) {
		}
		return os.toByteArray();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(movieWidth, movieHeight);
	}

	public int getMovieWidth() {
		return movieWidth;
	}

	public int getMovieHeight() {
		return movieHeight;
	}

	public long getMovieDuration() {
		return movieDuration;
	}

	@Override
	protected void onDraw(Canvas canvas) {

		long now = android.os.SystemClock.uptimeMillis();
		if (mMovieStart == 0) { // first time
			mMovieStart = now;
		}

		if (gifMovie != null) {

			int dur = gifMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}

			int relTime = (int) ((now - mMovieStart) % dur);

			gifMovie.setTime(relTime);

			gifMovie.draw(canvas, 0, 0);
			invalidate();

		}

	}

	private class Loder extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			URL gifURL;
			File fi = new File(urls[0]);
//				try {
//				gifInputStream = getResources().openRawResource(R.raw.tumblr_la);
				try {
					gifInputStream = context.getAssets().open("progressbar.gif");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

			// Insert dummy sleep
			// to simulate network delay
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (DECODE_STREAM) {
				gifMovie = Movie.decodeStream(gifInputStream);
			} else {
				byte[] array = streamToBytes(gifInputStream);
				gifMovie = Movie.decodeByteArray(array, 0, array.length);
			}
			movieWidth = gifMovie.width();
			movieHeight = gifMovie.height();
			movieDuration = gifMovie.duration();
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			invalidate();
			requestLayout();

		}
	}

}