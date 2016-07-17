package com.example.draft1;

import java.util.Random;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.example.PHPConnections.PHPConnections;

/**
 * This class is used to hold methods used by many different classes
 * 
 * @author Gary
 */
public class CommonFunctions {
	public static final int NOTIMESHASHUSERNAME = 500;
	public static final int NOOFFIRSTHASHES = 250;
	public static final int NOOFSECONDHASHES = 250;
	public static final int AUTHENTICATIONCODEHASHES = 250;
	public static final int CHECKJOBINTERVALSSECONDS = 5;
	public static final long INTERVALBEFORESPEAK = 1500;
	private static final int RANDOMNUMBERSUBTRACTED = 100000000;

	/**
	 * A toast is displayed to the user.
	 * 
	 * @param context
	 *            The class to show the toast on
	 * @param message
	 *            The message to display
	 * @param duration
	 *            The length of the message
	 * @param position
	 *            The postion of the message
	 */
	static void toastScreenCentre(Context context, String message,
			int duration, int position) {
		Toast toast = Toast.makeText(context, message, duration);
		toast.setGravity(position, 0, 0);
		toast.show();
	}

	/**
	 * Method returns a unique authentication code, using a hash of the phone
	 * id, hashed with the username and a random int to increase security. The
	 * hashing is performed twice
	 * 
	 * @param hashedUsername
	 *            The user logging in id
	 * @param deviceId
	 *            A unique ID for the phone
	 * @param noOfHashes
	 *            The number of times to hash. This is performed twice
	 * @return
	 */
	static String saltAndHashAuthenticationCode(String hashedUsername,
			String deviceId, int noOfHashes) {
		String authenticationCode = deviceId + hashedUsername;
		authenticationCode = PHPConnections.useSHA1(authenticationCode,
				noOfHashes);
		Random rand = new Random();
		authenticationCode = authenticationCode
				+ (System.currentTimeMillis() - rand
						.nextInt(RANDOMNUMBERSUBTRACTED));
		authenticationCode = PHPConnections.useSHA1(authenticationCode,
				noOfHashes);
		return authenticationCode;
	}

	static Bundle dummyDataWaitScreen(String hashedUsernameFromBasket,
			String authenticationCode) {
		String name = "Gary Carr";
		String address = "16 Glenhurst Drive, NE5 1SP";
		String category = "R2";
		String distance = "Unknown Distance";
		String contactNumber = "07802 111 333";
		String details = "Individual has had a fall at his home and is not responding. He has a history of heart conditions, and had a heart attack in 2008.  Possible cardiac arrest."
				+ "\n"
				+ "\n"
				+ "His wife is at the home and has been asked to collect his medicines.";

		String endLat = "55.0004723";
		String endLng = "-1.7084701";
		String firstDirection = "Turn left to stay on Greystoke Gardens";

		Bundle sendBasket = new Bundle();
		sendBasket.putString("name", name);
		sendBasket.putString("category", category);
		sendBasket.putString("address", address);
		sendBasket.putString("details", details);
		sendBasket.putString("contactNumber", contactNumber);
		sendBasket.putString("distance", distance);
		sendBasket.putString("dummyKey", "dummyKey");
		sendBasket.putString("details", details);
		sendBasket.putString("endLat", endLat);
		sendBasket.putString("endLng", endLng);
		sendBasket.putString("firstDirection", firstDirection);
		sendBasket.putString("authenticationCode", authenticationCode);
		sendBasket.putString("hashedUserName", hashedUsernameFromBasket);
		return sendBasket;

	}

	/**
	 * It is tested if an active internet connection can be obtained
	 * 
	 * @param context
	 *            The activity checking the connection
	 * @return True if there is an internet connection
	 */
	public static boolean testConnection(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	/**
	 * When the user presses back if they should be using the on screen button a
	 * dialog is displayed
	 * 
	 * @param screenContext
	 *            The activity to display the screen on
	 */
	static void backButtonPressedLogOut(Context screenContext) {
		final Context context = screenContext;
		final Dialog dialogClose = new Dialog(context);
		dialogClose.setContentView(R.layout.dialogbackpressed);
		dialogClose.setTitle("Please use the on screen button to log out");
		final Button bWaitScreenDismiss;
		bWaitScreenDismiss = (Button) dialogClose
				.findViewById(R.id.bWaitScreenDismiss);
		bWaitScreenDismiss.setVisibility(View.VISIBLE);
		bWaitScreenDismiss.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {

				dialogClose.dismiss();
				bWaitScreenDismiss.setVisibility(View.GONE);
			}
		});
		dialogClose.show();
	}

	/**
	 * When the user presses back if they are picking up a patient a dialog is
	 * displayed
	 * 
	 * @param screenContext
	 *            The activity to display the screen on
	 */
	static void backButtonPressedCannotLogOut(Context screenContext) {
		final Context context = screenContext;
		final Dialog dialogClose = new Dialog(context);
		dialogClose.setContentView(R.layout.dialogbackpressed);
		dialogClose.setTitle("Cannot log out whilst transporting patient");
		final Button bWaitScreenDismiss;
		bWaitScreenDismiss = (Button) dialogClose
				.findViewById(R.id.bWaitScreenDismiss);
		bWaitScreenDismiss.setVisibility(View.VISIBLE);
		bWaitScreenDismiss.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {

				dialogClose.dismiss();
				bWaitScreenDismiss.setVisibility(View.GONE);
			}
		});
		dialogClose.show();
	}

}
