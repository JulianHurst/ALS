package listelemm;
import java.util.ArrayList;

/**
 * @author Julian Hurst
 * @brief Permet de créer une liste de listes de lemmatisation
 */

public class ListeLemmWrapper{
    private ArrayList<ListeLemm> L;   
    
    public ListeLemmWrapper(){
        L=new ArrayList<>();
    }
    
    /**
     * @param String mot : Le mot à ajouter
     * @param String l : La forme lemmatisée
     * @brief Ajoute un mot dans la liste de lemmatisation au bon emplacement si une liste existe, sinon elle crée une nouvelle liste.
     */
    public void add(String mot,String l){
        boolean found=false;
        for(ListeLemm i : L)
            if(i.getLemm().equals(l)){                
                i.add(mot);
                return;
            }
        ListeLemm LL = new ListeLemm(l);
        L.add(LL);
        L.get(L.size()-1).add(mot);
    }
    
    /**
     * @return Les listes de lemmatisation
     * @brief Renvoie les listes de lemmatisation
     */
    public ArrayList<ListeLemm> getArray(){
		return L;
	}
	
	/**
	 * @return La liste de lemmatisation trouvée
	 * @brief Renvoie la liste de lemmatisation pour une forme lemmatisée donnée
	 */
	public ListeLemm find(String l){
		for(ListeLemm i : L)
			if(i.getLemm().equals(l))
				return i;
		return null;
	}
	
	/**
	 * @brief Affiche tous les éléments des listes de lemmatisation
	 */
	public void display(){
		for(ListeLemm i : L){
			System.out.println(i.getLemm());		
			for(String j : i.getArray())
				System.out.println(j);
		}
	}
				
}
