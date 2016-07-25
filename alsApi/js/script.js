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
}

/**
* @brief Permet de préparer les données pour les envoyer au script PhP
* @param current contient les informations du curseur
**/
function addValue(current){
	var pos = document.getElementById("pos");
	var neutre = document.getElementById("neutre");
	var neg = document.getElementById("neg");

	var courrant = document.getElementById(current).innerHTML;
	if(current <= 0)
		var pred = "~";
	else
		var pred = document.getElementById(current-1).innerHTML;

	if(pos.checked){
		var val = "pos";
	}
	else if(neutre.checked){
		var val = "neutre";
	}
	else if(neg.checked){
		var val = "neg";
	}
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