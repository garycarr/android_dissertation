package com.example.draft1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONObject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class GoingToPatientMap extends FragmentActivity implements
		OnClickListener {

	private GoogleMap map;
	private LatLng origin;
	private TextToSpeech tts;
	// private String firstDirection;
	private Button back;
	private Bundle gotbasket;
	private SupportMapFragment fm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.map);
		initializeVars();

		if (GPSReporter.getLng() == 0 || GPSReporter.getLat() == 0) {
			// currentLocationUnknown
			currentLocationUnknown();

		} else {
			// current Location Known
			currentLocationKnown();
		}
		tts = new TextToSpeech(GoingToPatientMap.this,
				new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int status) {
						// TODO Auto-generated method stub
						if (status != TextToSpeech.ERROR) {
							tts.setLanguage(Locale.UK);
						}
					}
				});

		new VoiceReporter().execute();

	}

	private void initializeVars() {
		fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map);
		map = fm.getMap();
		back = (Button) findViewById(R.id.bBack);
		back.setOnClickListener(this);
		gotbasket = getIntent().getExtras();
		back = (Button) findViewById(R.id.bBack);
		back.setOnClickListener(this);
	}

	private void currentLocationKnown() {
		// Getting Map for the SupportMapFragment
		origin = new LatLng(GPSReporter.getLat(), GPSReporter.getLng());
		LatLng dest = new LatLng(Double.parseDouble(gotbasket
				.getString("endLat")), Double.parseDouble(gotbasket
				.getString("endLng")));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 15));
		// Setting onclick event listener for the map

		// Enable MyLocation Button in the Map
		map.setMyLocationEnabled(true);

		// Getting URL to the Google Directions API
		String url = getDirectionsUrl(origin, dest);

		DownloadTask downloadTask = new DownloadTask();

		// Start downloading json data from Google Directions
		// API
		downloadTask.execute(url);

		map.addMarker(new MarkerOptions().position(
				new LatLng(Double.parseDouble(gotbasket.getString("endLat")),
						Double.parseDouble(gotbasket.getString("endLng"))))
				.title("Destination"));
	}

	private void currentLocationUnknown() {
		// TODO Auto-generated method stub
		LatLng dest = new LatLng(Double.parseDouble(gotbasket
				.getString("endLat")), Double.parseDouble(gotbasket
				.getString("endLng")));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(dest, 10));
		// Prepare new LayoutParams object that centers on our new

		map.addMarker(new MarkerOptions().position(
				new LatLng(Double.parseDouble(gotbasket.getString("endLat")),
						Double.parseDouble(gotbasket.getString("endLng"))))
				.title("Destination"));

	}

	private String getDirectionsUrl(LatLng origin, LatLng dest) {

		// Origin of route
		String str_origin = "origin=" + origin.latitude + ","
				+ origin.longitude;

		// Destination of route
		String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

		// Sensor enabled
		String sensor = "sensor=false";

		// Building the parameters to the web service
		String parameters = str_origin + "&" + str_dest + "&" + sensor;

		// Output format
		String output = "json";

		// Building the url to the web service
		String url = "https://maps.googleapis.com/maps/api/directions/"
				+ output + "?" + parameters;

		return url;
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		BufferedReader br = null;
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;

		URL url = new URL(strUrl);

		// Creating an http connection to communicate with url
		urlConnection = (HttpURLConnection) url.openConnection();

		// Connecting to url
		urlConnection.connect();

		// Reading data from url
		iStream = urlConnection.getInputStream();
		br = new BufferedReader(new InputStreamReader(iStream));

		StringBuffer sb = new StringBuffer();

		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}

		data = sb.toString();
		br.close();

		return data;
	}

	// Fetches data from url passed
	private class DownloadTask extends AsyncTask<String, Void, String> {

		// Downloading data in non-ui thread
		@Override
		protected String doInBackground(String... url) {

			// For storing data from web service
			String data = "";

			try {
				// Fetching the data from web service
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executes in UI thread, after the execution of
		// doInBackground()
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			ParserTask parserTask = new ParserTask();

			// Invokes the thread for parsing the JSON data
			parserTask.execute(result);

		}
	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

		// Parsing the data in non-ui thread
		@Override
		protected List<List<HashMap<String, String>>> doInBackground(
				String... jsonData) {

			JSONObject jObject;
			List<List<HashMap<String, String>>> routes = null;

			try {
				jObject = new JSONObject(jsonData[0]);
				DirectionsJSONParser parser = new DirectionsJSONParser();

				// Starts parsing data
				routes = parser.parse(jObject);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return routes;
		}

		// Executes in UI thread, after the parsing process
		@Override
		protected void onPostExecute(List<List<HashMap<String, String>>> result) {
			ArrayList<LatLng> points = null;
			PolylineOptions lineOptions = null;
			// MarkerOptions markerOptions = new MarkerOptions();

			// Traversing through all the routes
			for (int i = 0; i < result.size(); i++) {
				points = new ArrayList<LatLng>();
				lineOptions = new PolylineOptions();

				// Fetching i-th route
				List<HashMap<String, String>> path = result.get(i);

				// Fetching all the points in i-th route
				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}

				// Adding all the points in the route to LineOptions
				lineOptions.addAll(points);
				lineOptions.width(2);
				lineOptions.color(Color.RED);

			}

			// Drawing polyline in the Google Map for the i-th route
			map.addPolyline(lineOptions);
		}
	}

	@Override
	public void onClick(View v) {
		finish();

	}

	@Override
	public void onBackPressed() {
	}

	public class VoiceReporter extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			announceDirection();
			return null;
		}

	}

	public void announceDirection() {
		tts.speak(gotbasket.getString("firstDirection"),
				TextToSpeech.QUEUE_FLUSH, null);

	}
}
