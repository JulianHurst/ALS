<?php
  /**
   * Empêcher la mise en cache des pages avec PHP
   *
   * La fonction doit-être appellée avant toute balise HTML,
   * espace blanc, echo(), print()...
   *
   * @param : void
   * @return : void
   */
  function empecherLaMiseEnCache()
  {
    header('Pragma: no-cache');
    header('Expires: 0');
    header('Last-Modified: ' . gmdate('D, d M Y H:i:s') . ' GMT');
    header('Cache-Control: no-cache, must-revalidate');
  }

  empecherLaMiseEnCache();
?>

<!doctype html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title>Titre de la page</title>
		<link rel="stylesheet" href="css/style.css">
		<script src="js/script.js" <?php echo time(); ?>></script>
	</head>

	<body>
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

			if(isset($_GET['val']))
				$val = $_GET['val'];
			if(isset($_GET['pred']))
				$pred = $_GET['pred'];
			if(isset($_GET['courrant']))
				$courrant = $_GET['courrant'];

			//$db = connectPg('root', '','mysql');
			try{
				// $db = connectPg('root', '','mysql');
				$db = connectPg('tziymwsd_alsUsr', 'n!ghtdr3am13','mysql');
			}
			catch(Exception $e){
				echo $e;
				die($e);
			}
		?>
		<?php
			$myfile = fopen("res/test.txt", "r") or die("Unable to open file!");
			$text = fread($myfile,filesize("res/test.txt"));
			$text = str_replace(",", " ,", $text);
			$text = str_replace(".", " .", $text);
			$text = str_replace("?", " ?", $text);
			$split = explode(" ", $text);
			fclose($myfile);
		?>
		<!-- affichage selection utilisateur -->
		<p id="currentWord">valeur de : <?php echo $split[0] ?></p>
		<input type="radio" name="valeur" id="pos" value="positif">positif</br>
		<input type="radio" name="valeur" id="neutre" value="neutre">neutre</br>
		<input type="radio" name="valeur" id="neg" value="négatif">négatif</br>
		<input type="radio" name="valeur" id="ind" value="indecidable">indecidable</br>
		<?php
			echo "<p>";
			for($i = 0; $i < sizeof($split); $i++){
				if($i == 0)
					echo '<span id='.$i.' class="active">'.$split[$i].'</span> ';
				else
					echo '<span id='.$i.' class="">'.$split[$i].'</span> ';
			}
			echo "</p>";
		?>
		<input type="hidden" id="current" value=0>
		<input type="hidden" id="size" value=<?php echo sizeof($split) ?>>
		<button onclick="next()">Next</button>
		
		<!-- Affichage des données (pour test) -->
		<div id="txtHint">
			<?php include "./Layout/controlSet.php"; ?>
		</div>
	</body>
</html>