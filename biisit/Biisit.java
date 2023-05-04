package biisit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;

/**
 * @author admin
 * @version 9 Mar 2022
 * Yhdistää biisin nimen ja tiedot
 * Voi hakea biisiä tai jotain sen kriteeriä
 */
public class Biisit {
    
    private Nimet nimet = new Nimet();
    private Tiedot tiedot = new Tiedot();
    
    @SuppressWarnings("unused")
    private String hakemisto = "biisit";
    
    /**
     * Poistaa nimistä ja tiedoista ne joilla on nro. Kesken.
     * @param nimi viitenumero, jonka mukaan poistetaan
     * @return montako jäsentä poistettiin
     */
    public int poista(Nimi nimi) {
        if ( nimi == null ) return 0;
        int ret = nimet.poista(nimi.getTunnusNro());
        tiedot.poistaNimenTiedot(nimi.getTunnusNro());
        return ret;
    }
    
    public void poistaTieto(Tieto tieto) {
        tiedot.poista(tieto);
    }

        
    /**
     * Lisää biiseihin uuden nimen
     * @param nimi lisättävä nimi
     * @throws SailoException jos lisäystä ei voida tehdä
     * @example
     * <pre name="test">
     * #THROWS SailoException
     * Biisit biisit = new Biisit();
     * Nimi ibe = new Nimi(), melo = new Nimi();
     * ibe.rekisteroi(); melo.rekisteroi();
     * biisit.getNimia() === 0;
     * biisit.lisaa(ibe); biisit.getNimia() === 1;
     * biisit.lisaa(melo); biisit.getNimia() === 2; 
     * biisit.lisaa(ibe); biisit.getNimia() === 3;
     * biisit.getNimia() === 3;
     * biisit.annaNimi(0) === ibe;
     * biisit.annaNimi(1) === melo;
     * biisit.annaNimi(2) === ibe;
     * biisit.annaNimi(3) === ibe; #THROWS IndexOutOfBoundsException 
     * biisit.lisaa(ibe); biisit.getNimia() === 4;
     * biisit.lisaa(ibe); biisit.getNimia() === 5;
     * </pre>
     */
    public void lisaa(Nimi nimi) throws SailoException {
        nimet.lisaa(nimi);

    
    }
    
    /**
     * Lisätään uusi tieto biiseihin
     * @param tieto lisättävä tieto
     * @Throws SailoException jos tulee ongelmia
     */
    public void lisaa(Tieto tieto) {
        tiedot.lisaa(tieto);
    }
    
    /**
     * Palauttaa taulukossa hakuehtoon vastaavien nimien viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return tietorakenteen löytyneistä nimistä
     * @throws SailoException Jos jotakin menee väärin
     */
    public Collection<Nimi> etsi(String hakuehto, int k) throws SailoException {
        return nimet.etsi(hakuehto, k);
    }
   
    /**
     * Haetaan kaikki jäsen harrastukset
     * @param nimi nimi jolle tietoja haetaan
     * @return tietorakenne jossa viiteet löydettyihin tietoihin
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Biisit biisit = new Biisit();
     *  Nimi ibe = new Nimi(), melo = new Nimi(), flaco = new Nimi();
     *  ibe.rekisteroi(); melo.rekisteroi(); flaco.rekisteroi();
     *  int id1 = ibe.getTunnusNro();
     *  int id2 = melo.getTunnusNro();
     *  Tieto trap11 = new Tieto(id1); biisit.lisaa(trap11);
     *  Tieto trap12 = new Tieto(id1); biisit.lisaa(trap12);
     *  Tieto trap21 = new Tieto(id2); biisit.lisaa(trap21);
     *  Tieto trap22 = new Tieto(id2); biisit.lisaa(trap22);
     *  Tieto trap23 = new Tieto(id2); biisit.lisaa(trap23);
     *  
     *  List<Tieto> loytyneet;
     *  loytyneet = biisit.annaTiedot(flaco);
     *  loytyneet.size() === 0; 
     *  loytyneet = biisit.annaTiedot(ibe);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == trap11 === true;
     *  loytyneet.get(1) == trap12 === true;
     *  loytyneet = biisit.annaTiedot(melo);
     *  loytyneet.size() === 3; 
     *  loytyneet.get(0) == trap21 === true;
     * </pre> 
     */
    public List<Tieto> annaTiedot(Nimi nimi) {
        return tiedot.annaTiedot(nimi.getTunnusNro());
    }
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi + "/";
        nimet.setTiedostonPerusNimi(hakemistonNimi + "tiedot");
    }
    
    /**
     * @return nimien lukumäärä
     */
    public int getNimia() {
        return nimet.getLkm();
    }
    
    /**
     * Antaa biisien i:n nimen
     * @param i monesko nimi (alkaa 0:sta)
     * @return nimi paikasta i
     */
    public Nimi annaNimi(int i) {
        return nimet.anna(i);
    }
    
    /**
     * TODO: Testit
     * Lukee biisien tiedot tiedostosta
     * @param nimi jota käytetään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */    
    public void lueTiedostosta(String nimi) throws SailoException {
        File dir = new File(nimi);
        dir.mkdir();
        nimet = new Nimet();
        tiedot = new Tiedot();
        hakemisto = nimi;
        
        setTiedosto(nimi);
        nimet.lueTiedostosta();
        tiedot.lueTiedostosta();
    }
    
    /**
     * @throws SailoException -
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            nimet.tallenna();
        } catch (SailoException ex) {
            virhe = ex.getMessage();
        }
        
        try {
            tiedot.tallenna();
        } catch (SailoException ex) {
            virhe += ex.getMessage();
        }
        if ( ! "".equals(virhe)) throw new SailoException(virhe);
    }


    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Biisit biisit = new Biisit();
        
        try {
               
        Nimi ibe = new Nimi();
        Nimi Melo = new Nimi();
        
        ibe.rekisteroi();
        Melo.rekisteroi();  
        ibe.taytaMazzaTiedoilla(); //VastaaAkuAnkka
        Melo.taytaMazzaTiedoilla(); //VastaaAkuAnkka
        
        biisit.lisaa(ibe);
        biisit.lisaa(Melo);
        int id1 = ibe.getTunnusNro();
        int id2 = Melo.getTunnusNro();
        Tieto trap11 = new Tieto(id1);
        trap11.taytaTrapTiedoilla(id1);
        biisit.lisaa(trap11);
        Tieto trap21 = new Tieto(id1);
        trap21.taytaTrapTiedoilla(id1);
        biisit.lisaa(trap21);
        Tieto trap12 = new Tieto(id1);
        trap12.taytaTrapTiedoilla(id2);
        biisit.lisaa(trap12);
        Tieto trap22 = new Tieto(id1);
        trap22.taytaTrapTiedoilla(id2);
        biisit.lisaa(trap22);
        Tieto trap23 = new Tieto(id1);
        trap23.taytaTrapTiedoilla(id2);
        biisit.lisaa(trap23);
        
        System.out.println("============ Biisien testi ==============");
        Collection<Nimi> nimet = biisit.etsi("", -1);
        int i = 0;
        for (Nimi nimi : nimet) {
            System.out.println("Nimi paikassa: " + i);
            nimi.tulosta(System.out);
            List<Tieto> loytyneet = biisit.annaTiedot(nimi);
            for (Tieto tieto : loytyneet)
                tieto.tulosta(System.out);
            i++;
        }
          
        } catch ( SailoException ex ) {
            System.out.println(ex.getMessage());
        }
            
    }

}
