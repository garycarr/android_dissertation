<?php
include 'GetDistance.php';
$userName = $_POST['userName'];
$accessCode = $_POST['accessCode'];
$allowedDate = date('Y-m-d H:i:s', strtotime('-13 hours'));
$dbc = getConnection();

$stmt = $dbc->prepare("SELECT * FROM AccessCode WHERE Username = ? AND AccessCode = ? AND AllowedTime > '$allowedDate'");

$stmt->bind_param('ss', $userName, $accessCode);
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