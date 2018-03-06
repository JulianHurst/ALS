package utils;

import java.io.*;
import java.util.ArrayList;

import datamodel.Atom;
import datamodel.PointOfView;
import datamodel.Serie;
import tokemisation.Tokemiseur;

/**
 * @brief Classe contenant des méthodes utiles
 */

public class Utils{

    public Utils(){}

    /**
	 * @brief ouvre un texte, et le stock dans un String
	 * @param path chemin d'accès du texte à traiter
	 * @return Le texte extrait du fichier texte
	 */
	public String openTexte(String path){
		String line = "";
		String texte = "";
		try{
			File fichier = new File(path);
			//System.out.println(path);
			//System.out.println(getClass().getResourceAsStream(path));
			BufferedReader txt = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path), "UTF8"));
			while ((line = txt.readLine()) != null){
				texte += line+"\n";
			}
		}
		catch(Exception e){
			e.printStackTrace();
			//System.out.println(e);
		}
		return texte;
	}

    /**
	 * @param txt
	 * @return Le texte traité
	 * @brief Supprime la ponctuation dans le texte
	 */
	public String supprPonctuationASP(String txt){
		//txt=txt.replaceAll(",","");
		txt=txt.replaceAll("\\.","");
		txt=txt.replaceAll(";","");
		txt=txt.replaceAll(":","");
		txt=txt.replaceAll("!","");
		txt=txt.replaceAll("\\? ","");
		txt=txt.replaceAll("\\(","");
		txt=txt.replaceAll("\\)","");

		//Peut être une liste de mots inutiles supplémentaire à enlever

		txt=txt.replaceAll("…","");
		txt=txt.replaceAll("\"","");
		txt=txt.replaceAll(" {2,}", " ");
		txt=txt.replaceAll("(\r\n|\n|\r)","");
		return txt;
	}

    /**
	 * @param txt
	 * @return Le texte traité
	 * @brief Supprime la ponctuation dans le texte
	 */
	public String supprPonctuation(String txt){
		txt=txt.replaceAll(",","");
		txt=txt.replaceAll("\\.","");
		txt=txt.replaceAll(";","");
		txt=txt.replaceAll(":","");
		txt=txt.replaceAll("!","");
		txt=txt.replaceAll("\\? ","");
		txt=txt.replaceAll("\\(","");
		txt=txt.replaceAll("\\)","");

		//Peut être une liste de mots inutiles supplémentaire à enlever

		txt=txt.replaceAll("…","");
		txt=txt.replaceAll("\"","");
		txt=txt.replaceAll(" {2,}", " ");
		txt=txt.replaceAll("(\r\n|\n|\r)","");
		return txt;
	}
	
	/**
	 * Retourne le contenu d'un fichier
	 * @param file Le fichier à lire
	 * @return Le contenu du fichier
	 * @throws IOException
	 */
	public String lectureTexte(String file) throws IOException{
		StringBuffer content=new StringBuffer();
		String line;
		BufferedReader buf = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file), "UTF-8"));
		while((line=buf.readLine())!=null){
			content.append(line);
		}
		return content.toString();
	}
	
	public ArrayList<String> getAtomWords(Tokemiseur tok, ArrayList<String> words) {
		ArrayList<String> result = new ArrayList<>();
		for(String i : words) {
			if(tok.findTokem(i) != null)
				result.add(i);
		}
		return result;
	}
	
	public Serie getSerie(Tokemiseur A, Tokemiseur B, String atom) {
		if(A.findTokem(atom) != null)
			return Serie.A; 
		if(B.findTokem(atom) != null)
			return Serie.B;
		return null;
	}

	public ArrayList<String> getAtomWords(Tokemiseur A, Tokemiseur B, ArrayList<String> words) {
		ArrayList<String> result = new ArrayList<>();
		for(String i : words) {
			if(A.findTokem(i) != null || B.findTokem(i) != null)
				result.add(i);
		}
		return result;
	}
	
	public ArrayList<Integer> getAtomIndices(Tokemiseur A, Tokemiseur B, String txt){
		ArrayList<Integer> result = new ArrayList<>();
		String words[];
		int ind = 0;
		words = txt.split(" ");
		for(String i : words) {
			if(A.findTokem(i) != null || B.findTokem(i) != null)
				result.add(ind);
			ind++;
		}
		return result;
	}
	
	public PointOfView analyse(ArrayList<Atom> words) {
		int result = 0;
		for(Atom i : words) {
			result += i.getSerieValue() * i.getWeightValue();
		}
		if(result < 0)
			return PointOfView.EXTROVERT;
		if(result == 0)
			return PointOfView.HESITANT;
		return PointOfView.INTROVERT;
	}
	
}
