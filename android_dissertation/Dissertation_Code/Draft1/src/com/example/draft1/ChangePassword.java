package com.example.draft1;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PHPConnections.PHPConnections;

/**
 * Activity launches to allow a user to change there password
 * 
 * @author Gary
 */
public final class ChangePassword extends Activity implements OnClickListener {

	private EditText etNewPassword1, etNewPassword2;
	private Button bChangePasswordSubmitNewPassword, bChangePasswordExit;
	private TextView tvChangePasswordMessage;
	boolean validPassword;
	private Bundle getBasket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.change_password);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initializeVar();
	}

	/**
	 * Initialises the xml variables
	 */
	void initializeVar() {
		tvChangePasswordMessage = (TextView) findViewById(R.id.tvChangePasswordMessage);
		etNewPassword1 = (EditText) findViewById(R.id.etNewPassword1);
		etNewPassword2 = (EditText) findViewById(R.id.etNewPassword2);
		bChangePasswordSubmitNewPassword = (Button) findViewById(R.id.bChangePasswordSubmitNewPassword);
		bChangePasswordExit = (Button) findViewById(R.id.bChangePasswordExit);
		bChangePasswordSubmitNewPassword.setOnClickListener(this);
		bChangePasswordExit.setOnClickListener(this);

		getBasket = getIntent().getExtras();
		if (getBasket.containsKey("authorizedReset")) {
			tvChangePasswordMessage
					.setText("Your password has expired. Please enter a new password");
			CommonFunctions.toastScreenCentre(this,
					"Your password has expired", Toast.LENGTH_LONG,
					Gravity.CENTER_HORIZONTAL);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bChangePasswordSubmitNewPassword):
			if (!(etNewPassword1.getText().toString().equals(etNewPassword2
					.getText().toString()))) {
				// Two different passwords have been entered
				CommonFunctions.toastScreenCentre(this,
						"Passwords entered are different", Toast.LENGTH_SHORT,
						Gravity.CENTER_HORIZONTAL);
				return;
			} else {
				// Password validity is checked
				validPassword = passwordValidator(etNewPassword1.getText()
						.toString());
			}
			if (!validPassword) {
				// Password is invalid
				CommonFunctions
						.toastScreenCentre(
								this,
								"Password must be 8 characters, with an upper and lower case letter and contain a number",
								Toast.LENGTH_LONG, Gravity.CENTER_HORIZONTAL);
				return;
			}
			try {
				String hashedUsername = PHPConnections.useSHA1(
						getBasket.getString("username"),
						CommonFunctions.NOTIMESHASHUSERNAME);
				String result = new ChangePasswordInDatabase().execute(
						hashedUsername, etNewPassword1.getText().toString())
						.get();
				if (result.equals("1")) {
					CommonFunctions.toastScreenCentre(this,
							"Password has been changed", Toast.LENGTH_SHORT,
							Gravity.CENTER_HORIZONTAL);
				} else if (result.equals("0")) {
					// Shouldnt need this
				} else if (result.equals("-2")) {
					// Password is the same as previous password
					CommonFunctions
							.toastScreenCentre(
									this,
									"Password must be different from the previous password",
									Toast.LENGTH_SHORT,
									Gravity.CENTER_HORIZONTAL);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case (R.id.bChangePasswordExit):
			// User returns to login page
			Intent loginPage = new Intent(ChangePassword.this, Login.class);
			startActivity(loginPage);
			finish();
			break;

		default:
			// Will not get here
			break;
		}

	}

	/**
	 * Validity of the password is checked. Password must be above 8 character,
	 * have a digit and a lower and uppercase letter If succesful true is
	 * returned
	 * 
	 * @param password
	 *            The password to be checked
	 * @return True if password is valid
	 */
	private boolean passwordValidator(String password) {
		if (password.length() < 8) {
			// Ensures there are more than 8 letters
			return false;
		}
		if (password.equals(password.toLowerCase(Locale.getDefault()))
				|| password.equals(password.toUpperCase(Locale.getDefault()))) {
			// Tests if there is upper and lower case letters in the
			// password
			return false;
		}
		// Tests if password contains a digit
		for (char letter : password.toCharArray()) {
			if (Character.isDigit(letter)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Inner class of ChangePassword. Changes the users password in the database
	 * 
	 * @author Gary
	 */
	static class ChangePasswordInDatabase extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String test;
			String hashedPassword = null;
			String hashedUsername = params[0];
			String originalPassword = params[1];
			hashedPassword = PHPConnections.useSHA1(originalPassword,
					CommonFunctions.NOOFFIRSTHASHES);
			hashedPassword = hashedUsername + hashedPassword;

			hashedPassword = PHPConnections.useSHA1(hashedPassword,
					CommonFunctions.NOOFSECONDHASHES);

			test = PHPConnections.storeNewPassword(hashedUsername,
					hashedPassword);

			return test;
		}

	}

}
