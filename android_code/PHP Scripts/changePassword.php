<?php
include 'GetDistance.php';

$username = $_POST['username'];
$password = $_POST['password'];
$dbc = getConnection();
$dateTime = date("Y-m-d H:i:s");

//Checks if the password is the same as the previous password
$CheckDuplicateStmt = $dbc->prepare("Select * FROM passwords Where Username = ? AND Password = ?");
$CheckDuplicateStmt->bind_param('ss', $username, $password);
$CheckDuplicateStmt->execute();
$CheckDuplicateStmt->store_result();
if ($CheckDuplicateStmt->num_rows==1){
$CheckDuplicateStmt->close();
echo"-2";
	return;
}
$CheckDuplicateStmt->close();


$UpdateStmt = $dbc->prepare("UPDATE passwords SET Password = ?, FailedAttempts = 0, AllowedTime = '$dateTime' WHERE Username = ?");
$UpdateStmt->bind_param('ss', $password, $username);
$UpdateStmt->execute();
$UpdateStmt->store_result();
$UpdateStmt->close();

echo "1";
return;
	?>