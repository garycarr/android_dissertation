<?php
$myFile = "FirstStageJson.json";
$fh = fopen($myFile, 'a') or die("can't open file");
$first = '{"Contact_number":"07816 111 333","name":"Lisa Simpson","category":"G1","hNumber":"66 Broad Ash","Post_Code":"NE2 1pz","Details":"Individual has had a fall at his home and is not responding. Has a history of heart conditions.  Possible cardiac arrest.","Distance":1.9, "endLat":54.9844414,"endLng":-1.5932085,"firstDirection":"Turn left to stay on Greystoke Gardens"}';
fwrite($fh, $first);
fclose($fh);

?>