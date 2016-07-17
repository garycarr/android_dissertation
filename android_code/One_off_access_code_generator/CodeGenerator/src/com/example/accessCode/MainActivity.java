package com.example.accessCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codegenerator.R;

/**
 * Activity is used to generate a one off access code to enter the ambulance
 * application. The code is stored in a dispatcher database
 * 
 * @author Gary Carr
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button bGenerate, bExit;
	private String deviceId;
	private TextView etCode, tvAccessCode, tvUserName;
	private EditText etUserName;
	private static String URL = "http://homepages.cs.ncl.ac.uk/g.carr/Website/generateSecureCode.php";
	private ProgressDialog progress;
	private Button bYes, bNo;

	// private boolean authenticUser;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initializeVars();

	}

	/**
	 * Assigns variables to the xml ID
	 */
	void initializeVars() {
		bGenerate = (Button) findViewById(R.id.bGenerate);
		bExit = (Button) findViewById(R.id.bExit);
		etCode = (TextView) findViewById(R.id.etCode);
		tvAccessCode = (TextView) findViewById(R.id.tvAccessCode);
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		etUserName = (EditText) findViewById(R.id.etUserName);

		etCode.setVisibility(View.GONE);
		tvAccessCode.setVisibility(View.GONE);
		bGenerate.setOnClickListener(this);
		bExit.setOnClickListener(this);
		progress = new ProgressDialog(this);
		progress.setTitle("Connecting");
		progress.setMessage("Checking username");
		getDeviceId();
	}

	//
	/**
	 * A unique identification of the phone is created and hashed
	 * 
	 * Obtained from
	 * http://stackoverflow.com/questions/2785485/is-there-a-unique
	 * -android-device-id
	 * 
	 * @return A hashed unique identification for the phone
	 */
	void getDeviceId() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);

		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);

		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		deviceId = deviceUuid.toString();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bGenerate):
			bGenerate.setEnabled(false);
			if (etUserName.getText().toString().equals("")) {
				toastMessage("No username entered", Toast.LENGTH_SHORT);
				bGenerate.setEnabled(true);
			} else if (testConnection(MainActivity.this)) {
				new GetCode().execute(deviceId);
			} else {
				toastMessage("No internet connection", Toast.LENGTH_SHORT);
				bGenerate.setEnabled(true);
			}
			break;
		case (R.id.bExit):
			finish();
			break;

		default:
			break;
		}

	}

	/**
	 * Messages are displayed to the user if an error is made
	 * 
	 * @param message
	 *            The message to be displayed
	 * @param duration
	 *            The time the message is on the screen
	 */
	private void toastMessage(String message, int duration) {
		Context context = getApplicationContext();

		Toast toast = Toast.makeText(context, message, duration);
		toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
	}

	@Override
	public void onBackPressed() {
		final Dialog dialogClose = new Dialog(this);
		dialogClose.setContentView(R.layout.dialog);
		dialogClose.setTitle("Log out. Are you sure");

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialogClose.findViewById(R.id.text);
		text.setText("Log out. Are you sure?");

		bYes = (Button) dialogClose.findViewById(R.id.bYes);
		// if button is clicked, close the custom dialog
		bYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogClose.dismiss();
				finish();
			}
		});
		bNo = (Button) dialogClose.findViewById(R.id.bNo);
		bNo.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {
				dialogClose.dismiss();
			}
		});
		dialogClose.show();

	}

	void displayCopy(final String result) {
		final Dialog dialog = new Dialog(MainActivity.this);
		dialog.setContentView(R.layout.dialog);
		dialog.setTitle("Access code - " + result);

		// set the custom dialog components - text, image and button
		TextView text = (TextView) dialog.findViewById(R.id.text);
		text.setText("  Copy to clipboard?");

		Button bYes = (Button) dialog.findViewById(R.id.bYes);
		// if button is clicked, close the custom dialog
		bYes.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("label", "ACCESSCODE-"
						+ result);
				clipboard.setPrimaryClip(clip);
				dialog.dismiss();
			}
		});
		bNo = (Button) dialog.findViewById(R.id.bNo);
		bNo.setOnClickListener(new OnClickListener() {
			// @Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		dialog.show();

		etCode.setText(result);
		etCode.setVisibility(View.VISIBLE);
		tvAccessCode.setVisibility(View.VISIBLE);
		tvUserName.setVisibility(View.GONE);
		etUserName.setVisibility(View.GONE);
		bGenerate.setVisibility(View.GONE);

	}

	/**
	 * Called by the ASyncTask to show the progress bar
	 */
	void showProgress() {
		progress.show();
	}

	/**
	 * Called by the ASyncTask to turn button back on
	 */
	void turnOnButton() {
		bGenerate.setEnabled(true);
	}

	void dismissProgress() {
		progress.dismiss();
	}

	/**
	 * Inner class of main activity. Connects to the web and verifies a correct
	 * username has been given. If so returns a access code
	 * 
	 * @author Gary Carr
	 * 
	 */
	class GetCode extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			showProgress();
		}

		@Override
		protected String doInBackground(String... params) {
			String inputLine = null;
			// TODO Auto-generated method stub
			BufferedReader in = null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL);
			String hashedUsername = params[0];
			for (int i = 0; i < 500; i++) {
				hashedUsername = sha1(hashedUsername);
			}

			// Request parameters and other properties.
			List<NameValuePair> userDetails = new ArrayList<NameValuePair>(2);
			userDetails.add(new BasicNameValuePair("driverID", hashedUsername));
			userDetails.add(new BasicNameValuePair("deviceID", params[0]));
			try {
				httppost.setEntity(new UrlEncodedFormEntity(userDetails,
						"UTF-8"));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				in = new BufferedReader(new InputStreamReader(
						entity.getContent(), "US-ASCII"));
				inputLine = in.readLine();
				in.close();
			} catch (MalformedURLException e1) {
				// Error message code FJ1
				e1.printStackTrace();
				return "-3";
			} catch (IOException e) {
				// Error message code FJ2
				e.printStackTrace();
				return "-4";
			} catch (NullPointerException e) {
				// Error message code FJ3
				e.printStackTrace();
				return "-5";
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return inputLine;
		}

		@Override
		protected void onPostExecute(final String result) {
			dismissProgress();
			if (result.equals("0")) {
				toastMessage("Username not found", Toast.LENGTH_LONG);
				turnOnButton();
			} else {
				displayCopy(result);

			}
		}
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

		NetworkInfo wifiNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetwork != null && wifiNetwork.isConnected()) {
			return true;
		}

		NetworkInfo mobileNetwork = cm
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (mobileNetwork != null && mobileNetwork.isConnected()) {
			return true;
		}

		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) {
			return true;
		}

		return false;
	}

	/**
	 * Code obtained from http://www.sha1-online.com/sha1-java/
	 * 
	 * A string is converted into bytes and hashed using SHA1, an irreversable
	 * process
	 * 
	 * @param input
	 *            The string to be hashed
	 * @return The hashed string
	 */
	private static String sha1(String input) {
		MessageDigest mDigest;
		StringBuffer sb = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes(Charset
					.forName("UTF-8")));
			sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			// Exception will not be thrown as long as MessageDigest.getInstance
			// inputs "SHA1"
			sb = new StringBuffer();
			sb.append("Unknown Error");
			e.printStackTrace();
		}

		return sb.toString();
	}
}
