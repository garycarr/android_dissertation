<?php

	function getConnection(){
	 		$host='homepages.cs.ncl.ac.uk';
		$username='b2052238';
		$password='Eye[4Lag';
		$db_name='b2052238';
		
		$con = mysqli_connect("$host", "$username", "$password", $db_name);
		if(!$con){
			die('Not connected :' . mysqli_error());
		}
 return $con;
 }

	function getDistance($ambulanceLocation, $postCode, $name){
	// Our parameters
		$params = array(
		'origin'		=> "$ambulanceLocation",
		'destination'	=> "$postCode",
		'sensor'		=> 'true',
		'units'			=> 'imperial'
	);
		
	$builder=null;
	
	// Join parameters into URL string
	foreach($params as $var => $val){
   		$builder .= '&' . $var . '=' . urlencode($val);  
	}

	// Request URL
	$url = "http://maps.googleapis.com/maps/api/directions/json?".ltrim($builder, '&');
	
	// Make our API request
	$curl = curl_init();
	curl_setopt($curl, CURLOPT_URL, $url);
	curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
	$return = curl_exec($curl);
	curl_close($curl);
	
	// Parse the JSON response
	$directions = json_decode($return);
	//echo "<br></br>";
	//print_r($return);
	if (isset($directions->routes[0]->legs[0]->steps[1]->html_instructions)) {
		$test1 = $directions->routes[0]->legs[0]->steps[1]->html_instructions;
	}else{
		$test1 = $directions->routes[0]->legs[0]->steps[0]->html_instructions;
	}


	$_SESSION["endLat"] = $directions->routes[0]->legs[0]->end_location->lat;
	$_SESSION["endLng"] = $directions->routes[0]->legs[0]->end_location->lng;


	
	$distance = $directions->routes[0]->legs[0]->distance->text;
	$split = explode(" ", $distance);
	$float = (float)$split[0];
	echo "<td>".$float."</td></tr>".PHP_EOL;
	$_SESSION["ambulanceDriver".$name] = $float;
	$_SESSION["ambulanceDriver".$name."firstDirection"] = $test1;
	}
?>