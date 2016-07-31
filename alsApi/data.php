<?php
	/**
	* @brief function for connect with PDO
	* @param $user user login
	* @param $pwd password
	* @param $engine database engine (example : mysql)
	**/
	function connectPg($user, $pwd, $engine){
		try {
		    $db = new PDO($engine.':host=front-ha-mysql-01.shpv.fr;dbname=tziymwsd_als', $user, $pwd);
		    // $db = new PDO($engine.':localhost;dbname=tziymwsd_als', $user, $pwd);
		    return $db;
		} 
		catch(PDOException $e) {
			  $db = null;
			  print 'ERREUR DB: ' . $e->getMessage();
		}
	}

	$val = $_GET['val'];
	$pred = $_GET['pred'];
	$courrant = $_GET['courrant'];

	//$db = connectPg('root', '','mysql');
	try{
		// $db = connectPg('root','','mysql');
		$db = connectPg('tziymwsd_alsUsr', 'n!ghtdr3am13','mysql');
	}
	catch(Exception $e){
		echo $e;
		die($e);
	}

	// Cherche si la pair est déjà en base de donné
	$select = "SELECT count(*) AS count FROM valeur WHERE precedent = '".$pred."' AND courrant = '".$courrant."'";
	$count = $db->query($select);
	$result = $count->fetch(PDO::FETCH_ASSOC);
	try{
		// Si la pair de valeur n'est pas dans la base de donné
		if($result['count'] == 0){
			if($val == "pos")
				$req = "INSERT INTO valeur (precedent, courrant, positif, negatif, neutre, indecidable) VALUES ('".$pred."', '".$courrant."', 1, 0, 0, 0)";
			else if($val == "neutre")
				$req = "INSERT INTO valeur (precedent, courrant, positif, negatif, neutre, indecidable) VALUES ('".$pred."', '".$courrant."', 0, 0, 1, 0)";
			else if($val == "neg")
				$req = "INSERT INTO valeur (precedent, courrant, positif, negatif, neutre, indecidable) VALUES ('".$pred."', '".$courrant."', 0, 1, 0, 0)";
			else if($val == "ind")
				$req = "INSERT INTO valeur (precedent, courrant, positif, negatif, neutre, indecidable) VALUES ('".$pred."', '".$courrant."', 0, 0, 0, 1)";
		}
		//sinon
		else{
			if($val == "pos")
				$req = "UPDATE valeur set positif = positif + 1 WHERE precedent = '".$pred."' AND courrant = '".$courrant."'";
			else if($val == "neutre")
				$req = "UPDATE valeur set neutre = neutre + 1 WHERE precedent = '".$pred."' AND courrant = '".$courrant."'";
			else if($val == "neg")
				$req = "UPDATE valeur set negatif = negatif + 1 WHERE precedent = '".$pred."' AND courrant = '".$courrant."'";
			else if($val == "ind")
				$req = "UPDATE valeur set indecidable = indecidable + 1 WHERE precedent = '".$pred."' AND courrant = '".$courrant."'";
		}
		$db->query($req);
	}
	catch(Exception $e){
		echo $e;
	}
?>

<?php include "./Layout/controlSet.php"; ?>