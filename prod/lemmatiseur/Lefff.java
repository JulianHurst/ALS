package lemmatiseur;

import java.io.*;
import java.lang.*;
import java.util.*;
import tokemisation.*;

public class Lefff {
	String separator = ""+(char)9;
	String path;
	ArrayList<String> listExp = new ArrayList<String>();
	Tokemiseur arbreVerbe;
	Tokemiseur arbreOutil;


	/**
	 * @brief Constructeur pour le lefff
	 * @param path chemin d'accés du texte à traiter
	 */
	public Lefff(String path){
		arbreVerbe = new Tokemiseur();
		arbreOutil = new Tokemiseur();
		this.path = path;
		readLefff();
		readOutil("./res/outils.txt");
		readExp("./res/exp.txt");
	}
	
	/**
	* @brief Permet la lecture d'un fichier de lefff
	* @detail Permet de lire un fichier de lefff formatÃ© sous la forme : "abaissÃ© v abaisser" afin de prÃ©parer la mise Ã  l'infinitif des verbes.
	**/
	public void readLefff(){
		String[] cLine;
		String current = "";

		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			String line;
			while ((line = txt.readLine()) != null){
				cLine = line.split(separator);
				if(cLine[1].equalsIgnoreCase("v") || cLine[3].contains("K##")){
					arbreVerbe.createVthree(cLine[0], cLine[2]);
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * @brief lit et stock dans un arbre de tokemisationla liste des mots outils
	 * @param path chemin d'accés du fichier texte de mots outils
	 */
	public void readOutil(String path){
		String[] cLine;
		String separator = ""+(char)9;
		String current = "";

		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			String line;
			while ((line = txt.readLine()) != null){
				cLine = line.split(separator);
				arbreOutil.createVthree(cLine[0], cLine[1]);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	/**
	 * @brief permet de charger une liste d'expression
	 * @param path
	 */
	public void readExp(String path){
		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			String line;
			while ((line = txt.readLine()) != null){
				listExp.add(line);
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

	/**
	* @brief Ecrit le nouveau texte dans un fichier
	* @param String txt nouveau texte Ã  Ã©crire
	* @param String titre titre du fichier crÃ©Ã©e
	**/
	public void writeFile(String txt, String titre){
		try{
			String path = "./res/"+titre+".txt";
			File writing = new File(path);

			if(!writing.exists())
				writing.createNewFile();

			FileWriter fw = new FileWriter(writing.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(txt);
			bw.close();
		}
		catch(IOException e){
			e.printStackTrace();
			System.out.println(e);
		}
	}

	/**
	* @brief permet de traiter le texte
	* @detail traite le texte ce trouvant Ã  l'adresse envoyÃ© en paramÃ¨tre
	* @see writeFile(String txt, String titre)
	* @param String path chemin d'accÃ©s du fichier Ã  traiter
	**/
	public String traiteVerbe(String oldTexte){
		//String result = message.replaceAll("%%NAME", name);
		String newText = "";
		String exp = "";
		String tmpWord = "";
		String[] sdSplit;
		exp = findExp(oldTexte);
		String[] split = exp.split(" ");
		String infinitif;
		String outils;
				
		for(int i = 0; i < split.length; i++){
			//cas particulier ' avant le verbe
			if(split[i].contains("'")){
				sdSplit = split[i].split("'");
				tmpWord = sdSplit[1];
			}
			//cas particulier - après le verbe
			else if(split[i].contains("-")){
				sdSplit = split[i].split("-");
				tmpWord = sdSplit[0];
			}
			else
				tmpWord = split[i];
			infinitif = arbreVerbe.findTokem(tmpWord);
			//Vérifie qu'il y est un infinitif
			if(infinitif != null){
				outils = arbreOutil.findTokem(split[i-1]);
				//Vérifie qu'il n'est pas précédé d'un article indéfinie
				if(outils!=null && (!outils.equals("ad") && !outils.equals("ai"))){
					split[i] = split[i].replaceFirst(tmpWord, infinitif);
				}
			}
		}
		for(int i = 0; i < split.length; i++){
			newText += split[i]+" ";
		}
		newText += "\n";
		return newText;
	}

	/**
	 * @brief vérifie si il y a des expressions à ne pas traiter pour la lemmatisationd es verbes
	 * @param line texte à traiter
	 * @return Texte sans les expressions à exclure
	 */
	public String findExp(String line){
		String retour = line;
		for(int i = 0; i < listExp.size(); i++){
			if(line.contains(listExp.get(i))){
				retour = retour.replaceAll(" "+listExp.get(i)+" ", " ");
			}
		}
		return retour;
	}

	/**
	 * @brief permet de traiter un texte
	 * @param p path du texte à traiter
	 * @return Le texte traité
	 */
	public String traiteTexte(String p){
		String txt = "";
		String path = p;
		File f = new File(p);
		if(!f.exists()){
			return "Echec de l'ouverture";
		}
		System.out.println("ouverture du texte");
		txt = openTexte(p);
		System.out.println("Traitement des verbes");
		txt = traiteVerbe(txt);
		System.out.println("Traitement des expressions figées neutres");
		txt = supprExpNeutre(txt);
		
		//Ecriture du fichier texte de sortie
		String pathSplit[] = path.split("/");
		String titre = pathSplit[pathSplit.length-1];
		System.out.println("titre : "+titre);
		writeFile(txt, titre);
		return txt;
	}
	
	/**
	 * @brief enlève toute les expressions neutres du texte
	 * @param txt texte à traiter
	 * @return texte traité
	 */
	public String supprExpNeutre(String txt){
		String newTexte = txt;
		String figeList = "";
		String fLine[];
		try{
			File fige = new File("./res/fige.txt");
			BufferedReader expFige = new BufferedReader(new InputStreamReader(new FileInputStream(fige), "UTF8"));
			while ((figeList = expFige.readLine()) != null){
				fLine = figeList.split(separator);
				if(fLine[1].equals("neutre")){
					newTexte = newTexte.replaceAll(fLine[0], "");
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		return newTexte;
	}
	
	/**
	 * @brief ouvre un texte, et le stock dans un String
	 * @param path chemin d'accés du texte à traiter
	 * @return Le texte extrait du fichier texte
	 */
	public String openTexte(String path){
		String line = "";
		String texte = "";
		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			while ((line = txt.readLine()) != null){
				texte += line+"\n";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		return texte;
	}
}
