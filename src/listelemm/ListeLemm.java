package listelemm;
import java.util.ArrayList;

/**
 * @author Julian Hurst
 * @brief Permet de créer une liste de lemmatisation (Une liste de String auquel est associé une forme lemmatisée)
 */

public class ListeLemm{
    private ArrayList<String> A;
    private String lemm;
    
    /**
     * @brief Initialise une liste de lemmatisation vide
     */
    public ListeLemm(){
        A=new ArrayList<>();
        lemm="";
    }
    
    /**
     * @param String lemm
     * @brief Crée une liste de lemmatisation vide avec une forme lemmatisée
     */
    public ListeLemm(String lemm){
        A=new ArrayList<>();
        this.lemm=lemm;
    }
    
    /**
     * @param ArrayList<String> A
     * @param String lemm
     * @brief Crée une liste de lemmatisation à partir d'une liste de String et d'une forme lemmatisée
     */
    public ListeLemm(ArrayList<String> A,String lemm){
        this.A.addAll(A);
        this.lemm=lemm;
    }
    
    /**
     * @param String mot
     * @brief Ajoute un mot à la liste de lemmatisation
     */
    public void add(String mot){
        if(lemm.isEmpty())
            System.out.println("Attention il n'y a aucune forme lemmatisée pour "+mot);
        A.add(mot);
    }
    
    /**
     * @param String mot
     * @param String l
     * @brief Ajoute un mot à la liste de lemmatisation et définit la forme lemmatisée
     */
    public void add(String mot,String l){
        A.add(mot);
        lemm=l;
    }
    
    /**
     * @param String lemm
     * @brief Modifie la forme lemmatisée
     */
    public void setLemm(String lemm){
        this.lemm=lemm;
    }
    
    /**     
     * @return La forme lemmatisée
     * @brief Renvoie la forme lemmatisée
     */
    public String getLemm(){
        return lemm;
    }
    
    /**
     * @param ArrayList<String> A
     * @brief Modifie la liste de mots non-lemmatisés
     */
    public void setArray(ArrayList<String> A){
        this.A.clear();
        this.A.addAll(A);
    }
    
    /**     
     * @return La liste de mots non-lemmatisés
     * @brief Renvoie la liste de mots non-lemmatisés
     */
    public ArrayList<String> getArray(){
        return A;
    }
}
