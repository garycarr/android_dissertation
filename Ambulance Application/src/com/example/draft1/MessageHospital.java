package com.example.draft1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This activity screen will send a message from the application to a hospital
 * database
 * 
 * @author Gary
 */
public class MessageHospital extends Activity implements OnClickListener {

	private Button bMessageSubmit, bMessageBack;
	private EditText etInputMessage;
	private TextView tvMessageDisplayMessageHospital, tvMessageHistoryHospital;
	private Bundle gotBasket, sendBasket;
	private String textMessage = "";
	private int firstMessage = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message_hospital);
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
		etInputMessage = (EditText) findViewById(R.id.etInputMessage);
		bMessageSubmit = (Button) findViewById(R.id.bMessageSubmit);
		bMessageBack = (Button) findViewById(R.id.bMessageBack);

		tvMessageDisplayMessageHospital = (TextView) findViewById(R.id.tvMessageDisplayMessageHospital);
		tvMessageHistoryHospital = (TextView) findViewById(R.id.tvMessageHistoryHospital);

		bMessageSubmit.setOnClickListener(this);
		bMessageBack.setOnClickListener(this);

		// Checks if user has already sent a message, and if they have displays
		// it
		if (gotBasket.containsKey("messageHistory")) {
			textMessage = gotBasket.getString("messageHistory");
			tvMessageDisplayMessageHospital.setText(textMessage);
			firstMessage++;
			tvMessageHistoryHospital.setText("Message history");
		} else {
			tvMessageDisplayMessageHospital.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bMessageBack):
			// User going back to arrived menu

			Intent confirmIntent = new Intent(MessageHospital.this,
					ArrivedMenu.class);
			putInBasket();
			confirmIntent.putExtras(sendBasket);
			startActivity(confirmIntent);
			finish();
			break;
		case (R.id.bMessageSubmit):
			// User sends a message

			if (etInputMessage.getText().toString().equals("")) {
			} else {
				// A time stamp is created for the message
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				String time = dateFormat.format(cal.getTime());

				// Formatting is different for the first message as a carraige
				// return is not needed at the top
				if (firstMessage == 1) {
					textMessage += "\n" + "\n" + time + "\n   "
							+ etInputMessage.getText().toString();
				} else {
					textMessage += time + "\n   "
							+ etInputMessage.getText().toString();
					firstMessage++;
				}
				tvMessageHistoryHospital.setText("Message history");
				tvMessageDisplayMessageHospital.setText(textMessage);
				etInputMessage.setText("");
				tvMessageDisplayMessageHospital.setVisibility(View.VISIBLE);
			}
			break;
		default:
			// No default option
			break;
		}

	}

	/**
	 * Bundle is populated for following screens
	 */
	private void putInBasket() {
		sendBasket = new Bundle();
		sendBasket.putAll(gotBasket);
		sendBasket.putString("messageHistory", textMessage);
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

}
