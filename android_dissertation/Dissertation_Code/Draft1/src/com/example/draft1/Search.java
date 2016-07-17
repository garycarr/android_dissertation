package com.example.draft1;

import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity screen will search a medical database for further information
 * on user inputted drugs
 * 
 * @author Gary
 */
public class Search extends Activity implements OnClickListener {

	private Button bSearchSubmit, bSearchBack;
	private EditText etSearchInputMessage;
	private TextView tvSearchDisplayMessage;
	private Bundle gotBasket, sendBasket;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initializeVar();
	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVar() {
		gotBasket = getIntent().getExtras();
		etSearchInputMessage = (EditText) findViewById(R.id.etSearchInputMessage);
		bSearchSubmit = (Button) findViewById(R.id.bSearchSubmit);
		bSearchBack = (Button) findViewById(R.id.bSearchBack);
		tvSearchDisplayMessage = (TextView) findViewById(R.id.tvSearchDisplayMessage);
		bSearchSubmit.setOnClickListener(this);
		bSearchBack.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bSearchBack):
			// User is going back to the menu

			Intent confirmIntent = new Intent(Search.this, ArrivedMenu.class);
			putInBasket();
			confirmIntent.putExtras(sendBasket);
			startActivity(confirmIntent);
			finish();
			break;
		case (R.id.bSearchSubmit):
			// User is searching for the drug

			if (etSearchInputMessage.getText().toString().trim()
					.toLowerCase(Locale.ENGLISH).equals("cocaine")) {
				tvSearchDisplayMessage
						.setText("Powder cocaine (also called coke), freebase and crack are all forms of cocaine. They’re all powerful stimulants, with short-lived effects – which means that they temporarily speed up the way your mind and body work, but the effects are short-lived. Both ‘freebase’ cocaine (powder cocaine that’s been prepared for smoking) and ‘crack’ cocaine (a ‘rock’ like form of cocaine) can be smoked. This means that they reach the brain very quickly, while snorted powder cocaine gets to the brain more slowly.");
			} else {
				CommonFunctions.toastScreenCentre(this, "No results found",
						Toast.LENGTH_SHORT, Gravity.CENTER_HORIZONTAL);
				tvSearchDisplayMessage.setText("");
			}
			break;
		default:
			// No default option
			break;
		}

	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

	/**
	 * Bundle is populated for following screens
	 */
	private void putInBasket() {
		sendBasket = new Bundle();
		sendBasket.putAll(gotBasket);
	}

}
