<?php
include 'GetDistance.php';
$validator = "-1";
$authenticationCode = $_POST['authenticationCode'];
$usernameString = $_POST['usernameString'];
$dbc = getConnection();
echo $usernameString;

$CheckAuthenticationStmt = $dbc->prepare("SELECT * FROM AuthorizedDevices WHERE Username = ?");
$CheckAuthenticationStmt ->bind_param('s', $usernameString);
$CheckAuthenticationStmt ->execute();
$CheckAuthenticationStmt ->store_result();

if ($CheckAuthenticationStmt ->num_rows==0){
	$CheckAuthenticationStmt ->close();
	echo $validator;
	return;
}



$dateTime = date("Y-m-d H:i:s");

$InsertStmt = $dbc->prepare("UPDATE AuthorizedDevices SET idAuthorizedCodes = ?, AllowedTime = ? WHERE Username = ?");
$InsertStmt->bind_param('sss', $authenticationCode, $dateTime, $usernameString);
$InsertStmt->execute();
$InsertStmt->close();

$allowedDate = date('Y-m-d H:i:s', strtotime('-13 hours'));
$DeleteStmt = $dbc->prepare("UPDATE AuthorizedDevices SET idAuthorizedCodes = NULL WHERE AllowedTime < ?");
$DeleteStmt->bind_param('s', $allowedDate);
$DeleteStmt->execute();
$DeleteStmt->close();
$validator = "1";
echo $validator;
?>