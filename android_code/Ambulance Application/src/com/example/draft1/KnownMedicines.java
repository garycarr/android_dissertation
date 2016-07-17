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
 * Activity displays the known medicines of a patient to the user
 * 
 * @author Gary
 * 
 */
public class KnownMedicines extends Activity implements OnClickListener {

	private TextView medicineOne, medicineTwo, medicineThree;
	private String medicineTitleOne, medicineTitleTwo, medicineTitleThree;
	private String medicineDetailsOne, medicineDetailsTwo,
			medicineDetailsThree;
	private Button close;
	private Bundle gotBasket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.known_medicine);

		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initializeVars();
		// Instructs the user on how to use the screen
		CommonFunctions.toastScreenCentre(this,
				"Tap medicine to add/hide more information", Toast.LENGTH_LONG,
				Gravity.BOTTOM);
	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVars() {
		gotBasket = getIntent().getExtras();

		medicineOne = (TextView) findViewById(R.id.tvKMMedicineOne);
		medicineTwo = (TextView) findViewById(R.id.tvKMMedicineTwo);
		medicineThree = (TextView) findViewById(R.id.tvKMMedicineThree);

		medicineTitleOne = gotBasket.getString("medicineTitleOne");
		medicineTitleTwo = gotBasket.getString("medicineTitleTwo");
		medicineTitleThree = gotBasket.getString("medicineTitleThree");
		medicineDetailsOne = gotBasket.getString("medicineDetailsOne");
		medicineDetailsTwo = gotBasket.getString("medicineDetailsTwo");
		medicineDetailsThree = gotBasket.getString("medicineDetailsThree");

		medicineOne.setText(medicineTitleOne);
		medicineTwo.setText(medicineTitleTwo);
		medicineThree.setText(medicineTitleThree);
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
		case (R.id.bCloseAl):
			finish();
			break;
		// Text view will be switched to from what the user is currently looking
		// at
		case (R.id.tvKMMedicineOne):
			if (medicineOne.getText().toString().equals(medicineTitleOne)) {
				medicineOne.setText(medicineDetailsOne);
			} else {
				medicineOne.setText(medicineTitleOne);
			}
			break;
		case (R.id.tvKMMedicineTwo):
			if (medicineTwo.getText().toString().equals(medicineTitleTwo)) {
				medicineTwo.setText(medicineDetailsTwo);
			} else {
				medicineTwo.setText(medicineTitleTwo);
			}
			break;
		case (R.id.tvKMMedicineThree):
			if (medicineThree.getText().toString().equals(medicineTitleThree)) {
				medicineThree.setText(medicineDetailsThree);
			} else {
				medicineThree.setText(medicineTitleThree);
			}
			break;
		default:
			// Nothing
			break;
		}
	}
}
