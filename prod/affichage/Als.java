package affichage;

import java.util.Scanner;

import lemmatiseur.*;
import tokemisation.*;

public class Als {
	String txt1 = "./res/textTest.txt";
	Scanner option;
	public Als(){
		startProg();
	}
	
	public void startProg(){
		String choix = "";
		System.out.println("Préparation du lemmatiseur");
		Lefff l1 = new Lefff("./res/fplm.fr.txt");
		while(!choix.equals("quit")){
			System.out.println("Action suivante : ");
			System.out.println("1 - traiter un texte");
			System.out.println("tapez quit pour quitter le programme");
			option = new Scanner(System.in);
			choix = option.nextLine();
			switch(choix){
				case "1":
					System.out.println("Appuyer sur 1 pour les textes prédéfinies");
					System.out.println("sinon, entrez le chemin d'accés du texte à étudier");
					option = new Scanner(System.in);
					choix = option.nextLine();
					if(choix.equals("1"))
						System.out.println("nouveau text : "+l1.traiteText(choixTexte()));
					else
						System.out.println("nouveau text : "+l1.traiteText(choix));
					break;
			}
			System.out.println("fin");
		}
	}
	
	private String choixTexte(){
		System.out.println("1 pour le texte de Serge Leclaire");
		System.out.println("2 pour 'pourquoi l'extrême gauche française est la plus bête du monde'");
		System.out.println("3 pour le texte 'Livre des années 30'");
		System.out.println("4 pour le texte de Witold Gomlbrowicz");
		Scanner texteChoix = new Scanner(System.in);
		String texte = texteChoix.nextLine();
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
