<link rel="stylesheet" href="Style2.css" type="text/css" media="screen" />
<?php
//Finds the stations passed between the departure and destination city
function determineStationsBetweenRoute($print, $fromCityCopy, $toCity, $fastestPath, $leaveCopy, $dbc, $countJourneys, $noOfJourneys, $timeTable){

	$origLeave = $leaveCopy;
	$counter = 0;
	$noCount = count($fastestPath);
	$fastestTimeOverall = 300;
	$fastestArray= null;
	while($counter<$noCount){

		//Variables must be reset after each counterloop as they are overwritten during the loop
		$fromCity= $fromCityCopy;
		$leave=$leaveCopy;
		$startTime;
		$noOfStations=0;//Counts the number of stations passed through
		$count = 0;//Variable to find current and next city
		$startTime = 0;//To save the start time of the journey

		foreach ($fastestPath as $key=> $value) {
			if ($key==$toCity){
					
				$leave=$leaveCopy;
				$count = 0;

				$value = array_reverse($value);

				foreach ($value as $k => $cityId) {

					$noOfArrays = count ($value);
					$fromThisCity= $value[0];

					//If more than 2 stations found, enters here to loop through each route.  Does not enter once final 2 stations are reached
					if (($count+1)<$noOfArrays){
						$moreThanTwoStationsFound=moreThanTwoStationsFound($dbc, $leave, $count, $value, $timeTable);

						$noOfRows= $moreThanTwoStationsFound[0];
						$routeID = $moreThanTwoStationsFound[1];
						$transferFirstDep = $moreThanTwoStationsFound[2];
						$transferFetchCost= $moreThanTwoStationsFound[3];
						$dep=$moreThanTwoStationsFound[4];
							
						if ($count==0){
							$startTime = $dep;
						}
						$times = (($dep+$transferFetchCost[1])-0.02);
						$leave = $times;
					}else{
						//Gets details of the final 2 train stations
						$finalTwoStations = finalTwoStations($dbc, $value, $count, $leave, $toCity, $timeTable);
						$noOfRows= $finalTwoStations[0];
						$routeID = $finalTwoStations[1];
						$transferSecondDep = $finalTwoStations[2];
						$transferFetchCost= $finalTwoStations[3];
						$dep=$finalTwoStations[4];
					}

					//Displays no route found if there is no onward journey after the time
					//If first iteration saves original start time
					if ($count ==0 ){
						$startTime=$dep;
					}
					//Stores the destination time of the journey
					$finalTime = (($transferFetchCost[1]+$dep)-0.02);

					//Prints details of final station route
					$fastestTime =extra_journey_details($value, $count, $dep, null, 2, $finalTime, $toCity, $startTime);
					if ($fastestTime>0){
						if($fastestTime<$fastestTimeOverall){
							$fastestTimeOverall = $fastestTime;
							$fastestArray = $value;
						}
					}
				}
			}$counter++;
		}$counter++;
	}
	return $fastestArray;
}

function outputFastestRoute($print, $fromCityCopy, $toCity, $fastestPath, $leaveCopy, $dbc, $countJourneys, $noOfJourneys, $timeTable){
	$origLeave = $leaveCopy;
	$leave = $leaveCopy;

	$counter = 0;
	//$fastestTimeOverall = 300;
	$count = 0;//Variable to find current and next city

	//	Variables must be reset after each counterloop as they are overwritten during the loop
	$fromCity= $fromCityCopy;
	$startTime;

	$noOfStations=0;//Counts the number of stations passed through

	$startTime = 0;//To save the start time of the journey

	$fromCity= $fromCityCopy;
	$count = 0;
	$noOfArrays = count ($fastestPath);
	foreach ($fastestPath as $k => $cityId) {
		//If more than 2 stations found, enters here to loop through each route.  Does not enter once final 2 stations are reached
		if (($count+1)<$noOfArrays){
			$moreThanTwoStationsFound=moreThanTwoStationsFound($dbc, $leave, $count, $fastestPath, $timeTable);
			$noOfRows= $moreThanTwoStationsFound[0];
			$routeID = $moreThanTwoStationsFound[1];
			$transferFirstDep = $moreThanTwoStationsFound[2];
			$transferFetchCost= $moreThanTwoStationsFound[3];
			$dep=$moreThanTwoStationsFound[4];

			//Displays no route found if there is no onward journey after the time
			if ($noOfRows==0){
				noRouteFound($k, $count, $leave, $origLeave, $fromCity, $toCity, $countJourneys);
			}


			//On first iteration saves the original start time
			if ($count==0){
				$startTime = $dep;
			}


			//If user has selected to see the full journey then the delay times are printed
			if($count>0){
				if ($print==1){
					delayCost($dep, $times, $fastestPath, $count);
				}
			}
			$times = (($dep+$transferFetchCost[1])-0.02);
			if (($times-(floor($times)))>0.6){
				$times=$times+0.4;
			}
			$leave = $times;
			if ($print==1){
				extra_journey_details($fastestPath, $count, $dep, $times, 0, null, null, $startTime);
			}
		}else{
			//Gets details of the final 2 train stations
			$finalTwoStations = finalTwoStations($dbc, $fastestPath, $count, $leave, $toCity, $timeTable);
			$noOfRows= $finalTwoStations[0];
			$routeID = $finalTwoStations[1];
			$transferSecondDep = $finalTwoStations[2];
			$transferFetchCost= $finalTwoStations[3];
			$dep=$finalTwoStations[4];
			//Displays no route found if there is no onward journey after the tim
			if ($noOfRows==0){
				noRouteFound($fastestPath, $count, $leave, $origLeave, $fromCity, $toCity, $countJourneys);
			}

			//If first iteration saves original start time
			if ($count ==0 ){
				$startTime=$dep;
			}

			$times = (($dep+$transferFetchCost[1])-0.02);

			//Prints delay time at station
			if($noOfArrays>1){
				if ($print==1){
					delayCost($dep, $leave, $fastestPath, $count);
				}
			}

			//Stores the destination time of the journey
			$finalTime = (($transferFetchCost[1]+$dep)-0.02);

			if ($print==1){
				extra_journey_details($fastestPath, $count, $dep, $times, 1, $finalTime, $toCity, $startTime);
			}



		}
		$noOfStations++;
		$count++;
	}
	return array($noOfStations, $startTime, $finalTime);
}


//Prints the delay time at each station
function delayCost($dep, $times, $value, $count){

	$dep = number_format($dep, 2, '.', '');
	$times = number_format($times, 2, '.', '');
	$depCalc=$dep*100;
	$timeCalc=$times*100;

	$answer= $depCalc-$timeCalc;
	$answer = floor($answer);
	if(((floor($times))-floor($dep))<0){
		$answer = $answer -40;
	}
	if (($answer)>2.01){
		echo "<center> Change at ".$value[($count)].". Delay of ".(round($answer))." minutes</center>";
	}else if($answer<2.01){
		echo "<center> Stay on train at ".$value[($count)]."</center>";
	}
}

//This function is called if there are more than two stations to pass through on the journey to determine a journey between stations
function moreThanTwoStationsFound($dbc, $leave, $count, $value, $timeTable){
	$transferCost = mysqli_query($dbc, "SELECT pathID, cost  FROM final_metro_cost WHERE fromStation = '$value[$count]' AND toStation = '".$value[($count+1)]."'")or die(mysqli_error());
	$transferFetchCost = mysqli_fetch_array( $transferCost);
	$routeID = $transferFetchCost[0];
	$transfer= mysqli_query($dbc, "SELECT leavesAt FROM final_metro_week_dep WHERE pathID = '$routeID' AND leavesAt >= '$leave' order by leavesAt Asc" )or die(mysqli_error());
	$noOfRows = mysqli_num_rows($transfer);
	$transferFetchDep= mysqli_fetch_array( $transfer);
	$depString=$transferFetchDep[0];
	$dep= floatval($depString);

	return array ($noOfRows, $routeID, $transfer, $transferFetchCost, $dep);
}

//This function is called if there are only 2 stations left on the journey to determine a journey between stations
function finalTwoStations($dbc, $value, $count, $leave, $toCity, $timeTable){
	$transferCost = mysqli_query($dbc, "SELECT pathID, cost  FROM final_metro_cost WHERE fromStation = '$value[$count]' AND toStation = '".$toCity."'")or die(mysqli_error());
	$transferSecondFetchCost = mysqli_fetch_array( $transferCost);
	$routeID = $transferSecondFetchCost[0];
	$transfer= mysqli_query($dbc, "SELECT leavesAt FROM $timeTable WHERE pathID = '$routeID' AND leavesAt >= '$leave' order by leavesAt Asc" )or die(mysqli_error());
	$transferSecondFetchDep= mysqli_fetch_array( $transfer);
	$depString=$transferSecondFetchDep[0];
	$dep= floatval($depString);

	$noOfRows = mysqli_num_rows($transfer);
	return array($noOfRows, $routeID, $transfer, $transferSecondFetchCost, $dep);
}

//No route is possible for the journey, and a message is displayed to the user
function noRouteFound($value, $count, $leave, $origLeave, $fromCity, $toCity, $countJourneys){
	$origLeave = number_format($leave, 2, '.', '');

	echo <<<XU
	<div class="container"><center>No journey from $fromCity to $toCity after $origLeave<center>
XU;
	echo "<br><a class=\"tryagain\" href=\"index.html\">Search again</a><br/></div>";

	die();
}
//Prints the details between the stations
function extra_journey_Details($value, $count, $dep, $times, $option, $finalTime, $toCity, $startTime){
	if ($option==0){

		if ($dep-(floor($dep))>0.59){
			$dep= $dep + 0.4;
		}
		if ($times-(floor($times))>0.59){
			$times= $times + 0.4;
		}
		$dep = number_format($dep, 2, ':', '');
		$times = number_format($times, 2, ':', '');

		echo "<center> Leaves ".$value[$count]." at ".$dep."</center>";
		echo "<center> Arrives ".$value[($count+1)]. " at ".$times."</center><br/>";
	}else if ($option==1){
		$dep = number_format($dep, 2, ':', '');
		$finalTime = number_format($finalTime, 2, ':', '');
		echo "<center> Leaves ".$value[$count]." at ".$dep."</center>	";
		echo "<center>Arrives  at $toCity at $finalTime</center><br/>";
		($finalTime-$startTime);

	}else{
		$answer = ($finalTime-$startTime);
		return ($answer);
	}
}


?>