package com.example.draft1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Activity screen is launched if the device has been disabled by the
 * administrator. Only one option is presented to the user, a button which kills
 * the application
 * 
 * @author Gary
 */
public class ShutDown extends Activity implements OnClickListener {
	Button bShutDownExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.shut_down);
		initializeVar();
	}

	/**
	 * Variables are initialized again an XML id
	 */
	private void initializeVar() {
		bShutDownExit = (Button) findViewById(R.id.bShutDownExit);
		bShutDownExit.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shut_down, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		finish();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

}
