package tokemisation;

/**
* @author Kevin Garabedian
* @brief permet de stoquer chaque caractere, ainsi que son frere, et son fils
**/
public class Listechar {
	private boolean isInit = false;
	private String c;
	private Listechar fils;
	private Listechar frere;
	
	Listechar(){
		c = null;
		fils = null;
		frere = null;
	}
	
	Listechar(String c){
		this.c = c;
		fils = null;
		frere = null;
	}
	
	public void setIsInit(boolean init){
		isInit = init;
	}
	public void setChar(String c){
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
	public String getChar(){
		return c;
	}
	public Listechar getFils(){
		return fils;
	}
	public Listechar getFrere(){
		return frere;
	}
}