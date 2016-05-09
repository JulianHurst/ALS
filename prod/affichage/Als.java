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
					System.out.println("Chemin du texte à étudier");
					option = new Scanner(System.in);
					choix = option.nextLine();
					System.out.println(choix);
					System.out.println("nouveau text : "+l1.traiteText(choix));
					break;
			}
			System.out.println("fin");
		}
	}
}
