package com.example.draft1;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.PHPConnections.PHPConnections;

/**
 * Login - The introduction activity for the user launched by the manifest. Used
 * for logging in or resetting the users password
 * 
 * @author Gary Carr
 */
public final class Login extends Activity implements OnClickListener {

	private Button bWaitScreenLogin, bWaitScreenLogOut, bLoginResetPassword;
	private TextView tvUsername, tvPassword, tvAccessCode;
	private EditText etUserName, etPassword, etGencode;
	private ProgressDialog progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initializeVar();
		checkClipboard();
	}

	/**
	 * Variables are assigned to the appropriate xml item
	 */
	private void initializeVar() {
		tvUsername = (TextView) findViewById(R.id.tvUsername);
		tvPassword = (TextView) findViewById(R.id.tvPassword);
		tvAccessCode = (TextView) findViewById(R.id.tvAccessCode);
		bWaitScreenLogin = (Button) findViewById(R.id.bWaitScreenLogin);
		bWaitScreenLogin.setOnClickListener(this);
		bWaitScreenLogOut = (Button) findViewById(R.id.bWaitScreenLogOut);
		bWaitScreenLogOut.setOnClickListener(this);
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etGencode = (EditText) findViewById(R.id.etGenCode);
		progress = new ProgressDialog(this);
		progress.setTitle("Connecting");
		progress.setMessage("Checking login details");
		bLoginResetPassword = (Button) findViewById(R.id.bLoginResetPassword);
		bLoginResetPassword.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case (R.id.bWaitScreenLogin):
			// User has attempted to log onto the system. Login button is
			// disabled whilst the details are checked

			bWaitScreenLogin.setEnabled(false);
			if (etUserName.getText().toString().equals("")
					&& etPassword.getText().toString().equals("")
					&& etGencode.getText().toString().equals("")) {
				// Blank login for testing

				enterLoginScreen(PHPConnections.useSHA1(
						"TestingUsername", CommonFunctions.NOTIMESHASHUSERNAME));
				finish();
			} else if (etUserName.getText().toString().equals("")
					|| etPassword.getText().toString().equals("")
					|| etGencode.getText().toString().equals("")) {
				// User has not entered all boxes. The missing box will turn red

				if (etUserName.getText().toString().equals("")) {
					tvUsername
							.setBackgroundColor(Color.parseColor("#FFFF0000"));
				} else {
					tvUsername.setBackgroundColor(Color.TRANSPARENT);
				}
				if (etPassword.getText().toString().equals("")) {
					tvPassword
							.setBackgroundColor(Color.parseColor("#FFFF0000"));
				} else {
					tvPassword.setBackgroundColor(Color.TRANSPARENT);
				}

				if (etGencode.getText().toString().equals("")) {
					tvAccessCode.setBackgroundColor(Color
							.parseColor("#FFFF0000"));
				} else {
					tvAccessCode.setBackgroundColor(Color.TRANSPARENT);
				}
				setWaitScreenButtonOn();

			} else {
				// All boxes have been inputted. If there is internet connection
				// an attempt is made to login

				if (CommonFunctions.testConnection(Login.this)) {
					progress.show();
					new CheckLoginDetails().execute(etUserName.getText()
							.toString(), etPassword.getText().toString(),
							etGencode.getText().toString());
				} else {
					CommonFunctions.toastScreenCentre(Login.this,
							"No internet connection", Toast.LENGTH_SHORT,
							Gravity.CENTER_HORIZONTAL);
					setWaitScreenButtonOn();
				}
			}
			break;
		case (R.id.bWaitScreenLogOut):
			// User has logged out

			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			break;

		case (R.id.bLoginResetPassword):
			// User is attempting to reset password

			Intent resetPassword = new Intent(Login.this, UnlockPassword.class);
			startActivity(resetPassword);
			finish();
			break;
		default:
			// Java practice to include a default, but this option will not be
			// reached

			break;
		}

	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedLogOut(Login.this);

	}

	/**
	 * This method is entered if all login details were correct. A unique code
	 * is made from username and phone ID to be used as authentication with the
	 * dispatchers server
	 * 
	 * @param hashedUsername
	 *            A user name that has already been hashed
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws NoSuchAlgorithmException
	 */
	public void enterLoginScreen(String hashedUsername) {

		String deviceId = getDeviceId();
		String storeAuthenticationCode = CommonFunctions
				.saltAndHashAuthenticationCode(hashedUsername, deviceId,
						CommonFunctions.AUTHENTICATIONCODEHASHES);

		// Authentication code is stored on the dispatchers database. This must
		// be completed before the activity moves on so the device has the
		// authority to communicate with the server
		try {
			progress.show();
			RunCheckers.setHashedUsername(hashedUsername);
			RunCheckers.setAuthenticationCode(storeAuthenticationCode);
			new InsertAuthenticationCode().execute(storeAuthenticationCode,
					hashedUsername).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		progress.dismiss();

		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(etGencode.getWindowToken(), 0);
		Intent waitScreen = new Intent(Login.this, WaitScreen.class);
		Bundle sendBasket = new Bundle();

		sendBasket.putString("authenticationCode", storeAuthenticationCode);
		sendBasket.putString("hashedUserName", hashedUsername);
		waitScreen.putExtras(sendBasket);
		startActivity(waitScreen);
		finish();
	}

	/**
	 * A unique identification of the phone is created and hashed
	 * 
	 * Obtained from
	 * http://stackoverflow.com/questions/2785485/is-there-a-unique
	 * -android-device-id
	 * 
	 * @return A hashed unique identification for the phone
	 */
	private String getDeviceId() {
		final TelephonyManager tm = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice = "" + tm.getDeviceId();
		final String tmSerial = "" + tm.getSimSerialNumber();
		final String androidId = ""
				+ android.provider.Settings.Secure.getString(
						getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		return deviceUuid.toString();
	}

	/**
	 * The phones clipboard is checked and if it holds an access code the user
	 * is asked if they want it pasted
	 */
	private void checkClipboard() {
		ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

		try {
			ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
			final String split[] = item.getText().toString().split("-");
			// If there are 2 parts to the text and the first part equals
			// ACCESSCODE determined it is an access code

			if (split.length == 2 && split[0].equals("ACCESSCODE")) {
				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.dialog);
				dialog.setTitle("Access code?");
				// set the custom dialog components - text, image and button

				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText("Paste to access code?");
				Button bYes = (Button) dialog.findViewById(R.id.bYes);
				// if button is clicked, close the custom dialog

				bYes.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						setGenCode(split[1]);
						dialog.dismiss();
					}
				});
				Button bNo = (Button) dialog.findViewById(R.id.bNo);
				bNo.setOnClickListener(new OnClickListener() {
					// @Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		} catch (NullPointerException e) {
			// No data found in the clipboard. No action needed
		}

	}

	/**
	 * Returns the login button to work again if a user inputs the wrong details
	 */
	void setWaitScreenButtonOn() {
		bWaitScreenLogin.setEnabled(true);
	}

	/**
	 * When a code has been copied this method pastes it to the box. Used by the
	 * static class
	 * 
	 * @param code
	 *            - The copied code
	 * 
	 */
	void setGenCode(String code) {
		etGencode.setText(code);
	}

	/**
	 * Used by the static class to delete the progress display
	 */
	void dismissProgress() {
		progress.dismiss();
	}

	void loginToChangePassword() {
		Intent resetPassword = new Intent(Login.this, ChangePassword.class);
		Bundle basket = new Bundle();
		basket.putString("username", etUserName.getText().toString());
		basket.putString("authorizedReset", "true");
		String hashedUsernameString = PHPConnections.useSHA1(etUserName
				.getText().toString(), CommonFunctions.NOTIMESHASHUSERNAME);
		basket.putString("hashedUserName", hashedUsernameString);
		resetPassword.putExtras(basket);
		startActivity(resetPassword);
		finish();
	}

	/**
	 * Inner class of Login. The users username, password and access code are
	 * checked
	 * 
	 * @author Gary
	 * 
	 */
	private class CheckLoginDetails extends AsyncTask<String, Integer, String> {

		String hashedUsername = null;

		@Override
		protected String doInBackground(String... params) {
			String checkAccessCode = null;
			String checkUsername = null;
			String hashedUsernameAndPassword = null;

			// Login details are hashed
			hashedUsername = PHPConnections.useSHA1(params[0],
					CommonFunctions.NOTIMESHASHUSERNAME);
			hashedUsernameAndPassword = PHPConnections.useSHA1(
					params[1], CommonFunctions.NOOFFIRSTHASHES);

			// Hashed password is salted with hashed password
			hashedUsernameAndPassword = hashedUsername
					+ hashedUsernameAndPassword;
			hashedUsernameAndPassword = PHPConnections
					.useSHA1(hashedUsernameAndPassword,
							CommonFunctions.NOOFSECONDHASHES);

			// Dispatcher database is checked to see if username and password
			// are valid
			checkUsername = PHPConnections.checkNameAndPassword(
					hashedUsername, hashedUsernameAndPassword);

			if (checkUsername.equals("1")) {
				// Username and password is valid. Access code is now checked

				checkAccessCode = PHPConnections.checkAccessCode(
						hashedUsername, params[2]);

				if (checkAccessCode.equals("1")) {
					// Access code is invalid
					return "1";
				} else if (checkAccessCode.equals("0")) {
					// Invalid code
					return "0Access code invalid";
				} else {
					// Has been internet conne3ction problem
					return "0" + checkAccessCode;
				}

			} else if (checkUsername.equals("0")) {
				// Invalid username or password
				return "0Username or password incorrect";
			} else if (checkUsername.equals("-1")) {
				// Account is locked
				return "0Your account is locked.  Please contact the administrator";
			} else if (checkUsername.equals("-2")) {
				// The account has expired
				return "-2";
			} else {
				// Has been internet connection problem
				return "0" + checkUsername;
			}

		}

		@Override
		protected void onPostExecute(String result) {
			dismissProgress();

			if (result.substring(0, 1).equals("0")) {
				// Password not valid

				CommonFunctions.toastScreenCentre(Login.this,
						result.substring(1), Toast.LENGTH_SHORT,
						Gravity.CENTER_HORIZONTAL);
				setWaitScreenButtonOn();

			} else if (result.equals("-2")) {
				// Reset of password required
				loginToChangePassword();

			} else {
				// Valid password

				enterLoginScreen(hashedUsername);
			}
			finish();
		}
	}

	/**
	 * Inner class of Login. The authentication code is inserted into the
	 * database so the device can communicate with the server following login
	 * 
	 * @author Gary
	 */
	private static final class InsertAuthenticationCode extends
			AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... params) {
			String authenticationCode = params[0];
			String username = params[1];
			return PHPConnections.storeAuthenticationCode(
					authenticationCode, username);

		}

	}
}
