package affichage;
import lemmatiseur.*;

public class Als {
	Lefff l1 = new Lefff("./res/fplm.fr.txt");
	String txt1 = "./res/textTest.txt";
	public Als(){
		l1.traiteText(txt1);
	}
}
