<?php
include 'GetDistance.php';
$resetCode = $_POST['resetCode'];
$username = $_POST['username'];
$dbc = getConnection();

$stmt = $dbc->prepare("SELECT * FROM ResetCode WHERE Username = ? AND ResetCode = ?");

$stmt->bind_param('ss', $username, $resetCode);
$stmt->execute();
$stmt->store_result();
if ($stmt->num_rows==0){
	$stmt->close();
	echo "0";
}else{
	$stmt->close();
	echo "1";
}
	?>