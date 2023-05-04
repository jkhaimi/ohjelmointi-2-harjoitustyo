package biisit;

/**
 * @author admin
 * @version 9 Mar 2022
 * Poikkeusluokka tietorakenteesta aiheutuville poikkeuksille.
 */
public class SailoException extends Exception { 
   private static final long serialVersionUID = 1L;
   
   
/**
 * Poikkeuksen muodostoja jolle tuodaan poikkeuksessa käytettävä viesti 
 * @param viesti Poikkeuksen viesti
 */
public SailoException(String viesti) {
       super(viesti);
   }
}
