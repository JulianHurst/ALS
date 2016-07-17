class ASP{
    Tokemiseur mots_cles;
    ListeLemmWrapper T;
    String txt;
    String mots[];
    Tokemiseur arbreAdj;

    ASP(String txt){
        ai=new Tokemiseur("mots_cles.txt");
        T=new ListeLemmWrapper();
        this.txt=txt;
        mots=txt.split(" ");
    }

    int chaineNouA(int i,boolean attendNom){
        int ret=i;
        if(isAdj(i+1)){
            ret=chaineNouA(i+1,attendNom);
            if(ret!=-1){
                T.add(mots[i+1],"adj");
                return ret;
        }
        else if(mots[i+1].equals("et")){
            if(isAdj(i+2))
                if(attendNom)
                    if(isNom(i+3)){
                        T.add(mots[i+2],"adj");
                        T.add(mots[i+3],"nom");
                        return i+3;
                    }
                else{
                    T.add(mots[i+2],"adj");
                    return i+2;
                }
            return -1;
        }
        if(isNom(i+1)){
            T.add(mots[i+1],"nom");
            if(isAdj(i+2)){
                ret=chaineNouA(i+2,false);
                T.add(mots[i+2],"adj");
                if(ret!=-1)
                    return ret;
                else
                    return i+2;
            }
            return i+1;
        }
        return -1;
    }

    boolean isAdj(int i){
        return arbreAdj.findTokem(mots[i])!=null;
    }

    boolean isNom(int i){
        return arbreNom.findTokem(mots[i])!=null;
    }

    void analyse(){
        for(int i=0;i<mots.length;i++){
            if(mots_cles.findTokem(mots[i]))
                i=chaineNouA(i,true);
        }
    }
}
