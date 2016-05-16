import affichage.*;
import tokemisation.*;
import synonymes.*;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tokemiseur A = new Tokemiseur("res/serieA");
		Tokemiseur B = new Tokemiseur("res/serieB");
		Synonymes S = new Synonymes();
		ArrayList<String> L = new ArrayList<>();        
        L.add("fermé");
        L.add("moral");
        S.classifierListe(L,A,B);  
		System.out.println("test"+(char)9+"test");
		Als a1 = new Als();
	}
}
