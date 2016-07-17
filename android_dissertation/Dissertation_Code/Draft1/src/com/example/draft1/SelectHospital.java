package com.example.draft1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * In this activity the user selects a hospital and updates the condition of the
 * category. A entry is created in the hospital database of the patients details
 * 
 * @author Gary
 * 
 */
public final class SelectHospital extends Activity implements OnClickListener {
	private Bundle gotBasket;
	private Spinner spinnerHospital, spinnerCategory;
	private Button bSubmit;
	private Bundle sendBasket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.select_hospital);
		this.setFinishOnTouchOutside(false);
		initializeVar();
	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVar() {
		putInBasket();
		bSubmit = (Button) findViewById(R.id.bSHSubmit);
		spinnerHospital = (Spinner) findViewById(R.id.spinnerHospital);
		spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
		bSubmit.setOnClickListener(this);

	}

	/**
	 * Bundle is populated for following screens
	 */
	private void putInBasket() {
		gotBasket = getIntent().getExtras();
		sendBasket = new Bundle();
		sendBasket.putAll(gotBasket);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bSHSubmit):
			String hospital = String.valueOf(spinnerHospital.getSelectedItem());
			String category = String.valueOf(spinnerCategory.getSelectedItem());
			if (hospital.equals("Select Hospital")
					|| category.equals("Select Category")) {
				CommonFunctions.toastScreenCentre(this,
						"Please enter both boxes", Toast.LENGTH_LONG,
						Gravity.BOTTOM);
			} else if (hospital.equals("Newcastle General")) {
				sendBasket.putString("endLat", "54.974690");
				sendBasket.putString("endLng", "-1.646179");
			} else if (hospital.equals("Royal Victoria Infirmary")) {
				sendBasket.putString("endLat", "54.979357");
				sendBasket.putString("endLng", "-1.631416");
			} else if (hospital.equals("Newcastle Freeman Hospital")) {
				sendBasket.putString("endLat", "55.001395");
				sendBasket.putString("endLng", "-1.594262");
			}

			Intent intent = new Intent(SelectHospital.this, ArrivedMenu.class);

			intent.putExtras(sendBasket);
			startActivity(intent);
			finish();

			break;
		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

}
