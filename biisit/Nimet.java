 package biisit;

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
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author admin
 * @version 9 Mar 2022
 * 
 * Tietää biisien nimet ja niiden artistit
 * Ei tiedä biisien tietoja
 */
public class Nimet implements Iterable<Nimi>{
    
    private static final int MAX_NIMIA = 5;
    private boolean muutettu = false;
    private int lkm = 0;
    private String kokoNimi = "";
    private String tiedostonPerusNimi = "nimet";
    private Nimi[] alkiot = new Nimi[MAX_NIMIA];
    
    /**
     * Luodaan alustava taulukko
     */
    public Nimet() {
      
    }
    
   /**
    * Lisää uuden nimen tietorakenteeseen. Ottaa nimen omistukseensa.
    * @param nimi lisättävän nimen viite. Tietorakenne muuttuu omistajaksi
    * @example
    * <pre name="test">
    * #THROWS SailoException
    * Nimet nimet = new Nimet();
    * Nimi ibe = new Nimi();
    * Nimi Melo = new Nimi();
    * nimet.getLkm() === 0;
    * nimet.lisaa(ibe); nimet.getLkm() === 1;
    * nimet.lisaa(Melo); nimet.getLkm() === 2;
    * nimet.lisaa(ibe); nimet.getLkm() === 3;
    * nimet.anna(0) === ibe;
    * nimet.anna(1) === Melo;
    * nimet.anna(2) === ibe;
    * nimet.anna(1) == ibe === false;
    * nimet.anna(1) == Melo === true;
    * nimet.anna(3) === ibe; #THROWS IndexOutOfBoundsException
    * nimet.lisaa(ibe); nimet.getLkm() === 4;
    * nimet.lisaa(ibe); nimet.getLkm() === 5;
    * </pre>
    */
    public void lisaa(Nimi nimi) {
        if(lkm >= alkiot.length) alkiot = Arrays.copyOf(alkiot, lkm+20);
        alkiot[lkm] = nimi;
        lkm++;
        muutettu = true;
    }
    
    /**
     * Palauttaa viitteen i:teen nimeen.
     * @param i monennenko nimen viite halutaan
     * @return viite nimeen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Nimi anna(int i) throws IndexOutOfBoundsException {
        if(i < 0 || this.lkm <= i) throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lukee jäsenistön tiedostosta. 
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta() throws SailoException {
        String nimi = "biisit" + "/nimet.dat";
        File ftied = new File(nimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(ftied))) {
      
            String s = fi.nextLine().trim();
            while (fi.hasNext()) {
                s = fi.nextLine().trim();
                if (s == null || s.equals("") || s.charAt(0) == ';') continue;
                Nimi nimi1 = new Nimi();
                nimi1.parse(s); 
                lisaa(nimi1);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Ei saa luettua" + nimi);
        }
    }

    /**
     * Tallentaa nimet tiedostoon.  
     * @throws SailoException x
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;
        
        File fbak = new File(getBakNimi());
        File ftied = new File(getTiedostonNimi());
        fbak.delete();
        ftied.renameTo(fbak);
        
        try ( PrintWriter fo = new PrintWriter (new FileWriter("biisit/nimet.dat")) ){
            fo.println("; Nimet");
            fo.println("; " + getLkm());
            for (Nimi nimi : alkiot) {
                
                if ( nimi !=null) fo.println(nimi.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch (IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
        muutettu = false;
    }
    
    /**
     * Palauttaa biisin koko nimen
     * @return nimien lukumäärä
     */
    public String getKokoNimi() {
        return kokoNimi;
    }
    
    /**
     * Palauttaa listan nimien lukumäärän
     * @return nimien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimi
     */
    public String getTiedostonPerusNimi() {
        return tiedostonPerusNimi;
    }
    
    /**
     * Asettaa tiedoston perusnimen ilman tarkenninta
     * @param nimi tallennustiedoston perusnimi
     */
    public void setTiedostonPerusNimi(String nimi) {
        tiedostonPerusNimi = nimi;
    }
    
    /**
     * Palauttaa tiedoston nimen, jota käytetään tallennukseen
     * @return tallennustiedoston nimen
     */
    public String getTiedostonNimi() {
        return getTiedostonPerusNimi() + ".dat";
    }
    
    /**
     * Palauttaa varakopiotiedoston nimen
     * @return varakopiotiedoston nimi
     */
    public String getBakNimi() {
        return tiedostonPerusNimi + ".bak";
    }
    
    public int poista(int id) {
        int ind = etsiId(id);
        if (ind < 0) return 0;
        lkm--;
        for (int i = ind; i < lkm; i++)
            alkiot[i] = alkiot[i + 1];
        alkiot[lkm] = null;
        muutettu = true;
        return 1;
    }
    
    public Nimi annaId(int id) {
        for (Nimi nimi : this) {
            if (id == nimi.getTunnusNro()) return nimi;
        }
        return null;
    }
    
    public int etsiId(int id) {
        for (int i = 0; i < lkm; i++)
            if (id == alkiot[i].getTunnusNro()) return i;
        return -1;
    }
    
    /** 
     * Palauttaa "taulukossa" hakuehtoon vastaavien jäsenten viitteet 
     * @param hakuehto hakuehto 
     * @param k etsittävän kentän indeksi  
     * @return tietorakenteen löytyneistä jäsenistä 
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Nimet nimet = new Nimet();
     * Nimi nimi1 = new Nimi(); nimi1.parse("1 | Mazza | slowthai & A$AP Rocky |");
     * Nimi nimi2 = new Nimi(); nimi1.parse("2 | Mazza | slowthai & A$AP Rocky |");
     * Nimi nimi3 = new Nimi(); nimi1.parse("3 | Mazza | slowthai & A$AP Rocky |");
     * Nimi nimi4 = new Nimi(); nimi1.parse("4 | Mazza | slowthai & A$AP Rocky |");
     * Nimi nimi5 = new Nimi(); nimi1.parse("5 | Mazza | slowthai & A$AP Rocky |");
     * nimet.lisaa(nimi1);
     * nimet.lisaa(nimi2);
     * nimet.lisaa(nimi3);
     * nimet.lisaa(nimi4);
     * nimet.lisaa(nimi5);
     * </pre>
     */ 
    @SuppressWarnings("unused")
    public Collection<Nimi> etsi(String hakuehto, int k) {
        Collection<Nimi> loytyneet = new ArrayList<Nimi>();
        for ( Nimi nimi : alkiot) {
            loytyneet.add(nimi);
        }
        return loytyneet;
    }
    

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Nimet nimet = new Nimet();
               
        Nimi ibe = new Nimi();
        Nimi Melo = new Nimi();
        
        ibe.rekisteroi();
        Melo.rekisteroi();  
        ibe.taytaMazzaTiedoilla(); //VastaaAkuAnkka
        Melo.taytaMazzaTiedoilla(); //VastaaAkuAnkka
        
        nimet.lisaa(ibe);
        nimet.lisaa(Melo);     

      System.out.println("======= Nimet testi ========");
     
      for(int i = 0; i < nimet.getLkm(); i++) {
        Nimi nimi = nimet.anna(i);
        System.out.println("Nimen nro: " + i);
        nimi.tulosta(System.out);
      }
       
    }

    @Override
    public Iterator<Nimi> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

}
