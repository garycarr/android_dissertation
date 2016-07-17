function validate() {
	//Empty message is created to store input errors
	var msg = "";
	
	//Score is created, set at zero
	var score = 0;
	
	//Inputted name is stored as variable
	var name = document.forms[0].UserInfo.value;

	//Drop down box value for Q1 is stored as a variable
	var q1 = document.forms[0].Q1.value;
	
	//Each check box for Q2 is stored as a variable
	var check1 = document.getElementById("Q2a");
	var check2 = document.getElementById("Q2b");
	var check3 = document.getElementById("Q2c");
	var check4 = document.getElementById("Q2d");	
	
	//Each radio button for Q3 is stored as a variable
	var radio1 = document.getElementById('Q3a');
	var radio2 = document.getElementById('Q3b');
	var radio3 = document.getElementById('Q3c');
	var radio4 = document.getElementById('Q3d');
	
	//Inputted text for Q4 is stored as a variable
	var q4 = document.forms[0].Q4.value;
	

	
	//Checks name is not blank.  If blank turns the Id yellow and adds error message to msg variable
	if (name == "") {
		document.getElementById("UserInfo").style.backgroundColor = "yellow";
		msg+="Please enter your name \n";
	}else{
	/*If this is the second time the user has clicked submit, and the first time they forgot the name but 
	second time entered name correctly, the id is returned to white. Necessary as another question may still not 
	have been completed*/
		document.getElementById("UserInfo").style.backgroundColor = "white";
	}
	
	//Checks Q1 is not blank.  If blank turns the Id yellow and adds error message to msg variable. If correct answer adds 1 to score
	if (q1== "") {
		document.getElementById("Q1").style.backgroundColor = "yellow";
		msg+="Please enter Q1 \n";
	} else if (q1 == "c") {
		document.getElementById("Q1").style.backgroundColor = "white";
		score++;
	}else{
	/*If this is the second time the user has clicked submit, and the first time they forgot to input but 
	second time entered correctly, the id is returned to white. Necessary as another question may still not 
	have been completed*/
	document.getElementById("Q1").style.backgroundColor = "white";
	}
	
	
	//Variable created to count the number of checked boxes for question 2
	var numberOfChecked = 0;
	
	//If box is checked 1 is added to numberOfChecked
	if (check1.checked){
	numberOfChecked++;
	}
	if (check2.checked){
	numberOfChecked++;
	}
	if (check3.checked){
	numberOfChecked++;
	}
	if (check4.checked){
	numberOfChecked++;
	}
	
	//Checks 2 boxes have been selected for Q2. If not turns the Id yellow and adds error message to msg variable
	if(numberOfChecked==2){
		
		//Adds 1 to score for each correct answer
		if (check1.checked){
			score++;
			}
		if (check3.checked){
			score++;
		}
		document.getElementById("Q2").style.backgroundColor = "white";
	}else{
		document.getElementById("Q2").style.backgroundColor = "yellow";
		msg+="Please check Q2 and select 2 check boxes  \n";
	}
	
	
	
	//Checks user has selected one box for Q3.  If not turns the Id yellow and adds error message to msg variable. If correct answer adds 1 to score
	if(!radio1.checked && !radio2.checked && !radio3.checked && !radio4.checked){
		document.getElementById("Q3").style.backgroundColor = "yellow";
		msg+="Please enter Q3 \n";
	}else if (radio2.checked){
		score++;
		document.getElementById("Q3").style.backgroundColor = "white";
	}else{
			/*If this is the second time the user has clicked submit, and the first time they forgot to input but 
			second time entered correctly, the id is returned to white. Necessary as another question may still not 
			have been completed*/
		document.getElementById("Q3").style.backgroundColor = "white";
	}
	
	
	//Q4 is changed to lower case (as caps do not matter for getting the answer right)
	q4 = q4.toLowerCase();
	
	
	//Checks Q4 is not blank.  If blank turns the Id yellow and adds error message to msg variable. If Q4 is spelled correct 1 is added to the score
	if (q4 == "") {
		document.getElementById("Q4").style.backgroundColor = "yellow";
		msg+="Please enter Q4 \n";
	} else if (q4 == "vexillology") {
		score++;
		document.getElementById("Q4").style.backgroundColor = "white";
	}else{
		/*If this is the second time the user has clicked submit, and the first time they forgot to input but 
		second time entered correctly, the id is returned to white. Necessary as another question may still not 
		have been completed*/
		document.getElementById("Q4").style.backgroundColor = "white";
	}

	
	/*If message is not empty a box has been missed, and an alert is sent to the user and false returned.  If message is still 
	empty then all boxes have been entered, and the score is outputted and the score stored in the hidden type*/
	if (!(msg == "")) {
		alert(msg);
		return false;
	}else{
		alert("Your score is " + score);
		document.forms[0]["thisScore"].value = score;
	}
}
