/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package synonymes;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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


/**
 * @author juju
 * @version 1.0
 */
public class Synonymes {
    private final ArrayList<String> S;
    private String language;
    
    /**     
     * @param lang 
     * @brief Construit un objet Synonymes avec une langue spécifique
     */
    public Synonymes(String lang){
        this.language = lang;
        S=new ArrayList<>();
    }
    
    /**
     * @brief Construit un objet Synonymes avec la langue par défaut (fr_FR)
     */
    public Synonymes(){
        this("fr_FR");
    }
        
    /**     
     * @param mot 
     * @brief Retrouve les synonymes d'un mot grâce à l'API de thesaurus.altervista.org et les stocke dans S.
     */
    public void findSynonymes(String mot){         
        try {
            S.clear();
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
        } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException ex) {
            Logger.getLogger(Synonymes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**     
     * @param mot
     * @param lang 
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
}
