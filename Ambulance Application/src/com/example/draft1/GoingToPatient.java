package com.example.draft1;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PHPConnections.PHPConnections;

/**
 * Class populates the layout with incident and summary of care details. On
 * initialization an AsyncTask is launched to try to obtain the summary of care
 * detaials
 * 
 * @author Gary Carr
 * 
 */
public final class GoingToPatient extends Activity implements OnClickListener {
	private TextView firstFillName, firstFillCategory, firstFillAddress,
			firstFillDetails, firstFillContactNumber;

	private TextView secondLabelAllergies, secondLabelSecondWeight,
			secondLabelID, secondLabelMHistory, secondLabelBloodType,
			secondLabelDOB, secondFillWeight, secondFillMHistory, secondFillID,
			secondFillBloodType, secondFillDOB, secondFillAllergies;

	private TabHost tabhost;
	private Button showMapTab, arrived, bSecondAllergies,
			bSecondKnownMedicines;
	private Bundle gotBasket, sendBasket;
	private long start, stop, result;

	// For dummy test

	@Override
	protected void onCreate(Bundle patientDetails) {
		super.onCreate(patientDetails);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.userdetails2);
		gotBasket = getIntent().getExtras();
		sendBasket = new Bundle();

		if (gotBasket.containsKey("dummyKey")) {
			initializeTabsDummy();
			initializeVarFirstStageDummy();
			initializeVarSecondStageDummy();
			populateFirstStageDummy();
			populateSecondStageSummy();
			sendBasket.putBoolean("secondJobFound", true);
		} else if (gotBasket.containsKey("NotFound")) {
			// If the parsing failed the screen will display no information and
			// no attempt will be made to get the summary of care details
			initializeTabs();
			initializeVarFirstStage();
			initializeVarSecondStage();
			populateFirstStageNoDetails();
		} else {
			initializeTabs();
			initializeVarFirstStage();
			initializeVarSecondStage();
			populateFirstStage();
			new GetSummaryOfCare().execute(
					gotBasket.getString("authenticationCode"),
					gotBasket.getString("hashedUserName"));
		}

	}

	/**
	 * Executed if the JSON parsing of the incident details fails
	 */
	private void populateFirstStageNoDetails() {
		firstFillName.setText("No information");
		firstFillCategory.setVisibility(View.GONE);
		firstFillAddress.setVisibility(View.GONE);
		firstFillContactNumber.setVisibility(View.GONE);
		firstFillDetails.setVisibility(View.GONE);
	}

	private void populateSecondStageSummy() {
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
		String medicineTitleOne = "Diazepam";
		String medicineTitleTwo = "Prozac";
		String medicineTitleThree = "Doxycycline";
		String medicineDetailsOne = "It is commonly used to treat anxiety, panic attacks, insomnia, seizures (including status epilepticus), muscle spasms (such as in tetanus cases), restless legs syndrome, alcohol withdrawal, benzodiazepine withdrawal, opiate withdrawal syndrome and Ménières disease.";
		String medicineDetailsTwo = "Prozac (fluoxetine) is a selective serotonin reuptake inhibitors (SSRI) antidepressant. Prozac affects chemicals in the brain that may become unbalanced and cause depression, panic, anxiety, or obsessive-compulsive symptoms.";
		String medicineDetailsThree = "Doxycycline is a member of the tetracycline antibiotics group";

		sendBasket.putString("medicineTitleOne", medicineTitleOne);
		sendBasket.putString("medicineTitleTwo", medicineTitleTwo);
		sendBasket.putString("medicineTitleThree", medicineTitleThree);
		sendBasket.putString("medicineDetailsOne", medicineDetailsOne);
		sendBasket.putString("medicineDetailsTwo", medicineDetailsTwo);
		sendBasket.putString("medicineDetailsThree", medicineDetailsThree);

		String allergyTitleOne = "Anaphylaxis";
		String allergyTitleTwo = "Hayfever";
		String allergyTitleThree = "Peanuts";
		String allergyDetailsOne = "Anaphylaxis - An allergen is a substance which can cause an allergic reaction. While food is one of the most common allergens, medicine, insect stings, latex and exercise can also cause a reaction. ";
		String allergyDetailsTwo = "Hayfever - Pollen is a fine powder released by plants as part of their reproductive cycle. Pollen contains proteins that can cause the nose, eyes, throat and sinuses (small air-filled cavities behind your cheekbones and forehead) to become swollen, irritated and inflamed..";
		String allergyDetailsThree = "Peanut allergy is a type of food allergy distinct from nut allergies. It is a type 1 hypersensitivity reaction to dietary substances from peanuts that causes an overreaction of the immune system which in a small percentage of people may lead to severe physical symptoms. It is estimated to affect 0.4-0.6% of the population of the United States.";

		sendBasket.putString("allergyTitleOne", allergyTitleOne);
		sendBasket.putString("allergyTitleTwo", allergyTitleTwo);
		sendBasket.putString("allergyTitleThree", allergyTitleThree);
		sendBasket.putString("allergyDetailsOne", allergyDetailsOne);
		sendBasket.putString("allergyDetailsTwo", allergyDetailsTwo);
		sendBasket.putString("allergyDetailsThree", allergyDetailsThree);

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
		sendBasket.putString("medicineTitleOne", medicineTitleOne);
		sendBasket.putString("medicineTitleTwo", medicineTitleTwo);
		sendBasket.putString("medicineTitleThree", medicineTitleThree);
		sendBasket.putString("medicineDetailsOne", medicineDetailsOne);
		sendBasket.putString("medicineDetailsTwo", medicineDetailsTwo);
		sendBasket.putString("medicineDetailsThree", medicineDetailsThree);

		String allergies1 = "• " + allergyTitleOne + "\n •" + allergyTitleTwo
				+ "\n • " + allergyTitleThree;
		String mHistory = "• " + medicineTitleOne + "\n •" + medicineTitleTwo
				+ "\n • " + medicineTitleThree;
		String weight = "90kg";
		String dob = "12/5/1986";
		String bloodType = "B";
		String id = "B2052238";

		secondFillID.setText(id);
		secondFillBloodType.setText(bloodType);
		secondFillDOB.setText(dob);
		secondFillWeight.setText(weight);

		secondFillAllergies.setText(allergies1);
		secondFillMHistory.setText(mHistory);

		sendBasket.putString("weight", weight);
		sendBasket.putString("allergies", allergies1);
		sendBasket.putString("medicalHistory", mHistory);
		sendBasket.putString("idPatientDetails", id);
		sendBasket.putString("Blood_Type", bloodType);
		sendBasket.putString("DOB", dob);
		sendBasket.putAll(gotBasket);

	}

	private void initializeTabsDummy() {
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

	private void initializeVarFirstStageDummy() {
		firstFillName = (TextView) findViewById(R.id.tvPDFirstFillName);
		firstFillCategory = (TextView) findViewById(R.id.tvPDFirstFillCategory);
		firstFillAddress = (TextView) findViewById(R.id.tvPDFirstFillAddress);
		firstFillContactNumber = (TextView) findViewById(R.id.tvPDFirstFillContactNumber);
		firstFillDetails = (TextView) findViewById(R.id.tvPDFirstFillDetails);
		showMapTab = (Button) findViewById(R.id.bWorkingMap);
		showMapTab.setOnClickListener(this);
		arrived = (Button) findViewById(R.id.bArrived);
		arrived.setOnClickListener(this);
		start = gotBasket.getLong("startTime");
	}

	private void initializeVarSecondStageDummy() {
		bSecondAllergies = (Button) findViewById(R.id.bSecondButtonAllergies);
		bSecondAllergies.setOnClickListener(this);
		bSecondKnownMedicines = (Button) findViewById(R.id.bSecondButtonKnownMedicine);
		bSecondKnownMedicines.setOnClickListener(this);

		secondFillID = (TextView) findViewById(R.id.tvPDSecondFillID);
		secondFillBloodType = (TextView) findViewById(R.id.tvPDSecondFillBloodType);
		secondFillDOB = (TextView) findViewById(R.id.tvPDSecondFillDOB);
		secondFillAllergies = (TextView) findViewById(R.id.tvPDSecondFillAllergies);
		secondFillWeight = (TextView) findViewById(R.id.tvPDSecondFillWeight);
		secondFillMHistory = (TextView) findViewById(R.id.tvPDSecondFillMHistory);

		secondLabelID = (TextView) findViewById(R.id.tvPDSecondLabelID);
		secondLabelBloodType = (TextView) findViewById(R.id.tvPDSecondLabelBloodType);
		secondLabelDOB = (TextView) findViewById(R.id.tvPDSecondLabelDOB);
		secondLabelAllergies = (TextView) findViewById(R.id.tvPDSecondLabelAllergies);
		secondLabelSecondWeight = (TextView) findViewById(R.id.tvPDSecondLabelWeight);
		secondLabelMHistory = (TextView) findViewById(R.id.tvPDSecondLabelMHistory);
	}

	private void populateFirstStageDummy() {
		firstFillName.setText(gotBasket.getString("name"));
		firstFillCategory.setText(gotBasket.getString("category"));
		firstFillAddress.setText(gotBasket.getString("address"));
		firstFillContactNumber.setText(gotBasket.getString("contactNumber"));
		firstFillDetails.setText(gotBasket.getString("details"));

	}

	/**
	 * Populates first tab text views
	 */
	private void populateFirstStage() {
		firstFillName.setText(gotBasket.getString("name"));
		firstFillCategory.setText(gotBasket.getString("category"));
		firstFillAddress.setText(gotBasket.getString("address"));
		firstFillContactNumber.setText(gotBasket.getString("contactNumber"));
		firstFillDetails.setText(gotBasket.getString("details"));
	}

	/**
	 * 
	 * Initializes tabs
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
	 * Initializes first tab text views
	 */
	void initializeVarFirstStage() {
		firstFillName = (TextView) findViewById(R.id.tvPDFirstFillName);
		firstFillCategory = (TextView) findViewById(R.id.tvPDFirstFillCategory);
		firstFillAddress = (TextView) findViewById(R.id.tvPDFirstFillAddress);
		firstFillContactNumber = (TextView) findViewById(R.id.tvPDFirstFillDetails);
		firstFillDetails = (TextView) findViewById(R.id.tvPDFirstFillContactNumber);
		showMapTab = (Button) findViewById(R.id.bWorkingMap);
		showMapTab.setOnClickListener(this);
		arrived = (Button) findViewById(R.id.bArrived);
		arrived.setOnClickListener(this);
		start = gotBasket.getLong("startTime");
	}

	/**
	 * Initializes second stage tabs
	 */
	void initializeVarSecondStage() {
		bSecondAllergies = (Button) findViewById(R.id.bSecondButtonAllergies);
		bSecondAllergies.setOnClickListener(this);

		bSecondKnownMedicines = (Button) findViewById(R.id.bSecondButtonKnownMedicine);
		bSecondKnownMedicines.setOnClickListener(this);

		secondFillID = (TextView) findViewById(R.id.tvPDSecondFillID);
		secondFillBloodType = (TextView) findViewById(R.id.tvPDSecondFillBloodType);
		secondFillDOB = (TextView) findViewById(R.id.tvPDSecondFillDOB);
		secondFillAllergies = (TextView) findViewById(R.id.tvPDSecondFillAllergies);
		secondFillWeight = (TextView) findViewById(R.id.tvPDSecondFillWeight);
		secondFillMHistory = (TextView) findViewById(R.id.tvPDSecondFillMHistory);

		secondLabelID = (TextView) findViewById(R.id.tvPDSecondLabelID);
		secondLabelBloodType = (TextView) findViewById(R.id.tvPDSecondLabelBloodType);
		secondLabelDOB = (TextView) findViewById(R.id.tvPDSecondLabelDOB);
		secondLabelAllergies = (TextView) findViewById(R.id.tvPDSecondLabelAllergies);
		secondLabelSecondWeight = (TextView) findViewById(R.id.tvPDSecondLabelWeight);
		secondLabelMHistory = (TextView) findViewById(R.id.tvPDSecondLabelMHistory);
		secondLabelID.setText("Trying to obtain data");
		secondLabelBloodType.setVisibility(View.GONE);
		secondLabelDOB.setVisibility(View.GONE);
		secondLabelAllergies.setVisibility(View.GONE);
		secondLabelSecondWeight.setVisibility(View.GONE);
		secondLabelMHistory.setVisibility(View.GONE);
		bSecondAllergies.setVisibility(View.GONE);
		bSecondKnownMedicines.setVisibility(View.GONE);
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case (R.id.bWorkingMap):
			// User has selected the map view

			Intent mapIntent = new Intent(this, GoingToPatientMap.class);
			sendBasket.putAll(gotBasket);
			mapIntent.putExtras(sendBasket);
			startActivity(mapIntent);
			break;
		case (R.id.bArrived):
			// User has arrived at patient

			Intent confirmIntent = new Intent(GoingToPatient.this,
					SelectHospital.class);
			stop = System.currentTimeMillis();
			timeTaken();

			// Requests summary of care details are to be deleted
			RunCheckers.changeDeleteSummaryOfCareDetails(true);
			sendBasket.putAll(gotBasket);
			confirmIntent.putExtras(sendBasket);
			startActivity(confirmIntent);
			finish();
			break;
		case (R.id.bSecondButtonAllergies):
			// User is viewing information on allergies

			Intent intentAllergies = new Intent(GoingToPatient.this,
					KnownAllergies.class);
			sendBasket.putAll(gotBasket);
			intentAllergies.putExtras(sendBasket);
			startActivity(intentAllergies);
			break;
		case (R.id.bSecondButtonKnownMedicine):
			// User is viewing information on medicines

			Intent intentMedicines = new Intent(GoingToPatient.this,
					KnownMedicines.class);
			sendBasket.putAll(gotBasket);
			intentMedicines.putExtras(sendBasket);
			startActivity(intentMedicines);
			break;
		default:
			// No code
			break;
		}
	}

	/**
	 * Calculates the time between the activity launching and finishing, to
	 * display time taken
	 */
	void timeTaken() {
		result = stop - start;
		int seconds = (int) result / 1000;
		int minutes;
		if (seconds > 60) {
			minutes = seconds / 60;
		} else {
			minutes = 0;
		}

		seconds = seconds % 60;
		CommonFunctions.toastScreenCentre(GoingToPatient.this, "Time taken - "
				+ String.format("%02d:%02d", minutes, seconds),
				Toast.LENGTH_LONG, Gravity.BOTTOM);
	}

	/**
	 * If a summary of care JSON object is found this method populates the
	 * second tab
	 * 
	 * @param result
	 *            JSON Object of Summary of Care Details
	 */
	void resultsFound(String result) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(result);

			// Setting text fields
			String[] allergySplit = jsonObject.getString("allergies").split(
					"SPLIT");

			String allergyTitleOne, allergyTitleTwo, allergyTitleThree, allergyDetailsOne, allergyDetailsTwo, allergyDetailsThree;

			allergyTitleOne = allergySplit[0].trim();
			allergyDetailsOne = allergySplit[1].trim();

			allergyTitleTwo = allergySplit[2].trim();
			allergyDetailsTwo = allergySplit[3].trim();

			allergyTitleThree = allergySplit[4].trim();
			allergyDetailsThree = allergySplit[5].trim();

			String listOfAllergies = " • " + allergyTitleOne + "\n • "
					+ allergyTitleTwo + "\n • " + allergyTitleThree;

			sendBasket.putString("allergyTitleOne", allergyTitleOne);
			sendBasket.putString("allergyTitleTwo", allergyTitleTwo);
			sendBasket.putString("allergyTitleThree", allergyTitleThree);
			sendBasket.putString("allergyDetailsOne", allergyDetailsOne);
			sendBasket.putString("allergyDetailsTwo", allergyDetailsTwo);
			sendBasket.putString("allergyDetailsThree", allergyDetailsThree);

			String[] medicineSplit = jsonObject.getString("medicine").split(
					"SPLIT");

			String medicineTitleOne, medicineTitleTwo, medicineTitleThree, medicineDetailsOne, medicineDetailsTwo, medicineDetailsThree;

			medicineTitleOne = medicineSplit[0].trim();
			medicineDetailsOne = medicineSplit[1].trim();

			medicineTitleTwo = medicineSplit[2].trim();
			medicineDetailsTwo = medicineSplit[3].trim();

			medicineTitleThree = medicineSplit[4].trim();
			medicineDetailsThree = medicineSplit[5].trim();

			sendBasket.putString("medicineTitleOne", medicineTitleOne);
			sendBasket.putString("medicineTitleTwo", medicineTitleTwo);
			sendBasket.putString("medicineTitleThree", medicineTitleThree);
			sendBasket.putString("medicineDetailsOne", medicineDetailsOne);
			sendBasket.putString("medicineDetailsTwo", medicineDetailsTwo);
			sendBasket.putString("medicineDetailsThree", medicineDetailsThree);

			String medicalHistory = " • " + medicineTitleOne + "\n • "
					+ medicineTitleTwo + "\n • " + medicineTitleThree;

			secondLabelID.setText("NHS ID");

			// Displaying text fields
			secondFillID.setText(jsonObject.getString("idPatientDetails"));
			secondFillBloodType.setText(jsonObject.getString("Blood_Type"));
			secondFillDOB.setText(jsonObject.getString("DOB"));
			secondFillWeight.setText(jsonObject.getString("weight"));

			secondFillAllergies.setText(listOfAllergies);
			secondFillMHistory.setText(medicalHistory);

			// Putting objects in Bundle
			sendBasket.putString("idPatientDetails",
					jsonObject.getString("idPatientDetails"));
			sendBasket.putString("Blood_Type",
					jsonObject.getString("Blood_Type"));
			sendBasket.putString("DOB", jsonObject.getString("DOB"));
			sendBasket.putString("weight", jsonObject.getString("weight"));
			sendBasket.putString("allergies", listOfAllergies);
			sendBasket.putString("medicalHistory", medicalHistory);
			sendBasket.putBoolean("secondJobFound", true);
			sendBasket.putString("medicineTitleOne", medicineTitleOne);
			sendBasket.putString("medicineTitleTwo", medicineTitleTwo);
			sendBasket.putString("medicineTitleThree", medicineTitleThree);
			sendBasket.putString("medicineDetailsOne", medicineDetailsOne);
			sendBasket.putString("medicineDetailsTwo", medicineDetailsTwo);
			sendBasket.putString("medicineDetailsThree", medicineDetailsThree);

			secondLabelBloodType.setVisibility(View.VISIBLE);
			secondLabelDOB.setVisibility(View.VISIBLE);
			secondLabelAllergies.setVisibility(View.VISIBLE);
			secondLabelSecondWeight.setVisibility(View.VISIBLE);
			secondLabelMHistory.setVisibility(View.VISIBLE);
			bSecondAllergies.setVisibility(View.VISIBLE);
			bSecondKnownMedicines.setVisibility(View.VISIBLE);
		} catch (JSONException e) {
			sendBasket.putBoolean("secondJobFound", false);
			notFound();
		}

	}

	/**
	 * If no summary of care details are found the tab is turned red to inform
	 * no information is held
	 */
	void notFound() {
		secondLabelID.setText("No information found");
		tabhost.getTabWidget()
				.getChildAt(1)
				.setBackgroundColor(
						getResources().getColor(android.R.color.holo_red_dark));

		// For later screen
		sendBasket.putBoolean("secondJobFound", false);
	}

	/**
	 * Inner class of GoingToPatient. Attempts to obtain Summary of Care
	 * information
	 * 
	 * @author Gary
	 */
	class GetSummaryOfCare extends AsyncTask<String, Integer, String> {

		protected String doInBackground(String... params) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (!(CommonFunctions.testConnection(GoingToPatient.this))) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return PHPConnections.getSummaryOfCareJSON(params[0],
					params[1]);

		}

		@Override
		protected void onPostExecute(String result) {
			if (result == null || result.equals("0")) {
				// Summary of care details not found
				notFound();
			} else {
				// Summary of care details found
				resultsFound(result);
			}
		}

	}

}
