package lemmatiseur;

import java.io.*;
import java.security.MessageDigest;
import java.lang.*;
import java.util.*;
import tokemisation.*;

public class Lefff {
	String path;
	ArrayList<Verbe> listVerbe = new ArrayList<Verbe>(); 
	ArrayList<String> listExp = new ArrayList<String>(); 


	/**
	*
	**/
	public Lefff(String path){
		this.path = path;
		readLefff();
		readExp("./res/exp.txt");
	}
	
	/**
	* @brief Permet la lecture d'un fichier de lefff
	* @detail Permet de lire un fichier de lefff formaté sous la forme : "abaissé v abaisser" afin de préparer la mise à l'infinitif des verbes.
	**/
	public void readLefff(){
		String[] cLine;
		String separator = ""+(char)9;
		String current = "";

		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			String line;
			while ((line = txt.readLine()) != null){
				cLine = line.split(separator);
				if(cLine[1].equalsIgnoreCase("v") || cLine[3].contains("K##")){
					if(listVerbe.size() == 0 )
						listVerbe.add(new Verbe(cLine[0], cLine[2]));
					else if(!cLine[0].equalsIgnoreCase(listVerbe.get(listVerbe.size()-1).getConj())){
						listVerbe.add(new Verbe(cLine[0], cLine[2]));
					}
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
	}

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
	* @param String txt nouveau texte à écrire
	* @param String titre titre du fichier créée
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
	* @detail traite le texte ce trouvant à l'adresse envoyé en paramètre
	* @see writeFile(String txt, String titre)
	* @param String path chemin d'accés du fichier à traiter
	**/
	public String traiteText(String path){
		//String result = message.replaceAll("%%NAME", name);
		String newText = "";
		try{
			File fichier = new File(path);
			BufferedReader txt = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
			String line;
			String tmp = "";
			String exp = "";
			
			while ((line = txt.readLine()) != null){
				tmp = line;
				exp = findExp(line);
				String[] split = exp.split(" ");
				for(int i = 0; i < split.length; i++){
					int j = 0;
					while(j < listVerbe.size() && !listVerbe.get(j).getConj().equalsIgnoreCase(split[i])){
						j++;
					}
					if(j < listVerbe.size() && listVerbe.get(j).getConj().equalsIgnoreCase(split[i])){
						tmp = tmp.replaceAll(" "+split[i]+" ", " "+listVerbe.get(j).getInf()+" ");
					}
				}
				newText += tmp+"\n";
			}
			writeFile(newText, "testWrite");
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
		return newText;
	}

	public String findExp(String line){
		String retour = line;
		for(int i = 0; i < listExp.size(); i++){
			if(line.contains(listExp.get(i))){
				retour = retour.replaceAll(" "+listExp.get(i)+" ", " ");
			}
		}
		return retour;
	}
}