package com.example.draft1;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.PHPConnections.PHPConnections;

/**
 * RunChecker is executed as a thread to obtain incident details, delete first
 * incident details and delete summary of care details. A binary semaphore will
 * make the thread wait when there are no tasks to complete, or there is no
 * internet connection, and it will be notified by other threads when
 * appropriate
 * 
 * @author Gary
 */
public class RunCheckers implements Runnable {
	private Handler mHandler;
	private Context context;
	private final static BinarySemaphore connectionSleeper = new BinarySemaphore(
			0);
	private static boolean waitingForJob = true;
	private static boolean deleteIncidentDetails = false;
	private static boolean deleteSummaryDetails = false;
	private static String authenticationCode;
	private static String hashedUsername;

	public static BinarySemaphore getConnectionsleeper() {
		return connectionSleeper;
	}

	//
	/**
	 * The class will return information to a classes handler which called it
	 * 
	 * @param mHandler
	 *            The handler that the thread returns information to
	 * @param context
	 *            The activity that created the thread
	 */
	RunCheckers(Handler mHandler, Context context) {
		this.mHandler = mHandler;
		this.context = context;
	}

	@Override
	public void run() {
		// Checkes if there are any available jobs
		if (!checkIfWakeUpThread()) {
			try {
				getConnectionsleeper().noJobsStop();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (!CommonFunctions.testConnection(context)) {
			// Tests if there is no connection to the internet. If not sends a
			// message to the terminal and sleeps
			sendStatusNoConnection();
			try {
				getConnectionsleeper().noJobsStop();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (waitingForJob) {
			// If waiting for a job attempts to find the job
			sendStatusWaitingForJob();

		} else if (deleteIncidentDetails) {
			// If first incident details are to be deleted deletes them
			deleteIncidentDetails();

		} else if (deleteSummaryDetails) {
			// If second incident details are to be deleted deletes
			// them
			deleteSummaryOfCareDetails();
		}
	}

	/**
	 * Deletes the summary of care details
	 */
	private void deleteSummaryOfCareDetails() {
		PHPConnections.deleteSummaryOfCare(authenticationCode,
				hashedUsername);
		changeDeleteSummaryOfCareDetails(false);
	}

	/**
	 * Deletes the incident details
	 */
	private void deleteIncidentDetails() {
		PHPConnections.deleteIncidentDetails(authenticationCode,
				hashedUsername);
		changeDeleteFirstIncident(false);
	}

	/**
	 * Sends response from the job checker to the handler
	 */
	private void sendStatusWaitingForJob() {
		String response = PHPConnections.getIncidentJSON(
				authenticationCode, hashedUsername);
		Message msg2 = new Message();
		msg2.obj = response;
		mHandler.sendMessage(msg2);
	}

	/**
	 * Notifies the handler there is no connection
	 */
	private void sendStatusNoConnection() {
		Message msg1 = new Message();
		msg1.obj = "-1";
		mHandler.sendMessage(msg1);
	}

	/**
	 * @return True if there is a task the RunChecker needs to complete
	 */
	public static boolean checkIfWakeUpThread() {
		return waitingForJob || deleteIncidentDetails || deleteSummaryDetails;
	}

	/**
	 * @param change
	 *            Changes the status of waiting for a job, and wakes the boolean
	 *            if a job is available
	 */
	public static void changeWaitingForJob(boolean change) {
		waitingForJob = change;
		if (change == true) {
			RunCheckers.getConnectionsleeper().jobNeededStart();
		}
	}

	/**
	 * @return True if waiting for a job
	 */
	public static boolean checkWaitingForJob() {
		return waitingForJob;
	}

	/**
	 * @param change
	 *            Changes the status of deleting the incident details, and wakes
	 *            the boolean if a job is available
	 */
	public static void changeDeleteFirstIncident(boolean change) {
		deleteIncidentDetails = change;
		if (change == true) {
			RunCheckers.getConnectionsleeper().jobNeededStart();
		}
	}

	/**
	 * @return True if incident details need to be deleted
	 */
	public static boolean checkDeleteIncidentDetails() {
		return deleteIncidentDetails;
	}

	/**
	 * @param change
	 *            Changes the status of deleting the summary of care details,
	 *            and wakes the boolean if a job is available
	 */
	public static void changeDeleteSummaryOfCareDetails(boolean change) {
		deleteSummaryDetails = change;
		if (change == true) {
			RunCheckers.getConnectionsleeper().jobNeededStart();
		}
	}

	/**
	 * @@param True if summary of care details need to be deleted
	 */
	public static boolean checkDeleteSecondIncident() {
		return deleteSummaryDetails;
	}

	/**
	 * Sets the hashed username to be used in PHP connections
	 * 
	 * @param hashedUsername
	 */
	public static void setHashedUsername(String hashedUsername) {
		RunCheckers.hashedUsername = hashedUsername;
	}

	/**
	 * Sets the authentication code to be used in PHP connections
	 * 
	 * @param authenticationCode
	 */
	public static void setAuthenticationCode(String authenticationCode) {
		RunCheckers.authenticationCode = authenticationCode;
	}

}
