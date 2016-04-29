import tokemisation.Tokemiseur;
import synonymes.Synonymes;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}

class TestTokem{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tokemiseur t1 = new Tokemiseur("./res/test.txt");
		System.out.println("fin");
		System.out.println("find tokem : "+t1.findTokem("surhommes"));        
	}
}

class TestSynonymes{
    public static void main(String[] args) {
        Synonymes S = new Synonymes();
        S.findSynonymes("test");
        for(String i : S.getSynonymes())
            System.out.println(i);
    }
}
