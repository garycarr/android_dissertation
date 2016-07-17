<?php
function assignVariables(){
	$fromCity = $_POST["departure"];//Stores departure city
	$toCity = $_POST["destination"];//Stores destination city
	$print = 2;//Prints details of journey
	$day = $_POST["dayChoice"];//Stores whether a week or weekend
	$leave = $_POST["timeHour"] + $_POST["timeMinute"];//Sets leave after team

	if($_POST["timeMinute"]==0.001){//Necessary to distinguish between null and zero

		$leave = $leave-0.001;
	}
	if ((empty($fromCity) || empty($toCity)) || empty($_POST["timeHour"]) || empty($_POST["timeMinute"]) || $fromCity==$toCity){//If user
		//has inputted the same cities, or not entered a leave at time an error page is displayed
		echo '<div id="menucontainer">Error.  Please check 2 different cities have been selected, and that a leave after time has been chosen<br/>';
		echo "<br><a class=\"tryagain\" href=\"index.html\">Search again</a><br/></div>";
		die();
	}

	$return = null;//Stores return journey if times have been entered
	$noOfJourneys = 1;
	if($_POST["returnHour"]!=null && $_POST["returnMinute"]!=null){
		$return = $_POST["returnHour"] + $_POST["returnMinute"];
		$noOfJourneys=2;//If return journey is found, sets
	}

	if($_POST["returnMinute"]==0.001){
		$return = $return-0.001;//Necessary to distinguish between null and zero
	}

	return array ($fromCity, $toCity, $print, $leave, $day, $noOfJourneys, $return);
}

//Sets connection to the database
function connectDB(){
	$host='homepages.cs.ncl.ac.uk';
	$username='t8005t3';
	$password='Ash:Bray';
	$db_name='t8005t3';

	$dbc = mysqli_connect("$host", "$username", "$password", $db_name);
	if(!$dbc){
		die('Not connected :' . mysqli_error());
	}
	return $dbc;
}

//Selects all possible train routes and returns them as an array
function selectAllPossibleRoutes($dbc){

	$result = mysqli_query($dbc, "SELECT * FROM final_metro_cost")or die(mysqli_error());
	$routes = array();
	$i = 0;
	while($info = mysqli_fetch_array( $result ))  {
		$routes[$i] = array($info['fromStation'], $info['toStation'], $info['cost']);
		$i = $i + 1;
	}
	return $routes;
}


function displayResults($fromCity, $toCity, $numberOfStations, $startTime, $endTime){
	$departTime = number_format($startTime, 2, ':', '');
	$destTime = number_format($endTime, 2, ':', '');

	$quickest = round((($endTime-$startTime)*100));

	if(((floor($startTime))-floor($endTime))<0){
		$quickest = $quickest-40;
	}

	echo "<center>Departs at $fromCity at $departTime<br/>  </center>";
	echo "<center><br>Arrives at $toCity at $destTime<br/></center>";
	echo "<center><br>The quickest journey time is $quickest minutes <br/>";

	echo "<br><br/>";

}


?>