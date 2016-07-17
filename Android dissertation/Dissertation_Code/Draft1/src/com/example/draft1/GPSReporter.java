package com.example.draft1;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;

import com.example.PHPConnections.PHPConnections;

/**
 * GPSReporter will continously report the location of the phone to the
 * dispatchers server until the application is terminated. It will also notify
 * RunChecker when internet connectivity has been re-established
 * 
 * @author Gary
 * 
 */
public class GPSReporter extends IntentService {
	private static double curlat;
	private static double curlong;
	private static boolean stop = false;
	private int REPORTGPSINTERVAL = 5000;
	private MediaPlayer ourSong;
	private int voiceNoConnection = 0;

	/**
	 * GPSReporter will continously report the location of the phone to the
	 * dispatchers server until the application is terminated
	 */
	public GPSReporter() {
		super("SimpleIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		while (!stop) {
			LocationManager locationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			criteria.setAccuracy(Criteria.ACCURACY_FINE);

			// Listening for the location
			LocationListener loc_listener = new LocationListener() {

				@Override
				public void onStatusChanged(String provider, int status,
						Bundle extras) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onProviderEnabled(String provider) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onProviderDisabled(String provider) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLocationChanged(Location location) {
					curlat = location.getLatitude();
					curlong = location.getLongitude();
				}

			};

			// Requesting a location update
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, loc_listener);
			Location location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			// Sets the new location if not null
			if (location != null) {
				setLat(location.getLatitude());
				setLng(location.getLongitude());

			}

			// Tests if there is internet connection then makes the request to
			// put the co-ordinates in the database. If there is a job for
			// RunChecker to perform it is notified
			if (CommonFunctions.testConnection(this)) {
				voiceNoConnection = 0;
				PHPConnections.reportJob("" + curlat, "" + curlong);
				if (RunCheckers.checkIfWakeUpThread()) {
					RunCheckers.getConnectionsleeper().jobNeededStart();
				}
				REPORTGPSINTERVAL = 30000;
			} else if (voiceNoConnection != 4) {
				// If there is no internet connection the while loop time is
				// reduced so that it is known faster when connection is
				// re-established
				voiceNoConnection++;
				REPORTGPSINTERVAL = 5000;
			} else {
				// If there has been no connection for 4 cycles a sound is
				// played to notify the user of no connection
				ourSong = MediaPlayer.create(GPSReporter.this,
						R.raw.nointernetconnection);
				ourSong.start();
				voiceNoConnection = 0;
			}
			SystemClock.sleep(REPORTGPSINTERVAL);
		}
	}

	/**
	 * Stops the thread on shut down
	 */
	public static void killThread() {
		stop = true;
	}

	/**
	 * @return - The current latitude
	 */
	public static double getLat() {
		return curlat;
	}

	/**
	 * @return - The current longitude
	 */
	public static double getLng() {
		return curlong;
	}

	/**
	 * Sets the current latitude
	 * 
	 * @param lat
	 */
	public static void setLat(double lat) {
		curlat = lat;
	}

	/**
	 * Sets the current longitude
	 * 
	 * @param lng
	 */
	public static void setLng(double lng) {
		curlong = lng;
	}

	/**
	 * Sets the interval for sleeping the while loop
	 * 
	 * @param interval
	 */
	public void setREPORTGPSINTERVAL(int interval) {
		REPORTGPSINTERVAL = interval;
	}

}
