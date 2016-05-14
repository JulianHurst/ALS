package tokemisation;

/**
* @Author Kevin Garabedian
* @brief Permet la tokÃ©misation d'une liste de mot
* @detail PrÃ©parÃ© si nÃ©cessaire en attendant d'avoir les donnÃ©es finales.
**/

import java.io.*;

import java.security.MessageDigest;
import java.lang.*;

public class Tokemiseur{
	// lcf pour liste char base
	private Listechar lcb = null;
	private Listechar lc = lcb;
	private int nbLine = 0;
	private String path = "";

	/**
	 * @brief Permet d'ajouter dans l'arbre un verbe
	 * @param line Verbe conjugué à ajouté
	 * @param inf infitif du verbe
	 */
	public void createVthree(String line, String inf){
		boolean check = true;
		int k = 0;
		
		//Si il y a au moins 1 mot de chargé
		if(lcb != null){
			while(check && k<line.length()){
				//Vérifie si le fils suivant est le même
				if(lc.getChar() == line.charAt(k) && lc.getFils()!=null){
					lc = (Listechar) lc.getFils();
					k++;
				}
				//Sinon, cherche un frère identique
				else if(lc.getChar() != line.charAt(k) && lc.getFrere()!=null){
					lc = (Listechar) lc.getFrere();
				}
				//Sinon crée un nouveau frère
				else{
					lc.setFrere(new Listechar(line.charAt(k)));
					lc = (Listechar) lc.getFrere();
					k++;
					check = false;
				}
			}
			for(int j = k; j < line.length(); j++){
				lc.setFils(new Listechar(line.charAt(j)));
				lc = (Listechar) lc.getFils();
			}
		}
		//Si c'est le premier mot
		else{
			lcb = new Listechar(line.charAt(0));
			lc = lcb;	
			for(int j = 1; j < line.length(); ++j){
				lc.setFils(new Listechar(line.charAt(j)));
				lc = (Listechar) lc.getFils();	//Deplacement vers le fils
			}
		}
		if(lc.getFils() == null)
			lc.setFils(new Listechar(inf));
		else{
			while(lc.getFrere() != null){
				lc = lc.getFrere();
			}
			lc.setFrere(new Listechar(inf));;
		}
		lc = lcb;
	}
	
	/**
	* @param String s mot à chercher
	* @return String le numero du tokem, si il esxite pas il renvoie un String contenant "-1"
	* @brief cherche si le tokem est dans l'arbre lexical et retourne le numero de celui ci
	**/
	public String findTokem(String s){
		int k = 0;
		boolean check = true;
		lc = lcb;
		
		//Boucle infinie cassé par un return
		while(check){
			//Vérifie si le caractère courant de l'arbre est le même que celui du mot, si c'est le cas, incrémentation de k, et décallage du pointeur
			if(k < s.length() && lc.getChar() == s.charAt(k)){
				lc = lc.getFils();
				k++;
			}
			//sinon cherche le prochain frère identique
			else if(k < s.length() && lc.getChar() != s.charAt(k) && lc.getFrere() != null){
				lc = lc.getFrere();
			}
			//sinon si k atteint sa valeur max et que le pointeur est sur l'infinitif alors retourner l'infinitif
			else if(k == s.length() && lc.getFinal() != null){
				return lc.getFinal();
			}
			//Sinon chercher dans les frères l'infinitif
			else if(k == s.length() && lc.getFinal() == null){
				while (lc.getFinal() == null && lc.getFrere() != null){
					lc = lc.getFrere();
				}
				if(lc.getFinal() != null)
					return lc.getFinal();
				else
					return "-1";
			}
			//En cas d'échec renvoie -1
			else{
				return "-1";
			}
		}
		return "";
	}
}