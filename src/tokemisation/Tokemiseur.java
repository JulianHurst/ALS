package tokemisation;

/**
* @Author Kevin Garabedian
* @brief Permet la tokemisation d'une liste de mot
* @detail Prépare si nécessaire en attendant d'avoir les données finales.
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

	public Tokemiseur(){}

	/**
	 * @param String path : Le chemin du fichier
	 * @brief Crée un tokemiseur à partir d'un fichier
	 */
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
			//System.out.println(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path), "UTF8"));
			String line;
			while ((line = txt.readLine()) != null){
				nbLine ++;
				createTree(line);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	/**
	 * @brief Permet d'ajouter un mot dans l'arbre
	 * @param line mot à ajouter
	 */
	public void createTree(String line){
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
			lc.setFils(new Listechar("fin"));
		else{
			while(lc.getFrere() != null){
				lc = lc.getFrere();
			}
			lc.setFrere(new Listechar("fin"));
		}
		lc = lcb;
	}

	/**
	 * @brief Permet d'ajouter un verbe dans l'arbre
	 * @param line Verbe conjugué à ajouter
	 * @param inf L'infinitif du verbe
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
			for(int j = 1; j < line.length(); j++){
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
			lc.setFrere(new Listechar(inf));
		}
		lc = lcb;
	}

	/**
	* @param String s : Mot à chercher
	* @return String : Le mot de fin ou forme lemmatisée du tokemiseur, si il n'existe pas la méthode renvoie null
	* @brief Cherche si le tokem est dans l'arbre lexical (ignore par défaut la casse)
	**/
	public String findTokem(String s){
		int k = 0;
		boolean check = true;
		lc = lcb;

		//Boucle infinie cassé par un return
		while(check){			
			if(k < s.length() && lc.getChar() == Character.toLowerCase(s.charAt(k))){
				lc = lc.getFils();
				k++;
			}
			//sinon cherche le prochain frère identique
			else if(k < s.length() && lc.getChar() != Character.toLowerCase(s.charAt(k)) && lc.getFrere() != null){
				/*if(s.equals("heures"))
					//System.out.println(Character.toLowerCase(lc.getChar())+" "+Character.toLowerCase(s.charAt(k)));*/
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
					return null;
			}
			//En cas d'échec renvoie -1
			else{
				return null;
			}
		}
		return null;
	}

	/**
	* @param String s : Mot à chercher
	* @param boolean ignorecase : Permet de spécifier si on veut ignorer la casse ou non
	* @return String : Le mot de fin ou forme lemmatisée du tokemiseur, si il n'existe pas la méthode renvoie null
	* @brief Cherche si le tokem est dans l'arbre lexical
	**/
	public String findTokem(String s, boolean ignorecase){
		int k = 0;
		boolean check = true;
		lc = lcb;

		//Boucle infinie cassé par un return
		while(check){
			//Vérifie si le caractère courant de l'arbre est le même que celui du mot, si c'est le cas, incrémentation de k, et décallage du pointeur
			if(ignorecase){
				if(k < s.length() && lc.getChar() == Character.toLowerCase(s.charAt(k))){
					lc = lc.getFils();
					k++;
				}
				//sinon cherche le prochain frère identique
				else if(k < s.length() && lc.getChar() != Character.toLowerCase(s.charAt(k)) && lc.getFrere() != null){
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
						return null;
				}
				//En cas d'échec renvoie -1
				else{
					return null;
				}
			}
			else{
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
						return null;
				}
				//En cas d'échec renvoie -1
				else{
					return null;
				}
			}
		}
		return null;
	}
}
