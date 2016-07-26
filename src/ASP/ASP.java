package ASP;

/**
* @Author Julian Hurst
* @brief Permet d'affiner la distinction nom/adjectif pour certains mots d'un texte donné
*/

import tokemisation.*;
import listelemm.*;
import utils.*;

public class ASP{
    Tokemiseur mots_cles;
    ListeLemmWrapper T;
    String txt;
    String mots[];
    String phrases[];
    Utils U;
    Tokemiseur arbreAdj,arbreNoms,arbreNomsP;

    /**
     * @param String path : Le chemin du fichier
     * @param Tokemiseur arbreAdj : l'arbre des adjectifs
     * @param Tokemiseur arbreNoms : l'abre des noms communs
     * @param Tokemiseur arbreNomsP : l'abre des noms propres
     * @brief Initialise l'ASP
     */
    public ASP(String txt,Tokemiseur arbreAdj,Tokemiseur arbreNoms, Tokemiseur arbreNomsP){
        mots_cles=new Tokemiseur("res/ASP.txt");
        T=new ListeLemmWrapper();
        U=new Utils();
        this.txt=U.openTexte(txt);
        this.arbreAdj=arbreAdj;
        this.arbreNoms=arbreNoms;
        this.arbreNomsP=arbreNomsP;
        System.out.println("***ASP*** texte : "+this.txt);
        //System.out.println("***ASP*** phrases0 : "+this.phrases[0]);
        //mots=txt.split(" ");
    }

    /**
    * @param int i : L'indice du mot à partir duquel analyser dans une phrase donnée
    * @param boolean attendNom : Spécifie si l'on attend un Nom auquel rattacher des adjectifs ou un mot clé
    * @param boolean attendAdj : Spécifie si l'on attend un Adjectif lors d'une énumeration
    * @return int : Renvoie l'indice du dernier mot analysé
    * @brief Analyse une phrase ou partie de phrase afin de déterminer si une grammaire spécifique s'y trouve.\n
    * @detail Les grammaires (N=Nom, M=mot_clé, A=adj) :\n
    * \f{eqnarray*}{
    *    G &:& M E N \\
    *      &|& M N E \\
    *    E &:& A , E \\
    *      &|& A et A
    *      \f}
    */
    int chaineNouA(int i,boolean attendNom, boolean attendAdj){
        int ret=i;
        int l=mots.length;
        //System.out.println(i+" "+attendNom);
        if((i+1)<l){
            if(isAdj(i+1)){
                //System.out.println(mots[i+1]+"Trying adj...");
                ret=chaineNouA(i+1,attendNom,attendAdj);
                if(ret!=(i+1)){
                    T.add(mots[i+1],"adj");
                    return ret;
                }
            }
            else if(mots[i+1].equals(",")){
                return chaineNouA(i+1,attendNom,true);
            }
            else if(mots[i+1].equals("et")){
                //System.out.println(mots[i+1]+"Trying et... "+i+" "+l+" "+isAdj(i+2));
                if((i+2)<l && isAdj(i+2)){
                    System.out.println(mots[i+2]+"Trying adj..."+l+" "+(i+3));
                    if(attendNom){
                        if((i+3)<l && isNom(i+3)){
                            //System.out.println(mots[i+3]+"Trying nom... fji");
                            T.add(mots[i+2],"adj");
                            T.add(mots[i+3],"nom");
                            return i+3;
                        }
                    }
                    else{
                        T.add(mots[i+2],"adj");
                        return i+2;
                    }
                }
                return i;
            }
            if(!attendAdj && isNom(i+1)){
                //System.out.println(mots[i+1]+" Trying nom...");
                T.add(mots[i+1],"nom");
                if((i+2)<l && isAdj(i+2)){
                    ret=chaineNouA(i+2,false,attendAdj);
                    T.add(mots[i+2],"adj");
                    if(ret!=(i+2))
                        return ret;
                    else
                        return i+2;
                }
                return i+1;
            }
        }
        return i;
    }

    /**
    * @param int i : L'indice du mot à tester
    * @return boolean : Si le mot est un adjectif
    * @brief Renvoie si un mot d'indice i est un adjectif
    */
    boolean isAdj(int i){
        return arbreAdj.findTokem(mots[i])!=null;
    }

    /**
    * @param int i : L'indice du mot à tester
    * @return boolean : Si le mot est un nom
    * @brief Renvoie si un mot d'indice i est un nom
    */
    boolean isNom(int i){
        return (arbreNoms.findTokem(mots[i])!=null || arbreNomsP.findTokem(mots[i])!=null);
    }

    /**
    * @brief supprime la ponctuation en remplaçant les ',' par ' ,'
    */
    void ponctuation(){
        for(int i=0;i<phrases.length;i++){
            phrases[i]=phrases[i].replaceAll(","," ,");
            phrases[i]=U.supprPonctuationASP(phrases[i]);
        }
    }

    /**
    * @brief Analyse un texte pour y trouver certaines grammaires
    */
    public void analyse(){
        phrases=txt.split("\\.");
        ponctuation();
        //System.out.println("***ASP*** phrases0 : "+phrases[0]);
        for(String p : phrases){
            //System.out.println("***ASP*** p : "+p);
            mots=p.split(" ");
            for(int i=0;i<mots.length;i++){
                //System.out.println("mots : "+mots[i]);
                if(mots_cles.findTokem(mots[i])!=null)
                    i=chaineNouA(i,true,false);
            }
        }
        T.display();
        System.out.println("");
    }

    /**
    * @return ListeLemmWrapper : Renvoie le résultat de l'analyse
    * @brief Renvoie le résultat de l'analyse dans un ListeLemmWrapper
    */
    public ListeLemmWrapper getASP(){
        return T;
    }
}
