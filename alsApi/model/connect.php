<?php
function connectPg($user, $pwd, $engine){
	try {
	    $db = new PDO($engine.':host=localhost;dbname=circulation', $user, $pwd);
	    return $db;
	} 
	catch(PDOException $e) {
		  $db = null;
		  print 'ERREUR DB: ' . $e->getMessage();
	}
}
?>