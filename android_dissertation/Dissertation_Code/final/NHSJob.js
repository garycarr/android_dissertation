function showdiv(){
document.getElementById('typeFullName').style.display = "block";
document.getElementById('findingFullName').style.display = "none";
document.getElementById('manualFullName').style.display = "none"
}



function validate() {
	//Empty message is created to store input errors
	var msg = "";
	//Entries saved as variables
	var q1 = document.forms[0].q1Name.value;
	var q2 = document.forms[0].q2Address.value;
	var q3 = document.forms[0].q3PostCode.value;
	var q4 = document.forms[0].q4Details.value;
	var category = document.forms[0].q2Category.value;
	var check1 = document.getElementById("patientNotFound");
	
	var $ambulanceNum = 0;
	
	var ambulanceRadio = document.getElementsByName('driver');
	for( i = 0; i < ambulanceRadio.length; i++ ) {
       if( ambulanceRadio[i].checked ) {
    	   $ambulanceNum = $ambulanceNum + 1;
      }
    }
    
    if (!$ambulanceNum==1){
    	msg+="Select an ambulance driver \n";
    	document.getElementById("ambulanceLocation").style.backgroundColor = "yellow";
    }else{
    	document.getElementById("ambulanceLocation").style.backgroundColor = "white";
    	}
    
    	    if (category==""){
    	msg+="Select a category \n";
    	document.getElementById("pCategory").style.backgroundColor = "yellow";
    }else{
    	document.getElementById("ambulanceLocation").style.backgroundColor = "white";
    	}
    
var $patientNum = 0;    

    var patientRadio = document.getElementsByName('patient');
	for( i = 0; i < patientRadio.length; i++ ) {
       if( patientRadio[i].checked ) {
    	   $patientNum = $patientNum + 1;
      }
    }
    
    if (!$patientNum==1 && !check1.checked){
    	msg+="Choose a person \n";
    	document.getElementById("findingFullName").style.backgroundColor = "yellow";
    }else{
    	document.getElementById("findingFullName").style.backgroundColor = "white";
    	}
    
    
  
    	
    

	
	//	Checks Q1 is not blank.  If blank turns the Id yellow and adds error message to msg variable. If correct answer adds 1 to score
	if (q1=="" && check1.checked) {
		msg+="Type a name manually, or enter unknown \n";
		document.getElementById("pName").style.backgroundColor = "yellow";
	} else  {
		document.getElementById("pName").style.backgroundColor = "white";
	}

	//	Checks Q2 is not blank.  If blank turns the Id yellow and adds error message to msg variable. If correct answer adds 1 to score
	if (q2== "") {
		msg+="Enter the address \n";
		document.getElementById("pAddress").style.backgroundColor = "yellow";
	} else  {
		document.getElementById("pAddress").style.backgroundColor = "white";
	}
	
	//	Checks Q3 is not blank.  If blank turns the Id yellow and adds error message to msg variable. If correct answer adds 1 to score
	if (q3== "") {
		msg+="Enter the post code \n";
		document.getElementById("pPostCode").style.backgroundColor = "yellow";
	} else  {
		//Regular expression obtained from http://www.qodo.co.uk/blog/javascript-check-if-a-uk-postcode-is-valid/
		var postcode = /[A-Z]{1,2}[0-9]{1,2} ?[0-9][A-Z]{2}/i; 
		if(postcode.test(q3)){
			document.getElementById("pPostCode").style.backgroundColor = "white";
		}else{
			msg+="Incorrect post code format entered \n";
			document.getElementById("pPostCode").style.backgroundColor = "yellow";
		}
	}
			
	//	Checks Q4 is not blank.  If blank turns the Id yellow and adds error message to msg variable. If correct answer adds 1 to score
	if (q4== "") {
		msg+="Enter the details \n";
		document.getElementById("pDetails").style.backgroundColor = "yellow";
	} else  {
		document.getElementById("pDetails").style.backgroundColor = "white";
	}
	

	
	/*If message is not empty a box has been missed, and an alert is sent to the user and false returned.  If message is still 
	empty then all boxes have been entered, and the score is outputted and the score stored in the hidden type*/
	if (!(msg == "")) {
	alert(msg);
	return false;
	}else{
		return true;
}
}

function ambulanceDistance()
 {
 var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
 var postCode = document.forms[0].q3PostCode.value;
	   var postCodeTest = /[A-Z]{1,2}[0-9]{1,2} ?[0-9][A-Z]{2}/i; 
		if(!postCodeTest.test(postCode)){
			document.getElementById('errorPostCode').style.display = "block";
			return;
   } else{
	   document.getElementById('errorPostCode').style.display = "none";
   }
   

   xmlhttp.onreadystatechange=function()
   {
   if (xmlhttp.readyState==4 && xmlhttp.status==200)
     {
	document.getElementById("ambulanceSearch").innerHTML=""
   document.getElementById("ambulanceLocation").innerHTML=xmlhttp.responseText;
     }
   }
xmlhttp.open("GET","getAmbulanceLocation.php?postCode="+postCode, true);
xmlhttp.send();

 }
 
 
 function getPatientNames()
 {
 var xmlhttp;
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  
 var postCode = document.forms[0].q3PostCode.value;
 var hNumber = document.forms[0].q2Address.value;

 var postCode = document.forms[0].q3PostCode.value;
	   var postCodeTest = /[A-Z]{1,2}[0-9]{1,2} ?[0-9][A-Z]{2}/i; 
	   if(!postCodeTest.test(postCode)){
			document.getElementById('errorPostCode').style.display = "block";
			return;
  } else{
	   document.getElementById('errorPostCode').style.display = "none";
  }
 if (hNumber=="")
   {
	   document.getElementById('errorAddress').style.display = "block";
	   return;
   }else{
 
	   document.getElementById('errorAddress').style.display = "none";
   }   



if (window.XMLHttpRequest)
   {// code for IE7+, Firefox, Chrome, Opera, Safari
   xmlhttp=new XMLHttpRequest();
   }
 else
   {// code for IE6, IE5
   xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
   }
 xmlhttp.onreadystatechange=function()
   {
   if (xmlhttp.readyState==4 && xmlhttp.status==200)
     {
     document.getElementById("patientNames").innerHTML=xmlhttp.responseText;
     }
   }
xmlhttp.open("GET","getPatientDetails.php?postCode="+postCode + "&hNumber=" + hNumber, true);
xmlhttp.send();
 }