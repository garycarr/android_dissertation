<?php
include 'GetDistance.php';

$deviceID = $_POST['deviceID'];
$ambulanceCrew = $_POST['driverID'];



$dateTime = date("Y-m-d H:i:s");

$dbc = getConnection();

$stmtDevice = $dbc->prepare("SELECT * FROM Device WHERE DeviceID = ?");

$stmtDevice->bind_param('s', $deviceID);
$stmtDevice->execute();
$stmtDevice->store_result();

if ($stmtDevice->num_rows==0){
	$stmtDevice->close();
	echo "0";
	return;
}

$stmtDevice->close();
	
$stmtCrew = $dbc->prepare("SELECT * FROM AccessCode WHERE Username = ?");

$stmtCrew->bind_param('s', $ambulanceCrew);
$stmtCrew->execute();
$stmtCrew->store_result();
	
if ($stmtCrew->num_rows==1){
		
	$stmtCrew->close();
	$secureNumber = mt_rand(100000000, 1000000000);
	$stmtCrew = $dbc->prepare("UPDATE AccessCode SET AccessCode= ?, AllowedTime= ? WHERE UserName = ?");
	$stmtCrew->bind_param('iss', $secureNumber, $dateTime, $ambulanceCrew);
	$stmtCrew->execute();
	$stmtCrew->close();
	echo $secureNumber;
	$secureNumber=null;
	
}else{
	$stmtCrew->close();
	echo "0";
}

?>
