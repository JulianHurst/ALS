package ASP;

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

    boolean isAdj(int i){
        return arbreAdj.findTokem(mots[i])!=null;
    }

    boolean isNom(int i){
        return (arbreNoms.findTokem(mots[i])!=null || arbreNomsP.findTokem(mots[i])!=null);
    }

    void ponctuation(){
        for(int i=0;i<phrases.length;i++){
            phrases[i]=phrases[i].replaceAll(","," ,");
            phrases[i]=U.supprPonctuationASP(phrases[i]);
        }
    }

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

    public ListeLemmWrapper getASP(){
        return T;
    }
}
