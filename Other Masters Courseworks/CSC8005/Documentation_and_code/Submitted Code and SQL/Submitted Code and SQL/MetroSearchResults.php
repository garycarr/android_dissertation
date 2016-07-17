<!DOCTYPE html>
<html>
<head>
<title>Metro Results</title>

<style type="text/css">
dt {
	cursor: pointer;
}
</style>

<script type="text/javascript">
    function showHide(objname){
      var obj = document.getElementById(objname);
      if(obj.style.display == 'block' || obj.style.display =='')
      obj.style.display = 'none';
      else
      obj.style.display = 'block';
      }
</script>

<link rel="stylesheet" href="Style2.css" type="text/css" media="screen" />
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
</head>
<body>

	<header>
		<div id="box">Metro Results</div>
	</header>

	<!--Unordered list to show the navigation bar-->

	<div class="container">
		<ul id="nav">

			<!--Link to MetroSearch page-->

			<li><a
				href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/index.html">Metro
					Search</a>
			</li>

			<!--Link to external websites for destinations-->

			<li><a href="">Our Destinations</a>

				<ul>
					<li><a target="_blank" href="http://www.newcastleairport.com/">Airport</a>
					</li>
					<li><a target="_blank" href="http://www.newcastlegateshead.com/">Gateshead</a>
					</li>
					<li><a target="_blank" href="http://www.gosforthlife.co.uk/">Gosforth</a>
					</li>
					<li><a target="_blank"
						href="http://www.en.wikipedia.org/wiki/Jarrow">Jarrow</a></li>
					<li><a target="_blank" href="http://www.newcastlegateshead.com/">Monument</a>
					</li>
					<li><a target="_blank"
						href="http://www.en.wikipedia.org/wiki/North_Shields">North
							Shields</a></li>
					<li><a target="_blank" href="http://www.visitsouthtyneside.co.uk/">Shiremoor</a>
					</li>
					<li><a target="_blank"
						href="http://www.en.wikipedia.org/wiki/Shiremoor">South Shields</a>
					</li>
					<li><a target="_blank"
						href="http://www.en.wikipedia.org/wiki/Wallsend">Wallsend</a></li>
					<li><a target="_blank"
						href="http://www.northumberland-coast.co.uk/">Whitley Bay</a></li>
				</ul>
			</li>

			<!--Link to team members individual profiles-->

			<li><a href="">The Team</a>

				<ul>
					<li><a
						href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/Chris.html">Chris
							Kerr</a></li>
					<li><a
						href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/Gary.html">Gary
							Carr</a></li>
					<li><a
						href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/Jazz.html">Jasjott
							Mattu</a></li>
					<li><a
						href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/Kefu.html">Kefu
							Li</a></li>
					<li><a
						href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/Antao.html">Antao
							Xu</a></li>
				</ul>
			</li>

			<!--Link to Metro Map-->

			<li><a
				href="http://homepages.cs.ncl.ac.uk/2012-13/csc8005_team3/Map.html">Metro
					Map</a>
			</li>

			<!--Link to team members individual email addresses-->

			<li><a href="">Contact us</a>

				<ul>
					<li><a href="mailto:c.kerr@newcastle.ac.uk">Chris Kerr</a></li>
					<li><a href="mailto:g.carr@newcastle.ac.uk">Gary Carr</a></li>
					<li><a href="mailto:j.mattu@newcastle.ac.uk">Jasjott Mattu</a></li>
					<li><a href="mailto:k.li5@newcastle.ac.uk">Kefu Li</a></li>
					<li><a href="mailto:a.xu2@newcastle.ac.uk">Antao Xu</a></li>
				</ul>
			</li>

			<!--Link to Newcastle University-->

			<li><a target="_blank" href="http://www.ncl.ac.uk/">Newcastle
					University</a>
			</li>

		</ul>
	</div>
	<br>
	<br>



	<?php
	include 'dijAlgorithm.php';
	include 'generalFunctions.php';
	include 'routeFinder.php';

	//Assigns variables user has inputted. Also checks if input is correct, if not loads error page
	//Assigns variables user has inputted. Also checks if input is correct, if not loads error page
	$assignVariables = assignVariables();
	$fromCity= $assignVariables[0];
	$toCity = $assignVariables[1];
	$print = 2;
	$leave = $assignVariables[3];
	$day= $assignVariables[4];
	$noOfJourneys = $assignVariables[5];
	$countJourneys=0;

	if ($day==0){
	$timeTable = 'final_metro_week_dep';
	}else{
	$timeTable = 'final_metro_weekend_dep';

}

//If a return journey is found loops twice
while($countJourneys<$noOfJourneys){

	//Reassigns variables for return journey
	if ($countJourneys==1 && $noOfJourneys==2){
		$fromCity= $assignVariables[1];
		$toCity = $assignVariables[0];
		$leave = $assignVariables[6];
	}


	//Connects to the database
	$dbc = connectDB();

	//Tests if database is connected
	if(!$dbc){
		die('Not connected :' . mysqli_error());
	}
	//Selects all possible train station routes
	$routes = selectAllPossibleRoutes($dbc);

	//Creates Dijkstras algorithm to find quickest routh
	$oDijk = new Dijkstra($fromCity,$routes);

	$paths = $oDijk->getPath($fromCity);

	//Loops through the stations between the journey.
	$displayLeave = number_format($leave, 2, ':', '');


	$outputFastestRoute =null;
	$fastestPath = determineStationsBetweenRoute($print, $fromCity, $toCity, $paths, $leave, $dbc, $countJourneys, $noOfJourneys, $timeTable);
	$outputFastestRoute = outputFastestRoute($print, $fromCity, $toCity, $fastestPath, $leave, $dbc, $countJourneys, $noOfJourneys, $timeTable);

	$numberOfStations = ($outputFastestRoute[0]-1);
	$startTime= $outputFastestRoute[1];
	$endTime=$outputFastestRoute[2];
		if ($countJourneys==0 && $noOfJourneys==1): ?>
	<div id="menucontainer">
		<dt onClick='showHide("items1_2")'>
			<b class="button">Click For Full Journey Details</b>
		</dt>
		<br>
		<code style='display: none' id='items1_2' align="center">
			<br>
			<?php outputFastestRoute(1, $fromCity, $toCity, $fastestPath, $leave, $dbc, $countJourneys, $noOfJourneys, $timeTable); ?>
		</code>
		<?php
		echo "<center><br><b>Journey from $fromCity to $toCity after $displayLeave</b></center><br/>";//If user has asked to print the full route journey, the below prints

		$distances = $oDijk->getDistance();

		//Displays the result of the journey
		displayResults($fromCity, $toCity, $numberOfStations, $startTime, $endTime);

		$countJourneys++;
		?>
	</div>

	<?php elseif ($countJourneys==0 && $noOfJourneys==2): ?>
	<div id="leftcontainer">
		<dt onClick='showHide("items1_2")'>
			<b class="button">Click For Full Journey Details</b>
		</dt>
		<br>
		<code style='display: none' id='items1_2' align="center">
			<br>
			<?php  outputFastestRoute(1, $fromCity, $toCity, $fastestPath, $leave, $dbc, $countJourneys, $noOfJourneys, $timeTable); ?>
		</code>
		<?php

		echo "<center><br><b>Journey from $fromCity to $toCity after $displayLeave</b></center><br/>";//If user has asked to print the full route journey, the below prints



		$distances = $oDijk->getDistance();

		//Displays the result of the journey
		displayResults($fromCity, $toCity, $numberOfStations, $startTime, $endTime);

		$countJourneys++;
		?>
	</div>

	<?php else: ?>
	<div id="leftcontainer">
		<dt onClick='showHide("items1_1")'>
			<b class="button">Click For Full Journey Details</b>
		</dt>
		<br>
		<code style='display: none' id='items1_1' align="center">
			<br>
			<?php  outputFastestRoute(1, $fromCity, $toCity, $fastestPath, $leave, $dbc, $countJourneys, $noOfJourneys, $timeTable); ?>
		</code>
		<?php

		echo "<center><br><b>Journey from $fromCity to $toCity after $displayLeave</b></center><br/>";//If user has asked to print the full route journey, the below prints

$distances = $oDijk->getDistance();
	
//Displays the result of the journey
displayResults($fromCity, $toCity, $numberOfStations, $startTime, $endTime);

$countJourneys++;	
	?>
	</div>

	<?php endif;} ?>

</body>
</html>