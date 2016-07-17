<?php
	include 'GetDistance.php';
 
 $htmlDoc0 = <<<HTML
 <p>Pick ambulance driver</p> 
<table id="myDummyTable" class="tablesorter" border="1" cellpadding="0" cellspacing="1">
<thead>
<tr>
<th>Select</th>
<th>Driver name</th>
<th>Distance - Miles</th></tr>
</thead>
<tbody>	
HTML;
		
		$htmlDoc1 = <<<HTML
<tr><td><input type="radio" name="driver" id="name
HTML;
		$htmlDoc2 = <<<HTML
" value="
HTML;
		$htmlDoc3 = <<<HTML
" /></td>
HTML;
		
		$dbc = getConnection();
		
 		$result = mysqli_query($dbc, "Select * from locations")or die(mysqli_error());
		$postCode=$_GET["postCode"];
		echo $htmlDoc0;
		session_start();
	while($row = mysqli_fetch_array($result))
 		 {
 		 echo $htmlDoc1.$row['name'].$htmlDoc2.$row['name'].$htmlDoc3.PHP_EOL;
 		 echo "<td>".$row['name']."</td>".PHP_EOL;
 		 if($row['longitude']==null || $row['latitude']==null){
 		 	echo "<td>Unknown</td>".PHP_EOL;
 		 	$_SESSION["ambulanceDriver".$row['name']."firstDirection"] = "";
 		 	$_SESSION["ambulanceDriver".$row['name']] = "Unknown";
 		 }else{
 		 $concatLongLat = $row['latitude'].", ".$row['longitude'];
 		  	
 		 getDistance($concatLongLat, $postCode, $row['name']);
 		 }
 		 }
 		 echo "</tbody>";
 		 echo "</table>";
	
?>