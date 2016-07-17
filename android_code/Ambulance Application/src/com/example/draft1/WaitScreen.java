package com.example.draft1;

import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity is running when a user is waiting to receive a new job. On
 * initialization threads are launched to check for jobs, and report the GPS
 * location
 * 
 * @author Gary
 */
public final class WaitScreen extends Activity implements OnClickListener {

	private Button logOut, map, dummyData;
	private TextView message, updater;
	private final String[] updates = new String[] {
			"Last year NEAS saved xxx patients",
			"Average response time is x minutes", "Pay day this Friday",
			"Happy birthday to Martha" };
	private int messageDisplayer = 0;
	private String basketHashUsername;
	private String basketAuthenticationCode;
	final MyHandler mHandler = new MyHandler(this);
	private Bundle gotBasket, sendBasket;

	private ScheduledExecutorService exec = Executors
			.newSingleThreadScheduledExecutor();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waitscreen);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initializeVar();
		launchBackgroundThreads();
	}

	private void launchBackgroundThreads() {
		exec = Executors.newSingleThreadScheduledExecutor();
		exec.scheduleWithFixedDelay(new RunCheckers(mHandler, WaitScreen.this),
				0, CommonFunctions.CHECKJOBINTERVALSSECONDS, TimeUnit.SECONDS);
		Intent msgIntent = new Intent(WaitScreen.this, GPSReporter.class);
		startService(msgIntent);

	}

	/**
	 * Updates the message displayed on the wait screen
	 * 
	 * @param response
	 *            - The result of the PHP request
	 */
	private void changeMessage(String response) {

		if (response.equals("-1")) {
			// No internet connection
			updater.setText("Trying to reconnect to the internet");
		} else if (response.equals("-3")) {
			// MalformedURlException
			updater.setText("Unable to connect, if error persists please contact the administrator.  Error JF1 ");
		} else if (response.equals("-4")) {
			// IOexception
			updater.setText("Trying to reconnect to the internet. Error JF2");
		} else if (response.equals("-5")) {
			// Null pointer exception
			updater.setText("Unable to connect.  Error JF3");
		} else {
			// No job found. Text on screen is updated
			updater.setText(updates[messageDisplayer]);
			if (messageDisplayer == 3) {
				messageDisplayer = 0;
			} else {
				messageDisplayer++;
			}
		}

	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVar() {
		gotBasket = getIntent().getExtras();
		basketAuthenticationCode = gotBasket.getString("authenticationCode");
		basketHashUsername = gotBasket.getString("hashedUserName");

		logOut = (Button) findViewById(R.id.bLogOut);
		message = (TextView) findViewById(R.id.tvWaitScreenMessage);
		logOut.setOnClickListener(this);
		message.setText("Waiting for a new job");
		map = (Button) findViewById(R.id.bWaitMap);
		map.setOnClickListener(this);
		dummyData = (Button) findViewById(R.id.bGiveDummyData);
		dummyData.setOnClickListener(this);

		updater = (TextView) findViewById(R.id.tvProgressUpdate);

	}

	/**
	 * When a job is found the details are added to a bundle and the JobFound
	 * class opened. RunChecker is told to stop
	 * 
	 * @param incidentDetails
	 */
	private void jobFound(String incidentDetails) {
		Intent realIntent = new Intent(WaitScreen.this, JobFound.class);
		RunCheckers.changeWaitingForJob(false);

		try {
			realIntent.putExtras(putInRealBasket(incidentDetails));
		} catch (JSONException e) {
			sendBasket = new Bundle();
			sendBasket.putString("NotFound", "NotFound");
		}
		startActivity(realIntent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bLogOut):
			// User is logging out

			exec.shutdownNow();
			while (!exec.isTerminated()) {

			}
			GPSReporter.killThread();
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			break;
		case (R.id.bWaitMap):
			// User is viewing the map

			final Intent intent = new Intent(WaitScreen.this,
					WaitScreenMap.class);
			final Bundle basket = new Bundle();
			basket.putString("authenticationCode", basketAuthenticationCode);
			basket.putString("hashedUserName", basketHashUsername);
			intent.putExtras(basket);
			startActivity(intent);
			break;
		case (R.id.bGiveDummyData):
			RunCheckers.changeWaitingForJob(false);
			final Intent dummyIntent = new Intent(WaitScreen.this,
					JobFound.class);
			dummyIntent.putExtras(CommonFunctions.dummyDataWaitScreen(
					basketHashUsername, basketAuthenticationCode));
			startActivity(dummyIntent);
			break;
		default:
			// Nothing
			break;
		}

	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedLogOut(this);
	}

	// Application is shut down as the administrator has disabled the device
	void killApplication() {
		Intent shutdown = new Intent(WaitScreen.this, ShutDown.class);
		startActivity(shutdown);
		finish();
	}

	/**
	 * Bundle is populated with the results of the JSON object
	 * 
	 * @param result
	 *            The JSON object obtained from the web
	 * @return A populated bundle
	 * @throws JSONException
	 */
	private Bundle putInRealBasket(String result) throws JSONException {
		final JSONObject jsonObject = new JSONObject(result);
		sendBasket = new Bundle();
		sendBasket.putString("name", jsonObject.getString("name"));
		sendBasket.putString("category", jsonObject.getString("category"));
		sendBasket.putString(
				"address",
				jsonObject.getString("hNumber")
						+ ", "
						+ jsonObject.getString("Post_Code").toUpperCase(
								Locale.getDefault()));
		sendBasket.putString("details", jsonObject.getString("Details"));
		sendBasket.putString("contactNumber",
				jsonObject.getString("Contact_number"));
		sendBasket.putString("distance", jsonObject.getString("Distance"));
		sendBasket.putString("endLat", jsonObject.getString("endLat"));
		sendBasket.putString("endLng", jsonObject.getString("endLng"));
		sendBasket.putString("firstDirection",
				jsonObject.getString("firstDirection"));
		sendBasket.putString("authenticationCode", basketAuthenticationCode);
		sendBasket.putString("hashedUserName", basketHashUsername);
		return sendBasket;
	}

	/**
	 * Inner class of the WaitScreen thread. Used as a handler to receive
	 * messages from the RunChecker class
	 * 
	 * @author Gary Carr
	 * 
	 */
	public static class MyHandler extends Handler {
		private final WeakReference<WaitScreen> mActivity;

		MyHandler(WaitScreen activity) {
			mActivity = new WeakReference<WaitScreen>(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			final WaitScreen activity = mActivity.get();
			if (activity != null) {
				String result = (String) msg.obj;

				if (result.equals("0")) {
					// Job not found
					activity.changeMessage("0");
				} else if (result.equals("-1") || result.equals("-2")
						|| result.equals("-3") || result.equals("-4")
						|| result.equals("-5")) {
					// Connection errors
					activity.changeMessage(result);
				} else if (result.equals("-10")) {
					// Job found
				} else {

					activity.jobFound(result);
				}
			}

		}
	}

}
