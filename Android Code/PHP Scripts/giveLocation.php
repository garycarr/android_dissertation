<?php
include 'GetDistance.php';

$longitude = $_POST['longitude'];
$latitude = $_POST['latitude'];
$name = "Real driver";

$dbc = getConnection();

$stmt = $dbc->prepare("UPDATE locations SET longitude= ?, latitude=? WHERE name= ?");
$stmt->bind_param('sss', $longitude, $latitude, $name);
$stmt->execute();
$stmt->close();
?>