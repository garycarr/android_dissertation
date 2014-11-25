package com.example.draft1;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity opens when user has arrived at the patient. Acts as a menu to other
 * activities
 * 
 * @author Gary
 */
public class ArrivedMenu extends ListActivity {

	private String classes[] = { "Patient Details", "Message Hospital",
			"Search Drugs and Allergies", "Map", "Dropped off patient" };
	private Bundle gotBasket, sendBasket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setListAdapter(new ArrayAdapter<String>(ArrivedMenu.this,
				android.R.layout.simple_list_item_1, classes));
		gotBasket = getIntent().getExtras();
	}

	/**
	 * Add all items to a bundle for the following activity
	 */
	private void putInBasket() {
		sendBasket = new Bundle();
		sendBasket.putAll(gotBasket);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		// Class user has selected is picked and opened
		String chosenClass = classes[position];
		if (chosenClass.equals("Patient Details")) {
			putInBasket();
			Intent ourIntent = new Intent(ArrivedMenu.this,
					ArrivingPatientDetails.class);
			ourIntent.putExtras(sendBasket);
			startActivity(ourIntent);
			finish();
		} else if (chosenClass.equals("Message Hospital")) {
			putInBasket();
			Intent ourIntent = new Intent(ArrivedMenu.this,
					MessageHospital.class);
			ourIntent.putExtras(sendBasket);
			startActivity(ourIntent);
			finish();

		} else if (chosenClass.equals("Search Drugs and Allergies")) {
			chosenClass = "";
			putInBasket();
			Intent ourIntent = new Intent(ArrivedMenu.this, Search.class);
			ourIntent.putExtras(sendBasket);
			startActivity(ourIntent);
			finish();
		} else if (chosenClass.equals("Map")) {
			chosenClass = "Maps";
			putInBasket();
			Intent mapIntent = new Intent(this, GoingToPatientMap.class);
			sendBasket.putAll(gotBasket);
			mapIntent.putExtras(sendBasket);
			startActivity(mapIntent);

		} else if (chosenClass.equals("Dropped off patient")) {
			chosenClass = "WaitScreen";
			RunCheckers.changeWaitingForJob(true);
			// RunCheckers.getConnectionsleeper().jobNeededStart();
			finish();
		}
	}

	@Override
	public void onBackPressed() {
		CommonFunctions.backButtonPressedCannotLogOut(this);
	}

}
