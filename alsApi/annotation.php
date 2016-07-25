<!doctype html>
<html lang="fr">
	<head>
		<meta charset="utf-8">
		<title>Titre de la page</title>
		<link rel="stylesheet" href="css/style.css">
		<script src="js/script.js"></script>
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
				    //$db = new PDO($engine.':host=front-ha-mysql-01.shpv.fr;dbname=tziymwsd_als', $user, $pwd);
				    $db = new PDO($engine.':localhost;dbname=tziymwsd_als', $user, $pwd);
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
				$db = connectPg('root', '','mysql');
				//$db = connectPg('tziymwsd_alsUsr', 'n!ghtdr3am13','mysql');
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
			<table>
				<tr>
					<td colspan="8">Affichage temporaire de contrôle</td>
				</tr>
				<tr>
					<td>Précédent</td>
					<td>Suivant</td>
					<td>Positif</td>
					<td>Negatif</td>
					<td>Neutre</td>
					<td>%positif</td>
					<td>%negatif</td>
					<td>%neutre</td>
				</tr>
			<?php
				$req2 = "SELECT * FROM valeur V join pourcent P ON V.id = P.id_couple";
				try{
					$resReq = $db->query($req2);
					while($res = $resReq->fetch()){
						echo "<tr>";
							echo "<td>".$res['precedent']."</td>";
							echo "<td>".$res['courrant']."</td>";
							echo "<td>".$res['positif']."</td>";
							echo "<td>".$res['negatif']."</td>";
							echo "<td>".$res['neutre']."</td>";
							echo "<td>".$res['pourcentPos']."</td>";
							echo "<td>".$res['pourcentNeg']."</td>";
							echo "<td>".$res['pourcentNeutre']."</td>";
						echo "</tr>";
					}
					$resReq->closeCursor();
				}
				catch(Exception $e){
					echo $e;
				}
			?>
			</table>
		</div>
	</body>
</html>