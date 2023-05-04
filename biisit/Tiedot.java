package biisit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * @author admin
 * @version 9 Mar 2022
 * 
 * Tietää biisien nimet ja niiden artistit
 * Ei tiedä biisien tietoja
 */
public class Tiedot implements Iterable<Tieto> {
    
    private boolean muutettu = false;
    private String tiedostonPerusNimi = "tiedot";
    
    private final Collection<Tieto> alkiot = new ArrayList<Tieto>();
    
    /**
     * Luodaan alustava taulukko
     */
    public Tiedot() {
        //Ei tarvii tehdä mitään
    }
    
    /**
     * @param tieto lisättävä tieto
     */
    public void lisaa(Tieto tieto) {
        alkiot.add(tieto);
        muutettu = true;
    }
      
    /**
     * Lukee tiedot tiedostosta.
     * @param hakemisto tiedoston nimen alkuosa
     * @throws SailoException jos lukeminen epäonnistuu
     * 
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #import java.io.File;
     *  Tiedot tietoja = new Tiedot();
     *  Tieto pitsi21 = new Tieto(); pitsi21.taytaTrapTiedoilla(2);
     *  Tieto pitsi11 = new Tieto(); pitsi11.taytaTrapTiedoilla(1);
     *  Tieto pitsi22 = new Tieto(); pitsi22.taytaTrapTiedoilla(2); 
     *  Tieto pitsi12 = new Tieto(); pitsi12.taytaTrapTiedoilla(1); 
     *  Tieto pitsi23 = new Tieto(); pitsi23.taytaTrapTiedoilla(2); 
     *  String tiedNimi = "testikelmit";
     *  File ftied = new File(tiedNimi+".dat");
     *  ftied.delete();
     *  tietoja.lueTiedostosta(tiedNimi); #THROWS SailoException
     *  tietoja.lisaa(pitsi21);
     *  tietoja.lisaa(pitsi11);
     *  tietoja.lisaa(pitsi22);
     *  tietoja.lisaa(pitsi12);
     *  tietoja.lisaa(pitsi23);
     *  tietoja.tallenna();
     *  tietoja = new Tiedot();
     *  tietoja.lueTiedostosta(tiedNimi);
     *  Iterator<Tieto> i = tietoja.iterator();
     *  i.next().toString() === pitsi21.toString();
     *  i.next().toString() === pitsi11.toString();
     *  i.next().toString() === pitsi22.toString();
     *  i.next().toString() === pitsi12.toString();
     *  i.next().toString() === pitsi23.toString();
     *  i.hasNext() === false;
     *  tietoja.lisaa(pitsi23);
     *  tietoja.tallenna();
     *  ftied.delete() === true;
     *  File fbak = new File(tiedNimi+".bak");
     *  fbak.delete() === true;
     * </pre>
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        setTiedostonPerusNimi(hakemisto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            
            String rivi;
            while ( ( rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Tieto tieto = new Tieto();
                tieto.parse(rivi);
                lisaa(tieto);
            }
            muutettu = false;
            
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch (IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }

    }
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonPerusNimi());
    }

    
    /**
     * Tallentaa harrastukset tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete(); //  if ... System.err.println("Ei voi tuhota");
        ftied.renameTo(fbak); //  if ... System.err.println("Ei voi nimetä");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (Tieto tieto : this) {
                fo.println(tieto.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    }
    
    /**
     * Palauttaa kerhon harrastusten lukumäärän
     * @return harrastusten lukumäärä
     */
    public int getLkm() {
        return alkiot.size();
    }


    /**
     * Asettaa tiedoston perusnimen ilan tarkenninta
     * @param tied tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String tied) {
        tiedostonPerusNimi = tied;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }


    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonNimi() {
        return tiedostonPerusNimi + ".dat";
    }


    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }

    @Override
    public Iterator<Tieto> iterator() {
        return alkiot.iterator();
    }
    
    public boolean poista(Tieto tieto) {
        boolean ret = alkiot.remove(tieto);
        if (ret) muutettu = true;
        return ret;
    }
    
    /**
     * @param tunnusNro biisin numero
     * @return jotain
     */
    public int poistaNimenTiedot(int tunnusNro) {
        int n = 0;
        for (Iterator<Tieto> it = alkiot.iterator(); it.hasNext();) {
            Tieto tieto = it.next();
            if ( tieto.getNimiNro() == tunnusNro) {
                it.remove();
                n++;
            }
        }
        if (n > 0) muutettu = true;
        return n;
    }
    
    /**
     * Haetaan kaikki biisin tiedot
     * @param tunnusnro biisin tunnusnumero jolle tietoja haetaan
     * @return tietorakenne jossa viitteet löydettyihin tietoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * Tiedot tietoja = new Tiedot();
     * Tieto trap21 = new Tieto(2); tietoja.lisaa(trap21);
     * Tieto trap11 = new Tieto(1); tietoja.lisaa(trap11);
     * Tieto trap22 = new Tieto(2); tietoja.lisaa(trap22);
     * Tieto trap12 = new Tieto(1); tietoja.lisaa(trap12);
     * Tieto trap23 = new Tieto(2); tietoja.lisaa(trap23);
     * Tieto trap51 = new Tieto(5); tietoja.lisaa(trap51);
     * 
     * List<Tieto> loytyneet;
     * loytyneet = tietoja.annaTiedot(3);
     * loytyneet.size() === 0;
     * loytyneet = tietoja.annaTiedot(1);
     * loytyneet.size() === 2;
     * loytyneet.get(0) == trap11 === true;
     * loytyneet.get(1) == trap12 === true;
     * loytyneet = tietoja.annaTiedot(5);
     * loytyneet.size() === 1;
     * loytyneet.get(0) == trap51 === true;
     * </pre>
     */
    public List<Tieto> annaTiedot(int tunnusnro){
        List<Tieto> loydetyt = new ArrayList<Tieto>();
        for(Tieto tieto : alkiot)
            if(tieto.getNimiNro() == tunnusnro) loydetyt.add(tieto);
        return loydetyt;
    }

    
    /**
     * Testiohjelma tiedoille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Tiedot tietoja = new Tiedot();

        Tieto trap1 = new Tieto();
        trap1.taytaTrapTiedoilla(2);
        Tieto trap2 = new Tieto();
        trap2.taytaTrapTiedoilla(1);
        Tieto trap3 = new Tieto();
        trap3.taytaTrapTiedoilla(2);
        Tieto trap4 = new Tieto();
        trap4.taytaTrapTiedoilla(2);


        tietoja.lisaa(trap1);
        tietoja.lisaa(trap2);
        tietoja.lisaa(trap3);
        tietoja.lisaa(trap4);
        
        System.out.println("======= Tiedot testi ========");
        
        List<Tieto> tiedot2 = tietoja.annaTiedot(2);
        
        for (Tieto tieto : tiedot2) {
            System.out.println(tieto.getNimiNro() + " ");
            tieto.tulosta(System.out);
        }
        
    }
}