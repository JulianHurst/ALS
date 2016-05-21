/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package synonymes;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.List;
import tokemisation.Tokemiseur;
import java.lang.Math;

/**
 * @author Julian Hurst 
 * @brief Permet de classifier un mot, selon ses synonymes, dans la Série A ou la Série B.
 */
public class Synonymes {
    private final ArrayList<String> S;
    private String mot;
    private String language;
    private Tokemiseur arbreAdj;
    
    /**     
     * @param lang 
     * @brief Construit un objet Synonymes avec une langue spécifique
     */
    public Synonymes(String lang){
        this.language = lang;
        S=new ArrayList<>();
        arbreAdj=new Tokemiseur();
    }
    
    /**
     * @brief Construit un objet Synonymes avec la langue par défaut (fr_FR)
     */
    public Synonymes(){
        this("fr_FR");
    }
        
    /**     
     * @param mot 
     * @brief Retrouve les synonymes d'un mot grâce à l'API de thesaurus.altervista.org et les stocke dans la liste de synonymes S.
     */
    public boolean findSynonymes(String mot){
        if(arbreAdj.findTokem(mot)==null)
            return false; 
        try {
            S.clear();
            setMot(mot);
            URL url;     
            //Le format de sortie
            final String output="xml";
            //La clé de l'API
            final String key="RXSY8VaKMzJdzVXUeaCr";
            //Le préfixe du site
            final String site="http://thesaurus.altervista.org/thesaurus/v1";
            url = new URL(site+"?word="+mot+"&language="+language+"&output="+output+"&key="+key);
            
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();            
            HttpURLConnection conn = (HttpURLConnection)url.openConnection(); 
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            conn.setRequestMethod("GET");
            Document doc;
            XPath p;
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"))) {
                StringBuilder res=new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    res.append(line);
                }   doc = builder.parse(new ByteArrayInputStream(res.toString().getBytes("UTF-8")));
                p = XPathFactory.newInstance().newXPath();          
            }
            NodeList syn = (NodeList)p.compile("/*/*/synonyms").evaluate(doc,XPathConstants.NODESET);
            String[] s;
            for(int i=0;i<syn.getLength();i++){
                s=syn.item(i).getTextContent().split("\\|");
                //Les œ sont mal encodés et deviennent des ½ donc on les remplace
                for(String j : s){                                          
                    j=j.replace('½', 'œ');                    
                    S.add(j);
                }                          
            }            
        } catch (MalformedURLException ex) {
            Logger.getLogger(Synonymes.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException ex) {            
            System.out.println("Aucun synonyme n'a été trouvé pour "+mot+"\n");
            return false;
        }
        return true;
    }
    
    /**
     * @param Tokemiseur SerieA
     * @param Tokemiseur SerieB
     * @brief Détermine selon les synonymes trouvés si le mot d'origine appartient à la série A ou série B puis l'écrit dans un fichier de mise à jour serieA_maj.txt ou serieB_maj.txt
     */
    public void classification(Tokemiseur SerieA, Tokemiseur SerieB){
        try {
            int A,B,sum;
            float percent;
            boolean serie;
            A=B=0;
                                            
            if(SerieA.findTokem(mot)!=null || SerieB.findTokem(mot)!=null)            
				return;	
            if(dejaClassifie(mot))
                return;
			if(S.isEmpty())
				return;
            for(String i : S){
				//Si on trouve le mot dans serie A
				//System.out.println("syn : "+i);
                if(SerieA.findTokem(i)!=null){
                    A++;                      
                    System.out.println(i+"->serieA");
				}              
                //Si on trouve le mot dans serie B
                else if(SerieB.findTokem(i)!=null){
                    B++;                           
                    System.out.println(i+"->serieB");
				}                       
            }                                  
            sum = B-A;            
            if(sum<0)
				serie=true;
			else
				serie=false;
            sum=Math.abs(sum);            
            percent = (float)sum/(A+B);
            System.out.println("Pourcentage de différences : "+(int)(percent*100));                                 
            File file;
            if(percent>0.20){
				if(serie){
					System.out.println(mot+" appartient à la série A\n");
					file = new File("res/serieA_maj.txt");
				}
				else{
					System.out.println(mot+" appartient à la série B\n");
					file = new File("res/serieB_maj.txt");					
				}		
				                                 			
			    if(!file.exists())
				    file.createNewFile();
			    FileWriter writer = new FileWriter(file,true);
			    writer.write(mot+"\n");
			    writer.flush();
			    writer.close();
            }
			else
				System.out.println(mot+" est neutre\n");			  
		} catch (IOException ex) {
			Logger.getLogger(Synonymes.class.getName()).log(Level.SEVERE, null, ex);
		}
    }

	/**
	 * @param mot
	 * @return true si le mot est classifié, false sinon
	 * @brief Renvoie si le mot a déjà été classifié grâce aux synonymes ou non
	 */
    public boolean dejaClassifie(String mot){
        File f_A = new File("res/serieA_maj.txt");
        File f_B = new File("res/serieB_maj.txt");            
        Tokemiseur A_maj=null;            
        Tokemiseur B_maj=null;
        if(f_A.exists())
            A_maj = new Tokemiseur("res/serieA_maj.txt");
        if(f_B.exists())
            B_maj = new Tokemiseur("res/serieB_maj.txt");
    	if(A_maj!=null && A_maj.findTokem(mot)!=null)
			return true;
		if(B_maj!=null && B_maj.findTokem(mot)!=null)
			return true;
        return false;
    }

	/**
	 * @param mot
	 * @param Tokemiseur SerieA
	 * @param Tokemiseur SerieB
	 * @brief Détermine si un mot appartient à la série A ou à la série B
	 */
    public void classifierMot(String mot,Tokemiseur SerieA, Tokemiseur SerieB){
        if(SerieA.findTokem(mot)==null && SerieB.findTokem(mot)==null && !dejaClassifie(mot))
            if(findSynonymes(mot))
                classification(SerieA,SerieB);
    }
	
	/**
	 * @param List<String> L
	 * @param Tokemiseur A
	 * @param Tokemiseur B
	 * @brief Permet de déterminer à quelles séries appartiennent tous les mots d'une liste
	 */
    public void classifierListe(List<String> L,Tokemiseur A,Tokemiseur B){
        for(String i : L)
            classifierMot(i,A,B);   
    }
    
    /**
	 * @param String[] T
	 * @param Tokemiseur A
	 * @param Tokemiseur B
	 * @brief Permet de déterminer à quelles séries appartiennent tous les mots d'une liste
	 */
    public void classifierTableau(String[] T,Tokemiseur A,Tokemiseur B){
		for(String i : T)
			classifierMot(i,A,B);
	}
    
    /**     
     * @param mot Le mot pour lequel trouver les synonymes
     * @param lang La langue à utiliser
     * @brief La même chose que findSynonymes(String mot) mais avec une option de langue
     */
    public void findSynonymes(String mot,String lang){
        setLang(lang);
        findSynonymes(mot);
    }
    
    /**     
     * @return S
     * @brief Renvoie la liste de Synonymes
     */
    public ArrayList<String> getSynonymes(){
        return S;
    }
    
    /**
     * @param mot
     * @brief Règle le mot d'origine
     */
    private void setMot(String mot){
        this.mot=mot;
    }
    
    /**
     * @return String
     * @brief Retourne le mot d'origine pour lequel les synonymes ont été trouvés
     */
    public String getMot(){
        return mot;
    }
    
    /**     
     * @param lang 
     * @brief Permet de régler la langue
     */
    public void setLang(String lang){
        this.language=lang;
    }
    
    /**     
     * @return 
     * @brief Renvoie la langue actuelle
     */
    public String getLang(){
        return language;
    }

	/**
	 * @param arbreAdj
	 * @brief Permet de règler l'arbre d'adjectifs
	 */
    public void setAdj(Tokemiseur arbreAdj){
        this.arbreAdj=arbreAdj;
    }

	/**
	 * @return L'arbre d'adjectifs
	 * @brief Renvoie l'abre des adjectifs
	 */
    public Tokemiseur getAdj(){
        return arbreAdj;
    }
}
