 package fxBiisit;
import java.awt.Desktop;
import java.awt.Label;
import java.awt.TextField;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import biisit.Biisit;
import biisit.Nimi;
import biisit.SailoException;
import biisit.Tieto;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Lisätään pääsivulle tapahtumat
 * @author admin
 * @version 17 Mar 2022
 *
 */
public class PaasivuController implements Initializable {
    
    @FXML private TextField hakuehto;

    @FXML private Label labelVirhe;
    @FXML private ListChooser<Nimi> chooserNimet;
    @FXML private ScrollPane panelNimi;
    @FXML private StringGrid<Tieto> tableTiedot;
    
  
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();
    }

    @FXML private void handleHakuehto() {
        hae(0);
    }
    
    @FXML
    void handleTallenna() {
        
        tallenna();
    }
     
    
    @FXML private void handleLopeta() {
        tallenna();
        Platform.exit();
    } 
    
    @FXML
    void handleMuokkaaBiisi() {
        muokkaa();
    }
    
    @FXML
    void handleLisaaBiisi() {
        uusiBiisi();
          
    }
    
    @FXML
    void handleLisaaTiedot() {
        uusiTieto();
    }
    
    /**
     * @return ei mitää
     */
    public String getResult() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * gang
     */
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }

    /**
     * @param oletus oletus
     */
    public void setDefault(String oletus) {
        // TODO Auto-generated method stub
        
    }
        
    @FXML
    void handleSulje() {
        
        Platform.exit();
    
    }

    @FXML
    void handleHaeArtisti() {
        
        ModalController.showModal (AlkusivuController.class.getResource("HaeArtisti.fxml"), "Biisit", null, "");
    }

    @FXML
    void handleHaeBiisia() {

        ModalController.showModal (AlkusivuController.class.getResource("HaeBiisi.fxml"), "Biisit", null, "");
    }

    @FXML
    void handleHaeGenre() {
        
        ModalController.showModal (AlkusivuController.class.getResource("HaeGenre.fxml"), "Biisit", null, "");
    }

    @FXML
    void handleHaeLaatu() {

        ModalController.showModal (AlkusivuController.class.getResource("HaeLaatu.fxml"), "Biisit", null, "");
    }
    
    @FXML
    void handlePoistaBiisi() {
        
        poistaNimi();
    
    }    
        
    @FXML
    void handleAvaaTietoja() {
        
        ModalController.showModal (AlkusivuController.class.getResource("Tiedot.fxml"), "Biisit", null, "");
    }
    
    @FXML
    void handleAvaaApua() {
        
        Desktop desktop = Desktop.getDesktop();
        try {
            URI uri = new URI("https://tim.jyu.fi/view/kurssit/tie/ohj2/2022k/ht/jkhaimi");
            desktop.browse(uri);
        } catch (URISyntaxException e) {
            return;
        } catch (IOException e) {
            return;
        }

    }
    
    //================================================================================================
    
   private String biisinnimi = "nimi";
   private Biisit biisit;
   private Nimi nimiKohdalla;
   private static Nimi apunimi = new Nimi();
   private TextArea areaNimi = new TextArea(); //TODO: POISTA LOPUKSI
   
   /**
    * Tekee tarvittavat muut alustukset, nyt vaihdetaan GridPanen tilalle
    * yksi iso tekstikenttä, johon voidaan tulostaa nimien tiedot
    * Alustetaan myös nimilistan kuuntelija
    */
   private void alusta() {
        panelNimi.setContent(areaNimi);
        areaNimi.setFont(new Font("Courier New", 16));
        panelNimi.setFitToHeight(true);
        
        chooserNimet.clear();
        chooserNimet.addSelectionListener(e -> naytaNimi());
   }
   
   private void naytaVirhe(String virhe) {
       if ( virhe == null || virhe.isEmpty() ) {
           labelVirhe.setText("");

           return;
       }
       labelVirhe.setText(virhe);

   }
   
   private void uusiBiisi() {
       /*
       try {
           FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LisaaBiisi.fxml"));
           Parent root1 = (Parent) fxmlLoader.load();
           Stage stage = new Stage();
           stage.initModality(Modality.APPLICATION_MODAL);
           stage.setTitle("Lisää Biisi");
           stage.setScene(new Scene(root1));  
           stage.show();
           
           stage.onCloseRequestProperty().setValue(e -> stage.close());
       } catch(Exception e) {
           e.printStackTrace();
       }
       */
       
       LisaaBiisiController.avaa(null, nimiKohdalla , biisit);
       hae(0);

   }

   /*private void setTitle(String title) {
       ModalController.getStage(hakuehto).setTitle(title); 
   }
   
   /**
    * Alustaa biisit lukemalla sen valitun nimisestä tiedostosta
    * @param nimi tiedosto josta kerhon tiedot luetaan
    * @return null jos onnistuu, muuten virhe tekstinä
    */
   /**
 * @param nimi - 
 * @return - 
 */
protected String lueTiedosto(String nimi) {
       biisinnimi = "nimet";
   
       try {
           biisit.lueTiedostosta(nimi);
           hae(0);
           return null;
       } catch (SailoException e) {
           hae(0);
           String virhe = e.getMessage();
           if ( virhe != null) Dialogs.showMessageDialog(virhe);
           return virhe;
       }
   }
  
   
   /**
    * Kysytään tiedoston nimi ja luetaan se
    * @return true jos onnistui, false jos ei
    */
    /*public boolean avaa() {
       String uusinimi = AlkusivuController.kysyNimi(null, biisinnimi);
       if (uusinimi == null) return false;
       lueTiedosto(uusinimi);
       return true;
   } 
   
   /*
    * Tietojen tallennus
    */
   private String tallenna() {
       try {
           biisit.tallenna();
           return null;
       } catch (SailoException ex) {
           Dialogs.showMessageDialog("Tallennuksessa ongelmia! " + ex.getMessage());
           return ex.getMessage();
       }
   }
   
   /**
    * Tarkistetaan onko tallennus tehty
    * @return true jos saa sulkea sovelluksen, false jos ei
    */
   public boolean voikoSulkea() {
      tallenna();
      return true;
  }
 
   private void naytaNimi() {
       nimiKohdalla = chooserNimet.getSelectedObject();
       
       if(nimiKohdalla == null) {
           areaNimi.clear();
           return;
       }
       
       areaNimi.setText("");
       try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaNimi)) {
           tulosta(os, nimiKohdalla);
           
       }             
   }
   
   private void muokkaa() {
       Nimi nimiKohdalla = chooserNimet.getSelectedObject();
       ModalController.showModal (muokkaaBiisiController.class.getResource("MuokkaaBiisi.fxml"), "Biisit", null, nimiKohdalla);
   }
   
   /**
    * Hakee nimien tiedot listaan
    * @param Nnro biisin numero, joka aktivoidaan haun jälkeen
    */
   private void hae(int Nnro) {

       chooserNimet.clear();
       
       int index = 0;
       
       for(int i = 0; i < biisit.getNimia(); i++) {
           Nimi nimi = biisit.annaNimi(i);
           if(nimi.getTunnusNro() == Nnro) index = i;
               chooserNimet.add(""+nimi.getTunnusNro() + " " + nimi.getNimi(), nimi);
           }
       chooserNimet.setSelectedIndex(index); 
   }
   
   public void uusiNimi() {
       Nimi uusi = new Nimi();
       uusi.rekisteroi();
       try {
           biisit.lisaa(uusi);
       } catch (SailoException e) {
           Dialogs.showMessageDialog("Ongelmia luomisessa " + e.getMessage());
           return;
       }
       hae(uusi.getTunnusNro());
  }
   
   /**
    * Tekee uuden tyhjän tiedon editointia varten
    */
   public void uusiTieto() {
       if ( nimiKohdalla == null) return;
//       Tieto tieto = new Tieto();
//       tieto.rekisteroi();
//       tieto.setNimiNro(nimiKohdalla.getTunnusNro());
//       tieto.setGenre(cbGenre.getSelectedText());
//       tieto.setLaatu(cbLaatu.getSelectedText());
//       tieto.taytaTrapTiedoilla(nimiKohdalla.getTunnusNro());
//       biisit.lisaa(tieto); 
       hae(nimiKohdalla.getTunnusNro());
   }
   
//   private void uusiTieto() {
//       Tieto uusi = new Tieto();
//       uusi.rekisteroi();
//       uusi.setGenre(cbGenre.getSelectedText());
//       uusi.setLaatu(cbLaatu.getSelectedText());
//   }
   
   private void poistaTieto() {
       int rivi = tableTiedot.getRowNr();
       if ( rivi < 0 ) return;
       Tieto tieto = tableTiedot.getObject();
       if ( tieto == null ) return;
//       biisit.poistaTieto(tieto);
//       naytaTiedot(nimiKohdalla);
       int tietoja = tableTiedot.getItems().size();
       if ( rivi >= tietoja ) rivi = tietoja - 1;
       tableTiedot.getFocusModel().focus(rivi);
       tableTiedot.getSelectionModel().select(rivi);
   }
   
   private void poistaNimi() {
       Nimi nimi = nimiKohdalla;
       if ( nimi == null ) return;
       if ( !Dialogs.showQuestionDialog("Poisto", "Poistetaanko nimi: " + nimi.getNimi(), "Kyllä", "Ei"))
           return;
       biisit.poista(nimi);
       int index = chooserNimet.getSelectedIndex();
       hae(0);
       chooserNimet.setSelectedIndex(index);
   }
   
   
   /**
    * Asetetaan käytettävä biisi
    * @param biisit jota käytetään
    */
   public void setBiisit(Biisit biisit) {
       this.biisit = biisit;
       naytaNimi();
   }    
      
   private void tulosta(PrintStream os, final Nimi nimi) {
       os.println("------------------------------");
       nimi.tulosta(os);
       os.println("------------------------------");
       List<Tieto> tiedot = biisit.annaTiedot(nimi);
       for(Tieto tieto : tiedot)
           tieto.tulosta(os);
 }
   
   /**
    * Slatt
 * @param text tekstialue
 */
public void tulostaValitut(TextArea text) {
       try (PrintStream os = TextAreaOutputStream.getTextPrintStream(text)) {
           os.println("Tulostetaan kaikki nimet");
           Collection<Nimi> nimet = biisit.etsi("", -1);
           for (Nimi nimi : nimet) {
               tulosta(os, nimi);
               os.println("n\n");
           }
       } catch (SailoException ex) {
           Dialogs.showMessageDialog("Nimen hakemisessa ongelmia! " + ex.getMessage());
       }
   }
}
