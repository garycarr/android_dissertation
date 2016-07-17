<?php
// Our parameters

include 'GetDistance.php';
$dbc = getConnection();

$result = mysqli_query($dbc, "Select * from locations WHERE name = 'Real driver'")or die(mysqli_error());
$row = mysqli_fetch_array($result);
$ambulanceLocation = $row['latitude'].", ".$row['longitude'];
$params = array(
		'origin'		=> "$ambulanceLocation",
		'destination'	=> "NE5 1SP",
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
//echo $test1;
	$test1 = str_replace("<div style=\"font-size:0.9em\">",". ",$test1);
    $test1 = str_replace("<b>","",$test1);
    $test1 = str_replace("\/",". ",$test1);
    
    mb_internal_encoding("UTF-8");
    mb_regex_encoding("UTF-8");
    
    $test1 = mb_ereg_replace("<\/div>","", $test1);
    $test1 = mb_ereg_replace("<\/b>","", $test1);
   // $test1 = mb_ereg_replace("\/","", $test1);
    $test1 = mb_ereg_replace("d/A","d A", $test1);
    $test1 = mb_ereg_replace(" Rd"," Road", $test1);
    
$myFile = "FirstStageJson.json";
$fh = fopen($myFile, 'a') or die("can't open file");
$first = '{"Contact_number":"07816 111 333","name":"James Jones","category":"R1","hNumber":"16 Glenhurst Drive","Post_Code":"NE5 1SP","Details":"Individual has had a fall at his home and is not responding. Has a history of heart conditions.  Possible cardiac arrest.","Distance":16,"endLat":55.0004723,"endLng":-1.7084701,"firstDirection":"'.$test1."\""."}";
fwrite($fh, $first);
fclose($fh);
$myFile = "SecondStageJson.json";
$fh = fopen($myFile, 'a') or die("can't open file");
$second = '{"idPatientDetails":4,"name":"Lisa Simpson","Blood_Type":"A","DOB":"2003-05-08","weight":"90kg","allergies":"Anaphylaxis SPLIT Anaphylaxis - An allergen is a substance which can cause an allergic reaction. While food is one of the most common allergens, medicine, insect stings, latex and exercise can also cause a reaction. SPLIT  Hayfever SPLIT Hayfever - Pollen is a fine powder released by plants as part of their reproductive cycle. Pollen contains proteins that can cause the nose, eyes, throat and sinuses (small air-filled cavities behind your cheekbones and forehead) to become swollen, irritated and inflamed. SPLIT Peanuts SPLIT Peanut allergy is a type of food allergy distinct from nut allergies. It is a type 1 hypersensitivity reaction to dietary substances from peanuts that causes an overreaction of the immune system which in a small percentage of people may lead to severe physical symptoms. It is estimated to affect 0.4-0.6% of the population of the United States.","medicine":"Diazepam SPLIT It is commonly used to treat anxiety, panic attacks, insomnia, seizures (including status epilepticus), muscle spasms (such as in tetanus cases), restless legs syndrome, alcohol withdrawal, benzodiazepine withdrawal, opiate withdrawal syndrome and Ménières disease. SPLIT Prozac SPLIT Prozac (fluoxetine) is a selective serotonin reuptake inhibitors (SSRI) antidepressant. Prozac affects chemicals in the brain that may become unbalanced and cause depression, panic, anxiety, or obsessive-compulsive symptoms. SPLIT Doxycycline SPLIT Doxycycline is a member of the tetracycline antibiotics group"}';
fwrite($fh, $second);
fclose($fh);
?>