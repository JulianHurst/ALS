package tokemisation;

/**
* @author Kevin Garabedian
* @brief permet de stoquer chaque caractere, ainsi que son frere, et son fils
**/
public class Listechar {
	private boolean isInit = false;
	private char c;
	private Listechar fils;
	private Listechar frere;
	private String finalString = null;
	
	Listechar(){
		c = '.';
		fils = null;
		frere = null;
	}
	
	Listechar(char c){
		this.c = c;
		fils = null;
		frere = null;
	}
	
	Listechar(String s){
		this.c = '.';
		fils = null;
		frere = null;
		finalString = s;
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
	
	public String getFinal(){
		return finalString;
	}
}
