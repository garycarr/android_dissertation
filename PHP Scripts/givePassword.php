<?php
include 'GetDistance.php';

$userName = $_POST['userName'];
$password = $_POST['password'];
$allowedDate = date('Y-m-d H:i:s', strtotime('-30 days'));

$dbc = getConnection();

$CheckUsernameStmt = $dbc->prepare("SELECT * FROM passwords WHERE Username = ?");
$CheckUsernameStmt->bind_param('s', $userName);
$CheckUsernameStmt->execute();
$CheckUsernameStmt->store_result();

if ($CheckUsernameStmt->num_rows==0){
	//Username not found
	echo "0";
	$CheckUsernameStmt->close();
	return;
}

$CheckLockedstmt = $dbc->prepare("SELECT * FROM passwords WHERE Username = ? AND FailedAttempts <2");
$CheckLockedstmt ->bind_param('s', $userName);
$CheckLockedstmt ->execute();
$CheckLockedstmt ->store_result();

if ($CheckLockedstmt ->num_rows==0){
	//Account is locked
	$CheckLockedstmt ->close();
	echo "-1";
	return;
}

$allowedDate = date('Y-m-d H:i:s', strtotime('-30 days'));

$CheckDatestmt = $dbc->prepare("SELECT * FROM passwords WHERE Username = ? AND AllowedTime > '$allowedDate'");

$CheckDatestmt ->bind_param('s', $userName);
$CheckDatestmt ->execute();
$CheckDatestmt ->store_result();


if ($CheckDatestmt ->num_rows==0){
	//Account has expired
	$CheckDatestmt ->close();
	echo "-2";
	return;
}




$CheckPassword = $dbc->prepare("SELECT * FROM passwords WHERE Username = ? AND Password = ?");

$CheckPassword ->bind_param('ss', $userName, $password);
$CheckPassword ->execute();
$CheckPassword ->store_result();

//If password incorrect
if ($CheckPassword->num_rows==0){
	$CheckPassword->close();

	$Failedstmt = $dbc->prepare("UPDATE passwords SET FailedAttempts = FailedAttempts + 1 WHERE Username = ?");


	$Failedstmt->bind_param('s', $userName);
	$Failedstmt->execute();
	$Failedstmt->close();
	echo "0";
	return;
}


$CheckPassword->close();
echo "1";
?>