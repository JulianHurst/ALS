package lemmatiseur;

import java.io.*;
import java.security.MessageDigest;
import java.lang.*;

public class Verbe {
	String conjugue;
	String infinitif;

	Verbe(String conj, String inf){
		conjugue = conj;
		infinitif = inf;
	}

	public String getConj(){
		return conjugue;
	}
	public String getInf(){
		return infinitif;
	}
}