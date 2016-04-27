package tokemisation;

/**
* @author Kevin Garabedian
* @brief permet de stoquer chaque caractere, ainsi que son frere, et son fils
**/
public class Listechar {
	private boolean isInit = false;
        private char c;
        private int line;
	private Listechar fils;
	private Listechar frere;
	
	Listechar(){
		//d;
		fils = null;
		frere = null;
                line=-1;
	}
	
	Listechar(char c,int line){
		this.c = c;
                this.line=line;
		fils = null;
		frere = null;
	}
	
	public void setIsInit(boolean init){
		isInit = init;
	}
	public void setChar(char c){
		this.c = c;
	}
	public void setFils(Listechar fils){
		this.fils = fils;
	}
	public void setFrere(Listechar frere){
		this.frere = frere;
	}
        
        public int getLine(){
            return line;
        }
	
	public boolean getIsInit(){
		return isInit;
	}
	public char getChar(){
		return c;
	}
	public Listechar getFils(){
		return fils;
	}
	public Listechar getFrere(){
		return frere;
	}
}
