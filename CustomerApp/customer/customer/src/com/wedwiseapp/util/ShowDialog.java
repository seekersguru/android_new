package com.wedwiseapp.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Window;

public class ShowDialog {

	public static void displayDialog(Context mContext,String title,String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
		alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
		    new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		            dialog.dismiss();
		        }
		    });
		alertDialog.show();
	}
}
