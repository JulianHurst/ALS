package affichage;

import java.util.Scanner;

import lemmatiseur.*;
import tokemisation.*;
import synonymes.*;

public class Als {
	String txt1 = "./res/textTest.txt";
	Scanner option;
	String texte;
	
	public Als(){
		startProg();
	}
	
	/**
	 * @brief Lance le menu contextuel, et toute les actions relatives aux choix de l'utilisateur
	 */
	public void startProg(){
		String choix = "";
		System.out.println("Préparation du lemmatiseur");
		Lefff l1 = new Lefff("./res/fplm.fr.txt");
        Synonymes S=new Synonymes();
        Tokemiseur A = new Tokemiseur("res/serieA.txt");
        Tokemiseur B = new Tokemiseur("res/serieB.txt");
        S.setAdj(l1.getAdj());
		while(!choix.equals("quit")){
			System.out.println("Action suivante : ");
			System.out.println("1 - traiter un texte");
			System.out.println("tapez quit pour quitter le programme");
			option = new Scanner(System.in);
			choix = option.nextLine();
			switch(choix){
				case "1":
					System.out.println("Appuyer sur 1 pour les textes prédéfinies");
					System.out.println("sinon, entrez le chemin d'accès du texte à  étudier");
					option = new Scanner(System.in);
					choix = option.nextLine();
					if(choix.equals("1")){
						//System.out.println("nouveau texte : "+l1.traiteTexte(choixTexte()));
						//Suppression de la ponctuation et classification des mots du textes grÃ¢ce Ã  leurs synonymes						
						String ntxt = l1.traiteTexte(choixTexte());						
						System.out.println("texte lemmatisé : "+ntxt);						
                        S.classifierTableau(ntxt.split(" "),A,B);
                        					                        
					}
					else{
						String ntxt = l1.traiteTexte(choix);						
						System.out.println("texte lemmatisé : "+ntxt);						
                        S.classifierTableau(ntxt.split(" "),A,B);
					}
					break;
			}
			if(texte!=null && texte.equals("Echec de l'ouverture"))
				System.out.println(texte);
			System.out.println("fin");
		}
	}
	
	/**
	 * @brief liste les textes pré-définie, et retourne le path de celui choisit
	 * @return path du fichier texte choisit
	 */
	private String choixTexte(){
		System.out.println("1 pour le texte de Serge Leclaire");
		System.out.println("2 pour 'pourquoi l'extrème gauche française est la plus bète du monde'");
		System.out.println("3 pour le texte 'Livre des années 30'");
		System.out.println("4 pour le texte de Witold Gomlbrowicz");
		Scanner texteChoix = new Scanner(System.in);
		texte = texteChoix.nextLine();
		String path = "./res/txt/";
		switch(texte){
			case "1":
				path +=  "texte10.txt";
				break;
			case "2":
				path += "texte8.txt";
				break;
			case "3":
				path += "texte9.txt";
				break;
			case "4":
				path += "texte6.txt";
				break;
		}
		return path;
	}
}
