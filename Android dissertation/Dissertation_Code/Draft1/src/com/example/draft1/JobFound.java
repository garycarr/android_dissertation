package com.example.draft1;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

/**
 * This class notifies the user incident details have been obtained
 * 
 * @author Gary
 */
public final class JobFound extends Activity implements OnClickListener {

	private Button accept;
	private TextView address, distance, category;
	private MediaPlayer ourSong;
	private Bundle gotBasket, sendBasket;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.job_found);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		putInBasket();
		initializeVar();
	}

	/**
	 * Variables are initialized against XML ids. The incident details are
	 * displayed to the screen
	 */
	private void initializeVar() {
		accept = (Button) findViewById(R.id.bWaitScreenAccept);
		address = (TextView) findViewById(R.id.tvAddress);
		distance = (TextView) findViewById(R.id.tvDistance);
		category = (TextView) findViewById(R.id.tvCategory);
		accept.setOnClickListener(this);
		address.setText(gotBasket.getString("address"));
		category.setText(gotBasket.getString("category"));
		distance.setText(gotBasket.getString("distance") + " miles");

		// Sound plays to notify user of job
		ourSong = MediaPlayer.create(JobFound.this, R.raw.jobreceived);
		ourSong.start();

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bWaitScreenAccept):
			// User acknowlodges the job

			accept.setEnabled(false);
			final Intent goingToPatient = new Intent(JobFound.this,
					GoingToPatient.class);
			goingToPatient.putExtras(sendBasket);
			RunCheckers.changeDeleteFirstIncident(true);
			ourSong.release();
			startActivity(goingToPatient);
			finish();
			break;
		default:
			break;
		}
	}

	/**
	 * Transfers a bundle to pass to following screen
	 */
	private void putInBasket() {
		gotBasket = getIntent().getExtras();
		sendBasket = new Bundle();

		sendBasket.putAll(gotBasket);
		sendBasket.putLong("startTime", System.currentTimeMillis());
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}
}
