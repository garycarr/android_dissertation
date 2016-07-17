package com.example.draft1;

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
import android.widget.Toast;

import com.example.PHPConnections.PHPConnections;

/**
 * This activity allows the user to input their username and code to reset their
 * password
 * 
 * @author Gary Carr
 */
public final class UnlockPassword extends Activity implements OnClickListener {

	private EditText etResetPasswordUsername, etResetPasswordAdminCode;
	private Button bResetPasswordSubmitAdminCode, bResetPasswordExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_password);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initializeVar();
	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVar() {
		etResetPasswordAdminCode = (EditText) findViewById(R.id.etRPAdminCode);
		etResetPasswordUsername = (EditText) findViewById(R.id.etRPUsername);
		bResetPasswordSubmitAdminCode = (Button) findViewById(R.id.bResetPasswordSubmitAdminCode);
		bResetPasswordSubmitAdminCode.setOnClickListener(this);
		bResetPasswordExit = (Button) findViewById(R.id.bResetPasswordExit);
		bResetPasswordExit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bResetPasswordSubmitAdminCode):
			// User has attempted to reset code
			CheckAdminCode test = new CheckAdminCode();

			String checkCode = null;
			try {
				// Checking if the code is valid
				checkCode = test.execute(
						etResetPasswordAdminCode.getText().toString(),
						etResetPasswordUsername.getText().toString()).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (checkCode == null) {
				// unexpected exception. Indicates website fault
				CommonFunctions
						.toastScreenCentre(
								this,
								"Unexpected error.  If problems persists contact the administrator",
								Toast.LENGTH_SHORT, Gravity.CENTER_HORIZONTAL);
			} else if (!checkCode.equals("1")) {
				// Code is invalid
				CommonFunctions.toastScreenCentre(this,
						"Incorrect details entered", Toast.LENGTH_SHORT,
						Gravity.CENTER_HORIZONTAL);
			} else {
				// Code is valid
				Bundle basket = new Bundle();
				basket.putString("username", etResetPasswordUsername.getText()
						.toString());
				Intent changePassword = new Intent(UnlockPassword.this,
						ChangePassword.class);
				changePassword.putExtras(basket);
				startActivity(changePassword);
				finish();
			}
			break;
		case (R.id.bResetPasswordExit):
			// User returns to the login page
			Intent loginPage = new Intent(UnlockPassword.this, Login.class);
			startActivity(loginPage);
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

	/**
	 * Inner class of UnlockPassword. Checks if the users reset password code is
	 * valid
	 * 
	 * @author Gary
	 * 
	 */
	private static final class CheckAdminCode extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String test = null;
			String originalUnlockCode = params[0];
			String originalUsername = params[1];
			test = PHPConnections.unlockPassword(PHPConnections
					.useSHA1(originalUnlockCode,
							CommonFunctions.NOTIMESHASHUSERNAME),
					PHPConnections.useSHA1(originalUsername,
							CommonFunctions.NOTIMESHASHUSERNAME));
			return test;
		}

	}

}
