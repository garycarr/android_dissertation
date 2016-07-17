package com.example.draft1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity displays the known allergies of a patient to the user
 * 
 * @author Gary
 * 
 */
public class KnownAllergies extends Activity implements OnClickListener {

	private TextView allergyOne, allergyTwo, allergyThree;
	private String allergyTitleOne, allergyTitleTwo, allergyTitleThree;
	private String allergyDetailsOne, allergyDetailsTwo, allergyDetailsThree;
	private Button close;
	private Bundle gotBasket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.known_allergies);

		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initializeVars();

		// Instructs the user on how to use the screen
		CommonFunctions.toastScreenCentre(this,
				"Tap allergy to add/hide more information", Toast.LENGTH_LONG,
				Gravity.BOTTOM);
	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVars() {
		gotBasket = getIntent().getExtras();

		allergyOne = (TextView) findViewById(R.id.tvKAAllergyOne);
		allergyTwo = (TextView) findViewById(R.id.tvKAAllergyTwo);
		allergyThree = (TextView) findViewById(R.id.tvKAAllergyThree);

		allergyTitleOne = gotBasket.getString("allergyTitleOne");
		allergyTitleTwo = gotBasket.getString("allergyTitleTwo");
		allergyTitleThree = gotBasket.getString("allergyTitleThree");
		allergyDetailsOne = gotBasket.getString("allergyDetailsOne");
		allergyDetailsTwo = gotBasket.getString("allergyDetailsTwo");
		allergyDetailsThree = gotBasket.getString("allergyDetailsThree");

		allergyOne.setText(allergyTitleOne);
		allergyTwo.setText(allergyTitleTwo);
		allergyThree.setText(allergyTitleThree);
		close = (Button) findViewById(R.id.bCloseAl);
		close.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// Text view will be switched to from what the user is currently looking
		// at

		case (R.id.bCloseAl):
			finish();
			break;
		case (R.id.tvKAAllergyOne):
			if (allergyOne.getText().toString().equals(allergyTitleOne)) {
				allergyOne.setText(allergyDetailsOne);
			} else {
				allergyOne.setText(allergyTitleOne);
			}
			break;
		case (R.id.tvKAAllergyTwo):
			if (allergyTwo.getText().toString().equals(allergyTitleTwo)) {
				allergyTwo.setText(allergyDetailsTwo);
			} else {
				allergyTwo.setText(allergyTitleTwo);
			}
			break;
		case (R.id.tvKAAllergyThree):
			if (allergyThree.getText().toString().equals(allergyTitleThree)) {
				allergyThree.setText(allergyDetailsThree);
			} else {
				allergyThree.setText(allergyTitleThree);
			}
			break;
		default:
			break;
		}
	}
}
