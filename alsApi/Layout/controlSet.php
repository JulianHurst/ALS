<table>
	<tr>
		<td colspan="8">Affichage temporaire de contrôle</td>
	</tr>
	<tr>
		<td>déterminant</td>
		<td>Valué</td>
		<td>Positif</td>
		<td>Negatif</td>
		<td>Neutre</td>
		<td>Indécidable</td>
		<td>%positif</td>
		<td>%negatif</td>
		<td>%neutre</td>
		<td>%indécidable</td>
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
				echo "<td>".$res['indecidable']."</td>";
				echo "<td>".$res['pourcentPos']."</td>";
				echo "<td>".$res['pourcentNeg']."</td>";
				echo "<td>".$res['pourcentNeutre']."</td>";
				echo "<td>".$res['pourcentInd']."</td>";
			echo "</tr>";
		}
		$resReq->closeCursor();
	}
	catch(Exception $e){
		echo $e;
	}
?>
</table>