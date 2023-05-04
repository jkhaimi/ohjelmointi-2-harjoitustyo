 package biisit;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tieto joka osaa mm. itse huolehtia tunnusnumerostaan. 
 * @author admin
 * @version 22 Mar 2022
 */
public class Tieto {
    
    private int tunnusNro;
    private int nimiNro;
    private String genre;
    private String laatu;
    
    private static int seuraavaNro = 1;
    
    /**
     * Alustetaan tieto.
     */
    public Tieto() {
        //EI vielä
    }
    
    /**
     * Alustetaan tietyn biisin tieto
     * @param nimiNro nimen viitenumero
     */
    public Tieto(int nimiNro) {
        this.nimiNro = nimiNro;
    }
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot tiedolle.
     * @param nro viite biisiin jonka tiedosta on kyse
     */
    public void taytaTrapTiedoilla(int nro) {
        nimiNro = nro;
        genre = "trap";
        laatu = "Great";
    }
    
    /**
     * @param n n
     */
    public void setGenre(String n) {
        this.genre = n;
    }
    
    /**
     * @param n n
     */
    public void setLaatu(String n) {
        this.laatu = n;
    }
    
    /**
     * Tulostetaan tiedon tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(nimiNro + "  " + genre + " | " + laatu);
    }
    
    /**
     * Tulostetaan biisin tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    /**
     * Antaa biisin tiedolle seuraavan rekisterinumeron
     * @return biisin tiedon uusi tunnusNro
     * @example
     * <pre name="test">
     *   Tieto pitsi1 = new Tieto();
     *   pitsi1.getTunnusNro() === 0;
     *   pitsi1.rekisteroi();
     *   Tieto pitsi2 = new Tieto();
     *   pitsi2.rekisteroi();
     *   int n1 = pitsi1.getTunnusNro();
     *   int n2 = pitsi2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * Palautetaan tiedon oma id
     * @return tiedon id
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    /**
     * Palautetaan mille biisille kuuluu
     * @return biisin biisin id
     */
    public int getNimiNro() {
        return nimiNro;
    }
    
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
                
    /**
     * Palauttaa nimen tiedot merkkijonona jonka voi tallentaa hakemistoon
     * @return nimi tolppaeroteltuna merkkijonona
     * @example
     * <pre name="test">
     *  Tieto tieto = new Tieto();
     *  tieto.parse(" 3 | Mazza | slowthai & A$AP Rocky");
     *  tieto.toString().startsWith(" 3 | Mazza | slowthai & A$AP Rocky") === false;
     *  </pre>
     */
    @Override
    public String toString() {
        return "" + getTunnusNro() + "|" + nimiNro + "|" + genre + "|" + laatu;  
    }
    
    /**
     * Selvittää tiedon tiedot | eroteltuna merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro
     * @param rivi josta nimen tiedot otetaan
     * @example
     * <pre name="test">
     * Tieto tieto = new Tieto();
     * tieto.parse(" 3 | Mazza | slowthai & A$AP Rocky");
     * tieto.rekisteroi();
     * int n = tieto.getTunnusNro();
     * tieto.parse(""+(n+20));
     * tieto.rekisteroi();
     * tieto.getTunnusNro() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimiNro = Mjonot.erota(sb, '|', nimiNro);
        genre = Mjonot.erota(sb, '|', genre);
        laatu = Mjonot.erota(sb, '|', laatu);  
    }
    
    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) return false;
        return this.toString().equals(obj.toString());
    }
    
    @Override
    public int hashCode() {
        return tunnusNro;
    }
            
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
     Tieto tieto = new Tieto();
     tieto.taytaTrapTiedoilla(2);
     tieto.tulosta(System.out);
    }

    public void setNimiNro(int tunnusNro2) {
        
        this.nimiNro = tunnusNro2;
        
    }
}