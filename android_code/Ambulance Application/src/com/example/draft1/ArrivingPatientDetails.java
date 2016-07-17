package com.example.draft1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * Class populates the layout with incident and summary of care details.
 * Accessed after a user has picked up the patient
 * 
 * @author Gary Carr
 * 
 */
public final class ArrivingPatientDetails extends Activity implements
		OnClickListener {

	private TextView firstFillName, firstFillCategory, firstFillAddress,
			firstFillDetails, firstFillContactNumber;

	private TextView secondLabelAllergies, secondLabelWeight, secondLabelID,
			secondLabelMHistory, secondLabelBloodType, secondLabelDOB,
			secondFillWeight, secondFillMHistory, secondFillID,
			secondFillBloodType, secondFillDOB, secondFillAllergies,
			bSecondKnownMedicines;

	private TabHost tabhost;
	private Button arrived, map;
	private Bundle gotBasket, sendBasket;
	private Button bAllergies;

	@Override
	protected void onCreate(Bundle patientDetails) {
		super.onCreate(patientDetails);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.userdetails2);
		gotBasket = getIntent().getExtras();
		putInBasket();
		initializeTabs();
		initializeTextViews();

		if (gotBasket.getBoolean("secondJobFound")) {
			populateSecondStageFound();
		} else {
			populateSecondStageNotFound();
		}

	}

	/**
	 * Initialized if no summary of care details are located
	 */
	private void populateSecondStageNotFound() {
		firstFillName.setText(gotBasket.getString("name"));
		firstFillCategory.setText(gotBasket.getString("category"));
		firstFillAddress.setText(gotBasket.getString("address"));
		firstFillDetails.setText(gotBasket.getString("details"));
		firstFillContactNumber.setText(gotBasket.getString("contactNumber"));
		tabhost.getTabWidget()
				.getChildAt(1)
				.setBackgroundColor(
						getResources().getColor(android.R.color.holo_red_dark));
		secondLabelAllergies.setVisibility(View.GONE);
		secondLabelWeight.setVisibility(View.GONE);
		secondLabelMHistory.setVisibility(View.GONE);
		secondLabelID.setText("No information found");
		secondLabelDOB.setVisibility(View.GONE);
		secondLabelBloodType.setVisibility(View.GONE);
		bAllergies.setVisibility(View.GONE);
		bSecondKnownMedicines.setVisibility(View.GONE);

	}

	/**
	 * Initializes the tab views
	 */
	void initializeTabs() {
		tabhost = (TabHost) findViewById(R.id.tabhost);
		tabhost.setup();
		TabSpec specs = tabhost.newTabSpec("tag1");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Job Details");
		tabhost.addTab(specs);
		specs = tabhost.newTabSpec("tag2");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Summary of Care");
		tabhost.addTab(specs);
	}

	/**
	 * Initializes the classes text views
	 */
	void initializeTextViews() {
		bAllergies = (Button) findViewById(R.id.bSecondButtonAllergies);
		bAllergies.setOnClickListener(this);
		bSecondKnownMedicines = (Button) findViewById(R.id.bSecondButtonKnownMedicine);
		bSecondKnownMedicines.setOnClickListener(this);

		secondLabelAllergies = (TextView) findViewById(R.id.tvPDSecondLabelAllergies);
		secondLabelWeight = (TextView) findViewById(R.id.tvPDSecondLabelWeight);
		secondLabelMHistory = (TextView) findViewById(R.id.tvPDSecondLabelMHistory);
		secondLabelBloodType = (TextView) findViewById(R.id.tvPDSecondLabelBloodType);

		secondFillMHistory = (TextView) findViewById(R.id.tvPDSecondFillMHistory);
		secondFillID = (TextView) findViewById(R.id.tvPDSecondFillID);
		secondFillBloodType = (TextView) findViewById(R.id.tvPDSecondFillBloodType);
		secondFillDOB = (TextView) findViewById(R.id.tvPDSecondFillDOB);
		secondFillAllergies = (TextView) findViewById(R.id.tvPDSecondFillAllergies);

		secondLabelID = (TextView) findViewById(R.id.tvPDSecondLabelID);

		secondLabelDOB = (TextView) findViewById(R.id.tvPDSecondLabelDOB);

		firstFillName = (TextView) findViewById(R.id.tvPDFirstFillName);
		firstFillCategory = (TextView) findViewById(R.id.tvPDFirstFillCategory);
		firstFillAddress = (TextView) findViewById(R.id.tvPDFirstFillAddress);
		firstFillDetails = (TextView) findViewById(R.id.tvPDFirstFillDetails);
		firstFillContactNumber = (TextView) findViewById(R.id.tvPDFirstFillContactNumber);

		secondFillID = (TextView) findViewById(R.id.tvPDSecondFillID);
		secondFillWeight = (TextView) findViewById(R.id.tvPDSecondFillWeight);
		map = (Button) findViewById(R.id.bWorkingMap);
		map.setVisibility(View.GONE);
		arrived = (Button) findViewById(R.id.bArrived);
		arrived.setOnClickListener(this);
		arrived.setText("Back");
		bAllergies = (Button) findViewById(R.id.bSecondButtonAllergies);
		bAllergies.setOnClickListener(this);
	}

	/**
	 * Populates the textviews when a Summary of care JSON is found
	 */
	private void populateSecondStageFound() {

		// FirstStage
		firstFillName.setText(gotBasket.getString("name"));
		firstFillCategory.setText(gotBasket.getString("category"));
		firstFillAddress.setText(gotBasket.getString("address"));
		firstFillDetails.setText(gotBasket.getString("details"));
		firstFillContactNumber.setText(gotBasket.getString("contactNumber"));

		// SecondStage
		secondFillID.setText(gotBasket.getString("idPatientDetails"));
		secondFillBloodType.setText(gotBasket.getString("Blood_Type"));
		secondFillDOB.setText(gotBasket.getString("DOB"));
		secondFillWeight.setText(gotBasket.getString("weight"));
		secondFillAllergies.setText(gotBasket.getString("allergies"));

		secondFillMHistory.setText("• "
				+ gotBasket.getString("medicineTitleOne") + "\n •"
				+ gotBasket.getString("medicineTitleTwo") + "\n • "
				+ gotBasket.getString("medicineTitleThree"));

		secondFillAllergies.setText("• "
				+ gotBasket.getString("allergyTitleOne") + "\n •"
				+ gotBasket.getString("allergyTitleTwo") + "\n • "
				+ gotBasket.getString("allergyTitleThree"));
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case (R.id.bArrived):
			// User has arrived at incident
			Intent confirmIntent = new Intent(ArrivingPatientDetails.this,
					ArrivedMenu.class);
			putInBasket();
			confirmIntent.putExtras(sendBasket);
			startActivity(confirmIntent);
			finish();
			break;
		case (R.id.bSecondButtonAllergies):
			// User viewing more details on allergies
			Intent intentAllergies = new Intent(ArrivingPatientDetails.this,
					KnownAllergies.class);
			sendBasket.putAll(gotBasket);
			intentAllergies.putExtras(sendBasket);
			startActivity(intentAllergies);
			break;
		case (R.id.bSecondButtonKnownMedicine):
			// User viewing more details on medicines
			Intent intentMedicines = new Intent(ArrivingPatientDetails.this,
					KnownMedicines.class);
			sendBasket.putAll(gotBasket);
			intentMedicines.putExtras(sendBasket);
			startActivity(intentMedicines);
			break;
		default:
			break;
		}

	}

	/**
	 * Adding data to a bundle for the following screen
	 */
	private void putInBasket() {
		gotBasket = getIntent().getExtras();
		sendBasket = new Bundle();
		sendBasket.putAll(gotBasket);
	}

}
