<?php
include 'GetDistance.php';
$authenticationCode = $_POST['authenticationCode'];
$username = $_POST['username'];
$dbc = getConnection();

//Checks the device is authorized to communicate
$CheckAuthenticationStmt = $dbc->prepare("SELECT * FROM AuthorizedDevices
		 WHERE idAuthorizedCodes = ? AND DisableDevice = 0");
$CheckAuthenticationStmt ->bind_param('s', $authenticationCode);
$CheckAuthenticationStmt ->execute();
$CheckAuthenticationStmt ->store_result();

if ($CheckAuthenticationStmt ->num_rows==0){
	//Authentication not found
	$CheckAuthenticationStmt ->close();
	//Increment the number of failed attempts
	$Failedstmt = $dbc->prepare("UPDATE AuthorizedDevices SET FailedAttempt
			 = FailedAttempt + 1 WHERE Username = ?");
	$Failedstmt->bind_param('s', $username);
	$Failedstmt->execute();
	$Failedstmt->close();

	echo "-10";
	return;
}

		$status = $_POST['status'];
		if ($status == 'Y'){
			$status = 1;
		}else if ($status =='N'){
			$status = 0;
		}else{
			return;
		}
		
		
		$stmt = $dbc->prepare("UPDATE panelledJob SET status= ? WHERE jobNumber= ?");
		$stmt->bind_param('ii', $status, $_POST['jobNumber']);
		$stmt->execute();
		$stmt->close();
?>