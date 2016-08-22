/**
* @brief permet de faire glisser le curseur sur le texte
**/
function next(){
	var current = document.getElementById('current').value;
	var next = parseInt(current)+1;
	var size = document.getElementById('size').value;

	addValue(current, next);

	if(document.getElementById(next).innerHTML){
		var wordCurrent = document.getElementById(next).innerHTML;
		document.getElementById(current).className = "";

		while(next < size && wordCurrent ==',' || wordCurrent == '?'){
			next = parseInt(next)+1;
			wordCurrent = document.getElementById(next).innerHTML;
		}

		document.getElementById(next).className = "active";
		document.getElementById('current').value = next;
	}
	else
		alert("fin du texte");

	document.getElementById("pos").checked = false;
	document.getElementById("neutre").checked = false;
	document.getElementById("neg").checked = false;
	document.getElementById("ind").checked = false;
}

/**
* @brief Permet de préparer les données pour les envoyer au script PhP
* @param current contient les informations du curseur
**/
function addValue(current){
	var pos = document.getElementById("pos");
	var neutre = document.getElementById("neutre");
	var neg = document.getElementById("neg");
	var ind = document.getElementById("ind");
	var check = true;

	var courrant = document.getElementById(current).innerHTML;
	alert(typeof document.getElementsByClassName(activeS));
	if(current <= 0)
		var pred = "~";
	else
		if(typeof document.getElementsByClassName(activeS) !== 'undefined')
			var pred = document.getElementsByClassName(activeS).innerHTML;
		else if(typeof document.getElementsByClassName(inactiveS) !== 'undefined')
			var pred = document.getElementsByClassName(activeS).innerHTML;
		else
			check = false;

	if(pos.checked){
		var val = "pos";
	}
	else if(neutre.checked){
		var val = "neutre";
	}
	else if(neg.checked){
		var val = "neg";
	}
	else if(ind.checked){
		var val = "ind";
	}
	if(check == true)
		database(val, pred, courrant);
}
/**
* @brief appel le script PhP qui s'occupe de l'insertion des données en BDD
* @param val valeur (positif, neutre, négatif, inconnue)
* @param pred contient le mot précédent (sera remplacé par un mot choisit par l'utilisateur)
* @param courrant le mot sur lequel ce trouve le marqueur
**/
function database(val, pred, courrant){
	if(window.XMLHttpRequest){
		xmlhttp=new XMLHttpRequest();
	}
	else{
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
	}

	xmlhttp.onreadystatechange=function() {
    	if (xmlhttp.readyState==4 && xmlhttp.status==200) {
    		document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("GET","data.php?val="+val+"&courrant="+courrant+"&pred="+pred,true);
	xmlhttp.send();
}

function selectWord(obj){
	var labelClass = obj.className;
	try{
		document.getElementsByClassName("activeS")[0].className = "active";
	}
	catch(e){
	
	}

	try{
		document.getElementsByClassName("inactiveS")[0].className = "inactive";
	}
	catch(e){
	
	}

	if(labelClass == "active")
		obj.className = "activeS";
	else
		obj.className = "inactiveS";
}