<?php
$myFile = "FirstStageJson.json";
$fh = fopen($myFile, 'a') or die("can't open file");

    session_start();
    
    $driver = $_POST['driver'];
    $firstDirection = $_SESSION["ambulanceDriver".$driver."firstDirection"];
    $firstDirection = str_replace("<div style=\"font-size:0.9em\">",". ",$firstDirection);
    $firstDirection = str_replace("<b>","",$firstDirection);
    $firstDirection = str_replace("\/",". ",$firstDirection);
    
    mb_internal_encoding("UTF-8");
    mb_regex_encoding("UTF-8");
    
    $firstDirection = mb_ereg_replace("<\/div>","", $firstDirection);
    $firstDirection = mb_ereg_replace("<\/b>","", $firstDirection);
    // $firstDirection = mb_ereg_replace("\/","", $firstDirection);
    $firstDirection = mb_ereg_replace("d/A","d A", $firstDirection);
    $firstDirection = mb_ereg_replace(" Rd"," Road", $firstDirection);
    
    echo $firstDirection;
	
	if(isset($_POST['patientNotFound'])){
		$endLat = $_SESSION["endLat"];
		$endLng = $_SESSION["endLng"];
		
		$distance = $_SESSION["ambulanceDriver".$driver];
		
		$firstDetails = array("name" => $_POST['q1Name'], "category" => $_POST['q2Category'], "hNumber" => $_POST['q2Address'], "Post_Code" => $_POST['q3PostCode'], "Details" => $_POST['q4Details'], "Distance" => $distance, "endLat" => $endLat, "endLng" =>$endLng, "firstDirection" =>$firstDirection);
		
	fwrite($fh, json_encode($firstDetails)."\n");
	fclose($fh);
	
	$myFile = "SecondStageJson.json";
	$fh = fopen($myFile, 'a') or die("can't open file");
	$string = "Not Found \n";
	fwrite($fh, $string);
	fclose($fh);
	
	

	
		}else{
	
	$patient = $_POST['patient'];
	$driver = $_POST['driver'];
	$patientDetails = $_SESSION["patDetails1".$patient];
	$distance = $_SESSION["ambulanceDriver".$driver];
	$endLat = $_SESSION["endLat"];
	$endLng = $_SESSION["endLng"];
	$firstDirection = $_SESSION["ambulanceDriver".$driver."firstDirection"];
	//$firstDirection = strip_tags($firstDirection);
	$firstDirection = str_replace("<div style=\"font-size:0.9em\">",". ",$firstDirection); 
	$firstDirection = str_replace("<b>","",$firstDirection);
	//$firstDirection = str_replace("<\/b>","",$firstDirection);
	$firstDirection = str_replace("\/",". ",$firstDirection);
	//$firstDirection = str_replace("<\/div>",". ",$firstDirection);
	
	mb_internal_encoding("UTF-8");
	mb_regex_encoding("UTF-8");
	
	$firstDirection = mb_ereg_replace("<\/div>","", $firstDirection);
	$firstDirection = mb_ereg_replace("<\/b>","", $firstDirection);
	$firstDirection = mb_ereg_replace("\/","", $firstDirection);
	
	
	$firstDetails = array("name" => $patientDetails['name'], "category" => $_POST['q2Category'], 
			"hNumber" => $_POST['q2Address'], "Post_Code" => $_POST['q3PostCode'], "Details" => $_POST['q4Details'], 
			"Distance" => $distance, "endLat" => $endLat, "endLng" =>$endLng, "firstDirection" =>$firstDirection);
	fwrite($fh, json_encode($firstDetails)."\n");
	fclose($fh);
	
	
	
	$myFile = "SecondStageJson.json";
	$fh = fopen($myFile, 'a') or die("can't open file");
	fwrite($fh, json_encode($patientDetails)."\n");
	fclose($fh);
}


$html0 = <<<HTML
Job Submitted
<input type="button" value="Create new job" onclick="location.href='CreateJob';">
HTML;

echo $html0;
?>
