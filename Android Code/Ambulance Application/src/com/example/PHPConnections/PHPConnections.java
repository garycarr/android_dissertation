package com.example.PHPConnections;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * This class handles all of the PHP connections to the web
 * 
 * @author Gary Carr
 * 
 */
public class PHPConnections {

	private final static String URLADMINCODE = "http://homepages.cs.ncl.ac.uk/g.carr/Website/checkAdminCode.php";
	private final static String URLCHANGEPASSWORD = "http://homepages.cs.ncl.ac.uk/g.carr/Website/ChangePassword.php";
	private static final String URLFIRSTJSON = "http://homepages.cs.ncl.ac.uk/g.carr/Website/checkFirstStageJson.php";
	private static final String URLDELETEFIRSTDETAILS = "http://homepages.cs.ncl.ac.uk/g.carr/Website/deleteFirstJob.php";
	private static final String URLDELETESECONDDETAILS = "http://homepages.cs.ncl.ac.uk/g.carr/Website/deleteSecondJob.php";
	private static final String URLACCESSCODE = "http://homepages.cs.ncl.ac.uk/g.carr/Website/giveAccessCode.php";
	private static final String URLUSERNAME = "http://homepages.cs.ncl.ac.uk/g.carr/Website/givePassword.php";
	private static final String URLGPSReporter = "http://homepages.cs.ncl.ac.uk/g.carr/Website/giveLocation.php";
	private static final String URLAUTHENTICATIONCODE = "http://homepages.cs.ncl.ac.uk/g.carr/Website/giveAuthenticationCode.php";
	private static final String URLSECONDJSON = "http://homepages.cs.ncl.ac.uk/g.carr/Website/SecondStageJson.json";

	/**
	 * Requests to check if a user can reset their password
	 * 
	 * @param hashedResetCode
	 * @param hashedUsername
	 * @return The response from the PHP file
	 */
	public static String unlockPassword(String hashedResetCode,
			String hashedUsername) {
		// Request parameters and other properties.
		List<NameValuePair> paramsUser = new ArrayList<NameValuePair>(2);
		paramsUser.add(new BasicNameValuePair("resetCode", hashedResetCode));
		paramsUser.add(new BasicNameValuePair("username", hashedUsername));

		return establishConnection(paramsUser, URLADMINCODE);
	}

	/**
	 * Requests to store a new password for the user
	 * 
	 * @param hashedUsername
	 * @param hashedPassword
	 * @return The response from the PHP file
	 */
	public static String storeNewPassword(String hashedUsername,
			String hashedPassword) {
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("username", hashedUsername));
		params.add(new BasicNameValuePair("password", hashedPassword));

		return establishConnection(params, URLCHANGEPASSWORD);
	}

	/**
	 * Requests to check if a username and password is correct
	 * 
	 * @param hashedUsername
	 * @param hashedUsernameandpassword
	 * @return The response from the PHP file
	 */
	public static String checkNameAndPassword(String hashedUsername,
			String hashedUsernameandpassword) {
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("userName", hashedUsername));
		params.add(new BasicNameValuePair("password", hashedUsernameandpassword));
		return establishConnection(params, URLUSERNAME);
	}

	/**
	 * Requests to check if an access code is correct
	 * 
	 * @param hashedUsername
	 * @param gencode
	 * @return The response from the PHP file
	 */
	public static String checkAccessCode(String hashedUsername, String gencode) {
		// Request parameters and other properties.
		List<NameValuePair> paramsAccess = new ArrayList<NameValuePair>(2);
		paramsAccess.add(new BasicNameValuePair("userName", hashedUsername));
		paramsAccess.add(new BasicNameValuePair("accessCode", gencode));
		return establishConnection(paramsAccess, URLACCESSCODE);
	}

	/**
	 * Requests to check if an authentication code is correct
	 * 
	 * @param storeAuthenticationCode
	 * @param username
	 * @return The response from the PHP file
	 */
	public static String storeAuthenticationCode(
			String storeAuthenticationCode, String username) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("authenticationCode",
				storeAuthenticationCode));
		params.add(new BasicNameValuePair("usernameString", username));

		return establishConnection(params, URLAUTHENTICATIONCODE);
	}

	/**
	 * Requests to get incident details
	 * 
	 * @param authenticationCode
	 * @param hashedUserNameFromBasket
	 * @return
	 */
	public static String getIncidentJSON(String authenticationCode,
			String hashedUserNameFromBasket) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("authenticationCode",
				authenticationCode));
		params.add(new BasicNameValuePair("username", hashedUserNameFromBasket));
		return establishConnection(params, URLFIRSTJSON);
	}

	/**
	 * Requests to delete incident details
	 * 
	 * @param authenticationCode
	 * @param hashedUserNameFromBasket
	 * @return The response from the PHP file
	 */
	public static String deleteIncidentDetails(String authenticationCode,
			String hashedUserNameFromBasket) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("authenticationCode",
				authenticationCode));
		params.add(new BasicNameValuePair("username", hashedUserNameFromBasket));
		return establishConnection(params, URLDELETEFIRSTDETAILS);
	}

	/**
	 * Requests to get summary of care details
	 * 
	 * @param authenticationCode
	 * @param hashedUserNameFromBasket
	 * @return The response from the PHP file
	 */
	public static String getSummaryOfCareJSON(String authenticationCode,
			String hashedUserNameFromBasket) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("authenticationCode",
				authenticationCode));
		params.add(new BasicNameValuePair("username", hashedUserNameFromBasket));
		return establishConnection(params, URLSECONDJSON);
	}

	/**
	 * Requests to delete summary of care details
	 * 
	 * @param authenticationCode
	 * @param hashedUserNameFromBasket
	 * @return The response from the PHP file
	 */
	public static String deleteSummaryOfCare(String authenticationCode,
			String hashedUserNameFromBasket) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("authenticationCode",
				authenticationCode));
		params.add(new BasicNameValuePair("username", hashedUserNameFromBasket));
		return establishConnection(params, URLDELETESECONDDETAILS);

	}

	/**
	 * Method reports the current location of the device to a database
	 * 
	 * @param latitude
	 *            The current latitude
	 * @param longitude
	 *            The current longitude
	 */
	public static void reportJob(String latitude, String longitude) {
		List<NameValuePair> params = new ArrayList<NameValuePair>(3);

		params.add(new BasicNameValuePair("latitude", "" + latitude));
		params.add(new BasicNameValuePair("longitude", "" + longitude));
		establishConnection(params, URLGPSReporter);
	}

	/**
	 * Handles the PHP requests. Takes the parameters and the URL to connect to
	 * and returns the echo'd value
	 * 
	 * @param paramsUser
	 *            The parameters to accompany the PHP request
	 * @param weblink
	 *            The website to connect to
	 * @return The value the PHP echo's
	 */
	private static String establishConnection(List<NameValuePair> params,
			String weblink) {
		BufferedReader in = null;
		String inputLine = null;
		HttpClient httpclientUser = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(weblink);
		// Request parameters and other properties.

		try {
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = httpclientUser.execute(httppost);
			HttpEntity entity = response.getEntity();
			in = new BufferedReader(new InputStreamReader(entity.getContent(),
					"US-ASCII"));
			inputLine = in.readLine();
			in.close();
		} catch (MalformedURLException e1) {
			// Error message code FJ1
			e1.printStackTrace();
			return "-3";
		} catch (IOException e) {
			// Error message code FJ2
			e.printStackTrace();
			return "-4";
		} catch (NullPointerException e) {
			// Error message code FJ3
			e.printStackTrace();
			return "-5";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return inputLine;
	}

	/**
	 * Method hashes a string the number of times supplied by the parameter and
	 * returns the hashed string
	 * 
	 * @param input
	 *            The string to be hashed
	 * @param noOfHashes
	 *            The number of times the string will be hashed
	 * @return A hashed string
	 */
	public static String useSHA1(String input, int noOfHashes) {
		String hashedCode = input;
		for (int i = 0; i < noOfHashes; i++) {
			hashedCode = sha1(hashedCode);
		}
		return hashedCode;
	}

	/**
	 * Code obtained from http://www.sha1-online.com/sha1-java/
	 * 
	 * A string is converted into bytes and hashed using SHA1, an irreversable
	 * process
	 * 
	 * @param input
	 *            The string to be hashed
	 * @return The hashed string
	 */
	private static String sha1(String input) {
		MessageDigest mDigest;
		StringBuffer sb = null;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes(Charset
					.forName("UTF-8")));
			sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			// Exception will not be thrown as long as MessageDigest.getInstance
			// inputs "SHA1"
			sb = new StringBuffer();
			sb.append("Unknown Error");
			e.printStackTrace();
		}

		return sb.toString();
	}

}
