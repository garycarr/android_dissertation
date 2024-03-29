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



$file = "FirstStageJson.json";
if ( 0 == filesize( $file ) ){
	echo "0";
}else{
$f = fopen($file, 'r');
$line = fgets($f);
fclose($f);
echo $line;
}
?>