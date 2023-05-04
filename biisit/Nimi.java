package biisit;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author admin
 * @version 29 Mar 2022
 *
 */
public class Nimi {
    
    private int tunnusNro;
    private String nimi = "";
    private String artisti = "";
    
    private static int seuraavaNro = 1;
    
    /**
     * @return biisin nimi
     */
    public String getNimi() {
        return nimi + " | " + artisti;
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " " + nimi + " | " + artisti);
        
    }
    
    /**
     * Tulostetaan biisin nimi
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Antaa biisin nimelle seuraavan rekisterinumeron
     * @return biisin nimen uusi tunnusNro
     * @example
     * <pre name="test">
     *  Nimi ibe = new Nimi();
     *  ibe.getTunnusNro() === 0;
     *  ibe.rekisteroi();
     *  Nimi Melo = new Nimi();
     *  Melo.rekisteroi();
     *  int n1 = ibe.getTunnusNro();
     *  int n2 = Melo.getTunnusNro();
     *  n2 === n1+1;
     *  </pre>
     */
    public int rekisteroi() {
        this.tunnusNro = seuraavaNro;
        seuraavaNro++;
        return this.tunnusNro;
    }
    
    /**
     * Palauttaa biisin nimen tunnusnumeron
     * @return biisin nimen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot biisin nimelle.
     * TODO: poista kun kaikki toimii
     */
    public void taytaMazzaTiedoilla() {
        nimi = "Mazza"; 
        artisti = "slowthai & A$AP Rocky";
     
    }
    
    public void setNimi(String n) {
        this.nimi = n;
    }
    
    public void setArtisti(String n) {
        this.artisti = n;
    }
    
    /**
     * Palauttaa nimen tiedot merkkijonona jonka voi tallentaa hakemistoon
     * @return nimi tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *  Nimi nimi = new Nimi();
     *  nimi.parse(" 3 | Mazza | slowthai & A$AP Rocky");
     *  nimi.toString().startsWith("3|Mazza|slowthai & A$AP Rocky") === true;
     *  </pre>
     */
    @Override
    public String toString() {
        return "" + 
                getTunnusNro() + "|" +
                nimi + "|" + 
                artisti + "|";
    }
    
    @Override
    public boolean equals(Object nimi) {
        if ( nimi == null ) return false;
        return this.toString().equals(nimi.toString());
    }
    
    @Override
    public int hashCode() {
        return tunnusNro;
    }
    
    /**
     * Selvittää nimen tiedot | eroteltuna merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro
     * @param rivi josta nimen tiedot otetaan
     * @example
     * <pre name="test">
     * Nimi nimi = new Nimi();
     * nimi.parse(" 3 | Mazza | slowthai & A$AP Rocky");
     * nimi.getTunnusNro() === 3;
     * nimi.toString().startsWith("3|Mazza|slowthai & A$AP Rocky") === true;
     * 
     * nimi.rekisteroi();
     * int n = nimi.getTunnusNro();
     * nimi.parse(""+(n+20));
     * nimi.rekisteroi();
     * nimi.getTunnusNro() === n+20+1;
     * 
     * </pre>
     */
    public void parse(String rivi) {
        var sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        artisti = Mjonot.erota(sb, '|', artisti);
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Nimi Mazza = new Nimi();
        Nimi Mazza2 = new Nimi();
        
        Mazza.rekisteroi();
        Mazza2.rekisteroi();
          
        Mazza.taytaMazzaTiedoilla(); //VastaaAkuAnkka
        Mazza2.taytaMazzaTiedoilla();
        
        Mazza.tulosta(System.out);
        Mazza2.tulosta(System.out);
    }
}
