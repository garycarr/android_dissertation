package com.example.draft1;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * The class displays a Google Map of the current location of the user
 * 
 * @author Gary
 * 
 */
public final class WaitScreenMap extends FragmentActivity implements
		OnClickListener {

	private SupportMapFragment fm;
	private GoogleMap map;
	private Button back;
	private String authenticationCode, hashedUsername;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.map);
		initializeVar();
		focusMap();
	}

	/**
	 * Variables are initialized again an XML id
	 */
	void initializeVar() {
		Bundle gotBasket = getIntent().getExtras();
		authenticationCode = gotBasket.getString("authenticationCode");
		hashedUsername = gotBasket.getString("hashedUserName");
		fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(
				R.id.map);
		map = fm.getMap();
		back = (Button) findViewById(R.id.bBack);
		back.setOnClickListener(this);
		map.setMyLocationEnabled(true);
	}

	/**
	 * Centres the map on the current location taking the location from the
	 * GPSReporter
	 */
	void focusMap() {
		if (GPSReporter.getLng() == 0 || GPSReporter.getLat() == 0) {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					-1.5930112, 54.9846982), 10));
		} else {
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
					GPSReporter.getLat(), GPSReporter.getLng()), 15));
		}
	}

	@Override
	public void onClick(View v) {
		finish();
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}
}
