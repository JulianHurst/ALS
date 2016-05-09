package affichage;
import lemmatiseur.*;
import tokemisation.*;

public class Als {
	String txt1 = "./res/textTest.txt";
	public Als(){
		Tokemiseur exp = new Tokemiseur("./res/exp.txt");
		Lefff l1 = new Lefff("./res/fplm.fr.txt", tk);
		l1.traiteText(txt1);
		System.out.println(exp.findTokem("la plus"));
	}
}
