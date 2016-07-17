<?php
include 'GetDistance.php';
session_start();
 $htmlDoc0 = <<<HTML
 <p>Pick Patient</p> 
<table id="myDummyTable1" class="tablesorter" border="1" cellpadding="0" cellspacing="1">
<thead>
<tr>
<th>Select</th>
<th>Patient name</th>
<th>DOB</th></tr>
</thead>
<tbody>	
HTML;
		
		$htmlDoc1 = <<<HTML
<tr><td><input type="radio" name="patient" id="patientName
HTML;
		$htmlDoc2 = <<<HTML
" value="
HTML;
		$htmlDoc3 = <<<HTML
" /></td>
HTML;
 		$postCode=$_GET["postCode"];
 		$hNumber=$_GET["hNumber"];
 		
		$dbc = getConnection();
		
		$sql = "Select * from patientDetails WHERE Post_Code = ? AND hNumber = ?";
		$result = $dbc->prepare($sql);
		$result->bind_param("ss", $postCode, $hNumber);

		$result -> execute();
		$result->bind_result($idPatientDetails, $Name, $hNumber, $Post_Code, $Blood_Type, $DOB);
		$result->store_result();
		//$result = mysqli_query($dbc, "Select * from patientDetails WHERE Post_Code = '$postCode' AND hNumber = '$hNumber'")or die(mysqli_error());
		$checkEmpty = $result->num_rows;
		
		if($checkEmpty>0){
		echo $htmlDoc0;

	while($result->fetch())
 		 {
  		 echo $htmlDoc1.$idPatientDetails.$htmlDoc2.$idPatientDetails.$htmlDoc3.PHP_EOL;
  		 echo "<td>".$Name."</td>".PHP_EOL;
  		 echo "<td>".$DOB."</td>".PHP_EOL; 	
  		 //$row = array ($idPatientDetails, $Name, $hNumber, $Post_Code, $Blood_Type, $DOB);
 		 //$_SESSION["patDetails".$idPatientDetails] = $row;
 		 $row1 = array ("idPatientDetails" => $idPatientDetails, "name" => $Name, "Blood_Type" =>  $Blood_Type, "DOB" =>   $DOB);
 		 $_SESSION["patDetails1".$idPatientDetails] = $row1;
 		 }
 		 
 		 echo "</tbody>";
 		 echo "</table>";
		}else{
			echo "No patient found at the address. Enter manually TO DO - change div automatically";
		}
?>