package tokemisation;

/**
* @Author Kevin Garabedian
* @brief Permet la tokémisation d'une liste de mot
* @detail Préparé si nécessaire en attendant d'avoir les données finales.
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
	* @brief constructeur avec le path/ permet un traitement automatique de la tokemisation
	**/
	public Tokemiseur(String path){
		this.path = path;
		tokemFile();
	}

	/**
	* @brief Lancement de la tokemisation.
	**/
	public void tokemFile(){
		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			String line;
			while ((line = txt.readLine()) != null){
				nbLine ++;
				createThree(line, nbLine);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	/**
	* @brief creation de l'arbre
	* @detail toute la procedure de creation d'arbre s'affiche en console afin de verifier sont bon fonctionnement
	**/
	public void createThree(String line, int nbLine){
		String tsplit[];
		boolean check = true;
		int k;
		check = true;
		k = 0;
		if(lcb != null){
			while(check && k<line.length()){
				if(lc.getChar().charAt(0)==line.charAt(k) && lc.getFils()!=null){
					lc = lc.getFils();
					k++;
				}
				else if(lc.getChar().charAt(0)!=line.charAt(k) && lc.getFrere()!=null){
					lc = lc.getFrere();
				}
				else{
					lc.setFrere(new Listechar(""+line.charAt(k)));
					lc = lc.getFrere();
					k++;
					check = false;
				}
			}
			for(int j = k; j < line.length(); j++){
				lc.setFils(new Listechar(""+line.charAt(j)));
				lc = lc.getFils();
			}
		}
		//Dans le cas ou la chaine n a pas debutee
		else{
			lcb = new Listechar(""+line.charAt(0));
			lc = lcb;	
			for(int j = 1; j < line.length(); j++){
				lc.setFils(new Listechar(""+line.charAt(j)));
				lc = lc.getFils();	//Deplacement vers le fils
			}
		}
		lc.setFils(new Listechar(nbLine+""));
		lc = lcb;		//Retour au debut de la chaine
	}

	/**
	* @param String s un mot
	* @return String le numero du tokem, si il esxite pas il renvoie un String contenant "-1"
	* @brief cherche si le tokem est dans l'arbre lexical et retourne le numero de celui ci
	**/
	public String findTokem(String s){
		int k = 0;
		boolean check = true;
		//String tmp = "";
		lc = lcb;
		while(check){
			if(k < s.length() && lc.getChar().charAt(0) == s.charAt(k)){
				lc = lc.getFils();
				k++;
			}
			else if(k < s.length() && lc.getChar().charAt(0) != s.charAt(k) && lc.getFrere() != null){
				//System.out.println(k);
				//System.out.println("frere suivant ->"+lc.getFrere().getChar()+"->"+s.charAt(k));
				lc = lc.getFrere();
			}
			else if(k == s.length() && lc.getFils() == null){
				return lc.getChar();
			}
			else{
				return "-1";
			}
		}
		return "";
	}
}